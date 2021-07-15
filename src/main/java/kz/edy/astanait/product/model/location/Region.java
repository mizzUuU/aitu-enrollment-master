package kz.edy.astanait.product.model.location;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "regions")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
