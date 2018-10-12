package io.aurora.provider.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.aurora.provider.service.RateService;
import io.aurora.provider.web.rest.errors.BadRequestAlertException;
import io.aurora.provider.web.rest.util.HeaderUtil;
import io.aurora.provider.service.dto.RateDTO;
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
 * REST controller for managing Rate.
 */
@RestController
@RequestMapping("/api")
public class RateResource {

    private final Logger log = LoggerFactory.getLogger(RateResource.class);

    private static final String ENTITY_NAME = "rate";

    private final RateService rateService;

    public RateResource(RateService rateService) {
        this.rateService = rateService;
    }

    /**
     * POST  /rates : Create a new rate.
     *
     * @param rateDTO the rateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rateDTO, or with status 400 (Bad Request) if the rate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rates")
    @Timed
    public ResponseEntity<RateDTO> createRate(@RequestBody RateDTO rateDTO) throws URISyntaxException {
        log.debug("REST request to save Rate : {}", rateDTO);
        if (rateDTO.getId() != null) {
            throw new BadRequestAlertException("A new rate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RateDTO result = rateService.save(rateDTO);
        return ResponseEntity.created(new URI("/api/rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rates : Updates an existing rate.
     *
     * @param rateDTO the rateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rateDTO,
     * or with status 400 (Bad Request) if the rateDTO is not valid,
     * or with status 500 (Internal Server Error) if the rateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rates")
    @Timed
    public ResponseEntity<RateDTO> updateRate(@RequestBody RateDTO rateDTO) throws URISyntaxException {
        log.debug("REST request to update Rate : {}", rateDTO);
        if (rateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RateDTO result = rateService.save(rateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rates : get all the rates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rates in body
     */
    @GetMapping("/rates")
    @Timed
    public List<RateDTO> getAllRates() {
        log.debug("REST request to get all Rates");
        return rateService.findAll();
    }

    /**
     * GET  /rates/:id : get the "id" rate.
     *
     * @param id the id of the rateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rates/{id}")
    @Timed
    public ResponseEntity<RateDTO> getRate(@PathVariable String id) {
        log.debug("REST request to get Rate : {}", id);
        Optional<RateDTO> rateDTO = rateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rateDTO);
    }

    /**
     * DELETE  /rates/:id : delete the "id" rate.
     *
     * @param id the id of the rateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rates/{id}")
    @Timed
    public ResponseEntity<Void> deleteRate(@PathVariable String id) {
        log.debug("REST request to delete Rate : {}", id);
        rateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
