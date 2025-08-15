package student_mang_sys.Std_system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "studentmanagement") // MongoDB collection ka naam
public class std_Attribute {

    @Id
    private String id; // MongoDB String type ka ObjectId hota hai

    private String name;
    private String email;
    private String phone;
    private String password;
    private String createdBy;
}
