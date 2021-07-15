package kz.edy.astanait.product.model.document;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "document_issuing_authorities")
public class DocumentIssuingAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
