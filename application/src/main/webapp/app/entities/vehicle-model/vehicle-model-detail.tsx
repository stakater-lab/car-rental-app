import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './vehicle-model.reducer';
import { IVehicleModel } from 'app/shared/model/vehicle-model.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVehicleModelDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class VehicleModelDetail extends React.Component<IVehicleModelDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { vehicleModelEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            VehicleModel [<b>{vehicleModelEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{vehicleModelEntity.name}</dd>
            <dt>
              <span id="year">Year</span>
            </dt>
            <dd>{vehicleModelEntity.year}</dd>
          </dl>
          <Button tag={Link} to="/entity/vehicle-model" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/vehicle-model/${vehicleModelEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ vehicleModel }: IRootState) => ({
  vehicleModelEntity: vehicleModel.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VehicleModelDetail);
