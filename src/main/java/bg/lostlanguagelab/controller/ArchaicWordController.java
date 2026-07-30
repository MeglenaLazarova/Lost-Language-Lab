package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.archaicWord.service.ArchaicWordService;
import bg.lostlanguagelab.category.enums.CategoryType;
import bg.lostlanguagelab.model.dto.ArchaicWordDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
public class ArchaicWordController {

    private final ArchaicWordService archaicWordService;

    @Autowired
    public ArchaicWordController(ArchaicWordService archaicWordService) {
        this.archaicWordService = archaicWordService;
    }

    @GetMapping("/words/new")
    public ModelAndView showAddWordForm() {
        ModelAndView modelAndView = new ModelAndView("add-word");
        modelAndView.addObject("wordDTO", new ArchaicWordDto());
        modelAndView.addObject("categories", CategoryType.values());

        return modelAndView;
    }

    @PostMapping("/words/new")
    public ModelAndView addWord(
            @Valid @ModelAttribute("wordDTO") ArchaicWordDto wordDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("add-word");
            modelAndView.addObject("categories", CategoryType.values());

            return modelAndView;
        }

        System.out.println("Добавена дума: " + wordDTO.getWord());
        System.out.println("Категория: " + wordDTO.getCategory());

        archaicWordService.create(wordDTO);

        return new ModelAndView("redirect:/words");
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/words/{id}/delete")
    public ModelAndView deleteWord(@PathVariable UUID id) {

        archaicWordService.deleteById(id);

        return new ModelAndView("redirect:/words");
    }

    @GetMapping("/words")
    public ModelAndView showWords() {
        ModelAndView modelAndView = new ModelAndView("words-list");
        modelAndView.addObject("words", archaicWordService.getAll());
        return modelAndView;
    }

    @GetMapping("/words/{id}")
    public ModelAndView showWordDetails(@PathVariable UUID id) {
        ModelAndView modelAndView = new ModelAndView("word-details");
        modelAndView.addObject("word", archaicWordService.getById(id));
        return modelAndView;
    }


}

