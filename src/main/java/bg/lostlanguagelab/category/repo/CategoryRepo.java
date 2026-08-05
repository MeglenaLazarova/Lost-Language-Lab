package lostlanguagelab.category.repo;

import lostlanguagelab.category.entity.Category;
import lostlanguagelab.category.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, UUID> {
    Optional<Category> findByType(CategoryType type);

    Optional<Category> findById(UUID id);
}

