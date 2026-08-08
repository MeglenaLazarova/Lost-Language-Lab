package bg.lostlanguagelab.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
    public class EditProfileDTO {

        @NotBlank
        private String username;

        @Email
        @NotBlank
        private String email;

        private String password;
}
