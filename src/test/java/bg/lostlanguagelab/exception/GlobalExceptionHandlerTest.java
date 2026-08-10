package bg.lostlanguagelab.exception;

import bg.lostlanguagelab.controller.ArchaicWordController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ArchaicWordController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private bg.lostlanguagelab.archaicWord.service.ArchaicWordService archaicWordService;

    @MockBean
    private bg.lostlanguagelab.comment.service.CommentService commentService;

    @Test
    void testHandleCustomException() throws Exception {

        when(archaicWordService.getById(any()))
                .thenThrow(new WordAlreadyExistsException("Думата вече съществува"));

        mockMvc.perform(get("/words/" + UUID.randomUUID())
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    void testHandleBuiltInException() throws Exception {

        when(archaicWordService.getById(any()))
                .thenThrow(new IllegalArgumentException("Невалиден аргумент"));

        mockMvc.perform(get("/words/" + java.util.UUID.randomUUID())
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    void testHandleGenericException() throws Exception {

        when(archaicWordService.getById(any()))
                .thenThrow(new RuntimeException("Грешка"));

        mockMvc.perform(get("/words/" + java.util.UUID.randomUUID())
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    void testHandleNoSuchElementException() throws Exception {

        when(archaicWordService.getById(any()))
                .thenThrow(new NoSuchElementException("not found"));

        mockMvc.perform(get("/words/" + UUID.randomUUID())
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("message", "Ресурсът не беше намерен."));
    }

    @Test
    void testHandleGeneralException() throws Exception {

        when(archaicWordService.getById(any()))
                .thenThrow(new RuntimeException("boom"));

        when(commentService.getCommentsForWord(any()))
                .thenReturn(Collections.emptyList());   // важно!

        mockMvc.perform(get("/words/" + UUID.randomUUID())
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("message", "boom"));
    }



}
