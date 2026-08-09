package bg.lostlanguagelab.service;

import bg.lostlanguagelab.client.SearchServiceClient;
import bg.lostlanguagelab.model.dto.SearchRecordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordSearchService {

    private final SearchServiceClient searchClient;

    public void saveWord(String word) {
        searchClient.saveWord(word);
    }

    public List<SearchRecordDto> getAllWords() {
        return searchClient.getAllWords();
    }

    public List<SearchRecordDto> getTop3Words() {
        return searchClient.getTop3Words();
    }

}

