package kz.edy.astanait.product.repository.document;

import kz.edy.astanait.product.model.document.UniversityFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityFileRepository extends JpaRepository<UniversityFile, Long> {
    Optional<UniversityFile> findByPathStartingWith(String prePath);
}
