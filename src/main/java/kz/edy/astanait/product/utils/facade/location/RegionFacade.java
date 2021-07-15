package kz.edy.astanait.product.utils.facade.location;

import kz.edy.astanait.product.dto.responseDto.location.RegionDtoResponse;
import kz.edy.astanait.product.model.location.Region;

public class RegionFacade {

    public RegionDtoResponse regionToRegionDtoResponse(Region region) {
        RegionDtoResponse regionDtoResponse = new RegionDtoResponse();
        regionDtoResponse.setId(region.getId());
        regionDtoResponse.setName(region.getName());
        return regionDtoResponse;
    }
}
