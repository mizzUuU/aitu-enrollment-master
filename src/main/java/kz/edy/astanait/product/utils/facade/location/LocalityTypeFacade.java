package kz.edy.astanait.product.utils.facade.location;

import kz.edy.astanait.product.dto.responseDto.location.LocalityTypeDtoResponse;
import kz.edy.astanait.product.model.location.LocalityType;

public class LocalityTypeFacade {

    public LocalityTypeDtoResponse localityToLocalityTypeDtoResponse(LocalityType localityType) {
        LocalityTypeDtoResponse localityTypeDtoResponse = new LocalityTypeDtoResponse();
        localityTypeDtoResponse.setId(localityType.getId());
        localityTypeDtoResponse.setName(localityType.getName());
        return localityTypeDtoResponse;
    }
}
