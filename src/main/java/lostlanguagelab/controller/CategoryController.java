package lostlanguagelab.controller;

import lostlanguagelab.archaicWord.entity.ArchaicWord;
import lostlanguagelab.archaicWord.repository.ArchaicWordRepo;
import lostlanguagelab.category.enums.CategoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CategoryController {
    private final ArchaicWordRepo archaicWordRepo;

    @Autowired
    public CategoryController(ArchaicWordRepo archaicWordRepo) {
        this.archaicWordRepo = archaicWordRepo;
    }

    @GetMapping("/categories")
    public ModelAndView viewCategories() {
        ModelAndView modelAndView = new ModelAndView("categories");

        Map<CategoryType, List<ArchaicWord>> map = new LinkedHashMap<>();

        for (CategoryType type : CategoryType.values()) {
            List<ArchaicWord> words = archaicWordRepo.findAllByCategory(type);
            map.put(type, words);
        }

        modelAndView.addObject("categoriesMap", map);
        return modelAndView;
    }




}
