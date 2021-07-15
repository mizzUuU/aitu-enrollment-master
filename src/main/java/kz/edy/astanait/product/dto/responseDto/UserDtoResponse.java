package kz.edy.astanait.product.dto.responseDto;

import kz.edy.astanait.product.dto.responseDto.security.RoleDtoResponse;
import lombok.Data;

@Data
public class UserDtoResponse {
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String phoneNumber;
    private RoleDtoResponse role;
    private Boolean isActive;
}
