package student_mang_sys.Std_system;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface repository extends MongoRepository<std_Attribute, String> {

    // ✅ custom method for login / registration
    Optional<std_Attribute> findByEmail(String email);

    // ✅ custom method for fetching user-specific students
    List<std_Attribute> findByCreatedBy(String createdBy);
}
