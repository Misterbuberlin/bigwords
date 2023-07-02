package com.cgm.life.repository;

import com.cgm.life.entity.WordEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class WordRepository implements PanacheRepository<WordEntity> {

    public List<WordEntity> findAllWords() {
        return list("isPremium", false);
    }

    public PanacheQuery<WordEntity> findPremiumWords() {
        return find("isPremium", true);
    }
}
