package student_mang_sys.Std_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class std_imple implements std_service {

    @Autowired
    repository res; // MongoRepository<std_Attribute, String>

    @Override
    public String Addingstudents(std_Attribute std) {
        res.save(std);
        return "✅ Data Saved Successfully!";
    }

    @Override
    public List<std_Attribute> showemoloyee() {
        return res.findAll();
    }

    @Override
    public String deletestudent(String id) {
        if(res.existsById(id)){
            res.deleteById(id);
            return "Data deleted successfully";
        } else {
            return "ID not found";
        }
    }

    @Override
    public String saveAllStudents(List<std_Attribute> students) {
        res.saveAll(students);
        return "✅ All Students Saved Successfully!";
    }

    @Override
    public List<std_Attribute> getstudent() {
        return res.findAll();
    }

    // 🔍 Fetch student by ID
    @Override
    public std_Attribute getStudentById(String id) {
        Optional<std_Attribute> optional = res.findById(id);
        return optional.orElse(null);
    }

    // ✏ Update only name, email, phone
    @Override
    public std_Attribute updateStudent(String id, std_Attribute updatedStudent) {
        Optional<std_Attribute> optional = res.findById(id);
        if(!optional.isPresent()) return null;

        std_Attribute existing = optional.get();
        existing.setName(updatedStudent.getName());
        existing.setEmail(updatedStudent.getEmail());
        existing.setPhone(updatedStudent.getPhone());
        // password & createdBy remain unchanged
        return res.save(existing);
    }
}
