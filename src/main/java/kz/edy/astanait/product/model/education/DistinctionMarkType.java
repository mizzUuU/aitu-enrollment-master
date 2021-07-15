package kz.edy.astanait.product.model.education;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "kz_distinction_mark_types")
public class DistinctionMarkType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "education_institution_type_id", nullable = false)
    private EducationInstitutionType institutionType;

    @Column(nullable = false)
    private String name;
}
