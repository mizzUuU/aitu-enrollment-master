package kz.edy.astanait.product.dto.responseDto.location;

import lombok.Data;

@Data
public class StreetDtoResponse {
    private Long id;

    private String name;

    private String number;

    private int fraction;

    private LocalityDtoResponse locality;
}
