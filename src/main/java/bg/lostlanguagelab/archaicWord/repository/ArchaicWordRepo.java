package bg.lostlanguagelab.archaicWord.repository;

import bg.lostlanguagelab.archaicWord.entity.ArchaicWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArchaicWordRepo extends JpaRepository<ArchaicWord, UUID> {
}
