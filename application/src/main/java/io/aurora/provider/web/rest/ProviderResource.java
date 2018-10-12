package io.aurora.provider.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.aurora.provider.service.ProviderService;
import io.aurora.provider.web.rest.errors.BadRequestAlertException;
import io.aurora.provider.web.rest.util.HeaderUtil;
import io.aurora.provider.service.dto.ProviderDTO;
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
 * REST controller for managing Provider.
 */
@RestController
@RequestMapping("/api")
public class ProviderResource {

    private final Logger log = LoggerFactory.getLogger(ProviderResource.class);

    private static final String ENTITY_NAME = "provider";

    private final ProviderService providerService;

    public ProviderResource(ProviderService providerService) {
        this.providerService = providerService;
    }

    /**
     * POST  /providers : Create a new provider.
     *
     * @param providerDTO the providerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new providerDTO, or with status 400 (Bad Request) if the provider has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/providers")
    @Timed
    public ResponseEntity<ProviderDTO> createProvider(@RequestBody ProviderDTO providerDTO) throws URISyntaxException {
        log.debug("REST request to save Provider : {}", providerDTO);
        if (providerDTO.getId() != null) {
            throw new BadRequestAlertException("A new provider cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProviderDTO result = providerService.save(providerDTO);
        return ResponseEntity.created(new URI("/api/providers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /providers : Updates an existing provider.
     *
     * @param providerDTO the providerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated providerDTO,
     * or with status 400 (Bad Request) if the providerDTO is not valid,
     * or with status 500 (Internal Server Error) if the providerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/providers")
    @Timed
    public ResponseEntity<ProviderDTO> updateProvider(@RequestBody ProviderDTO providerDTO) throws URISyntaxException {
        log.debug("REST request to update Provider : {}", providerDTO);
        if (providerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProviderDTO result = providerService.save(providerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, providerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /providers : get all the providers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of providers in body
     */
    @GetMapping("/providers")
    @Timed
    public List<ProviderDTO> getAllProviders() {
        log.debug("REST request to get all Providers");
        return providerService.findAll();
    }

    /**
     * GET  /providers/:id : get the "id" provider.
     *
     * @param id the id of the providerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the providerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/providers/{id}")
    @Timed
    public ResponseEntity<ProviderDTO> getProvider(@PathVariable String id) {
        log.debug("REST request to get Provider : {}", id);
        Optional<ProviderDTO> providerDTO = providerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(providerDTO);
    }

    /**
     * DELETE  /providers/:id : delete the "id" provider.
     *
     * @param id the id of the providerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/providers/{id}")
    @Timed
    public ResponseEntity<Void> deleteProvider(@PathVariable String id) {
        log.debug("REST request to delete Provider : {}", id);
        providerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
