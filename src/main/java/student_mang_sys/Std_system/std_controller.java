package student_mang_sys.Std_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "https://managementstudentreal-vzfo.vercel.app")
@RestController
@RequestMapping("/employees")
public class std_controller {

    @Autowired
    private std_service ser;

    @Autowired
    private repository reps;

    // ✔ Register new user
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody std_Attribute newUser) {

        if (newUser.getEmail() == null || newUser.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email and Password are required");
        }

        Optional<std_Attribute> existing = reps.findByEmail(newUser.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User already exists with this email");
        }

        reps.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    // ✔ Save multiple students
    @PostMapping("/saveall")
    public ResponseEntity<String> saveMultiple(
            @RequestBody List<std_Attribute> students,
            @RequestParam String email) {

        if (students == null || students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No students provided");
        }

        for (std_Attribute student : students) {
            if (student.getName() == null ||
                    student.getEmail() == null ||
                    student.getPhone() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("All fields are required for every student");
            }
            student.setCreatedBy(email);
        }

        ser.saveAllStudents(students);
        return ResponseEntity.status(HttpStatus.CREATED).body("Students registered successfully");
    }

    // ✔ Save single student
    @PostMapping("/save")
    public ResponseEntity<String> saveSingle(@RequestBody std_Attribute student) {

        if (student.getName() == null ||
                student.getEmail() == null ||
                student.getPhone() == null ||
                student.getCreatedBy() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("All fields including createdBy are required");
        }

        reps.save(student);
        return ResponseEntity.ok("Student saved successfully");
    }

    // ⭐ GET student by ID (for update)
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getStudentById(
            @PathVariable String id,
            @RequestParam String email) {

        Optional<std_Attribute> studentOpt = reps.findById(id);

        if (studentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }

        std_Attribute student = studentOpt.get();

        // ✅ Check if logged-in user owns this student
        if (!student.getCreatedBy().equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized to view this student");
        }

        return ResponseEntity.ok(student);
    }

    // ⭐ UPDATE student
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudent(
            @PathVariable String id,
            @RequestParam String email,
            @RequestBody std_Attribute updatedData) {

        Optional<std_Attribute> existingOpt = reps.findById(id);

        if (existingOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }

        std_Attribute student = existingOpt.get();

        // ✅ Only allow update if student belongs to logged-in user
        if (!student.getCreatedBy().equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized to update this student");
        }

        // Update fields
        student.setName(updatedData.getName());
        student.setEmail(updatedData.getEmail());
        student.setPhone(updatedData.getPhone());
        student.setAddress(updatedData.getAddress());
        student.setCourse(updatedData.getCourse());

        reps.save(student);
        return ResponseEntity.ok("Student updated successfully");
    }

    // ⭐ Search students by logged-in user
    @GetMapping("/find")
    public ResponseEntity<List<std_Attribute>> fetchByUser(@RequestParam String email) {

        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<std_Attribute> students = reps.findByCreatedBy(email);
        return ResponseEntity.ok(students);
    }

    // ✔ Delete student
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id, @RequestParam String email) {
        Optional<std_Attribute> studentOpt = reps.findById(id);

        if (studentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }

        std_Attribute student = studentOpt.get();

        if (!student.getCreatedBy().equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized to delete this student");
        }

        ser.deletestudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }

    // ✔ Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody std_Attribute request) {
        Optional<std_Attribute> user =
                reps.findByEmailAndPassword(request.getEmail(), request.getPassword());

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getEmail());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }
    }

    // ✔ Fetch logged-in user's students
    @GetMapping("/my")
    public ResponseEntity<List<std_Attribute>> getMyStudents(@RequestParam String email) {
        List<std_Attribute> myStudents = reps.findByCreatedBy(email);
        return ResponseEntity.ok(myStudents);
    }
}
