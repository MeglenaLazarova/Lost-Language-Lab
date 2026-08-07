package bg.lostlanguagelab.category.entity;

import bg.lostlanguagelab.archaicWord.entity.ArchaicWord;
import bg.lostlanguagelab.category.enums.CategoryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;
    @Column(columnDefinition = "TEXT")
    private String description;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @OrderBy("word asc")
    private List<ArchaicWord> words = new ArrayList<>();
    @Column(nullable = false)
    private String categoryName;
}