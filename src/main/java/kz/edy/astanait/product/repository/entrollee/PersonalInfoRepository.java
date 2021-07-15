package kz.edy.astanait.product.repository.entrollee;

import kz.edy.astanait.product.model.enrollee.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {
    Optional<PersonalInfo> findByUserEmail(String email);
    Optional<PersonalInfo> findByUserId(Long id);
    List<PersonalInfo> findByIinStartingWith(String iin);
    List<PersonalInfo> findAllByUserIdIn(Set<Long> abiturIds);
}
