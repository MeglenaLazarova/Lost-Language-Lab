package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.client.SearchServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class WordSearchController {

    private final SearchServiceClient searchServiceClient;

    @PostMapping("/search")
    public String searchWord(@RequestParam String word) {
        searchServiceClient.saveWord(word);
        return "redirect:/search/top";}

    @GetMapping("/search/top")
    public String showTop(Model model) {model.addAttribute("topWords", searchServiceClient.getTop3Words());
        return "top-words";}

    @GetMapping("/search")
    public String searchPage() {
        return "search";}}