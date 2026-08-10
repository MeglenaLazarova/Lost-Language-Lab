package bg.lostlanguagelab.searchservice.scheduler;

import bg.lostlanguagelab.searchservice.repository.SearchRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchScheduler {

    private final SearchRecordRepository repository;

    @Scheduled(cron = "0 0 0 * * SUN")
    public void resetWeeklyStats() {
        log.info("Weekly reset of search statistics started.");
        repository.deleteAll();
        log.info("Weekly reset completed. All search records removed.");
    }

    @Scheduled(fixedRate = 30000)
    public void logSearchCount() {
        long count = repository.count();
        log.info("Current number of search records: {}", count);
    }

}
