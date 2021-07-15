package kz.edy.astanait.product.repository.location;

import kz.edy.astanait.product.model.location.LocalityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalityTypeRepository extends JpaRepository<LocalityType, Long> {
}
