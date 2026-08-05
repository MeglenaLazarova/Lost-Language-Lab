package bg.lostlanguagelab.category.service;

import bg.lostlanguagelab.category.entity.Category;
import bg.lostlanguagelab.category.enums.CategoryType;
import bg.lostlanguagelab.category.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo= categoryRepo;
    }

    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category getById(UUID id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));
    }

    @Override
    public Category create(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public Category update(UUID id, Category updated) {
        Category existing = getById(id);

        existing.setType(updated.getType());
        existing.setDescription(updated.getDescription());

        return categoryRepo.save(existing);
    }

    @Override
    public void delete(UUID id) {
        categoryRepo.deleteById(id);
    }

    public Category getByType(CategoryType type) {
        return categoryRepo.findByType(type)
                .orElseThrow(() -> new RuntimeException("Category not found: " + type));
    }
}

