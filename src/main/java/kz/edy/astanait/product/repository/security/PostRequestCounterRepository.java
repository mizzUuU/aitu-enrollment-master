package kz.edy.astanait.product.repository.security;

import kz.edy.astanait.product.model.security.PostRequestCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRequestCounterRepository extends JpaRepository<PostRequestCounter, Long> {
    Optional<PostRequestCounter> findByUserEmail(String email);
}
