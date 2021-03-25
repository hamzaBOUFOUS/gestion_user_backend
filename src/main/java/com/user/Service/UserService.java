package com.user.Service;

import com.user.Entity.User;
import com.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getAllUser(int page, int size){
        List<User> users= new ArrayList<User>();
        userRepository.findAll().forEach(users::add);
        return userRepository.findAll(PageRequest.of(page, size));
    }

    public User getUserById(long id){
        return userRepository.getUserById(id);
    }

    public Page<User> getUsersByFirstName(String firstName,int page, int size){
        return userRepository.getUsersByFirstName(firstName, PageRequest.of(page, size));
    }
    
    public User addUser(User user) throws Exception {
        if (user == null)
            throw new Exception("Erreur");
        return userRepository.save(user);
    }

    public String updateUser(User user) throws Exception {
        if (user == null)
            throw new Exception("Erreur");
        userRepository.updateUser(user.getId(), user.getEmail(), user.getPassword(),
                user.getFirstName(), user.getLastName(), user.getRole());
        return "True";
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public  User getUsersByEmailAndPassword(String email,String password){
        return userRepository.getUsersByEmailAndPassword(email,password);
    }
}
