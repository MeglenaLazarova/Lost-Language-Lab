package bg.lostlanguagelab.service;

import bg.lostlanguagelab.client.SearchServiceClient;
import bg.lostlanguagelab.model.dto.SearchRecordDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WordSearchService {

    private final SearchServiceClient searchClient;

    public void saveWord(String word) {
        log.info("Calling search-service to save word: {}", word);
        searchClient.saveWord(word);
    }

    public List<SearchRecordDto> getAllWords() {
        log.info("Fetching all search records from search-service");
        return searchClient.getAllWords();
    }

    public List<SearchRecordDto> getTop3Words() {
        log.info("Fetching top 3 searched words from search-service");
        return searchClient.getTop3Words();
    }

}

