package bg.lostlanguagelab.category.repo;

import bg.lostlanguagelab.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, UUID> {
}
