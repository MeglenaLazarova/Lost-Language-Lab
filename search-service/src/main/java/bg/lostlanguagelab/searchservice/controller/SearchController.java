package bg.lostlanguagelab.searchservice.controller;

import bg.lostlanguagelab.searchservice.entity.SearchRecord;
import bg.lostlanguagelab.searchservice.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @PostMapping
    public SearchRecord saveWord(@RequestParam String word) {
        return searchService.saveWord(word);
    }

    @GetMapping
    public List<SearchRecord> getAllWords() {
        return searchService.getAllWords();
    }

    @GetMapping("/top")
    public List<SearchRecord> getTop3Words() {
        return searchService.getTop3Words();
    }

}

