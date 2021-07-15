package kz.edy.astanait.product.repository.document;

import kz.edy.astanait.product.model.document.DocumentIssuingAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentIssuingAuthorityRepository extends JpaRepository<DocumentIssuingAuthority, Long> {
}
