import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './vehicle.reducer';
import { IVehicle } from 'app/shared/model/vehicle.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVehicleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class VehicleDetail extends React.Component<IVehicleDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { vehicleEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Vehicle [<b>{vehicleEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="ownerId">Owner Id</span>
            </dt>
            <dd>{vehicleEntity.ownerId}</dd>
            <dt>
              <span id="availability">Availability</span>
            </dt>
            <dd>{vehicleEntity.availability}</dd>
            <dt>
              <span id="driver">Driver</span>
            </dt>
            <dd>{vehicleEntity.driver}</dd>
            <dt>
              <span id="images">Images</span>
            </dt>
            <dd>
              {vehicleEntity.images ? (
                <div>
                  <a onClick={openFile(vehicleEntity.imagesContentType, vehicleEntity.images)}>
                    <img src={`data:${vehicleEntity.imagesContentType};base64,${vehicleEntity.images}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {vehicleEntity.imagesContentType}, {byteSize(vehicleEntity.images)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>Rating</dt>
            <dd>{vehicleEntity.ratingId ? vehicleEntity.ratingId : ''}</dd>
            <dt>Type</dt>
            <dd>{vehicleEntity.typeId ? vehicleEntity.typeId : ''}</dd>
            <dt>Rate</dt>
            <dd>{vehicleEntity.rateId ? vehicleEntity.rateId : ''}</dd>
            <dt>Detail</dt>
            <dd>{vehicleEntity.detailId ? vehicleEntity.detailId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/vehicle" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/vehicle/${vehicleEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ vehicle }: IRootState) => ({
  vehicleEntity: vehicle.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VehicleDetail);
