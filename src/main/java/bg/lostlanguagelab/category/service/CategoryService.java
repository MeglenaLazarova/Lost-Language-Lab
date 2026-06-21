package bg.lostlanguagelab.category.service;

import bg.lostlanguagelab.category.entity.Category;
import bg.lostlanguagelab.category.enums.CategoryType;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getByType(CategoryType type);

}

