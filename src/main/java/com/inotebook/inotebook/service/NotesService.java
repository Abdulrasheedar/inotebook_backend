package com.inotebook.inotebook.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inotebook.inotebook.entity.Notes;
import com.inotebook.inotebook.repository.NotesRepo;


@Service
public class NotesService {
	
	
	@Autowired
	private NotesRepo notesRepository;
	
	
	public Notes saveNotes(Notes notes, ObjectId userId) {
		notes.setUserId(userId);
		return notesRepository.save(notes);
	}

	public List<Notes> getAll(ObjectId userId){
		
		return notesRepository.findAllByUserId(userId);
	}
	
	
	public Notes updateNotes(Notes notes) {
		return notesRepository.save(notes);
		
	}
	
	public Optional<Notes> getNote(ObjectId noteId) {
		return notesRepository.findById(noteId);
	}
	

@Transactional
public boolean deleteById(ObjectId id) {
	boolean removed = false;
	try {
	
		notesRepository.deleteById(id);
		removed = true;
	}
	catch(Exception e) {
		System.out.println(e);
		throw new RuntimeException("An error occurred while deleting the entry",e);
	}
	return removed;
}
}