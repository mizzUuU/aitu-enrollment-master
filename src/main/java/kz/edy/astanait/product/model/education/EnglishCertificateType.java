package kz.edy.astanait.product.model.education;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "english_certificate_types")
public class EnglishCertificateType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
}
