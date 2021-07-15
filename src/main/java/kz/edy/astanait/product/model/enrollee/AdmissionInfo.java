package kz.edy.astanait.product.model.enrollee;

import com.fasterxml.jackson.annotation.JsonFormat;
import kz.edy.astanait.product.model.User;
import kz.edy.astanait.product.model.education.*;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "admission_info")
public class AdmissionInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "academic_degree_id")
    private AcademicDegree academicDegree;

    @ManyToOne
    @JoinColumn(name = "educational_program_id")
    private EducationalProgram educationalProgram;

    @ManyToOne
    @JoinColumn(name = "exam_type_id")
    private ExamType examType;

    @JsonFormat(pattern = "yyyy")
    @Column(nullable = false, updatable = false)
    private LocalDate year;

    private String combinedExamPointInformatics;

    private String combinedExamPointEnglish;

    @ManyToOne
    @JoinColumn(name = "english_certification_type_id")
    private EnglishCertificateType englishCertificateType;

    @ManyToOne
    @JoinColumn(name = "ent_details_id")
    private ENTDetails entDetails;

    private String ikt;

    private String interviewPoints;

    private String creativeExamPoints;

    @PrePersist
    protected void onCreate() {
        this.year = LocalDate.now();
    }
}
