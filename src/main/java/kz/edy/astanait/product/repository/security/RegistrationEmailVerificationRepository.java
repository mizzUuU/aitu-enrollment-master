package kz.edy.astanait.product.repository.security;
import kz.edy.astanait.product.model.security.RegistrationEmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationEmailVerificationRepository extends JpaRepository<RegistrationEmailVerification, Long> {
    Optional<RegistrationEmailVerification> findByToken(String token);
}
