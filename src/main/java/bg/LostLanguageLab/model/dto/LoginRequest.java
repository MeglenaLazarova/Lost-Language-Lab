package bg.LostLanguageLab.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest{
    @NotBlank
    @Size(min = 6, message = "Username must be at least 6 characters")
    private String username;
    @NotBlank
    @Size(min = 6, max = 6, message = "Password must be exactly 6 characters")
    private String password;

}
