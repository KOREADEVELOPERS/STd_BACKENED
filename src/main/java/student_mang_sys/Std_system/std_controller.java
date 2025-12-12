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

    // ➕ Add Multiple Students
    @PostMapping("/add-multiple/{email}")
    public ResponseEntity<?> addMultipleStudents(
            @PathVariable String email,
            @RequestBody List<Student> students) {

        List<Student> saved = service.saveMultipleStudents(students, email);
        return ResponseEntity.ok(saved);
    }

    // ➕ Add Single Student
    @PostMapping("/add/{email}")
    public ResponseEntity<?> addStudent(
            @PathVariable String email,
            @RequestBody Student student) {

        Student saved = service.saveStudent(student, email);
        return ResponseEntity.ok(saved);
    }

    // 📌 Get All Students of Logged-in User
    @GetMapping("/all/{email}")
    public ResponseEntity<?> getStudentsByUser(@PathVariable String email) {
        return ResponseEntity.ok(service.getStudentsByEmail(email));
    }

    // 🔍 Get Student by ID (MongoDB)
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable String id) {
        Student student = service.getStudentById(id);

        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found");
        }
        return ResponseEntity.ok(student);
    }

    // ✏ Update Student by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudent(
            @PathVariable String id,
            @RequestBody Student updatedStudent) {

        Student updated = service.updateStudent(id, updatedStudent);

        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found");
        }
        return ResponseEntity.ok(updated);
    }

    // ❌ Delete Student
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable String id) {
        boolean deleted = service.deleteStudent(id);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found");
        }

        return ResponseEntity.ok("Deleted Successfully");
    }
}
