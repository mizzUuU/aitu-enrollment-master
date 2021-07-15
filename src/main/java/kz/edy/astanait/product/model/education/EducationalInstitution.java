package kz.edy.astanait.product.model.education;

import kz.edy.astanait.product.model.location.Street;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "educational_institutions")
public class EducationalInstitution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "street_id")
    private Street street;

    @ManyToOne
    @JoinColumn(name = "education_institution_type_id", nullable = false)
    private EducationInstitutionType institutionType;

    @Column(nullable = false)
    private String name;
}
