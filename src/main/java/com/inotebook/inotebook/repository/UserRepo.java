package com.inotebook.inotebook.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.inotebook.inotebook.entity.User;

public interface UserRepo extends MongoRepository<User, ObjectId> {
	
//	User findByUserName(String userName);
	
	User findByEmail(String email);
	
//	void deleteByUserName(String userName);
	
}
