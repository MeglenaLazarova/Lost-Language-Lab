package bg.LostLanguageLab.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class CommentDto {

    @NotBlank(message = "Коментарът не може да е празен")
    @Size(min = 2, max = 255, message = "Коментарът трябва да е между 2 и 255 символа")
    private String content;

    @NotNull(message = "Трябва да има дума")
    private UUID wordId;

    @NotNull(message = "Трябва да има автор")
    private UUID authorId;
}

