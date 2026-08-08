package bg.lostlanguagelab.searchservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "search_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private Long time;
}

