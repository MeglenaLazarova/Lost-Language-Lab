package bg.lostlanguagelab.model.dto;

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
    @Size(min = 2, max = 20, message = "Username must be at least 2 characters")
    private String username;
    @NotBlank
    @Size(min = 6, message = "Password must be min 6 characters")
    private String password;

}
