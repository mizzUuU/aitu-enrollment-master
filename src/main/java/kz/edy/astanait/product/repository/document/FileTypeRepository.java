package kz.edy.astanait.product.repository.document;

import kz.edy.astanait.product.model.document.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileTypeRepository extends JpaRepository<FileType, Long> {

    Optional<FileType> findByRequestParamType(String requestParamType);
}
