package bg.lostlanguagelab.searchservice.service;

import bg.lostlanguagelab.searchservice.entity.SearchRecord;
import bg.lostlanguagelab.searchservice.repository.SearchRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

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
}
