package bg.lostlanguagelab.model.dto;

import bg.lostlanguagelab.user.entity.UserRole;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRoleDTO {
    private UserRole role;
}
