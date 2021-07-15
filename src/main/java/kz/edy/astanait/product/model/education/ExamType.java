package kz.edy.astanait.product.model.education;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "exam_types")
public class ExamType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
}
