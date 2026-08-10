package bg.lostlanguagelab.searchservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "search_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchRecord {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private Long time;

    @Transient
    private Long count;

}

