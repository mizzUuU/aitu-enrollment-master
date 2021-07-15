package kz.edy.astanait.product.controller.location;

import kz.edy.astanait.product.dto.responseDto.location.*;
import kz.edy.astanait.product.exception.ExceptionHandling;
import kz.edy.astanait.product.service.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController extends ExceptionHandling {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/region")
    public ResponseEntity<List<RegionDtoResponse>> getAllRegions() {
     List<RegionDtoResponse> regionDtoResponses = locationService.getAllRegions();
     return new ResponseEntity<>(regionDtoResponses, OK);
    }

    @GetMapping("/locality")
    public ResponseEntity<List<LocalityDtoResponse>> getAllLocalities() {
        List<LocalityDtoResponse> localityDtoResponses = locationService.getAllLocalities();
        return new ResponseEntity<>(localityDtoResponses, OK);
    }

    @GetMapping("/locality-type")
    public ResponseEntity<List<LocalityTypeDtoResponse>> getAllLocalityTypes() {
        List<LocalityTypeDtoResponse> localityTypeDtoResponses = locationService.getAllLocalitiesType();
        return new ResponseEntity<>(localityTypeDtoResponses, OK);
    }

    @GetMapping("/street")
    public ResponseEntity<List<StreetDtoResponse>> getAllStreets() {
        List<StreetDtoResponse> streetDtoResponses = locationService.getAllStreets();
        return new ResponseEntity<>(streetDtoResponses, OK);
    }

    @GetMapping("/nationality")
    public ResponseEntity<List<NationalityDtoResponse>> getAllNationalities() {
        List<NationalityDtoResponse> nationalityDtoResponses = locationService.getAllNationalities();
        return new ResponseEntity<>(nationalityDtoResponses, OK);
    }

    @GetMapping("/country")
    public ResponseEntity<List<CountryDtoResponse>> getAllCountries() {
        List<CountryDtoResponse> countryDtoResponses = locationService.getAllCountries();
        return new ResponseEntity<>(countryDtoResponses, OK);
    }
}
