package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.archaicWord.service.ArchaicWordService;
import bg.lostlanguagelab.category.enums.CategoryType;
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
            ModelAndView mav = new ModelAndView("add-word");
            mav.addObject("categories", CategoryType.values());
            return mav;
        }

        System.out.println("Добавена дума: " + wordDTO.getWord());
        System.out.println("Категория: " + wordDTO.getCategoryId());

        return new ModelAndView("redirect:/home");
    }

    @PostMapping("/words/{id}/delete")
    public ModelAndView deleteWord(@PathVariable UUID id) {


        archaicWordService.deleteById(id);

        return new ModelAndView("redirect:/words");
    }


}

