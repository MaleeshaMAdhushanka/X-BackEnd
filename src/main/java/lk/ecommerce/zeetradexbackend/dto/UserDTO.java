package lk.ecommerce.zeetradexbackend.dto;

import lk.ecommerce.zeetradexbackend.enums.USER_ROLE;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    @NotBlank(message = "Email is required")
    private String emil;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Role is required")
    private USER_ROLE role;


}
