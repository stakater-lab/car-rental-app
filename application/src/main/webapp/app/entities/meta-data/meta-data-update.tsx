import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IVehicle } from 'app/shared/model/vehicle.model';
import { getEntities as getVehicles } from 'app/entities/vehicle/vehicle.reducer';
import { getEntity, updateEntity, createEntity, reset } from './meta-data.reducer';
import { IMetaData } from 'app/shared/model/meta-data.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMetaDataUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMetaDataUpdateState {
  isNew: boolean;
  vehicleId: string;
}

export class MetaDataUpdate extends React.Component<IMetaDataUpdateProps, IMetaDataUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      vehicleId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getVehicles();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { metaDataEntity } = this.props;
      const entity = {
        ...metaDataEntity,
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
    this.props.history.push('/entity/meta-data');
  };

  render() {
    const { metaDataEntity, vehicles, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="jhipsterApp.metaData.home.createOrEditLabel">Create or edit a MetaData</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : metaDataEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="meta-data-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField id="meta-data-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="valueLabel" for="value">
                    Value
                  </Label>
                  <AvField id="meta-data-value" type="text" name="value" />
                </AvGroup>
                <AvGroup>
                  <Label for="vehicle.id">Vehicle</Label>
                  <AvInput id="meta-data-vehicle" type="select" className="form-control" name="vehicleId">
                    <option value="" key="0" />
                    {vehicles
                      ? vehicles.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/meta-data" replace color="info">
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
  vehicles: storeState.vehicle.entities,
  metaDataEntity: storeState.metaData.entity,
  loading: storeState.metaData.loading,
  updating: storeState.metaData.updating
});

const mapDispatchToProps = {
  getVehicles,
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
)(MetaDataUpdate);
