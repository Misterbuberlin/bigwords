package com.cgm.life;

import javax.persistence.*;

@Entity
@Table(name = "words", schema = "cgm")
public class WordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //
    private String word;

    public WordEntity() {
        // Default constructor required by JPA
    }

    public WordEntity(String word) {
        this.word = word;
    }

    public Long getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
