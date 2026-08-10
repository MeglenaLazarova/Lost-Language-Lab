package bg.lostlanguagelab.searchservice.service;

import bg.lostlanguagelab.searchservice.entity.SearchRecord;
import bg.lostlanguagelab.searchservice.repository.SearchRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private SearchRecordRepository repository;

    @InjectMocks
    private SearchService service;

    @Test
    void testSaveWordCreatesRecordCorrectly() {
        String word = "test";

        ArgumentCaptor<SearchRecord> captor = ArgumentCaptor.forClass(SearchRecord.class);

        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        SearchRecord saved = service.saveWord(word);

        verify(repository).save(captor.capture());
        SearchRecord captured = captor.getValue();

        assertEquals("test", captured.getWord());
        assertNotNull(captured.getTime());
        assertEquals("test", saved.getWord());
    }

    @Test
    void testGetAllWordsReturnsList() {
        when(repository.findAll()).thenReturn(List.of(
                SearchRecord.builder().word("a").build(),
                SearchRecord.builder().word("b").build()
        ));

        List<SearchRecord> result = service.getAllWords();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void testGetTop3WordsCountsAndSortsCorrectly() {

        List<SearchRecord> mockData = List.of(
                SearchRecord.builder().word("apple").build(),
                SearchRecord.builder().word("banana").build(),
                SearchRecord.builder().word("apple").build(),
                SearchRecord.builder().word("cherry").build(),
                SearchRecord.builder().word("banana").build(),
                SearchRecord.builder().word("banana").build()
        );

        when(repository.findAll()).thenReturn(mockData);

        List<SearchRecord> top3 = service.getTop3Words();

        assertEquals(3, top3.size());

        assertEquals("banana", top3.get(0).getWord());
        assertEquals(3, top3.get(0).getCount());

        assertEquals("apple", top3.get(1).getWord());
        assertEquals(2, top3.get(1).getCount());

        assertEquals("cherry", top3.get(2).getWord());
        assertEquals(1, top3.get(2).getCount());
    }
}

