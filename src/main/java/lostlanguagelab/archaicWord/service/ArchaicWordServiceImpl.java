package lostlanguagelab.archaicWord.service;

import lostlanguagelab.archaicWord.entity.ArchaicWord;
import lostlanguagelab.archaicWord.repository.ArchaicWordRepo;
import lostlanguagelab.model.dto.ArchaicWordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
        if (!repo.existsById(id)) {
            throw new RuntimeException("Word not found: " + id);
        }

        repo.deleteById(id);
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



}

