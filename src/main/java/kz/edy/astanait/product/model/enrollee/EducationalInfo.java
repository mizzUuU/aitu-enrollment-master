package kz.edy.astanait.product.model.enrollee;

import com.fasterxml.jackson.annotation.JsonFormat;
import kz.edy.astanait.product.model.User;
import kz.edy.astanait.product.model.education.DistinctionMarkType;
import kz.edy.astanait.product.model.education.EducationInstitutionType;
import kz.edy.astanait.product.model.education.EducationalInstitution;
import kz.edy.astanait.product.model.location.Country;
import kz.edy.astanait.product.model.location.Locality;
import kz.edy.astanait.product.model.location.Region;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class EducationalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "kz_graduation_locality_id")
    private Locality kzGraduationLocality;

    @ManyToOne
    @JoinColumn(name = "kz_educational_institution_id")
    private EducationalInstitution kzEducationalInstitution;

    private String educationalInstitution;

    @ManyToOne
    @JoinColumn(name = "kz_distinction_mark_type_id")
    private DistinctionMarkType distinctionMarkType;

    private String kzGraduationCertificateSeries;

    private String kzGraduationCertificateNumber;

    private String kzGraduationCertificateAveragePoint;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate graduationCertificateIssueDate;

    private String graduationCertificateName;

    private String nostrificationCertificateNumber;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate nostrificationCertificateDate;

    private String specialityName;

    @ManyToOne
    @JoinColumn(name = "education_institution_type")
    private EducationInstitutionType educationInstitutionType;
}
