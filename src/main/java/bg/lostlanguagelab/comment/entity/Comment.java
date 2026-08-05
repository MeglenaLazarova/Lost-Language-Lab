package lostlanguagelab.comment.entity;

import lostlanguagelab.archaicWord.entity.ArchaicWord;
import lostlanguagelab.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @ManyToOne(optional = false)
    private User author;

    @ManyToOne(optional = false)
    private ArchaicWord word;
}
