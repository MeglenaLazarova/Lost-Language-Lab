package bg.lostlanguagelab.searchservice.controller;

import bg.lostlanguagelab.searchservice.entity.SearchRecord;
import bg.lostlanguagelab.searchservice.service.SearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(SearchController.class)
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService searchService;

    @Test
    void testSaveWord() throws Exception {

        SearchRecord saved = SearchRecord.builder()
                .word("test")
                .time(123456L)
                .build();

        when(searchService.saveWord("test")).thenReturn(saved);

        mockMvc.perform(post("/api/search")
                        .param("word", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value("test"));

        verify(searchService).saveWord("test");
    }

    @Test
    void testGetAllWords() throws Exception {

        List<SearchRecord> mockList = List.of(
                SearchRecord.builder().word("a").time(1L).build(),
                SearchRecord.builder().word("b").time(2L).build()
        );

        when(searchService.getAllWords()).thenReturn(mockList);

        mockMvc.perform(get("/api/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(searchService).getAllWords();
    }

    @Test
    void testGetTop3Words() throws Exception {

        List<SearchRecord> mockTop = List.of(
                SearchRecord.builder().word("banana").count(3L).build(),
                SearchRecord.builder().word("apple").count(2L).build(),
                SearchRecord.builder().word("cherry").count(1L).build()
        );

        when(searchService.getTop3Words()).thenReturn(mockTop);

        mockMvc.perform(get("/api/search/top"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].word").value("banana"))
                .andExpect(jsonPath("$[0].count").value(3));

        verify(searchService).getTop3Words();
    }
}

