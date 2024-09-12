package com.inotebook.inotebook.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inotebook.inotebook.entity.Notes;
import com.inotebook.inotebook.entity.User;
import com.inotebook.inotebook.jwt.JwtUtil;
import com.inotebook.inotebook.repository.UserRepo;
import com.inotebook.inotebook.service.NotesService;

@RestController
@RequestMapping("/api/notes")
public class NotesController {
	
	
	@Autowired
    private JwtUtil jwtUtil;
	
	@Autowired
	private UserRepo userRepository;
	
	@Autowired
	private NotesService notesService;
	
	@GetMapping("/fetchAllNotes")
	public ResponseEntity<?> getAllNotes(@RequestHeader("Authorization") String token ){
		
		
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
	        if (user==null) {
	            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	        }
	        
	        
	        List<Notes> getAllNotes = notesService.getAll(user.getId());

	        // Return user details (you may want to exclude sensitive information)
	        return ResponseEntity.ok(getAllNotes);
	    } catch (Exception e) {
	        // Handle any exceptions
	        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@PostMapping("/addNote")
	public ResponseEntity<?> addNotes(@RequestHeader("Authorization") String token ,@RequestBody Notes notes){
		
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
	        if (user==null) {
	            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	        }
	        
	        
	        Notes noteSaved = notesService.saveNotes(notes, user.getId());

	        // Return user details (you may want to exclude sensitive information)
	        return ResponseEntity.ok(noteSaved);
	    } catch (Exception e) {
	        // Handle any exceptions
	        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	        
	        
		
	}


@PutMapping("/updateNote/{myId}")
public ResponseEntity<?> updateNotes(@RequestHeader("Authorization") String token ,@RequestBody Notes newNotes, @PathVariable ObjectId myId){
	
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
        
        if (user==null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        
        Optional<Notes> getNote = notesService.getNote(myId);
        
        if(!getNote.isEmpty()) {
        	Notes oldNote = getNote.get();
        	oldNote.setTitle(newNotes.getTitle()!=null && !newNotes.getTitle().equals("")?newNotes.getTitle():oldNote.getTitle());
        	oldNote.setDescription(newNotes.getDescription()!=null && !newNotes.getDescription().equals("")?newNotes.getDescription():oldNote.getDescription());
        	oldNote.setTag(newNotes.getTag()!=null && !newNotes.getTag().equals("")?newNotes.getTag():oldNote.getTag());
        	notesService.updateNotes(oldNote);
        	return new ResponseEntity<>(oldNote, HttpStatus.OK);
        	
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
    } catch (Exception e) {
        // Handle any exceptions
        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
        
        
	
}

@DeleteMapping("/deleteNote/{myId}")
public ResponseEntity<?> deleteNotes(@RequestHeader("Authorization") String token ,@PathVariable ObjectId myId){
	
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
        
        if (user==null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        
        Optional<Notes> getNote = notesService.getNote(myId);
        
        if(!getNote.isEmpty()) {
        	Boolean deletedNote = notesService.deleteById(myId);
        	
        	return new ResponseEntity<>("Notes Deleted is "+deletedNote, HttpStatus.OK);
        	
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
    } catch (Exception e) {
        // Handle any exceptions
        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
        
        
	
}
}