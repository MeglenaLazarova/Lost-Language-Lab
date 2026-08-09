package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.comment.service.CommentService;
import bg.lostlanguagelab.model.dto.CommentDto;
import bg.lostlanguagelab.security.UserData;
import bg.lostlanguagelab.user.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
class CommentControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    void testAddComment_NoUserId() throws Exception {
        UUID wordId = UUID.randomUUID();

        mockMvc.perform(post("/comments/add/" + wordId)
                        .param("content", "Valid comment"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void testAddComment_InvalidDto() throws Exception {
        UUID wordId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        mockMvc.perform(post("/comments/add/" + wordId)
                        .param("content", "")
                        .sessionAttr("userId", userId)
                        .with(SecurityMockMvcRequestPostProcessors.authentication(
                                new UsernamePasswordAuthenticationToken(
                                        new UserData(userId, "Megi", "123456", UserRole.USER),
                                        "123456",
                                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                                )
                        )))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/words/" + wordId));
    }


    @Test
    void testAddComment_Success() throws Exception {
        UUID wordId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        UserData principal = new UserData(
                userId,
                "Megi",
                "123456",
                UserRole.USER
        );

        mockMvc.perform(post("/comments/add/" + wordId)
                        .param("content", "This is a valid comment")
                        .param("wordId", wordId.toString())
                        .param("authorId", userId.toString())
                        .sessionAttr("userId", userId)
                        .with(SecurityMockMvcRequestPostProcessors.authentication(
                                new UsernamePasswordAuthenticationToken(
                                        principal,
                                        principal.getPassword(),
                                        principal.getAuthorities()
                                )
                        )))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/words/" + wordId));

        verify(commentService, times(1))
                .create(any(CommentDto.class), any(UUID.class), any(UUID.class));
    }


    @Test
    void testDeleteComment() throws Exception {
        UUID commentId = UUID.randomUUID();
        UUID wordId = UUID.randomUUID();

        UserData principal = new UserData(
                UUID.randomUUID(),
                "Megi",
                "123456",
                UserRole.USER
        );

        mockMvc.perform(post("/comments/delete/" + commentId)
                        .param("wordId", wordId.toString())
                        .with(SecurityMockMvcRequestPostProcessors.authentication(
                                new UsernamePasswordAuthenticationToken(
                                        principal,
                                        principal.getPassword(),
                                        principal.getAuthorities()
                                )
                        )))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/words/" + wordId));

        verify(commentService, times(1)).delete(commentId);
    }

}

