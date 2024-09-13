package com.inotebook.inotebook.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inotebook.inotebook.entity.User;
import com.inotebook.inotebook.jwt.JwtUtil;
import com.inotebook.inotebook.repository.UserRepo;
import com.inotebook.inotebook.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserLoginController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	@Autowired
	private UserRepo userRepository;
	
	//Create user using PostMapping. Doesn't require Auth
	@PostMapping("/createUser")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		Boolean success = false;
		try {
            if(user.getName().length()<3) {
            	return new ResponseEntity<>(success, HttpStatus.BAD_REQUEST);
            }
            else if(user.getPassword().length()<6) {
            	return new ResponseEntity<>(success, HttpStatus.BAD_REQUEST);
            }
            user.setDate(LocalDate.now());
            User savedUser = userService.saveUser(user);
            return new ResponseEntity<>(success = true, HttpStatus.CREATED);
		} catch (Exception e) {
            // Handle general exceptions or constraint violations
//			"Error creating user: " + e.getMessage()
            return new ResponseEntity<>(success, HttpStatus.BAD_REQUEST);
        }
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User user){
		Boolean success = false;
		String email = user.getEmail();
        String password = user.getPassword();
        String tokenOrMessage = userService.validateEmailAndPassword(email, password);
        
        if (tokenOrMessage.equals("Invalid email ID") || tokenOrMessage.equals("Invalid password")) {
        	success = false;
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(success);
        } else {
            // Return JWT token if credentials are valid
        	success = true;
            Map<Boolean, String> response = new HashMap<>();
            response.put(success, tokenOrMessage);
            return ResponseEntity.ok(response);
        }
		
	}

	@PostMapping("/getUser")
	public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String token){
		
		try {
	        // Remove the Bearer prefix from the token
	        if (token.startsWith("Bearer ")) {
	            token = token.substring(7);
	        }

	        // Extract username (email) from the token
	        String username = jwtUtil.extractUsername(token);
	        
	        // Validate the token (optional if not done elsewhere)
	        if (!jwtUtil.validateToken(token, username)) {
	            return new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
	        }

	        // Retrieve the user details from the database using the email/username
	        User user = userRepository.findByEmail(username);
	        System.out.println("username here: "+user.getName());
	        if (user==null) {
	            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	        }

	        // Return user details (you may want to exclude sensitive information)
	        return ResponseEntity.ok(user);
	    } catch (Exception e) {
	        // Handle any exceptions
	        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
		
	}
	
}
