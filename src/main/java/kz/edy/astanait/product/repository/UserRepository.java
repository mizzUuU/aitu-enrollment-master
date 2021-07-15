package kz.edy.astanait.product.repository;

import kz.edy.astanait.product.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    List<User> findByNameContainsAndSurnameContainsAndPatronymicContains(String name, String surname, String patronymic);
    List<User> findByNameContainsAndSurnameContains(String name, String surname);
}
