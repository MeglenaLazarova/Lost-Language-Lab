package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.category.enums.CategoryType;
import bg.lostlanguagelab.model.dto.ArchaicWordDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ArchaicWordController {

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

}

