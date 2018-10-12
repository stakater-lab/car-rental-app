import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './provider.reducer';
import { IProvider } from 'app/shared/model/provider.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProviderDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProviderDetail extends React.Component<IProviderDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { providerEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Provider [<b>{providerEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="type">Type</span>
            </dt>
            <dd>{providerEntity.type}</dd>
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{providerEntity.name}</dd>
            <dt>
              <span id="ratings">Ratings</span>
            </dt>
            <dd>{providerEntity.ratings}</dd>
            <dt>
              <span id="noOfVehicles">No Of Vehicles</span>
            </dt>
            <dd>{providerEntity.noOfVehicles}</dd>
            <dt>
              <span id="emailAddress">Email Address</span>
            </dt>
            <dd>{providerEntity.emailAddress}</dd>
          </dl>
          <Button tag={Link} to="/entity/provider" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/provider/${providerEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ provider }: IRootState) => ({
  providerEntity: provider.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProviderDetail);
