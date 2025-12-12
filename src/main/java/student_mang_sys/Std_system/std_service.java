package student_mang_sys.Std_system;

import java.util.List;

public interface std_service {
    String Addingstudents(std_Attribute std);
    List<std_Attribute> showemoloyee();
    String deletestudent(String id);
    String saveAllStudents(List<std_Attribute> students);
    List<std_Attribute> getstudent();

    // 🔹 New methods for fetch/update by ID
    std_Attribute getStudentById(String id);
    std_Attribute updateStudent(String id, std_Attribute updatedStudent);
}
