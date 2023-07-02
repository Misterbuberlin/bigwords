package com.cgm.life.service;

import com.cgm.life.repository.WordRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PremiumWordService {

    @Inject
    private WordRepository wordRepository;


}
