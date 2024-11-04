package net.alok.journalApp.controller;


import net.alok.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class JournalEntryController {

    //Will act as a table
    private Map<ObjectId, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }

    /*
      @RequestBody - It's like saying "Hey spring, please take the data from the request and turn it into a java
      object that I can use in my code.
     */
    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        journalEntries.put(myEntry.getId(), myEntry);
        return true;

    }

    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable Long myId){
        return journalEntries.get(myId);

    }

    @DeleteMapping("id/{myId}")
    public JournalEntry deleteJournalEntryById(@PathVariable Long myId){
        return journalEntries.remove(myId);

    }

    @PutMapping("id/{id}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry myEntry){
        return journalEntries.put(id, myEntry);

    }


}
