package io.aurora.provider.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.aurora.provider.service.VehicleModelService;
import io.aurora.provider.web.rest.errors.BadRequestAlertException;
import io.aurora.provider.web.rest.util.HeaderUtil;
import io.aurora.provider.service.dto.VehicleModelDTO;
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
 * REST controller for managing VehicleModel.
 */
@RestController
@RequestMapping("/api")
public class VehicleModelResource {

    private final Logger log = LoggerFactory.getLogger(VehicleModelResource.class);

    private static final String ENTITY_NAME = "vehicleModel";

    private final VehicleModelService vehicleModelService;

    public VehicleModelResource(VehicleModelService vehicleModelService) {
        this.vehicleModelService = vehicleModelService;
    }

    /**
     * POST  /vehicle-models : Create a new vehicleModel.
     *
     * @param vehicleModelDTO the vehicleModelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehicleModelDTO, or with status 400 (Bad Request) if the vehicleModel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicle-models")
    @Timed
    public ResponseEntity<VehicleModelDTO> createVehicleModel(@RequestBody VehicleModelDTO vehicleModelDTO) throws URISyntaxException {
        log.debug("REST request to save VehicleModel : {}", vehicleModelDTO);
        if (vehicleModelDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleModelDTO result = vehicleModelService.save(vehicleModelDTO);
        return ResponseEntity.created(new URI("/api/vehicle-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicle-models : Updates an existing vehicleModel.
     *
     * @param vehicleModelDTO the vehicleModelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehicleModelDTO,
     * or with status 400 (Bad Request) if the vehicleModelDTO is not valid,
     * or with status 500 (Internal Server Error) if the vehicleModelDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicle-models")
    @Timed
    public ResponseEntity<VehicleModelDTO> updateVehicleModel(@RequestBody VehicleModelDTO vehicleModelDTO) throws URISyntaxException {
        log.debug("REST request to update VehicleModel : {}", vehicleModelDTO);
        if (vehicleModelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VehicleModelDTO result = vehicleModelService.save(vehicleModelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehicleModelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicle-models : get all the vehicleModels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vehicleModels in body
     */
    @GetMapping("/vehicle-models")
    @Timed
    public List<VehicleModelDTO> getAllVehicleModels() {
        log.debug("REST request to get all VehicleModels");
        return vehicleModelService.findAll();
    }

    /**
     * GET  /vehicle-models/:id : get the "id" vehicleModel.
     *
     * @param id the id of the vehicleModelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehicleModelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vehicle-models/{id}")
    @Timed
    public ResponseEntity<VehicleModelDTO> getVehicleModel(@PathVariable String id) {
        log.debug("REST request to get VehicleModel : {}", id);
        Optional<VehicleModelDTO> vehicleModelDTO = vehicleModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleModelDTO);
    }

    /**
     * DELETE  /vehicle-models/:id : delete the "id" vehicleModel.
     *
     * @param id the id of the vehicleModelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicle-models/{id}")
    @Timed
    public ResponseEntity<Void> deleteVehicleModel(@PathVariable String id) {
        log.debug("REST request to delete VehicleModel : {}", id);
        vehicleModelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
