package com.inotebook.inotebook.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.inotebook.inotebook.entity.User;
import com.inotebook.inotebook.repository.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
 
	@Autowired
	private UserRepo userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user= userRepository.findByEmail(email);
		if(user!=null) {
			UserDetails userDetails= org.springframework.security.core.userdetails
					.User.builder()
					.username(user.getEmail())
					.password(user.getPassword())
					.build(); 
			return userDetails;
		}
		throw new UsernameNotFoundException("user name not found: "+email);
	}

}
