import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './provider.reducer';
import { IProvider } from 'app/shared/model/provider.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProviderUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IProviderUpdateState {
  isNew: boolean;
}

export class ProviderUpdate extends React.Component<IProviderUpdateProps, IProviderUpdateState> {
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
      const { providerEntity } = this.props;
      const entity = {
        ...providerEntity,
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
    this.props.history.push('/entity/provider');
  };

  render() {
    const { providerEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="jhipsterApp.provider.home.createOrEditLabel">Create or edit a Provider</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : providerEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="provider-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="typeLabel">Type</Label>
                  <AvInput
                    id="provider-type"
                    type="select"
                    className="form-control"
                    name="type"
                    value={(!isNew && providerEntity.type) || 'Individual'}
                  >
                    <option value="Individual">Individual</option>
                    <option value="Business">Business</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField id="provider-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="ratingsLabel" for="ratings">
                    Ratings
                  </Label>
                  <AvField id="provider-ratings" type="string" className="form-control" name="ratings" />
                </AvGroup>
                <AvGroup>
                  <Label id="noOfVehiclesLabel" for="noOfVehicles">
                    No Of Vehicles
                  </Label>
                  <AvField id="provider-noOfVehicles" type="string" className="form-control" name="noOfVehicles" />
                </AvGroup>
                <AvGroup>
                  <Label id="emailAddressLabel" for="emailAddress">
                    Email Address
                  </Label>
                  <AvField id="provider-emailAddress" type="text" name="emailAddress" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/provider" replace color="info">
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
  providerEntity: storeState.provider.entity,
  loading: storeState.provider.loading,
  updating: storeState.provider.updating
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
)(ProviderUpdate);
