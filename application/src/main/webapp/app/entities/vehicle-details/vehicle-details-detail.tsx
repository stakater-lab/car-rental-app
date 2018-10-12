import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './vehicle-details.reducer';
import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVehicleDetailsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class VehicleDetailsDetail extends React.Component<IVehicleDetailsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { vehicleDetailsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            VehicleDetails [<b>{vehicleDetailsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="manufacturer">Manufacturer</span>
            </dt>
            <dd>{vehicleDetailsEntity.manufacturer}</dd>
            <dt>
              <span id="color">Color</span>
            </dt>
            <dd>{vehicleDetailsEntity.color}</dd>
            <dt>
              <span id="transmission">Transmission</span>
            </dt>
            <dd>{vehicleDetailsEntity.transmission}</dd>
            <dt>
              <span id="fuel">Fuel</span>
            </dt>
            <dd>{vehicleDetailsEntity.fuel}</dd>
            <dt>Model</dt>
            <dd>{vehicleDetailsEntity.modelId ? vehicleDetailsEntity.modelId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/vehicle-details" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/vehicle-details/${vehicleDetailsEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ vehicleDetails }: IRootState) => ({
  vehicleDetailsEntity: vehicleDetails.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VehicleDetailsDetail);
