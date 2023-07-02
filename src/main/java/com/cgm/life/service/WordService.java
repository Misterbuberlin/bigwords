package com.cgm.life.service;

import com.cgm.life.entity.WordEntity;
import com.cgm.life.repository.WordRepository;
import com.cgm.life.util.Roles;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class WordService {

    @Inject
    private WordRepository wordRepository;

    public List<WordEntity> getWords() {
        return wordRepository.findAllWords();
    }


    public void persistWords(List<WordEntity> collect) {
        wordRepository.persist(collect);
    }

    @RolesAllowed({Roles.BIG_WORDS})
    public PanacheQuery<WordEntity> getPremiumWords() {
        return wordRepository.findPremiumWords();
    }

}
