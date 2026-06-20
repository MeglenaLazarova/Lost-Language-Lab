package bg.lostlanguagelab.archaicWord.entity;

import bg.lostlanguagelab.category.entity.Category;
import bg.lostlanguagelab.comment.entity.Comment;
import bg.lostlanguagelab.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArchaicWord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String word;
    @Column(nullable = false)
    private String meaning;
    @ManyToOne(fetch = FetchType.EAGER)
    private User addedBy;
    @ManyToOne(optional = false)
    private Category category;
    @OneToMany(mappedBy = "word")
    @OrderBy("createdOn desc")
    private List<Comment> comments = new ArrayList<>();
    @Column(nullable = false)
    private LocalDateTime createdOn;
    @Column(nullable = false)
    private LocalDateTime updatedOn;


}
