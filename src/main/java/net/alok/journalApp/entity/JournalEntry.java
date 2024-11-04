package net.alok.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

//POJO - Plain old Java object
//@Data - It has RequiredArgsConstructor , but it does not have @NoArgsConstructor, so we have to add it
//explicitly. It is required during deserialization of JSON to POJO.
@Document
@Data
@NoArgsConstructor
public class JournalEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;


}
