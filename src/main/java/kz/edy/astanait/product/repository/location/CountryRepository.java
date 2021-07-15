package kz.edy.astanait.product.repository.location;

import kz.edy.astanait.product.model.location.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {}
