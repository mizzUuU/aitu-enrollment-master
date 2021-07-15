package kz.edy.astanait.product.model.education;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "academic_degrees")
public class AcademicDegree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
