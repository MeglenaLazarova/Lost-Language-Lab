package bg.lostlanguagelab.archaicWord.repository;

import bg.lostlanguagelab.archaicWord.entity.ArchaicWord;
import bg.lostlanguagelab.category.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ArchaicWordRepo extends JpaRepository<ArchaicWord, UUID> {
    boolean existsByWord( String word);
    List<ArchaicWord> findAllByCategory(CategoryType category);

}
