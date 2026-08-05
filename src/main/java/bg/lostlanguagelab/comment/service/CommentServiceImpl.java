package bg.lostlanguagelab.comment.service;

import bg.lostlanguagelab.archaicWord.repository.ArchaicWordRepo;
import bg.lostlanguagelab.comment.entity.Comment;
import bg.lostlanguagelab.comment.repo.CommentRepo;
import bg.lostlanguagelab.model.dto.CommentDto;
import bg.lostlanguagelab.user.entity.User;
import bg.lostlanguagelab.archaicWord.entity.ArchaicWord;
import bg.lostlanguagelab.user.repository.UserRepo;
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
