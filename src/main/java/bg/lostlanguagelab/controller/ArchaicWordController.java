package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.archaicWord.service.ArchaicWordService;
import bg.lostlanguagelab.category.enums.CategoryType;
import bg.lostlanguagelab.category.service.CategoryService;
import bg.lostlanguagelab.model.dto.ArchaicWordDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
public class ArchaicWordController {

    private final ArchaicWordService archaicWordService;
    private final CategoryService categoryService;


    @Autowired
    public ArchaicWordController(ArchaicWordService archaicWordService, CategoryService categoryService) {
        this.archaicWordService = archaicWordService;
        this.categoryService = categoryService;
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


}

