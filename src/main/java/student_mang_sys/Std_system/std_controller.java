package student_mang_sys.Std_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@CrossOrigin("*")
public class std_controller {

    @Autowired
    private std_service ser;

    @Autowired
    private repository reps;

    // -------------------------------------------------------------
    // SAVE MULTIPLE STUDENTS (User specific)
    // -------------------------------------------------------------
    @PostMapping("/fit")
    public ResponseEntity<String> saveMultiple(
            @RequestBody List<std_Attribute> students,
            @RequestParam String email) {

        if (students == null || students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No students provided");
        }

        for (std_Attribute student : students) {
            if (student.getName() == null || student.getEmail() == null || student.getPhone() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("All fields are required for every student");
            }

            // ✔ store which user created it
            student.setCreatedBy(email);
        }

        ser.saveAllStudents(students);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Students registered successfully");
    }

    // -------------------------------------------------------------
    // SAVE SINGLE STUDENT (User specific)
    // -------------------------------------------------------------
    @PostMapping("/save")
    public ResponseEntity<String> saveSingle(@RequestBody std_Attribute student) {

        if (student.getName() == null ||
                student.getEmail() == null ||
                student.getPhone() == null ||
                student.getCreatedBy() == null) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("All fields including createdBy are required");
        }

        ser.saveStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Student registered successfully");
    }

    // -------------------------------------------------------------
    // GET ALL STUDENTS OF LOGGED-IN USER
    // -------------------------------------------------------------
    @GetMapping("/find")
    public ResponseEntity<List<std_Attribute>> findByUserEmail(@RequestParam String email) {

        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // ✔ Only return logged-in user's students
        List<std_Attribute> students = reps.findByCreatedBy(email);

        return ResponseEntity.ok(students);
    }

    // -------------------------------------------------------------
    // SEARCH students of logged-in user using keyword
    // (name / email / phone → but only that user's data)
    // -------------------------------------------------------------
    @GetMapping("/search")
    public ResponseEntity<List<std_Attribute>> searchStudents(
            @RequestParam String email,
            @RequestParam String keyword) {

        if (email == null || keyword == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // ✔ Fetch only that user's data
        List<std_Attribute> userStudents = reps.findByCreatedBy(email);

        // ✔ Manual filtering (case-insensitive)
        keyword = keyword.toLowerCase();

        List<std_Attribute> filtered = userStudents.stream()
                .filter(st ->
                        st.getName().toLowerCase().contains(keyword) ||
                        st.getEmail().toLowerCase().contains(keyword) ||
                        st.getPhone().contains(keyword)
                )
                .toList();

        return ResponseEntity.ok(filtered);
    }

    // -------------------------------------------------------------
    // DELETE SPECIFIC STUDENT (Only own student)
    // -------------------------------------------------------------
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable int id,
            @RequestParam String email) {

        // ✔ Check student belongs to this user
        std_Attribute student = reps.findById(id).orElse(null);

        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found");
        }

        if (!student.getCreatedBy().equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("You can delete only your students");
        }

        ser.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }
}
