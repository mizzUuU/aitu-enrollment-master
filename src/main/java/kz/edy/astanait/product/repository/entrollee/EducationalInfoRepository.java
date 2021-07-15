package kz.edy.astanait.product.repository.entrollee;

import kz.edy.astanait.product.model.enrollee.EducationalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EducationalInfoRepository extends JpaRepository<EducationalInfo, Long> {
    Optional<EducationalInfo> findByUserEmail(String email);
    List<EducationalInfo> findByKzGraduationLocality_Name(String locality);
    Optional<EducationalInfo> findByUserId(Long id);
    List<EducationalInfo> findAllByUserIdIn(Set<Long> abiturIds);
}
