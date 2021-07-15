package kz.edy.astanait.product.model.location;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "localities")
public class Locality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "locality_type_id", nullable = false)
    private LocalityType localityType;

}
