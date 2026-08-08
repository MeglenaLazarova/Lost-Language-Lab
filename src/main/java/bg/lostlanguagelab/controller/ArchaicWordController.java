package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.archaicWord.entity.ArchaicWord;
import bg.lostlanguagelab.archaicWord.service.ArchaicWordService;
import bg.lostlanguagelab.category.enums.CategoryType;
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

    @Autowired
    public ArchaicWordController(ArchaicWordService archaicWordService, CommentService commentService) {
        this.archaicWordService = archaicWordService;
        this.commentService = commentService;

    }

    @GetMapping("/words/new")
    public ModelAndView showAddWordForm() {
        ModelAndView modelAndView = new ModelAndView("word-form");
        modelAndView.addObject("wordDTO", new ArchaicWordDto());
        modelAndView.addObject("types", CategoryType.values());
        modelAndView.addObject("formAction", "/words/new");
        modelAndView.addObject("formTitle", "Добави архаична дума");
        modelAndView.addObject("submitLabel", "Добави");
        return modelAndView;
    }

    @PostMapping("/words/new")
    public ModelAndView addWord(
            @Valid @ModelAttribute("wordDTO") ArchaicWordDto wordDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("word-form");
            modelAndView.addObject("wordDTO", wordDTO);
            modelAndView.addObject("types", CategoryType.values());
            modelAndView.addObject("formAction", "/words/new");
            modelAndView.addObject("formTitle", "Добави архаична дума");
            modelAndView.addObject("submitLabel", "Добави");

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
        modelAndView.addObject("comments", commentService.getCommentsForWord(id));
        modelAndView.addObject("commentDto", new CommentDto());
        return modelAndView;
    }

    @GetMapping("/words/{id}/edit")
    public ModelAndView showEditWordForm(@PathVariable UUID id) {

        ArchaicWord word = archaicWordService.getById(id);

        ArchaicWordDto dto = new ArchaicWordDto();
        dto.setId(word.getId());
        dto.setWord(word.getWord());
        dto.setMeaning(word.getMeaning());
        dto.setEtymology(word.getEtymology());
        dto.setExampleUsage(word.getExampleUsage());
        dto.setCategory(word.getCategory());

        ModelAndView modelAndView = new ModelAndView("edit-word");
        modelAndView.addObject("wordDTO", dto);
        modelAndView.addObject("types", CategoryType.values());
        modelAndView.addObject("formAction", "/words/" + id + "/edit");
        modelAndView.addObject("formTitle", "Редактиране на архаична дума");
        modelAndView.addObject("submitLabel", "Запази промените");

        return modelAndView;
    }

    @PostMapping("/words/{id}/edit")
    public ModelAndView editWord(
            @PathVariable UUID id,
            @Valid @ModelAttribute("wordDTO") ArchaicWordDto wordDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("edit-word");
            modelAndView.addObject("wordDTO", wordDTO);
            modelAndView.addObject("types", CategoryType.values());
            modelAndView.addObject("formAction", "/words/" + id + "/edit");
            modelAndView.addObject("formTitle", "Редактиране на архаична дума");
            modelAndView.addObject("submitLabel", "Запази промените");
            return modelAndView;
        }

        archaicWordService.update(id, wordDTO);

        return new ModelAndView("redirect:/words/" + id);
    }

}

