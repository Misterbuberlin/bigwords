package com.cgm.life.resource;

import com.cgm.life.service.PremiumWordService;
import com.cgm.life.util.Roles;
import com.cgm.life.entity.WordEntity;
import com.cgm.life.service.WordService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirements;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/words")
@SecurityRequirements(
        @SecurityRequirement(name = "bearerAuth")
)
public class WordsResource {
    @Inject
    private WordService wordService;

    @Inject
    private SecurityIdentity securityIdentity;


    @GET
    @Path("/")
    @RolesAllowed({Roles.END_USER, Roles.BIG_WORDS})
    @Operation(summary = "Get words", description = "Retrieve a list of words")
    @APIResponse(responseCode = "200", description = "Successful operation")
    public Response getWords(@QueryParam("sortOrder") String sortOrder) {

        List<WordEntity> all = wordService.getWords();
        List<String> allWords = all.stream().map(w -> w.getWord()).collect(Collectors.toList());
        if (sortOrder != null && sortOrder.equals("desc")) {
            Collections.sort(allWords, Comparator.reverseOrder());
        } else if (sortOrder != null && sortOrder.equals("asc")) {
            Collections.sort(allWords);
        } else {
            // default sort order is ascending.
            Collections.sort(allWords);
        }

         return Response.ok(allWords).build();
    }


    @POST
    @Path("/")
    @Transactional
    @RolesAllowed({Roles.END_USER, Roles.BIG_WORDS})
    @Operation(summary = "Add words", description = "Add a list of words")
    @RequestBody(description = "List of words to add", required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @APIResponse(responseCode = "200", description = "Successful operation")
    public Response addWords(List<String> words) {
        List<WordEntity> wordEntities = words.stream().map(w -> new WordEntity(w.toLowerCase(), Boolean.FALSE)).collect(Collectors.toList());
        wordService.persistWords(wordEntities);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/premium")
    @Transactional
    @RolesAllowed({ Roles.BIG_WORDS})
    @Operation(summary = "Add words", description = "Add a list of words")
    @RequestBody(description = "List of words to add", required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @APIResponse(responseCode = "200", description = "Successful operation")
    public Response addPremiumWords(List<String> words) {
        List<WordEntity> wordEntities = words.stream().map(w -> new WordEntity(w.toLowerCase(), Boolean.TRUE)).collect(Collectors.toList());
        wordService.persistWords(wordEntities);
        return Response.status(Response.Status.CREATED).build();
    }


    @GET
    @Path("/premium")
    @RolesAllowed({ Roles.BIG_WORDS})
    @Operation(summary = "Get Premium Data", description = "Retrieve premium data for users with 'BIG_WORDS' role.")
    @APIResponse(responseCode = "200", description = "Success")
    public Response getPremiumData() {
        PanacheQuery<WordEntity> premiumWords = wordService.getPremiumWords();
        List<WordEntity> list = premiumWords.list();
        List<String> premiumList = list.stream().map(w -> w.getWord()).collect(Collectors.toList());
        return Response.ok(premiumList).build();

    }
}

