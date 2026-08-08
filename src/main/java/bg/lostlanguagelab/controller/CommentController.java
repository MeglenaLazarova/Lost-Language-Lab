package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.comment.service.CommentService;
import bg.lostlanguagelab.model.dto.CommentDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
                             @Valid @ModelAttribute CommentDto commentDto,
                             BindingResult bindingResult,
                             @SessionAttribute("userId") UUID userId) {

        if (userId == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "redirect:/words/" + wordId;
        }

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
