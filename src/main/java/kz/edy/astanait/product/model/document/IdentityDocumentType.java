package kz.edy.astanait.product.model.document;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "identity_document_types")
public class IdentityDocumentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
