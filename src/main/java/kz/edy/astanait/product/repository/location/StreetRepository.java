package kz.edy.astanait.product.repository.location;

import kz.edy.astanait.product.model.location.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreetRepository extends JpaRepository<Street, Long> {
}
