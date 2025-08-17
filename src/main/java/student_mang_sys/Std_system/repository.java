package student_mang_sys.Std_system;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface repository extends MongoRepository<std_Attribute, String> {

    // Check if email exists
    Optional<std_Attribute> findByEmail(String email);

    // Login with email + password
    Optional<std_Attribute> findByEmailAndPassword(String email, String password);

    // Fetch students created by a user
    List<std_Attribute> findByCreatedBy(String createdBy);
}
