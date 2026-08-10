package bg.lostlanguagelab.category.service;

import bg.lostlanguagelab.category.entity.Category;
import bg.lostlanguagelab.category.enums.CategoryType;
import bg.lostlanguagelab.category.repo.CategoryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo= categoryRepo;
    }

    @Override
    public List<Category> getAllCategories() {
        log.info("Fetching all categories");
        return categoryRepo.findAll();
    }

    @Override
    public Category getById(UUID id) {
        log.info("Fetching category with id={}", id);
        return categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));
    }

    @Override
    public Category create(Category category) {
        log.info("Creating new category: {}", category.getCategoryName());
        return categoryRepo.save(category);
    }

    @Override
    @Transactional
    public Category update(UUID id, Category updated) {
        log.info("Updating category with id={}", id);

        if (updated.getDescription() == null || updated.getDescription().isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        Category existing = getById(id);

        existing.setType(updated.getType());
        existing.setCategoryName(updated.getCategoryName());
        existing.setDescription(updated.getDescription());

        log.info("Category updated successfully: {}", id);

        return categoryRepo.save(existing);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!categoryRepo.existsById(id)) {
            throw new RuntimeException("Category not found: " + id);
        }
        categoryRepo.deleteById(id);
        log.info("Category deleted successfully: {}", id);
    }

    public Category getByType(CategoryType type) {
        log.info("Fetching category by type={}", type);
        return categoryRepo.findByType(type)
                .orElseThrow(() -> new RuntimeException("Category not found: " + type));
    }
}

