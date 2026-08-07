package bg.lostlanguagelab.archaicWord.service;

import bg.lostlanguagelab.archaicWord.entity.ArchaicWord;
import bg.lostlanguagelab.model.dto.ArchaicWordDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface ArchaicWordService {
    void create(ArchaicWordDto dto);
    void deleteById(UUID id);

    List<ArchaicWord> getAll();

    ArchaicWord getById(UUID id);

    ArchaicWord update(UUID id, @Valid ArchaicWordDto wordDTO);
}

