package kz.edy.astanait.product.dto.requestDto.admin;

import lombok.Data;

import java.util.List;

@Data
public class CreateUsersDtoRequest {
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String password;
    private String phoneNumber;
    private Long roleId;
    private String role;
    private List<Long> authoritiesId;

}
