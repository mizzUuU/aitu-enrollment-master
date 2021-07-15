package kz.edy.astanait.product.service.location;

import kz.edy.astanait.product.dto.responseDto.location.*;
import kz.edy.astanait.product.model.location.*;
import kz.edy.astanait.product.repository.location.*;
import kz.edy.astanait.product.utils.facade.location.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    private final RegionRepository regionRepository;
    private final LocalityRepository localityRepository;
    private final LocalityTypeRepository localityTypeRepository;
    private final StreetRepository streetRepository;
    private final NationalityRepository nationalityRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public LocationService(RegionRepository regionRepository, LocalityRepository localityRepository, LocalityTypeRepository localityTypeRepository, StreetRepository streetRepository, NationalityRepository nationalityRepository, CountryRepository countryRepository) {
        this.regionRepository = regionRepository;
        this.localityRepository = localityRepository;
        this.localityTypeRepository = localityTypeRepository;
        this.streetRepository = streetRepository;
        this.nationalityRepository = nationalityRepository;
        this.countryRepository = countryRepository;
    }

    public List<RegionDtoResponse> getAllRegions() {
        List<Region> regions = regionRepository.findAll();
        List<RegionDtoResponse> regionDtoResponses = new ArrayList<>();

        regions.forEach(value -> regionDtoResponses.add(new RegionFacade().regionToRegionDtoResponse(value)));
        return regionDtoResponses;
    }

    public List<LocalityDtoResponse> getAllLocalities() {
        List<Locality> localities = localityRepository.findAll();
        List<LocalityDtoResponse> localityDtoResponses = new ArrayList<>();

        localities.forEach(value -> localityDtoResponses.add(new LocalityFacade().localityToLocalityDtoResponse(value)));
        return localityDtoResponses;
    }

    public List<LocalityTypeDtoResponse> getAllLocalitiesType() {
        List<LocalityType> localityTypes = localityTypeRepository.findAll();
        List<LocalityTypeDtoResponse> localityTypeDtoResponses = new ArrayList<>();

        localityTypes.forEach(value -> localityTypeDtoResponses.add(new LocalityTypeFacade().localityToLocalityTypeDtoResponse(value)));
        return localityTypeDtoResponses;
    }

    public List<StreetDtoResponse> getAllStreets() {
        List<Street> streets = streetRepository.findAll();
        List<StreetDtoResponse> streetDtoResponses = new ArrayList<>();

        streets.forEach(value -> streetDtoResponses.add(new StreetFacade().streetToStreetDtoResponse(value)));
        return streetDtoResponses;
    }

    public List<NationalityDtoResponse> getAllNationalities() {
        List<Nationality> nationalities = nationalityRepository.findAll();
        List<NationalityDtoResponse> nationalityDtoResponses = new ArrayList<>();

        nationalities.forEach(value -> nationalityDtoResponses.add(new NationalityFacade().nationalityToNationalityDtoResponse(value)));
        return nationalityDtoResponses;
    }

    public List<CountryDtoResponse> getAllCountries() {
        List<Country> countries = countryRepository.findAll();
        List<CountryDtoResponse> countryDtoResponses = new ArrayList<>();

        countries.forEach(value -> countryDtoResponses.add(new CountryFacade().countryToCountryDtoResponse(value)));
        return countryDtoResponses;
    }

}
