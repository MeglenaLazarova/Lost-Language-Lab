package bg.lostlanguagelab.searchservice.service;

import bg.lostlanguagelab.searchservice.entity.SearchRecord;
import bg.lostlanguagelab.searchservice.exception.SearchRecordNotFoundException;
import bg.lostlanguagelab.searchservice.repository.SearchRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final SearchRecordRepository repository;

    public SearchRecord saveWord(String word) {
        log.info("Saving word: {}", word);

        SearchRecord record = SearchRecord.builder()
                .word(word)
                .time(Instant.now().getEpochSecond())
                .build();

        return repository.save(record);
    }

    public List<SearchRecord> getAllWords() {
        log.info("Fetching all search records");
        return repository.findAll();
    }

    public List<SearchRecord> getTop3Words() {
        log.info("Fetching top 3 most searched words");

        return repository.findAll().stream()
                .collect(Collectors.groupingBy(SearchRecord::getWord, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .map(entry -> SearchRecord.builder()
                        .word(entry.getKey())
                        .count(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    public void deleteRecord(UUID id) {
        log.info("Attempting to delete search record with id: {}", id);

        if (!repository.existsById(id)) {
            throw new SearchRecordNotFoundException("Record not found: " + id);
        }

        repository.deleteById(id);
        log.info("Search record deleted successfully: {}", id);
    }

}
