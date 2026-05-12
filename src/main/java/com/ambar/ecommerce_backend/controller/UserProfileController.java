package com.ambar.ecommerce_backend.controller;

import com.ambar.ecommerce_backend.model.UserProfile;
import com.ambar.ecommerce_backend.service.UserProfileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class UserProfileController {
    @Autowired
    private UserProfileService service;

    @PostMapping
    public UserProfile saveProfile(@RequestBody UserProfile profile){
        return service.saveProfile(profile);
    }
    @GetMapping
    public UserProfile getProfile(){
        return service.getProfile();
    }

    @GetMapping("/{id}")
    public UserProfile getProfileById(@PathVariable Long id){
        return service.getProfileById(id);
    }

    @PutMapping("/{id}")
    public UserProfile updateProfile(@PathVariable Long id,@RequestBody UserProfile profile){
        return service.updateProfile(id,profile);
    }
}
