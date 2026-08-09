package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.archaicWord.service.ArchaicWordService;
import bg.lostlanguagelab.category.entity.Category;
import bg.lostlanguagelab.category.enums.CategoryType;
import bg.lostlanguagelab.category.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private ArchaicWordService archaicWordService;

    @Test
    void testViewCategories() throws Exception {
        when(archaicWordService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/categories")
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("categories"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("words"));
    }


    @Test
    void testShowAddForm() throws Exception {
        mockMvc.perform(get("/categories/add")
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("add-category"))
                .andExpect(model().attributeExists("category"));
    }

    @Test
    void testCreateCategory_Invalid() throws Exception {
        mockMvc.perform(post("/categories/add")
                        .param("categoryName", "")
                        .param("description", "desc")
                        .param("type", CategoryType.CHURCH_SLAVIC.name())
                                .with(user("test").roles("USER")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/categories"));
    }

    @Test
    void testCreateCategory_Valid() throws Exception {
        mockMvc.perform(post("/categories/add")
                        .with(user("test").roles("USER"))
                        .param("categoryName", "Animals")
                        .param("description", "Category for animals")
                        .param("type", CategoryType.CHURCH_SLAVIC.name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"));

        verify(categoryService, times(1)).create(any(Category.class));
    }

    @Test
    void testShowEditForm() throws Exception {
        UUID id = UUID.randomUUID();
        Category category = new Category();
        category.setId(id);
        category.setCategoryName("Test");
        category.setDescription("Desc");
        category.setType(CategoryType.FOLKLORE);

        when(categoryService.getById(id)).thenReturn(category);

        mockMvc.perform(get("/categories/edit/" + id)
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-category"))
                .andExpect(model().attributeExists("category"));
    }

    @Test
    void testUpdateCategory_Invalid() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(post("/categories/edit/" + id)
                        .param("categoryName", "")
                        .param("description", "desc")
                        .param("type", CategoryType.RENAISSANCE.name())
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

    @Test
    void testUpdateCategory_Valid() throws Exception {
        UUID id = UUID.randomUUID();

        Category existing = new Category();
        existing.setId(id);
        existing.setCategoryName("Old");
        existing.setDescription("Old desc");
        existing.setType(CategoryType.OLD_BULGARIAN);

        when(categoryService.getById(id)).thenReturn(existing);

        mockMvc.perform(post("/categories/edit/" + id)
                .with(user("test").roles("USER"))
                        .param("categoryName", "New Name")
                        .param("description", "New desc")
                        .param("type", CategoryType.DIALECT.name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"));

        verify(categoryService, times(1)).create(any(Category.class));
    }

    @Test
    void testDeleteCategory() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(post("/categories/delete/" + id)
                .with(user("test").roles("USER")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"));

        verify(categoryService, times(1)).delete(id);
    }
}

