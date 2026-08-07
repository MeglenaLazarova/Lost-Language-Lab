package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.comment.service.CommentService;
import bg.lostlanguagelab.model.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add/{wordId}")
    public String addComment(@PathVariable UUID wordId,
                             @ModelAttribute CommentDto commentDto,
                             @SessionAttribute("userId") UUID userId) {

        commentService.create(commentDto, wordId, userId);
        return "redirect:/words/" + wordId;
    }

    @PostMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable UUID commentId,
                                @RequestParam UUID wordId) {

        commentService.delete(commentId);
        return "redirect:/words/" + wordId;
    }
}
