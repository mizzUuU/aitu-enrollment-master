package kz.edy.astanait.product.model.enrollee;

import com.fasterxml.jackson.annotation.JsonFormat;
import kz.edy.astanait.product.model.User;
import kz.edy.astanait.product.model.document.DocumentIssuingAuthority;
import kz.edy.astanait.product.model.document.IdentityDocumentType;
import kz.edy.astanait.product.model.document.UniversityFile;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "Documents")
public class DocumentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "identity_document_type_id")
    private IdentityDocumentType identityDocumentType;

    private String otherDocumentType;

    private String identityDocumentNumber;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(columnDefinition = "date")
    private LocalDate identityDocumentIssueDate;

    @ManyToOne
    @JoinColumn(name = "identity_document_issuing_authority_id")
    private DocumentIssuingAuthority documentIssuingAuthority;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(columnDefinition = "date")
    private LocalDate identityDocumentValidityPeriod;

    @OneToOne
    @JoinColumn(name = "identity_document_front_file_id")
    private UniversityFile identityDocumentScanFront;

    @OneToOne
    @JoinColumn(name = "identity_document_back_file_id")
    private UniversityFile identityDocumentScanBack;

    @OneToOne
    @JoinColumn(name = "form_086_file_id")
    private UniversityFile form086;

    @OneToOne
    @JoinColumn(name = "ent_certificate")
    private UniversityFile entCertificate;

    @OneToOne
    @JoinColumn(name = "english_certificate")
    private UniversityFile englishCertificate;

    @OneToOne
    @JoinColumn(name = "graduation_certificate")
    private UniversityFile graduationCertificate;

    @OneToOne
    @JoinColumn(name = "graduation_certificate_application")
    private UniversityFile graduationCertificateApplication;
}
