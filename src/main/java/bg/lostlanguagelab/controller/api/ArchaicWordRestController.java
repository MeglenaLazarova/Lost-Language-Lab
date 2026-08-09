package bg.lostlanguagelab.controller.api;

import bg.lostlanguagelab.archaicWord.entity.ArchaicWord;
import bg.lostlanguagelab.archaicWord.service.ArchaicWordService;
import bg.lostlanguagelab.model.dto.ArchaicWordDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/archaic-words")
public class ArchaicWordRestController {

    private final ArchaicWordService service;

    public ArchaicWordRestController(ArchaicWordService service) {
        this.service = service;
    }

    @GetMapping
    public List<ArchaicWord> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ArchaicWord getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ArchaicWordDto dto) {
        service.create(dto);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ArchaicWord update(@PathVariable UUID id, @RequestBody ArchaicWordDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

