package bg.lostlanguagelab.client;

import bg.lostlanguagelab.model.dto.SearchRecordDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "search-service", url = "http://localhost:8081")
    public interface SearchServiceClient {

        @PostMapping("/api/search")
        SearchRecordDto saveWord(@RequestParam String word);

        @GetMapping("/api/search")
        List<SearchRecordDto> getAllWords();

        @GetMapping("/api/search/top")
        List<SearchRecordDto> getTop3Words();

        @DeleteMapping("/api/search/{id}")
        void deleteSearchRecord(@PathVariable UUID id);


    }

