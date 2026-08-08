package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.model.dto.SearchRecordDto;
import bg.lostlanguagelab.service.WordSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class WordSearchController {

    private final WordSearchService wordSearchService;

    @PostMapping
    public void save(@RequestParam String word) {
        wordSearchService.saveWord(word);
    }

    @GetMapping("/history")
    public List<SearchRecordDto> history() {
        return wordSearchService.getAllWords();
    }
}

