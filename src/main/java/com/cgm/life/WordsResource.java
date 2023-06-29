package com.cgm.life;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/words")
public class WordsResource {
    @Inject
    private WordRepository wordRepository;


    @GET
    @RolesAllowed({Roles.BIG_WORDS, Roles.END_USER})
   // @Authenticated
    public List<String> getWords(@QueryParam("sortOrder") String sortOrder) {

        PanacheQuery<WordEntity> all = wordRepository.findAll();
        List<String> allWords = all.stream().map(w -> w.getWord()).collect(Collectors.toList());
        if (sortOrder != null && sortOrder.equals("desc")) {
            Collections.sort(allWords, Comparator.reverseOrder());
        }
        else if (sortOrder != null && sortOrder.equals("asc")) {
            Collections.sort(allWords);
        }
        else {
            // default sort order is ascending.
            Collections.sort(allWords);
        }
        return allWords;
    }


    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({Roles.BIG_WORDS, Roles.END_USER})
    //@Authenticated
    public Response addWords(List<String> words) {
        for (String word : words) {
            WordEntity wordEntity = new WordEntity(word);
            wordRepository.persist(wordEntity);
        }
        return Response.ok().build();
    }
}

