package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.client.SearchServiceClient;
import bg.lostlanguagelab.model.dto.SearchRecordDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WordSearchController.class)
class WordSearchControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchServiceClient searchServiceClient;

    @Test
    void testSearchWordRedirects() throws Exception {
        mockMvc.perform(post("/search")
                        .param("word", "testword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/search/top"));

        verify(searchServiceClient).saveWord("testword");
    }

    @Test
    void testShowTopReturnsView() throws Exception {
        List<SearchRecordDto> topWords = List.of(
                new SearchRecordDto(1L, "w1", 100L, 5L),
                new SearchRecordDto(2L, "w2", 200L, 3L)
        );

        when(searchServiceClient.getTop3Words()).thenReturn(topWords);

        mockMvc.perform(get("/search/top"))
                .andExpect(status().isOk())
                .andExpect(view().name("top-words"))
                .andExpect(model().attributeExists("topWords"));

        verify(searchServiceClient).getTop3Words();
    }

    @Test
    void testSearchPageReturnsView() throws Exception {
        mockMvc.perform(get("/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("search"));
    }
}
