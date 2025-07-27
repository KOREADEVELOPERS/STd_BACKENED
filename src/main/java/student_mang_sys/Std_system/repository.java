package student_mang_sys.Std_system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface repository extends JpaRepository<std_Attribute, Long> {
    @Query("SELECT s FROM std_Attribute s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.email) LIKE LOWER(CONCAT('%', :query, '%')) OR s.phone LIKE CONCAT('%', :query, '%')")
    List<std_Attribute> searchStudents(@Param("query") String query);
    Optional<std_Attribute> findByEmailAndPassword(String email, String password);
    List<std_Attribute> findByCreatedBy(String email);
}
