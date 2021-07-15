package kz.edy.astanait.product.repository.security;

import kz.edy.astanait.product.model.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AuthoritiesRepository extends JpaRepository<Authority, Long> {
}
