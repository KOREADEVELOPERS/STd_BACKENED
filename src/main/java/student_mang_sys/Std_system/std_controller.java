package student_mang_sys.Std_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://managementstudentreal-vzfo.vercel.app")
@RestController
@RequestMapping("/students")
public class std_controller {

    @Autowired
    private std_service service;

    // 🔍 Get Student by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable String id) {
        std_Attribute student = service.getStudentById(id);
        if(student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
        return ResponseEntity.ok(student);
    }

    // ✏ Update Student by ID (only 3 fields)
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable String id, @RequestBody std_Attribute updatedStudent) {
        std_Attribute updated = service.updateStudent(id, updatedStudent);
        if(updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
        return ResponseEntity.ok(updated);
    }

    // Optional: Add Single Student
    @PostMapping("/add/{email}")
    public ResponseEntity<?> addStudent(@PathVariable String email, @RequestBody std_Attribute student) {
        student.setCreatedBy(email);
        service.Addingstudents(student);
        return ResponseEntity.ok(student);
    }

    // Optional: Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable String id) {
        String res = service.deletestudent(id);
        if(res.equals("ID not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
        return ResponseEntity.ok("Deleted Successfully");
    }
}
