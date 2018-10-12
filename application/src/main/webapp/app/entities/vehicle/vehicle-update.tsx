import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IRating } from 'app/shared/model/rating.model';
import { getEntities as getRatings } from 'app/entities/rating/rating.reducer';
import { IVehicleType } from 'app/shared/model/vehicle-type.model';
import { getEntities as getVehicleTypes } from 'app/entities/vehicle-type/vehicle-type.reducer';
import { IRate } from 'app/shared/model/rate.model';
import { getEntities as getRates } from 'app/entities/rate/rate.reducer';
import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';
import { getEntities as getVehicleDetails } from 'app/entities/vehicle-details/vehicle-details.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './vehicle.reducer';
import { IVehicle } from 'app/shared/model/vehicle.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVehicleUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IVehicleUpdateState {
  isNew: boolean;
  ratingId: string;
  typeId: string;
  rateId: string;
  detailId: string;
}

export class VehicleUpdate extends React.Component<IVehicleUpdateProps, IVehicleUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      ratingId: '0',
      typeId: '0',
      rateId: '0',
      detailId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getRatings();
    this.props.getVehicleTypes();
    this.props.getRates();
    this.props.getVehicleDetails();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { vehicleEntity } = this.props;
      const entity = {
        ...vehicleEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/vehicle');
  };

  render() {
    const { vehicleEntity, ratings, vehicleTypes, rates, vehicleDetails, loading, updating } = this.props;
    const { isNew } = this.state;

    const { images, imagesContentType } = vehicleEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="jhipsterApp.vehicle.home.createOrEditLabel">Create or edit a Vehicle</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : vehicleEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="vehicle-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="ownerIdLabel" for="ownerId">
                    Owner Id
                  </Label>
                  <AvField id="vehicle-ownerId" type="string" className="form-control" name="ownerId" />
                </AvGroup>
                <AvGroup>
                  <Label id="availabilityLabel">Availability</Label>
                  <AvInput
                    id="vehicle-availability"
                    type="select"
                    className="form-control"
                    name="availability"
                    value={(!isNew && vehicleEntity.availability) || 'AVAILABLE'}
                  >
                    <option value="AVAILABLE">AVAILABLE</option>
                    <option value="BOOKED">BOOKED</option>
                    <option value="IN_SERVICE">IN_SERVICE</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="driverLabel">Driver</Label>
                  <AvInput
                    id="vehicle-driver"
                    type="select"
                    className="form-control"
                    name="driver"
                    value={(!isNew && vehicleEntity.driver) || 'WITH'}
                  >
                    <option value="WITH">WITH</option>
                    <option value="WITH_OUT">WITH_OUT</option>
                    <option value="WITH_AND_WITH_OUT">WITH_AND_WITH_OUT</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="imagesLabel" for="images">
                      Images
                    </Label>
                    <br />
                    {images ? (
                      <div>
                        <a onClick={openFile(imagesContentType, images)}>
                          <img src={`data:${imagesContentType};base64,${images}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {imagesContentType}, {byteSize(images)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('images')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_images" type="file" onChange={this.onBlobChange(true, 'images')} accept="image/*" />
                    <AvInput type="hidden" name="images" value={images} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label for="rating.id">Rating</Label>
                  <AvInput id="vehicle-rating" type="select" className="form-control" name="ratingId">
                    <option value="" key="0" />
                    {ratings
                      ? ratings.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="type.id">Type</Label>
                  <AvInput id="vehicle-type" type="select" className="form-control" name="typeId">
                    <option value="" key="0" />
                    {vehicleTypes
                      ? vehicleTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="rate.id">Rate</Label>
                  <AvInput id="vehicle-rate" type="select" className="form-control" name="rateId">
                    <option value="" key="0" />
                    {rates
                      ? rates.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="detail.id">Detail</Label>
                  <AvInput id="vehicle-detail" type="select" className="form-control" name="detailId">
                    <option value="" key="0" />
                    {vehicleDetails
                      ? vehicleDetails.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/vehicle" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  ratings: storeState.rating.entities,
  vehicleTypes: storeState.vehicleType.entities,
  rates: storeState.rate.entities,
  vehicleDetails: storeState.vehicleDetails.entities,
  vehicleEntity: storeState.vehicle.entity,
  loading: storeState.vehicle.loading,
  updating: storeState.vehicle.updating
});

const mapDispatchToProps = {
  getRatings,
  getVehicleTypes,
  getRates,
  getVehicleDetails,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VehicleUpdate);
