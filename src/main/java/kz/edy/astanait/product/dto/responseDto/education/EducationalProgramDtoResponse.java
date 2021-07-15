package kz.edy.astanait.product.dto.responseDto.education;
import lombok.Data;

@Data
public class EducationalProgramDtoResponse {
    private Long id;

    private String name;

    private EducationalProgramGroupDtoResponse educationalProgramGroup;
}
