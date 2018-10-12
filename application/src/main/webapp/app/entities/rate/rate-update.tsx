import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './rate.reducer';
import { IRate } from 'app/shared/model/rate.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRateUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRateUpdateState {
  isNew: boolean;
}

export class RateUpdate extends React.Component<IRateUpdateProps, IRateUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { rateEntity } = this.props;
      const entity = {
        ...rateEntity,
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
    this.props.history.push('/entity/rate');
  };

  render() {
    const { rateEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="jhipsterApp.rate.home.createOrEditLabel">Create or edit a Rate</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : rateEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="rate-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="hourlyLabel" for="hourly">
                    Hourly
                  </Label>
                  <AvField id="rate-hourly" type="text" name="hourly" />
                </AvGroup>
                <AvGroup>
                  <Label id="hourlyWithoutDriverLabel" for="hourlyWithoutDriver">
                    Hourly Without Driver
                  </Label>
                  <AvField id="rate-hourlyWithoutDriver" type="text" name="hourlyWithoutDriver" />
                </AvGroup>
                <AvGroup>
                  <Label id="dailyLabel" for="daily">
                    Daily
                  </Label>
                  <AvField id="rate-daily" type="text" name="daily" />
                </AvGroup>
                <AvGroup>
                  <Label id="dailyWithoutDriverLabel" for="dailyWithoutDriver">
                    Daily Without Driver
                  </Label>
                  <AvField id="rate-dailyWithoutDriver" type="text" name="dailyWithoutDriver" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/rate" replace color="info">
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
  rateEntity: storeState.rate.entity,
  loading: storeState.rate.loading,
  updating: storeState.rate.updating
});

const mapDispatchToProps = {
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
)(RateUpdate);
