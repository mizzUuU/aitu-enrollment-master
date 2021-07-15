package kz.edy.astanait.product.repository.education;

import kz.edy.astanait.product.model.education.ExamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamTypeRepository extends JpaRepository<ExamType, Long> {
}
