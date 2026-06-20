package bg.lostlanguagelab.comment.service;

import bg.lostlanguagelab.model.dto.CommentDto;
import bg.lostlanguagelab.comment.entity.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    void create(CommentDto dto, UUID wordId, UUID userId);

    List<Comment> getCommentsForWord(UUID wordId);

    void delete(UUID commentId);
}
