package bg.lostlanguagelab.archaicWord.service;

import bg.lostlanguagelab.archaicWord.entity.ArchaicWord;
import bg.lostlanguagelab.archaicWord.repository.ArchaicWordRepo;
import bg.lostlanguagelab.model.dto.ArchaicWordDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ArchaicWordServiceImpl implements ArchaicWordService {

    private final ArchaicWordRepo repo;

    public ArchaicWordServiceImpl(ArchaicWordRepo repo) {
        this.repo = repo;
    }

    @Override
    public void create(ArchaicWordDto dto) {
        ArchaicWord word = new ArchaicWord();
        word.setWord(dto.getWord());
        word.setMeaning(dto.getMeaning());
        word.setEtymology(dto.getEtymology());
        word.setExampleUsage(dto.getExampleUsage());
        word.setCategory(dto.getCategoryId());

        repo.save(word);
    }

    @Override
    public void deleteById(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Word not found: " + id);
        }

        repo.deleteById(id);
    }

}

