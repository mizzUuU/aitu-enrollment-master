package kz.edy.astanait.product.model.education;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "educational_program_groups")
public class EducationalProgramGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String educationalProgramCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject1_id")
    private Subject subject1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject2_id")
    private Subject subject2;

    @Column
    private Boolean isCreativeExam = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "academic_degree_id")
    private AcademicDegree academicDegree;

}
