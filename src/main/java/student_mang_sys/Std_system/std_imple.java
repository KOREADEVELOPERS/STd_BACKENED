package student_mang_sys.Std_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class std_imple implements std_service {

    @Autowired
    repository res;

    @Override
    public String Addingstudents(std_Attribute std) {
        System.out.println("Saving: " + std);
        res.save(std);
        return "✅ Data Saved Successfully!";
    }

    @Override
    public List<std_Attribute> showemoloyee() {
        return res.findAll();
    }

    @Override
    public String deletestudent(Long id) {
        if(res.existsById(String.valueOf(id))){
            res.deleteById(String.valueOf(id));
            return "Data deleted successfully";
        }else
            return "ID is not found";

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


}
