package kz.edy.astanait.product.dto.responseDto.document;

import kz.edy.astanait.product.dto.responseDto.UserDtoResponse;
import lombok.Data;

@Data
public class UniversityFileDtoResponse {
    private Long id;
    private FileTypeDtoResponse fileType;
    private String path;
    private UserDtoResponse userDtoResponse;
}
