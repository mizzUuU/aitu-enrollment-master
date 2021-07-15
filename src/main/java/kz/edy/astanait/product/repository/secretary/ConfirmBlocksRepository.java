package kz.edy.astanait.product.repository.secretary;

import kz.edy.astanait.product.model.secretary.ConfirmBlocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmBlocksRepository extends JpaRepository<ConfirmBlocks, Long> {
    Optional<ConfirmBlocks> findByUserId(Long id);
    Optional<ConfirmBlocks> findByUserEmail(String email);
}
