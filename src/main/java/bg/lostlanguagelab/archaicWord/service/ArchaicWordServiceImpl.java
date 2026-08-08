package bg.lostlanguagelab.archaicWord.service;

import bg.lostlanguagelab.archaicWord.entity.ArchaicWord;
import bg.lostlanguagelab.archaicWord.repository.ArchaicWordRepo;
import bg.lostlanguagelab.exception.UnauthorizedDeleteException;
import bg.lostlanguagelab.model.dto.ArchaicWordDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ArchaicWordServiceImpl implements ArchaicWordService {

    private final ArchaicWordRepo repo;

    @Autowired
    public ArchaicWordServiceImpl(ArchaicWordRepo repo) {
        this.repo = repo;
    }

    @Override
    public void create(ArchaicWordDto dto) {
        if (repo.existsByWord(dto.getWord())) {
            throw new RuntimeException("Тази дума вече съществува!");
        }

        ArchaicWord word = new ArchaicWord();

        word.setWord(dto.getWord());
        word.setMeaning(dto.getMeaning());
        word.setEtymology(dto.getEtymology());
        word.setExampleUsage(dto.getExampleUsage());
        word.setCategory(dto.getCategory());
        word.setCreatedOn(LocalDateTime.now());
        word.setUpdatedOn(LocalDateTime.now());

        repo.save(word);
    }

    @Override
    public void deleteById(UUID id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {

            throw new UnauthorizedDeleteException("Only admin can delete words");
        }

        if (!repo.existsById(id)) {
            throw new RuntimeException("Word not found: " + id);
        }

        repo.deleteById(id);

        log.info("Admin deleted word {}", id);
    }

    @Override
    public List<ArchaicWord> getAll() {
        return repo.findAll();
    }

    @Override
    public ArchaicWord getById(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Word not found"));
    }

    @Override
    public ArchaicWord update(UUID id, ArchaicWordDto dto) {

        ArchaicWord existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Word not found: " + id));

        existing.setWord(dto.getWord());
        existing.setMeaning(dto.getMeaning());
        existing.setEtymology(dto.getEtymology());
        existing.setExampleUsage(dto.getExampleUsage());
        existing.setCategory(dto.getCategory());
        existing.setUpdatedOn(LocalDateTime.now());

        return repo.save(existing);
    }

}

