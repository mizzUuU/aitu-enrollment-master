package kz.edy.astanait.product.repository.location;

import kz.edy.astanait.product.model.location.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NationalityRepository extends JpaRepository<Nationality, Long> {}
