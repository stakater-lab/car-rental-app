package io.aurora.provider.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.aurora.provider.service.VehicleDetailsService;
import io.aurora.provider.web.rest.errors.BadRequestAlertException;
import io.aurora.provider.web.rest.util.HeaderUtil;
import io.aurora.provider.service.dto.VehicleDetailsDTO;
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
 * REST controller for managing VehicleDetails.
 */
@RestController
@RequestMapping("/api")
public class VehicleDetailsResource {

    private final Logger log = LoggerFactory.getLogger(VehicleDetailsResource.class);

    private static final String ENTITY_NAME = "vehicleDetails";

    private final VehicleDetailsService vehicleDetailsService;

    public VehicleDetailsResource(VehicleDetailsService vehicleDetailsService) {
        this.vehicleDetailsService = vehicleDetailsService;
    }

    /**
     * POST  /vehicle-details : Create a new vehicleDetails.
     *
     * @param vehicleDetailsDTO the vehicleDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehicleDetailsDTO, or with status 400 (Bad Request) if the vehicleDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicle-details")
    @Timed
    public ResponseEntity<VehicleDetailsDTO> createVehicleDetails(@RequestBody VehicleDetailsDTO vehicleDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save VehicleDetails : {}", vehicleDetailsDTO);
        if (vehicleDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleDetailsDTO result = vehicleDetailsService.save(vehicleDetailsDTO);
        return ResponseEntity.created(new URI("/api/vehicle-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicle-details : Updates an existing vehicleDetails.
     *
     * @param vehicleDetailsDTO the vehicleDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehicleDetailsDTO,
     * or with status 400 (Bad Request) if the vehicleDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the vehicleDetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicle-details")
    @Timed
    public ResponseEntity<VehicleDetailsDTO> updateVehicleDetails(@RequestBody VehicleDetailsDTO vehicleDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update VehicleDetails : {}", vehicleDetailsDTO);
        if (vehicleDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VehicleDetailsDTO result = vehicleDetailsService.save(vehicleDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehicleDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicle-details : get all the vehicleDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vehicleDetails in body
     */
    @GetMapping("/vehicle-details")
    @Timed
    public List<VehicleDetailsDTO> getAllVehicleDetails() {
        log.debug("REST request to get all VehicleDetails");
        return vehicleDetailsService.findAll();
    }

    /**
     * GET  /vehicle-details/:id : get the "id" vehicleDetails.
     *
     * @param id the id of the vehicleDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehicleDetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vehicle-details/{id}")
    @Timed
    public ResponseEntity<VehicleDetailsDTO> getVehicleDetails(@PathVariable String id) {
        log.debug("REST request to get VehicleDetails : {}", id);
        Optional<VehicleDetailsDTO> vehicleDetailsDTO = vehicleDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleDetailsDTO);
    }

    /**
     * DELETE  /vehicle-details/:id : delete the "id" vehicleDetails.
     *
     * @param id the id of the vehicleDetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicle-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteVehicleDetails(@PathVariable String id) {
        log.debug("REST request to delete VehicleDetails : {}", id);
        vehicleDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
