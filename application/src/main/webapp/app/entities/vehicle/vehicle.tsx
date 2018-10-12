import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { openFile, byteSize, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './vehicle.reducer';
import { IVehicle } from 'app/shared/model/vehicle.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVehicleProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Vehicle extends React.Component<IVehicleProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { vehicleList, match } = this.props;
    return (
      <div>
        <h2 id="vehicle-heading">
          Vehicles
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Vehicle
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Owner Id</th>
                <th>Availability</th>
                <th>Driver</th>
                <th>Images</th>
                <th>Rating</th>
                <th>Type</th>
                <th>Rate</th>
                <th>Detail</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vehicleList.map((vehicle, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${vehicle.id}`} color="link" size="sm">
                      {vehicle.id}
                    </Button>
                  </td>
                  <td>{vehicle.ownerId}</td>
                  <td>{vehicle.availability}</td>
                  <td>{vehicle.driver}</td>
                  <td>
                    {vehicle.images ? (
                      <div>
                        <a onClick={openFile(vehicle.imagesContentType, vehicle.images)}>
                          <img src={`data:${vehicle.imagesContentType};base64,${vehicle.images}`} style={{ maxHeight: '30px' }} />
                          &nbsp;
                        </a>
                        <span>
                          {vehicle.imagesContentType}, {byteSize(vehicle.images)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{vehicle.ratingId ? <Link to={`rating/${vehicle.ratingId}`}>{vehicle.ratingId}</Link> : ''}</td>
                  <td>{vehicle.typeId ? <Link to={`vehicle-type/${vehicle.typeId}`}>{vehicle.typeId}</Link> : ''}</td>
                  <td>{vehicle.rateId ? <Link to={`rate/${vehicle.rateId}`}>{vehicle.rateId}</Link> : ''}</td>
                  <td>{vehicle.detailId ? <Link to={`vehicle-details/${vehicle.detailId}`}>{vehicle.detailId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${vehicle.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vehicle.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vehicle.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ vehicle }: IRootState) => ({
  vehicleList: vehicle.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Vehicle);
