package com.ambar.ecommerce_backend.service;

import com.ambar.ecommerce_backend.model.UserProfile;
import com.ambar.ecommerce_backend.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository repository;

    public UserProfile saveProfile(UserProfile profile){
        return repository.save(profile);
    }
    public UserProfile getProfile(){
        return repository.findAll().get(0);
    }
    public UserProfile getProfileById(Long id){
        return repository.findById(id)
                .orElse(null);
    }

    public UserProfile updateProfile(Long id,UserProfile updatedProfile){
        UserProfile profile=repository.findById(id)
                .orElseThrow();
        profile.setName(updatedProfile.getName());
        profile.setMobile(updatedProfile.getMobile());
        profile.setEmail(updatedProfile.getEmail());
        profile.setAddress(updatedProfile.getAddress());
        profile.setPincode(updatedProfile.getPincode());
        return repository.save(profile);
    }
}
