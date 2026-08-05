package lostlanguagelab.archaicWord.service;

import lostlanguagelab.archaicWord.entity.ArchaicWord;
import lostlanguagelab.model.dto.ArchaicWordDto;

import java.util.List;
import java.util.UUID;

public interface ArchaicWordService {
    void create(ArchaicWordDto dto);
    void deleteById(UUID id);

    List<ArchaicWord> getAll();

    ArchaicWord getById(UUID id);
}

