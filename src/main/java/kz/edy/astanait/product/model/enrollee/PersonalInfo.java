package kz.edy.astanait.product.model.enrollee;

import com.fasterxml.jackson.annotation.JsonFormat;
import kz.edy.astanait.product.model.User;
import kz.edy.astanait.product.model.document.UniversityFile;
import kz.edy.astanait.product.model.location.Country;
import kz.edy.astanait.product.model.location.Locality;
import kz.edy.astanait.product.model.location.Nationality;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
public class PersonalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToOne
    @JoinColumn(name = "image3x4_id")
    private UniversityFile image3x4;

    @Column(length = 12, unique = true)
    private String iin;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    private Boolean isMale;

    @OneToOne
    @JoinColumn(name = "nationality_id")
    private Nationality nationality;

    @OneToOne
    @JoinColumn(name = "citizenship_id")
    private Country citizenship;

    private String residenceAddress;

    private String lifeAddress;

    private String firstParentName;

    private String firstParentSurname;

    private String firstParentPatronymic;

    private String firstParentPhoneNumber;

    private String secondParentName;

    private String secondParentSurname;

    private String secondParentPatronymic;

    private String secondParentPhoneNumber;

}
