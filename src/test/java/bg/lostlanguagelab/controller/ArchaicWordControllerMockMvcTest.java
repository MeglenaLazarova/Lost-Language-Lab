package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.archaicWord.entity.ArchaicWord;
import bg.lostlanguagelab.archaicWord.service.ArchaicWordService;
import bg.lostlanguagelab.category.entity.Category;
import bg.lostlanguagelab.category.enums.CategoryType;
import bg.lostlanguagelab.comment.service.CommentService;
import bg.lostlanguagelab.model.dto.ArchaicWordDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArchaicWordController.class)
class ArchaicWordControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArchaicWordService archaicWordService;

    @MockBean
    private CommentService commentService;

    @Test
    void testShowAddWordForm() throws Exception {
        mockMvc.perform(get("/words/new")
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("word-form"))
                .andExpect(model().attributeExists("wordDTO"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attribute("formAction", "/words/new"))
                .andExpect(model().attribute("formTitle", "Добави архаична дума"))
                .andExpect(model().attribute("submitLabel", "Добави"));
    }

    @Test
    void testAddWordSuccess() throws Exception {

        mockMvc.perform(post("/words/new")
                        .with(user("test").roles("USER"))
                        .param("word", "дума")
                        .param("meaning", "значение")
                        .param("etymology", "етимология")
                        .param("exampleUsage", "пример")
                        .param("category", CategoryType.FOLKLORE.name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/words"));

        verify(archaicWordService, times(1)).create(any(ArchaicWordDto.class));
    }

    @Test
    void testAddWordValidationFail() throws Exception {

        mockMvc.perform(post("/words/new")
                        .with(user("test").roles("USER"))
                        .param("word", "")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("word-form"))
                .andExpect(model().attributeExists("wordDTO"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attribute("formAction", "/words/new"))
                .andExpect(model().attribute("formTitle", "Добави архаична дума"))
                .andExpect(model().attribute("submitLabel", "Добави"));

        verify(archaicWordService, never()).create(any());
    }

    @Test
    void testDeleteWordAsAdmin() throws Exception {

        UUID id = UUID.randomUUID();

        mockMvc.perform(post("/words/" + id + "/delete")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/words"));

        verify(archaicWordService, times(1)).deleteById(id);
    }

    @Test
    void testDeleteWordForbiddenForUser() throws Exception {

        UUID id = UUID.randomUUID();

        mockMvc.perform(post("/words/" + id + "/delete")
                        .with(user("user").roles("USER")))
                .andExpect(status().isOk());

        verify(archaicWordService, never()).deleteById(any());
    }

    @Test
    void testShowWords() throws Exception {

        when(archaicWordService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/words")
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("words-list"))
                .andExpect(model().attributeExists("words"));
    }

    @Test
    void testShowWordDetails() throws Exception {

        UUID id = UUID.randomUUID();

        ArchaicWord word = new ArchaicWord();
        word.setId(id);
        word.setWord("дума");
        word.setMeaning("значение");
        word.setEtymology("етимология");
        word.setExampleUsage("пример");

        Category c = new Category();
        c.setType(CategoryType.FOLKLORE);
        word.setCategory(c.getType());

        when(archaicWordService.getById(id)).thenReturn(word);
        when(commentService.getCommentsForWord(id)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/words/" + id)
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("word-details"))
                .andExpect(model().attributeExists("word"))
                .andExpect(model().attributeExists("comments"))
                .andExpect(model().attributeExists("commentDto"));
    }


    @Test
    void testShowEditWordForm() throws Exception {

        UUID id = UUID.randomUUID();

        ArchaicWord word = new ArchaicWord();
        word.setId(id);
        word.setWord("дума");

        when(archaicWordService.getById(id)).thenReturn(word);

        mockMvc.perform(get("/words/" + id + "/edit")
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-word"))
                .andExpect(model().attributeExists("wordDTO"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attribute("formAction", "/words/" + id + "/edit"))
                .andExpect(model().attribute("formTitle", "Редактиране на архаична дума"))
                .andExpect(model().attribute("submitLabel", "Запази промените"));
    }

    @Test
    void testEditWordSuccess() throws Exception {

        UUID id = UUID.randomUUID();

        mockMvc.perform(post("/words/" + id + "/edit")
                        .with(user("test").roles("USER"))
                        .param("word", "нова дума")
                        .param("meaning", "значение")
                        .param("etymology", "етимология")
                        .param("exampleUsage", "пример")
                        .param("category", CategoryType.FOLKLORE.name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/words/" + id));

        verify(archaicWordService, times(1)).update(eq(id), any(ArchaicWordDto.class));
    }

    @Test
    void testEditWordValidationFail() throws Exception {

        UUID id = UUID.randomUUID();

        mockMvc.perform(post("/words/" + id + "/edit")
                        .with(user("test").roles("USER"))
                        .param("word", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-word"))
                .andExpect(model().attributeExists("wordDTO"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attribute("formAction", "/words/" + id + "/edit"))
                .andExpect(model().attribute("formTitle", "Редактиране на архаична дума"))
                .andExpect(model().attribute("submitLabel", "Запази промените"));

        verify(archaicWordService, never()).update(any(), any());
    }
}

