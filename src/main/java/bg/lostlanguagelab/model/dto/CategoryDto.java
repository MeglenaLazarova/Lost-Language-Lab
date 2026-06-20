package bg.lostlanguagelab.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    @NotBlank(message = "Типът е задължителен")
    @Size(min = 2, max = 50, message = "Типът трябва да е между 2 и 50 символа")
    private String type;

    @Size(max = 255, message = "Описанието трябва да е до 255 символа")
    private String description;
}

