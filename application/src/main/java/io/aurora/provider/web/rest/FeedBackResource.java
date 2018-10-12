package io.aurora.provider.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.aurora.provider.service.FeedBackService;
import io.aurora.provider.web.rest.errors.BadRequestAlertException;
import io.aurora.provider.web.rest.util.HeaderUtil;
import io.aurora.provider.service.dto.FeedBackDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FeedBack.
 */
@RestController
@RequestMapping("/api")
public class FeedBackResource {

    private final Logger log = LoggerFactory.getLogger(FeedBackResource.class);

    private static final String ENTITY_NAME = "feedBack";

    private final FeedBackService feedBackService;

    public FeedBackResource(FeedBackService feedBackService) {
        this.feedBackService = feedBackService;
    }

    /**
     * POST  /feed-backs : Create a new feedBack.
     *
     * @param feedBackDTO the feedBackDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new feedBackDTO, or with status 400 (Bad Request) if the feedBack has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/feed-backs")
    @Timed
    public ResponseEntity<FeedBackDTO> createFeedBack(@RequestBody FeedBackDTO feedBackDTO) throws URISyntaxException {
        log.debug("REST request to save FeedBack : {}", feedBackDTO);
        if (feedBackDTO.getId() != null) {
            throw new BadRequestAlertException("A new feedBack cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeedBackDTO result = feedBackService.save(feedBackDTO);
        return ResponseEntity.created(new URI("/api/feed-backs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /feed-backs : Updates an existing feedBack.
     *
     * @param feedBackDTO the feedBackDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated feedBackDTO,
     * or with status 400 (Bad Request) if the feedBackDTO is not valid,
     * or with status 500 (Internal Server Error) if the feedBackDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/feed-backs")
    @Timed
    public ResponseEntity<FeedBackDTO> updateFeedBack(@RequestBody FeedBackDTO feedBackDTO) throws URISyntaxException {
        log.debug("REST request to update FeedBack : {}", feedBackDTO);
        if (feedBackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FeedBackDTO result = feedBackService.save(feedBackDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, feedBackDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /feed-backs : get all the feedBacks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of feedBacks in body
     */
    @GetMapping("/feed-backs")
    @Timed
    public List<FeedBackDTO> getAllFeedBacks() {
        log.debug("REST request to get all FeedBacks");
        return feedBackService.findAll();
    }

    /**
     * GET  /feed-backs/:id : get the "id" feedBack.
     *
     * @param id the id of the feedBackDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the feedBackDTO, or with status 404 (Not Found)
     */
    @GetMapping("/feed-backs/{id}")
    @Timed
    public ResponseEntity<FeedBackDTO> getFeedBack(@PathVariable String id) {
        log.debug("REST request to get FeedBack : {}", id);
        Optional<FeedBackDTO> feedBackDTO = feedBackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedBackDTO);
    }

    /**
     * DELETE  /feed-backs/:id : delete the "id" feedBack.
     *
     * @param id the id of the feedBackDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/feed-backs/{id}")
    @Timed
    public ResponseEntity<Void> deleteFeedBack(@PathVariable String id) {
        log.debug("REST request to delete FeedBack : {}", id);
        feedBackService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
