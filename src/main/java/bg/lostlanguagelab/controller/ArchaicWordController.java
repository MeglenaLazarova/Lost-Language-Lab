package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.archaicWord.entity.ArchaicWord;
import bg.lostlanguagelab.archaicWord.service.ArchaicWordService;
import bg.lostlanguagelab.category.enums.CategoryType;
import bg.lostlanguagelab.category.service.CategoryService;
import bg.lostlanguagelab.comment.service.CommentService;
import bg.lostlanguagelab.model.dto.ArchaicWordDto;
import bg.lostlanguagelab.model.dto.CommentDto;
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
    private final CommentService commentService;
    private final CategoryService categoryService;


    @Autowired
    public ArchaicWordController(ArchaicWordService archaicWordService, CommentService commentService, CategoryService categoryService) {
        this.archaicWordService = archaicWordService;
        this.commentService = commentService;
        this.categoryService = categoryService;
    }

    @GetMapping("/words/new")
    public ModelAndView showAddWordForm() {
        ModelAndView modelAndView = new ModelAndView("add-word");
        modelAndView.addObject("wordDTO", new ArchaicWordDto());
        modelAndView.addObject("categories", categoryService.getAllCategories());

        return modelAndView;
    }

    @PostMapping("/words/new")
    public ModelAndView addWord(
            @Valid @ModelAttribute("wordDTO") ArchaicWordDto wordDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("add-word");
            modelAndView.addObject("categories", categoryService.getAllCategories());


            return modelAndView;
        }

        System.out.println("Добавена дума: " + wordDTO.getWord());
        System.out.println("Категория: " + wordDTO.getCategoryId());

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
        modelAndView.addObject("comments", commentService.getCommentsForWord(id));
        modelAndView.addObject("commentDto", new CommentDto());
        return modelAndView;
    }

    @GetMapping("/words/{id}/edit")
    public ModelAndView showEditWordForm(@PathVariable UUID id) {

        ArchaicWord word = archaicWordService.getById(id);

        ArchaicWordDto dto = new ArchaicWordDto();
        dto.setWord(word.getWord());
        dto.setMeaning(word.getMeaning());
        dto.setEtymology(word.getEtymology());
        dto.setExampleUsage(word.getExampleUsage());
        dto.setCategoryId(word.getCategory().getId());

        ModelAndView modelAndView = new ModelAndView("edit-word");
        modelAndView.addObject("wordDTO", dto);
        modelAndView.addObject("categories", categoryService.getAllCategories());

        return modelAndView;
    }

    @PostMapping("/words/{id}/edit")
    public ModelAndView editWord(
            @PathVariable UUID id,
            @Valid @ModelAttribute("wordDTO") ArchaicWordDto wordDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("edit-word");
            modelAndView.addObject("categories", categoryService.getAllCategories());
            return modelAndView;
        }

        archaicWordService.update(id, wordDTO);

        return new ModelAndView("redirect:/words/" + id);
    }


}

