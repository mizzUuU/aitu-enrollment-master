package kz.edy.astanait.product.dto.requestDto;

import lombok.Data;

@Data
public class UserDtoRequest {
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String password;
    private String phoneNumber;
}
