package ru.pflb.testingapp.controllers;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pflb.testingapp.entities.Admin;
import ru.pflb.testingapp.repositories.AdminRepository;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by insider on 13.09.2017.
 */
@RestController
@RequestMapping("/api/editor/chek-editor")
public class AdminController {
    private final AdminRepository adminRepository;


    @Autowired
    public AdminController(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

//
//    @PostMapping("/{adminLogin}/{adminPassword}")
//    @ResponseBody
//    public ResponseEntity<String> getjsonAdmin(@PathVariable("adminLogin") String adminLogin, @PathVariable("adminPassword") String adminPassword){
//       Admin admin = new Admin();
//     //  String s ="жди";
//       admin.setAdminLogin(adminLogin);
//        admin.setAdminPassword(adminPassword);
//        List<Admin> list = adminRepository.findAll();
//
//   // для примера...
//        Admin admin2 = new Admin();
//        admin2.setAdminLogin("vasya");
//        admin2.setAdminPassword("12345");
//        list.add(admin2);

//
//        for (Admin admin1 : list) {
//           // System.out.println(admin1.getAdminLogin()+" "+admin1.getAdminPassword());
//            if(admin.getAdminLogin().equals(admin1.getAdminLogin())&&admin.getAdminPassword().equals(admin1.getAdminPassword())) {
//                return ResponseEntity.ok("нашол");
//            }
//
//        }
//       return ResponseEntity.notFound().build();
//    }



    @PostMapping
    @ResponseBody
    public ResponseEntity<Integer> getjsonAdmin(@RequestBody Admin admin){
       // adminRepository.save(admin);
       // List<Admin> list = adminRepository.findAll();
        Admin a  = adminRepository.findByAdminLogin(admin.getAdminLogin());
        if(!a.equals(null)){
            if (admin.getAdminPassword().equals(a.getAdminPassword()))
                return ResponseEntity.ok(a.getAdminId());
        }
        return  ResponseEntity.notFound().build();


    }

    @GetMapping()
    @ResponseBody
    public ResponseEntity<List<Admin>> show(){
        List<Admin> list = adminRepository.findAll();
        return ResponseEntity.ok(list);
    }


}
