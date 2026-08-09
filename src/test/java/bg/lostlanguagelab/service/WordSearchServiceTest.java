package bg.lostlanguagelab.service;

import bg.lostlanguagelab.client.SearchServiceClient;
import bg.lostlanguagelab.model.dto.SearchRecordDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class WordSearchServiceTest {

    @Test
    void testSaveWord() {
        SearchServiceClient client = mock(SearchServiceClient.class);
        WordSearchService service = new WordSearchService(client);

        service.saveWord("test");

        verify(client, times(1)).saveWord("test");
    }

    @Test
    void testGetAllWords() {
        SearchServiceClient client = mock(SearchServiceClient.class);
        WordSearchService service = new WordSearchService(client);

        SearchRecordDto dto1 = new SearchRecordDto(1L, "word1", 100L, 5L);
        SearchRecordDto dto2 = new SearchRecordDto(2L, "word2", 200L, 3L);

        when(client.getAllWords()).thenReturn(List.of(dto1, dto2));

        List<SearchRecordDto> result = service.getAllWords();

        assertEquals(2, result.size());
        assertEquals("word1", result.get(0).getWord());
        assertEquals("word2", result.get(1).getWord());
    }

    @Test
    void testGetTop3Words() {
        SearchServiceClient client = mock(SearchServiceClient.class);
        WordSearchService service = new WordSearchService(client);

        SearchRecordDto dto1 = new SearchRecordDto(1L, "top1", 100L, 10L);
        SearchRecordDto dto2 = new SearchRecordDto(2L, "top2", 200L, 8L);
        SearchRecordDto dto3 = new SearchRecordDto(3L, "top3", 300L, 6L);

        when(client.getTop3Words()).thenReturn(List.of(dto1, dto2, dto3));

        List<SearchRecordDto> result = service.getTop3Words();

        assertEquals(3, result.size());
        assertEquals("top1", result.get(0).getWord());
        assertEquals("top2", result.get(1).getWord());
        assertEquals("top3", result.get(2).getWord());
    }
}
