package lostlanguagelab.comment.repo;

import lostlanguagelab.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepo extends JpaRepository<Comment, UUID> {
    List<Comment> findAllByWordIdOrderByCreatedOnDesc(UUID wordId);
}
