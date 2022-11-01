package com.example.musicwebapp.controller;


import com.example.musicwebapp.entity.Music;
import com.example.musicwebapp.entity.User;
import com.example.musicwebapp.entity.enums.Role;
import com.example.musicwebapp.repository.UserRepository;
import com.example.musicwebapp.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private MusicService musicService;

    @GetMapping("")
    public String viewHomePage(){
        return "index";
    }

    //Registration code
    @GetMapping("/register")
    public String showSignUpForm(Model model){
        model.addAttribute("user", new User());

        return "signup_form";
    }

    //Submit registration handler
    @PostMapping("/process_register")
    public String processRegistration(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        //user.setRole(Role.USER);
        repo.save(user);

        return "register_success";
    }

    @GetMapping("/list_users")
    public String viewUsersList(Model model) {
        List<User> listUsers = repo.findAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/music-news")
    public String viewMusicList(Model model) {
        Object listMusicNews = musicService.findAllMusic();
        model.addAttribute("listMusicNews", listMusicNews);
        return "musicNews";
    }


}
