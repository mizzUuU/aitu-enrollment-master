package kz.edy.astanait.product.model.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import kz.edy.astanait.product.model.User;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class PostRequestCounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private Integer counter;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime lastBlockedDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }
}
