package com.cgm.life.entity;

import javax.persistence.*;

@Entity
@Table(name = "words", schema = "cgm")
public class WordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //
    private String word;
    @Column(name = "is_premium")
    private boolean isPremium;

    public WordEntity() {
        // Default constructor required by JPA
    }

    public WordEntity(String word, boolean isPremium) {
        this.word = word;
        this.isPremium = isPremium;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
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
