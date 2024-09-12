package com.inotebook.inotebook.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.inotebook.inotebook.entity.Notes;


public interface NotesRepo extends MongoRepository<Notes, ObjectId> {
	List<Notes> findAllByUserId(ObjectId userId);
}
