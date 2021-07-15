package kz.edy.astanait.product.dto.responseDto.location;

import lombok.Data;

@Data
public class LocalityDtoResponse {
    private Long id;
    private RegionDtoResponse region;
    private String name;
    private LocalityTypeDtoResponse localityType;
}
