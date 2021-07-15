package kz.edy.astanait.product.utils.facade.location;

import kz.edy.astanait.product.dto.responseDto.location.StreetDtoResponse;
import kz.edy.astanait.product.model.location.Street;

public class StreetFacade {

    public StreetDtoResponse streetToStreetDtoResponse(Street street) {
        StreetDtoResponse streetDtoResponse = new StreetDtoResponse();
        streetDtoResponse.setId(street.getId());
        streetDtoResponse.setName(street.getName());
        streetDtoResponse.setNumber(street.getNumber());
        if (street.getFraction() != null) {
            streetDtoResponse.setFraction(street.getFraction());
        }
        if (street.getLocality() != null) {
            streetDtoResponse.setLocality(new LocalityFacade().localityToLocalityDtoResponse(street.getLocality()));
        }
        return streetDtoResponse;
    }
}
