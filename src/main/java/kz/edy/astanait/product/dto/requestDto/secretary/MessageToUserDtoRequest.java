package kz.edy.astanait.product.dto.requestDto.secretary;

import lombok.Data;

@Data
public class MessageToUserDtoRequest {
    String email;
    String message;
}
