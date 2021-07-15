package kz.edy.astanait.product.model.education;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "educational_programs")
public class EducationalProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "educational_program_group_id")
    private EducationalProgramGroup educationalProgramGroup;
}
