package com.cgm.life;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class WordRepository implements PanacheRepository<WordEntity> {

    public List<WordEntity> findAllWords() {
        return listAll();
    }
}
