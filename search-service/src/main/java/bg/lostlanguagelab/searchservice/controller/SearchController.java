package bg.lostlanguagelab.searchservice.controller;

import bg.lostlanguagelab.searchservice.entity.SearchRecord;
import bg.lostlanguagelab.searchservice.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable UUID id) {
        searchService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }

}

