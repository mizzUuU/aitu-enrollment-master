package kz.edy.astanait.product.utils.facade.location;

import kz.edy.astanait.product.dto.responseDto.location.NationalityDtoResponse;
import kz.edy.astanait.product.model.location.Nationality;

public class NationalityFacade {

    public NationalityDtoResponse nationalityToNationalityDtoResponse(Nationality nationality) {
        NationalityDtoResponse nationalityDtoResponse = new NationalityDtoResponse();
        nationalityDtoResponse.setId(nationality.getId());
        nationalityDtoResponse.setName(nationality.getName());
        return nationalityDtoResponse;
    }
}
