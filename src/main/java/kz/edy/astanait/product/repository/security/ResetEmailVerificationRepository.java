package kz.edy.astanait.product.repository.security;

import kz.edy.astanait.product.model.security.ResetEmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetEmailVerificationRepository extends JpaRepository<ResetEmailVerification, Long> {
        Optional<ResetEmailVerification> findByToken(String token);
}
