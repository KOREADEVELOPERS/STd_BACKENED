package student_mang_sys.Std_system;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface repository extends MongoRepository<std_Attribute, String> {
    Optional<std_Attribute> findByEmailAndPassword(String email, String password);
    List<std_Attribute> findByCreatedBy(String email);
}
