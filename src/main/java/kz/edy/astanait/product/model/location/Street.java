package kz.edy.astanait.product.model.location;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "streets")
@Data
public class Street {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String number;

    private Integer fraction;

    @ManyToOne
    @JoinColumn(name = "locality_id", nullable = false)
    private Locality locality;
}
