package bg.lostlanguagelab.searchservice.scheduler;

import bg.lostlanguagelab.searchservice.repository.SearchRecordRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchSchedulerTest {

    @Mock
    private SearchRecordRepository repository;

    @InjectMocks
    private SearchScheduler scheduler;

    @Test
    void testResetWeeklyStats() {
        scheduler.resetWeeklyStats();
        verify(repository, times(1)).deleteAll();
    }

    @Test
    void testLogSearchCount() {
        when(repository.count()).thenReturn(5L);

        scheduler.logSearchCount();

        verify(repository, times(1)).count();
    }
}

