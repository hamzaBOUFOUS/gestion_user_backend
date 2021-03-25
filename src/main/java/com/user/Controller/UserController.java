package com.user.Controller;

import com.user.Entity.User;
import com.user.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/user")
@CrossOrigin(origins = "https://gestion-users-frontend.herokuapp.com", allowedHeaders = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/listUser")
    public Page<User> getUsers(
            @RequestParam(name="page",defaultValue="0") int page,
            @RequestParam(name="size",defaultValue="3") int size){
        return userService.getAllUser(page,size);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id){
        try {
            User user = userService.getUserById(id);
            if(user == null)
                return ResponseEntity.ok().body("User does not exist");
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @GetMapping("/getUsersByFirstName")
    public Page<User> getUsersByFirstName(@RequestParam("firstName") String firstName,
            @RequestParam(name="page",defaultValue="0") int page,
            @RequestParam(name="size",defaultValue="3") int size){
        return userService.getUsersByFirstName((firstName.equals("null")?"":firstName),page,size);
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user) throws Exception {
        return ResponseEntity.ok().body(userService.addUser(user));
    }


    @PostMapping("/loginUser")
    public ResponseEntity<?>
        loginUser(@RequestParam("email") String email,@RequestParam("password") String password) throws Exception {
        User user = userService.getUsersByEmailAndPassword(email,password);
        if(user == null){
            return ResponseEntity.ok().body(false);
        }
        return ResponseEntity.ok().body(user);
    }

    @Transactional
    @PostMapping("/updateUser")
    public String updateUser(@RequestBody User user) throws Exception {
        return userService.updateUser(user);
    }

    @Transactional
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return "TRRRRRRRRRRRRUE";
    }

}
