package kz.edy.astanait.product.model.security;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false, unique = true)
    private String authorityName;
    @ManyToMany(mappedBy = "authorities", fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    public Authority() {
    }

    public Authority(Long id, String authorityName, List<Role> roles) {
        this.id = id;
        this.authorityName = authorityName;
        this.roles = roles;
    }
}
