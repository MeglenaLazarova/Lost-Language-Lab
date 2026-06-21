package bg.lostlanguagelab.archaicWord.service;

import bg.lostlanguagelab.archaicWord.entity.ArchaicWord;
import bg.lostlanguagelab.archaicWord.repository.ArchaicWordRepo;
import bg.lostlanguagelab.category.entity.Category;
import bg.lostlanguagelab.category.repo.CategoryRepo;
import bg.lostlanguagelab.model.dto.ArchaicWordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArchaicWordServiceImpl implements ArchaicWordService {

    private final ArchaicWordRepo repo;
    private final CategoryRepo categoryRepo;



    @Autowired
    public ArchaicWordServiceImpl(ArchaicWordRepo repo, CategoryRepo categoryRepo) {
        this.repo = repo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public void create(ArchaicWordDto dto) {
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
        if (!repo.existsById(id)) {
            throw new RuntimeException("Word not found: " + id);
        }

        repo.deleteById(id);
    }

    @Override
    public List<ArchaicWord> getAll() {
        return repo.findAll();
    }

}

