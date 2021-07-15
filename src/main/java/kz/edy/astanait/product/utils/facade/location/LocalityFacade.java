package kz.edy.astanait.product.utils.facade.location;

import kz.edy.astanait.product.dto.responseDto.location.LocalityDtoResponse;
import kz.edy.astanait.product.model.location.Locality;

public class LocalityFacade {

    public LocalityDtoResponse localityToLocalityDtoResponse(Locality locality) {
        LocalityDtoResponse localityDtoResponse = new LocalityDtoResponse();
        localityDtoResponse.setId(locality.getId());
        if (locality.getRegion() != null) {
            localityDtoResponse.setRegion(new RegionFacade().regionToRegionDtoResponse(locality.getRegion()));
        }
        localityDtoResponse.setName(locality.getName());
        if (locality.getLocalityType() != null) {
            localityDtoResponse.setLocalityType(new LocalityTypeFacade().localityToLocalityTypeDtoResponse(locality.getLocalityType()));
        }
        return localityDtoResponse;
    }

}
