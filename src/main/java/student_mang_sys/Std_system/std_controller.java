package student_mang_sys.Std_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/employees")
public class std_controller {

    @Autowired
    std_service ser;

    @Autowired
    repository reps;

    // Save multiple students for logged-in user (with email as "createdBy")
    @PostMapping("/fit")
    public ResponseEntity<String> saveMultiple(
            @RequestBody List<std_Attribute> students,
            @RequestParam String email) {

        if (students == null || students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No students provided");
        }

        for (std_Attribute student : students) {
            if (student.getName() == null || student.getEmail() == null || student.getPhone() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All fields are required for every student");
            }
            student.setCreatedBy(email);
        }

        ser.saveAllStudents(students);
        return ResponseEntity.status(HttpStatus.CREATED).body("Students registered successfully");
    }

    // Save single student
    @PostMapping("/save")
    public ResponseEntity<String> saveSingle(@RequestBody std_Attribute student) {
        if (student.getName() == null || student.getEmail() == null || student.getPhone() == null || student.getCreatedBy() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All fields including createdBy are required");
        }

        reps.save(student);
        return ResponseEntity.ok("Student saved successfully");
    }

    // Show all students
    @GetMapping("/show")
    public List<std_Attribute> displayAll() {
        return ser.showemoloyee();
    }

    // Alternate show method
    @GetMapping("/find")
    public List<std_Attribute> fetchAll() {
        return ser.getstudent();
    }

    // Delete student by ID
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        return ser.deletestudent(id);
    }

    // Login endpoint - returns email if valid
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody std_Attribute request) {
        Optional<std_Attribute> user = reps.findByEmailAndPassword(request.getEmail(), request.getPassword());

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getEmail()); // ✅ plain string
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // Fetch only the logged-in user's students
    @GetMapping("/my")
    public ResponseEntity<List<std_Attribute>> getMyStudents(@RequestParam String email) {
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<std_Attribute> myStudents = reps.findByCreatedBy(email);
        return ResponseEntity.ok(myStudents);
    }
}
