package bg.lostlanguagelab.model.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRecordDto {
    private Long id;
    private String word;
    private Long time;
}