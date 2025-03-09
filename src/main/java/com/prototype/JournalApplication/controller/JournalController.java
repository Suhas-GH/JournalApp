package com.prototype.JournalApplication.controller;


import com.fasterxml.jackson.databind.json.JsonMapper;
import com.prototype.JournalApplication.entity.JournalEntry;
import com.prototype.JournalApplication.entity.User;
import com.prototype.JournalApplication.service.JournalEntryService;
import com.prototype.JournalApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    JournalEntryService journalEntryService;

    @Autowired
    UserService userService;

    @PostMapping("/create/{id}")
    public ResponseEntity<JournalEntry> createJournalEntry(@PathVariable String id, @RequestBody JournalEntry journalEntry) {
        try {
            journalEntryService.createJournalEntry(id, journalEntry);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String id) {
        User user= userService.getById(id).orElse(null);

        List<JournalEntry> journals = user.getJournalEntries();
        if(!journals.isEmpty()){
            return new ResponseEntity<>(journals,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<JournalEntry> getByJournalId(@PathVariable Long id) {
        Optional<JournalEntry> byId = journalEntryService.getById(id);
        if (byId.isPresent()) {
            return new ResponseEntity<>(byId.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{userId}/{id}")
    public ResponseEntity<String> deleteByJournalId(@PathVariable String userId, @PathVariable Long id) {
        journalEntryService.deleteById(userId ,id);

        return new ResponseEntity<>("Deleted Successful",HttpStatus.NO_CONTENT);//use gson.toJson or other methods to pass String value
    }

    @PutMapping("{userId}/{journalId}")
    public ResponseEntity<JournalEntry> updateJournalEntry(@PathVariable String userId, @PathVariable Long journalId, @RequestBody JournalEntry UpdatejournalEntry) {
        return journalEntryService.updateJournal(userId, journalId, UpdatejournalEntry);
    }

}
