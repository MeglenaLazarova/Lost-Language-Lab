package lostlanguagelab.comment.service;

import lostlanguagelab.archaicWord.repository.ArchaicWordRepo;
import lostlanguagelab.comment.entity.Comment;
import lostlanguagelab.comment.repo.CommentRepo;
import lostlanguagelab.model.dto.CommentDto;
import lostlanguagelab.user.entity.User;
import lostlanguagelab.archaicWord.entity.ArchaicWord;
import lostlanguagelab.user.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepository;
    private final UserRepo userRepository;
    private final ArchaicWordRepo wordRepository;

    @Autowired
    public CommentServiceImpl(CommentRepo commentRepository,
                              UserRepo userRepository,
                              ArchaicWordRepo wordRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.wordRepository = wordRepository;
    }

    @Override
    public void create(CommentDto dto, UUID wordId, UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ArchaicWord word = wordRepository.findById(wordId)
                .orElseThrow(() -> new RuntimeException("Word not found"));

        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setCreatedOn(LocalDateTime.now());
        comment.setAuthor(user);
        comment.setWord(word);

        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsForWord(UUID wordId) {
        return commentRepository.findAllByWordIdOrderByCreatedOnDesc(wordId);
    }

    @Override
    public void delete(UUID commentId) {
        commentRepository.deleteById(commentId);
    }
}
