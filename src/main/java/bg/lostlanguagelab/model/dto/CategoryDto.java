package bg.lostlanguagelab.model.dto;

import bg.lostlanguagelab.category.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private UUID categoryId;

    @NotBlank(message = "Типът е задължителен")
    private CategoryType category;

    @Size(max = 255, message = "Описанието трябва да е до 255 символа")
    private String description;
}

