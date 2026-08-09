package bg.lostlanguagelab.archaicWord.service;

import bg.lostlanguagelab.archaicWord.entity.ArchaicWord;
import bg.lostlanguagelab.archaicWord.repository.ArchaicWordRepo;
import bg.lostlanguagelab.exception.UnauthorizedDeleteException;
import bg.lostlanguagelab.exception.WordAlreadyExistsException;
import bg.lostlanguagelab.model.dto.ArchaicWordDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArchaicWordServiceIntegrationTest {

    @Mock
    private ArchaicWordRepo repo;

    @InjectMocks
    private ArchaicWordServiceImpl service;

    @Test
    void testCreateSuccess() {
        ArchaicWordDto dto = new ArchaicWordDto();
        dto.setWord("дума");
        dto.setMeaning("значение");
        dto.setCategory(null);

        when(repo.existsByWord("дума")).thenReturn(false);

        service.create(dto);

        ArgumentCaptor<ArchaicWord> captor = ArgumentCaptor.forClass(ArchaicWord.class);
        verify(repo).save(captor.capture());

        ArchaicWord saved = captor.getValue();
        assertEquals("дума", saved.getWord());
        assertEquals("значение", saved.getMeaning());
        assertNotNull(saved.getCreatedOn());
        assertNotNull(saved.getUpdatedOn());
    }

    @Test
    void testCreateWordAlreadyExists() {
        ArchaicWordDto dto = new ArchaicWordDto();
        dto.setWord("дума");

        when(repo.existsByWord("дума")).thenReturn(true);

        assertThrows(WordAlreadyExistsException.class, () -> service.create(dto));
    }

    @Test
    void testDeleteByIdSuccess() {
        UUID id = UUID.randomUUID();

        TestingAuthenticationToken auth =
                new TestingAuthenticationToken("admin", "pass", "ROLE_ADMIN");
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(repo.existsById(id)).thenReturn(true);

        service.deleteById(id);

        verify(repo).deleteById(id);
    }

    @Test
    void testDeleteByIdUnauthorized() {
        UUID id = UUID.randomUUID();

        SecurityContextHolder.getContext().setAuthentication(null);

        assertThrows(UnauthorizedDeleteException.class, () -> service.deleteById(id));
    }

    @Test
    void testDeleteByIdNotFound() {
        UUID id = UUID.randomUUID();

        TestingAuthenticationToken auth =
                new TestingAuthenticationToken("admin", "pass", "ROLE_ADMIN");
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(repo.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.deleteById(id));
    }

    @Test
    void testGetAll() {
        ArchaicWord w1 = new ArchaicWord();
        ArchaicWord w2 = new ArchaicWord();

        when(repo.findAll()).thenReturn(List.of(w1, w2));

        List<ArchaicWord> result = service.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void testGetByIdSuccess() {
        UUID id = UUID.randomUUID();
        ArchaicWord word = new ArchaicWord();
        word.setId(id);

        when(repo.findById(id)).thenReturn(Optional.of(word));

        ArchaicWord result = service.getById(id);

        assertEquals(id, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        UUID id = UUID.randomUUID();

        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getById(id));
    }

    @Test
    void testUpdateSuccess() {
        UUID id = UUID.randomUUID();

        ArchaicWord existing = new ArchaicWord();
        existing.setId(id);
        existing.setWord("старо");

        ArchaicWordDto dto = new ArchaicWordDto();
        dto.setWord("ново");
        dto.setMeaning("ново значение");

        when(repo.findById(id)).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        ArchaicWord updated = service.update(id, dto);

        assertEquals("ново", updated.getWord());
        assertEquals("ново значение", updated.getMeaning());
        assertNotNull(updated.getUpdatedOn());
    }

    @Test
    void testUpdateNotFound() {
        UUID id = UUID.randomUUID();

        when(repo.findById(id)).thenReturn(Optional.empty());

        ArchaicWordDto dto = new ArchaicWordDto();
        dto.setWord("ново");

        assertThrows(RuntimeException.class, () -> service.update(id, dto));
    }
}
