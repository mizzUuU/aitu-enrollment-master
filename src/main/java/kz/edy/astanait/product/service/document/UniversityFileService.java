package kz.edy.astanait.product.service.document;

import kz.edy.astanait.product.model.User;
import kz.edy.astanait.product.model.document.FileType;
import kz.edy.astanait.product.model.document.UniversityFile;
import kz.edy.astanait.product.repository.document.UniversityFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.Optional;

@Service
public class UniversityFileService {
    private final UniversityFileRepository universityFileRepository;

    @Autowired
    public UniversityFileService(UniversityFileRepository universityFileRepository) {
        this.universityFileRepository = universityFileRepository;
    }

    public UniversityFile saveUniversityFile(User user, FileType fileType, String path) {
        Optional<UniversityFile> optional = universityFileRepository
                .findByPathStartingWith(path.substring(0, path.lastIndexOf('.')));
        UniversityFile universityFile;
        if (optional.isEmpty()) {
            universityFile = new UniversityFile();
            universityFile.setFileType(fileType);
        } else {
            universityFile = optional.get();
        }
        universityFile.setUser(user);
        universityFile.setPath(path);
        return universityFileRepository.save(universityFile);
    }

    public UniversityFile findUniversityFileById(Long id){
        Optional<UniversityFile> optional = universityFileRepository.findById(id);
        return optional.orElseThrow(NoResultException::new);
    }
}
