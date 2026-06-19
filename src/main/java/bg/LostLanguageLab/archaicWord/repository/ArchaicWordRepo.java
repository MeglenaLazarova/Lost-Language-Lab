package bg.LostLanguageLab.archaicWord.repository;

import bg.LostLanguageLab.archaicWord.entity.ArchaicWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArchaicWordRepo extends JpaRepository<ArchaicWord, UUID> {
}
