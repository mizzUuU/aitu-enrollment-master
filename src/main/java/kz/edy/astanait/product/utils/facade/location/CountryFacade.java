package kz.edy.astanait.product.utils.facade.location;

import kz.edy.astanait.product.dto.responseDto.location.CountryDtoResponse;
import kz.edy.astanait.product.model.location.Country;

public class CountryFacade {

    public CountryDtoResponse countryToCountryDtoResponse(Country country) {
        CountryDtoResponse countryDtoResponse = new CountryDtoResponse();
        countryDtoResponse.setId(country.getId());
        countryDtoResponse.setName(country.getName());
        return countryDtoResponse;
    }
}
