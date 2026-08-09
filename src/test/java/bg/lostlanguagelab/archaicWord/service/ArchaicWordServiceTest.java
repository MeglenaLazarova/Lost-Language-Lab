package bg.lostlanguagelab.archaicWord.service;

import bg.lostlanguagelab.archaicWord.entity.ArchaicWord;
import bg.lostlanguagelab.archaicWord.repository.ArchaicWordRepo;
import bg.lostlanguagelab.category.enums.CategoryType;
import bg.lostlanguagelab.exception.UnauthorizedDeleteException;
import bg.lostlanguagelab.exception.WordAlreadyExistsException;
import bg.lostlanguagelab.model.dto.ArchaicWordDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArchaicWordServiceTest {

    @Mock
    private ArchaicWordRepo repo;

    @InjectMocks
    private ArchaicWordServiceImpl service;

    @Test
    void testCreateWordSuccessfully() {
        ArchaicWordDto dto = new ArchaicWordDto();
        dto.setWord("test");
        dto.setMeaning("meaning");

        when(repo.existsByWord("test")).thenReturn(false);
        when(repo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.create(dto);

        verify(repo, times(1)).save(any());
    }

    @Test
    void testCreateWordCopiesAllFields() {
        ArchaicWordDto dto = new ArchaicWordDto();
        dto.setWord("test");
        dto.setMeaning("meaning");
        dto.setEtymology("origin");
        dto.setExampleUsage("example");
        dto.setCategory(CategoryType.MEDIEVAL);

        when(repo.existsByWord("test")).thenReturn(false);

        service.create(dto);

        ArgumentCaptor<ArchaicWord> captor =
                ArgumentCaptor.forClass(ArchaicWord.class);

        verify(repo).save(captor.capture());

        ArchaicWord saved = captor.getValue();

        assertEquals("test", saved.getWord());
        assertEquals("meaning", saved.getMeaning());
        assertEquals("origin", saved.getEtymology());
        assertEquals("example", saved.getExampleUsage());
        assertEquals(CategoryType.MEDIEVAL, saved.getCategory());
        assertNotNull(saved.getCreatedOn());
        assertNotNull(saved.getUpdatedOn());
    }

    @Test
    void testCreateWordThrowsException() {
        ArchaicWordDto dto = new ArchaicWordDto();
        dto.setWord("duplicate");

        when(repo.existsByWord("duplicate")).thenReturn(true);

        assertThrows(WordAlreadyExistsException.class, () -> service.create(dto));
    }

    @Test
    void testGetAllWords() {
        when(repo.findAll()).thenReturn(List.of(new ArchaicWord(), new ArchaicWord()));

        List<ArchaicWord> result = service.getAll();

        assertEquals(2, result.size());
        verify(repo, times(1)).findAll();
    }


    @Test
    void testGetById() {
        UUID id = UUID.randomUUID();
        ArchaicWord word = new ArchaicWord();
        word.setId(id);
        word.setWord("test");

        when(repo.findById(id)).thenReturn(Optional.of(word));

        ArchaicWord result = service.getById(id);

        assertEquals("test", result.getWord());
    }

    @Test
    void testDeleteByIdUnauthorized() {
        UUID id = UUID.randomUUID();

        assertThrows(UnauthorizedDeleteException.class, () -> service.deleteById(id));
    }

    @Test
    void testUpdateWord() {
        UUID id = UUID.randomUUID();

        ArchaicWord existing = new ArchaicWord();
        existing.setId(id);
        existing.setWord("old");

        ArchaicWordDto dto = new ArchaicWordDto();
        dto.setWord("new");
        dto.setMeaning("updated");

        when(repo.findById(id)).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ArchaicWord updated = service.update(id, dto);

        assertEquals("new", updated.getWord());
        assertEquals("updated", updated.getMeaning());
    }

    @Test
    void testUpdateWordNotFound() {
        UUID id = UUID.randomUUID();
        ArchaicWordDto dto = new ArchaicWordDto();

        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.update(id, dto));
    }

}

