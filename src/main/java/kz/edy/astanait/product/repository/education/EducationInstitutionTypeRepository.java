package kz.edy.astanait.product.repository.education;

import kz.edy.astanait.product.model.education.EducationInstitutionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationInstitutionTypeRepository extends JpaRepository<EducationInstitutionType, Long> {
}
