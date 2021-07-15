package kz.edy.astanait.product.model.education;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ent_details")
@Data
public class ENTDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer mathematicalLiteracyPoint;
    private Integer readingLiteracyPoint;
    private Integer kazakhstanHistoryPoint;
    private Integer profileSubject1Point;
    private Integer profileSubject2Point;
}
