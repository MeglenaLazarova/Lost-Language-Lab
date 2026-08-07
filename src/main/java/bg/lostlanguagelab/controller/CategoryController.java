package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.archaicWord.entity.ArchaicWord;
import bg.lostlanguagelab.category.entity.Category;
import bg.lostlanguagelab.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ModelAndView viewCategories() {
        ModelAndView modelAndView = new ModelAndView("categories");

        List<Category> categories = categoryService.getAllCategories();

        Map<Category, List<ArchaicWord>> map = new LinkedHashMap<>();

        for (Category category : categories) {
            map.put(category, category.getWords());
        }

        modelAndView.addObject("categoriesMap", map);
        return modelAndView;
    }
    @GetMapping("/categories/add")
    public ModelAndView showAddForm() {
        ModelAndView modelAndView = new ModelAndView("add-category");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/categories/add")
    public ModelAndView createCategory(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("add-category");
            return modelAndView;
        }
        categoryService.create(category);
        return new ModelAndView("redirect:/categories");
    }

    @GetMapping("/categories/edit/{id}")
    public ModelAndView showEditForm(@PathVariable UUID id) {
        ModelAndView modelAndView = new ModelAndView("edit-category");
        modelAndView.addObject("category", categoryService.getById(id));
        return modelAndView;
    }

    @PostMapping("/categories/edit/{id}")
    public ModelAndView updateCategory(@Valid @PathVariable UUID id,
                                       @ModelAttribute Category updated, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("edit-category");
            return modelAndView;
        }
        Category existing = categoryService.getById(id);
        existing.setCategoryName(updated.getCategoryName());
        existing.setDescription(updated.getDescription());
        existing.setType(updated.getType());

        categoryService.create(existing);
        return new ModelAndView("redirect:/categories");
    }

    @PostMapping("/categories/delete/{id}")
    public ModelAndView deleteCategory(@PathVariable UUID id) {
        categoryService.delete(id);
        return new ModelAndView("redirect:/categories");
    }

}
