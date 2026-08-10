package bg.lostlanguagelab.searchservice.service;

import bg.lostlanguagelab.searchservice.entity.SearchRecord;
import bg.lostlanguagelab.searchservice.repository.SearchRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRecordRepository repository;

    public SearchRecord saveWord(String word) {
        SearchRecord record = SearchRecord.builder()
                .word(word)
                .time(Instant.now().getEpochSecond())
                .build();

        return repository.save(record);
    }

    public List<SearchRecord> getAllWords() {
        return repository.findAll();
    }

    public List<SearchRecord> getTop3Words() {
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
        if (!repository.existsById(id)) {
            throw new RuntimeException("Record not found: " + id);
        }

        repository.deleteById(id);
    }

}
