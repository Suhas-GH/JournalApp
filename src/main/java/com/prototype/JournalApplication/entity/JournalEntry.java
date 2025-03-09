package com.prototype.JournalApplication.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "journal _entries")
@NoArgsConstructor
public class JournalEntry {
    @Id
    private long id;

    @NonNull
    private String title;

    @NonNull
    private String content;

    private LocalDateTime date;

}
