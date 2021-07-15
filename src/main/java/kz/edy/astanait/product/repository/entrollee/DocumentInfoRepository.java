package kz.edy.astanait.product.repository.entrollee;

import kz.edy.astanait.product.model.enrollee.DocumentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DocumentInfoRepository extends JpaRepository<DocumentInfo, Long> {
    Optional<DocumentInfo> findByUserEmail(String email);
    Optional<DocumentInfo> findByUserId(Long id);
    List<DocumentInfo> findAllByUserIdIn(Set<Long> abiturIds);
}
