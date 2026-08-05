package bg.lostlanguagelab.category.service;

import bg.lostlanguagelab.category.entity.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getById(UUID id);

    Category create(Category category);

    Category update(UUID id, Category category);

    void delete(UUID id);
}

