package bg.lostlanguagelab.controller.api;

import bg.lostlanguagelab.archaicWord.entity.ArchaicWord;
import bg.lostlanguagelab.archaicWord.service.ArchaicWordService;
import bg.lostlanguagelab.category.enums.CategoryType;
import bg.lostlanguagelab.model.dto.ArchaicWordDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ArchaicWordRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class ArchaicWordControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArchaicWordService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllWords() throws Exception {
        ArchaicWord w1 = new ArchaicWord();
        w1.setId(UUID.randomUUID());
        w1.setWord("дума1");

        ArchaicWord w2 = new ArchaicWord();
        w2.setId(UUID.randomUUID());
        w2.setWord("дума2");

        when(service.getAll()).thenReturn(List.of(w1, w2));

        mockMvc.perform(get("/api/archaic-words"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetWordById() throws Exception {
        UUID id = UUID.randomUUID();

        ArchaicWord word = new ArchaicWord();
        word.setId(id);
        word.setWord("дума");

        when(service.getById(id)).thenReturn(word);

        mockMvc.perform(get("/api/archaic-words/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value("дума"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateWord() throws Exception {
        ArchaicWordDto dto = ArchaicWordDto.builder()
                .word("нова")
                .meaning("значение")
                .category(CategoryType.MEDIEVAL)
                .build();

        mockMvc.perform(post("/api/archaic-words")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(service).create(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateWord() throws Exception {
        UUID id = UUID.randomUUID();

        ArchaicWordDto dto = ArchaicWordDto.builder()
                .word("обновена")
                .meaning("ново значение")
                .category(CategoryType.MEDIEVAL)
                .build();

        ArchaicWord updated = new ArchaicWord();
        updated.setId(id);
        updated.setWord("обновена");

        when(service.update(eq(id), any())).thenReturn(updated);

        mockMvc.perform(put("/api/archaic-words/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value("обновена"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteWord() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/archaic-words/" + id))
                .andExpect(status().isNoContent());

        verify(service).deleteById(id);
    }

}

