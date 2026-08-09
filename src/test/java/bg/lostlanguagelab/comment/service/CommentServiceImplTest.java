package bg.lostlanguagelab.comment.service;

import bg.lostlanguagelab.archaicWord.entity.ArchaicWord;
import bg.lostlanguagelab.archaicWord.repository.ArchaicWordRepo;
import bg.lostlanguagelab.comment.entity.Comment;
import bg.lostlanguagelab.comment.repo.CommentRepo;
import bg.lostlanguagelab.model.dto.CommentDto;
import bg.lostlanguagelab.user.entity.User;
import bg.lostlanguagelab.user.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepo commentRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private ArchaicWordRepo wordRepo;

    @InjectMocks
    private CommentServiceImpl service;

    @Test
    void testCreateComment() {
        UUID userId = UUID.randomUUID();
        UUID wordId = UUID.randomUUID();

        CommentDto dto = new CommentDto();
        dto.setContent("Test content");

        User user = new User();
        user.setId(userId);

        ArchaicWord word = new ArchaicWord();
        word.setId(wordId);

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(wordRepo.findById(wordId)).thenReturn(Optional.of(word));
        when(commentRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        service.create(dto, wordId, userId);

        verify(commentRepo, times(1)).save(any(Comment.class));
    }

    @Test
    void testCreateCommentUserNotFound() {
        UUID userId = UUID.randomUUID();
        UUID wordId = UUID.randomUUID();

        CommentDto dto = new CommentDto();
        dto.setContent("Test");

        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.create(dto, wordId, userId));
    }

    @Test
    void testCreateCommentWordNotFound() {
        UUID userId = UUID.randomUUID();
        UUID wordId = UUID.randomUUID();

        CommentDto dto = new CommentDto();
        dto.setContent("Test");

        User user = new User();
        user.setId(userId);

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(wordRepo.findById(wordId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.create(dto, wordId, userId));
    }

    @Test
    void testGetCommentsForWord() {
        UUID wordId = UUID.randomUUID();

        Comment c1 = new Comment();
        Comment c2 = new Comment();

        when(commentRepo.findAllByWordIdOrderByCreatedOnDesc(wordId))
                .thenReturn(List.of(c1, c2));

        List<Comment> result = service.getCommentsForWord(wordId);

        assertEquals(2, result.size());
        verify(commentRepo, times(1))
                .findAllByWordIdOrderByCreatedOnDesc(wordId);
    }

    @Test
    void testDeleteComment() {
        UUID id = UUID.randomUUID();

        service.delete(id);

        verify(commentRepo, times(1)).deleteById(id);
    }
}
