package kz.edy.astanait.product.model.secretary;

import kz.edy.astanait.product.model.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ConfirmBlocks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean firstBlock;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean secondBlock;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean thirdBlock;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean forthBlock;
}
