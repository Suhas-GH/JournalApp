package com.prototype.JournalApplication.repository;

import com.prototype.JournalApplication.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalEntryRepo extends MongoRepository<JournalEntry, Long> {
}
