import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './rate.reducer';
import { IRate } from 'app/shared/model/rate.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RateDetail extends React.Component<IRateDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { rateEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Rate [<b>{rateEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="hourly">Hourly</span>
            </dt>
            <dd>{rateEntity.hourly}</dd>
            <dt>
              <span id="hourlyWithoutDriver">Hourly Without Driver</span>
            </dt>
            <dd>{rateEntity.hourlyWithoutDriver}</dd>
            <dt>
              <span id="daily">Daily</span>
            </dt>
            <dd>{rateEntity.daily}</dd>
            <dt>
              <span id="dailyWithoutDriver">Daily Without Driver</span>
            </dt>
            <dd>{rateEntity.dailyWithoutDriver}</dd>
          </dl>
          <Button tag={Link} to="/entity/rate" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/rate/${rateEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ rate }: IRootState) => ({
  rateEntity: rate.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RateDetail);
