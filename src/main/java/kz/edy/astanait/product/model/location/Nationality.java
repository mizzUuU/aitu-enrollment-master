package kz.edy.astanait.product.model.location;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "nationalities")
public class Nationality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;
}
