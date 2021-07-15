package kz.edy.astanait.product.model.document;

import kz.edy.astanait.product.model.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "university_files")
public class UniversityFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_type_id", nullable = false)
    private FileType fileType;
    @Column(nullable = false, unique = true)
    private String path;
}
