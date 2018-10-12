package io.aurora.provider.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.aurora.provider.service.MetaDataService;
import io.aurora.provider.web.rest.errors.BadRequestAlertException;
import io.aurora.provider.web.rest.util.HeaderUtil;
import io.aurora.provider.service.dto.MetaDataDTO;
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
 * REST controller for managing MetaData.
 */
@RestController
@RequestMapping("/api")
public class MetaDataResource {

    private final Logger log = LoggerFactory.getLogger(MetaDataResource.class);

    private static final String ENTITY_NAME = "metaData";

    private final MetaDataService metaDataService;

    public MetaDataResource(MetaDataService metaDataService) {
        this.metaDataService = metaDataService;
    }

    /**
     * POST  /meta-data : Create a new metaData.
     *
     * @param metaDataDTO the metaDataDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new metaDataDTO, or with status 400 (Bad Request) if the metaData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/meta-data")
    @Timed
    public ResponseEntity<MetaDataDTO> createMetaData(@RequestBody MetaDataDTO metaDataDTO) throws URISyntaxException {
        log.debug("REST request to save MetaData : {}", metaDataDTO);
        if (metaDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new metaData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MetaDataDTO result = metaDataService.save(metaDataDTO);
        return ResponseEntity.created(new URI("/api/meta-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /meta-data : Updates an existing metaData.
     *
     * @param metaDataDTO the metaDataDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated metaDataDTO,
     * or with status 400 (Bad Request) if the metaDataDTO is not valid,
     * or with status 500 (Internal Server Error) if the metaDataDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/meta-data")
    @Timed
    public ResponseEntity<MetaDataDTO> updateMetaData(@RequestBody MetaDataDTO metaDataDTO) throws URISyntaxException {
        log.debug("REST request to update MetaData : {}", metaDataDTO);
        if (metaDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MetaDataDTO result = metaDataService.save(metaDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, metaDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /meta-data : get all the metaData.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of metaData in body
     */
    @GetMapping("/meta-data")
    @Timed
    public List<MetaDataDTO> getAllMetaData() {
        log.debug("REST request to get all MetaData");
        return metaDataService.findAll();
    }

    /**
     * GET  /meta-data/:id : get the "id" metaData.
     *
     * @param id the id of the metaDataDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the metaDataDTO, or with status 404 (Not Found)
     */
    @GetMapping("/meta-data/{id}")
    @Timed
    public ResponseEntity<MetaDataDTO> getMetaData(@PathVariable String id) {
        log.debug("REST request to get MetaData : {}", id);
        Optional<MetaDataDTO> metaDataDTO = metaDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(metaDataDTO);
    }

    /**
     * DELETE  /meta-data/:id : delete the "id" metaData.
     *
     * @param id the id of the metaDataDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/meta-data/{id}")
    @Timed
    public ResponseEntity<Void> deleteMetaData(@PathVariable String id) {
        log.debug("REST request to delete MetaData : {}", id);
        metaDataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
