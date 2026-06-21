package bg.lostlanguagelab.model.dto;

import bg.lostlanguagelab.category.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArchaicWordDto {

    @NotBlank(message = "Думата е задължителна")
    @Size(min = 2, max = 50, message = "Думата трябва да е между 2 и 50 символа")
    private String word;

    @NotBlank(message = "Значението е задължително")
    @Size(min = 5, message = "Значението трябва да е поне 5 символа")
    private String meaning;

    @Size(max = 255, message = "Етимологията трябва да е до 255 символа")
    private String etymology;

    @Size(max = 255, message = "Примерът трябва да е до 255 символа")
    private String exampleUsage;

    @NotNull(message = "Категорията е задължителна")
    private CategoryType category;


}
