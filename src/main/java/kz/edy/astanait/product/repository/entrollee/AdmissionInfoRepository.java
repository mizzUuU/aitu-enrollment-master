package kz.edy.astanait.product.repository.entrollee;

import kz.edy.astanait.product.model.enrollee.AdmissionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AdmissionInfoRepository extends JpaRepository<AdmissionInfo, Long> {
    Optional<AdmissionInfo> findByUserEmail(String email);
    Optional<AdmissionInfo> findByUserId(Long id);
    List<AdmissionInfo> findAllByUserIdIn(Set<Long> abiturIds);
}
