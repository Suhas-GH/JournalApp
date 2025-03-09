package com.prototype.JournalApplication.service;

import com.prototype.JournalApplication.entity.JournalEntry;
import com.prototype.JournalApplication.entity.User;
import com.prototype.JournalApplication.repository.JournalEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    JournalEntryRepo journalEntryRepo;

    @Autowired
    UserService userService;

    @Transactional
    public void createJournalEntry(String id, JournalEntry journalEntry){
        try {
            User user = userService.getById(id).orElse(null);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.createUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An error occurred while saving",e);
        }
    }

    public void saveJournalEntry(JournalEntry journalEntry){
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> getById(Long id){
        return journalEntryRepo.findById(id);
    }

    public void deleteById(String userId, Long id){
        User user = userService.getById(userId).orElse(null);
        user.getJournalEntries().removeIf(x->x.getId() == id);
        userService.createUser(user);
        journalEntryRepo.deleteById(id);
    }

    public ResponseEntity<JournalEntry> updateJournal(String userId, Long journalId, JournalEntry updateJournal) {

        JournalEntry currentjournalEntry = getById(journalId).orElse(null);
        if(currentjournalEntry!=null){
            currentjournalEntry.setTitle(updateJournal.getTitle()!=null && updateJournal.getTitle().length()>0? updateJournal.getTitle(): currentjournalEntry.getTitle() );
            currentjournalEntry.setContent(updateJournal.getContent()!=null && updateJournal.getContent().length()>0? updateJournal.getContent() : currentjournalEntry.getContent());
            saveJournalEntry(currentjournalEntry);
            return new ResponseEntity<>(currentjournalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
