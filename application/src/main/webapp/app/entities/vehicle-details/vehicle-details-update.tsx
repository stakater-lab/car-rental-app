import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IVehicleModel } from 'app/shared/model/vehicle-model.model';
import { getEntities as getVehicleModels } from 'app/entities/vehicle-model/vehicle-model.reducer';
import { getEntity, updateEntity, createEntity, reset } from './vehicle-details.reducer';
import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVehicleDetailsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IVehicleDetailsUpdateState {
  isNew: boolean;
  modelId: string;
}

export class VehicleDetailsUpdate extends React.Component<IVehicleDetailsUpdateProps, IVehicleDetailsUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      modelId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getVehicleModels();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { vehicleDetailsEntity } = this.props;
      const entity = {
        ...vehicleDetailsEntity,
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
    this.props.history.push('/entity/vehicle-details');
  };

  render() {
    const { vehicleDetailsEntity, vehicleModels, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="jhipsterApp.vehicleDetails.home.createOrEditLabel">Create or edit a VehicleDetails</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : vehicleDetailsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="vehicle-details-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="manufacturerLabel" for="manufacturer">
                    Manufacturer
                  </Label>
                  <AvField id="vehicle-details-manufacturer" type="text" name="manufacturer" />
                </AvGroup>
                <AvGroup>
                  <Label id="colorLabel" for="color">
                    Color
                  </Label>
                  <AvField id="vehicle-details-color" type="text" name="color" />
                </AvGroup>
                <AvGroup>
                  <Label id="transmissionLabel">Transmission</Label>
                  <AvInput
                    id="vehicle-details-transmission"
                    type="select"
                    className="form-control"
                    name="transmission"
                    value={(!isNew && vehicleDetailsEntity.transmission) || 'AUTOMATIC'}
                  >
                    <option value="AUTOMATIC">AUTOMATIC</option>
                    <option value="MANUAL">MANUAL</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="fuelLabel">Fuel</Label>
                  <AvInput
                    id="vehicle-details-fuel"
                    type="select"
                    className="form-control"
                    name="fuel"
                    value={(!isNew && vehicleDetailsEntity.fuel) || 'PETROL'}
                  >
                    <option value="PETROL">PETROL</option>
                    <option value="DIESIL">DIESIL</option>
                    <option value="HYBRID">HYBRID</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="model.id">Model</Label>
                  <AvInput id="vehicle-details-model" type="select" className="form-control" name="modelId">
                    <option value="" key="0" />
                    {vehicleModels
                      ? vehicleModels.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/vehicle-details" replace color="info">
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
  vehicleModels: storeState.vehicleModel.entities,
  vehicleDetailsEntity: storeState.vehicleDetails.entity,
  loading: storeState.vehicleDetails.loading,
  updating: storeState.vehicleDetails.updating
});

const mapDispatchToProps = {
  getVehicleModels,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VehicleDetailsUpdate);
