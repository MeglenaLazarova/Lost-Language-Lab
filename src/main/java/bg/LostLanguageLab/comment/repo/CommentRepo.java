package bg.LostLanguageLab.comment.repo;

import bg.LostLanguageLab.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepo extends JpaRepository<Comment, UUID> {
}
