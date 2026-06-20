package bg.lostlanguagelab.archaicWord.service;

import bg.lostlanguagelab.model.dto.ArchaicWordDto;

import java.util.UUID;

public interface ArchaicWordService {
    void create(ArchaicWordDto dto);
    void deleteById(UUID id);

}

