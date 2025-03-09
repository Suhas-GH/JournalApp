package com.prototype.JournalApplication.service;

import com.prototype.JournalApplication.entity.User;
import com.prototype.JournalApplication.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public void createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public Optional<User> getById(String id) {
        return userRepo.findById(id);
    }

    public void deleteById(String id) {
        userRepo.deleteById(id);
    }
    

    public void updateUser(String id ,User user){
        User userObject = userRepo.findById(id).orElse(null);
        if(userObject!= null){
            userObject.setUserName(user.getUserName());
            userObject.setPassword(user.getPassword());
        }
        userRepo.save(userObject);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username);
        if(user== null){
            System.out.println("No User Found");
            throw new UsernameNotFoundException("No user found");
        }

        return new UserServiceImpl(user);
    }
}
