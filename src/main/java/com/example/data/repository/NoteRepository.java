package com.example.data.repository;


import com.example.data.entitry.NoteEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class NoteRepository {

    private final List<NoteEntity> noteList = new ArrayList<>();

    public List<NoteEntity> findAll () {
        return this.noteList;
    }

    public Optional<NoteEntity> findById (UUID id) {
        return this.noteList.stream()
                .filter(note -> note.getId().equals(id))
                .findFirst();
    }

    public NoteEntity save(NoteEntity note) {
        if (Objects.isNull(note.getId())) {
            note.setId(UUID.randomUUID());
            this.noteList.add(note);
        } else {
            Optional<NoteEntity> optionalNote = this.noteList.stream()
                    .filter(n -> n.getId().equals(note.getId()))
                    .findFirst();
            if (optionalNote.isPresent()) {
                this.noteList.remove(optionalNote.get());
                this.noteList.add(note);
            }
        }
        return note;
    }

    public List<NoteEntity> saveAll(Collection<NoteEntity> notes) {
        notes.forEach(note -> note.setId(UUID.randomUUID()));
        this.noteList.addAll(notes);
        return notes.stream().toList();
    }

    public void deleteById(UUID id) {
        this.noteList.stream()
                .filter(note -> note.getId().equals(id))
                .findFirst()
                .ifPresent(this.noteList::remove);
    }
}
