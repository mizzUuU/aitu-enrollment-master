package kz.edy.astanait.product.repository.document;

import kz.edy.astanait.product.model.document.IdentityDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentityDocumentTypeRepository extends JpaRepository<IdentityDocumentType, Long> {
}
