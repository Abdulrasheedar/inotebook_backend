package com.inotebook.inotebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inotebook.inotebook.entity.User;
import com.inotebook.inotebook.jwt.JwtUtil;
import com.inotebook.inotebook.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepository;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	private static final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
	
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public String validateEmailAndPassword(String email, String password) {
		User user = userRepository.findByEmail(email);
		if (user==null) {
            return "Invalid email ID";
        }
        // Check if password matches (assuming passwords are encrypted)
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid password";
        }
		
        return jwtUtil.generateToken(user.getEmail());
		
	}

}
