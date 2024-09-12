//package com.inotebook.inotebook.dto;
//
//import java.time.LocalDate;
//
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//
//
//import org.springframework.data.mongodb.core.index.Indexed;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor(staticName = "build")
//@NoArgsConstructor
//public class UserRequest {
//	
//	
//	@NotNull(message = "Name cannot be null")
//    @Size(min = 3, message = "Name must be at least 3 characters long")
//	private String name;
//	
//	@Indexed(unique = true)
//	@NotNull(message = "Email cannot be null")
//    @Email(message = "Email should be valid")
//	private String email;
//	
//	@NotNull(message = "Password cannot be null")
//    @Size(min = 6, message = "Password must be at least 6 characters long")
//	private String password;
//	
//	private LocalDate date;
//}
