package lostlanguagelab.archaicWord.repository;

import lostlanguagelab.archaicWord.entity.ArchaicWord;
import lostlanguagelab.category.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ArchaicWordRepo extends JpaRepository<ArchaicWord, UUID> {
    boolean existsByWord( String word);
    List<ArchaicWord> findAllByCategory(CategoryType category);

}
