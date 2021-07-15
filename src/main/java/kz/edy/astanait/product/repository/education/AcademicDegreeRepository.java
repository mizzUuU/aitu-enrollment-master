package kz.edy.astanait.product.repository.education;

import kz.edy.astanait.product.model.education.AcademicDegree;
import kz.edy.astanait.product.model.enrollee.AdmissionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcademicDegreeRepository extends JpaRepository<AcademicDegree, Long> {
}
