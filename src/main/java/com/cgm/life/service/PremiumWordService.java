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
public class PremiumWordService {

    @Inject
    private WordRepository wordRepository;


}
