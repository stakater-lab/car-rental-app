import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IRating } from 'app/shared/model/rating.model';
import { getEntities as getRatings } from 'app/entities/rating/rating.reducer';
import { getEntity, updateEntity, createEntity, reset } from './feed-back.reducer';
import { IFeedBack } from 'app/shared/model/feed-back.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFeedBackUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IFeedBackUpdateState {
  isNew: boolean;
  ratingId: string;
}

export class FeedBackUpdate extends React.Component<IFeedBackUpdateProps, IFeedBackUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      ratingId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getRatings();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { feedBackEntity } = this.props;
      const entity = {
        ...feedBackEntity,
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
    this.props.history.push('/entity/feed-back');
  };

  render() {
    const { feedBackEntity, ratings, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="jhipsterApp.feedBack.home.createOrEditLabel">Create or edit a FeedBack</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : feedBackEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="feed-back-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="scoreLabel" for="score">
                    Score
                  </Label>
                  <AvField id="feed-back-score" type="string" className="form-control" name="score" />
                </AvGroup>
                <AvGroup>
                  <Label id="commentLabel" for="comment">
                    Comment
                  </Label>
                  <AvField id="feed-back-comment" type="text" name="comment" />
                </AvGroup>
                <AvGroup>
                  <Label for="rating.id">Rating</Label>
                  <AvInput id="feed-back-rating" type="select" className="form-control" name="ratingId">
                    <option value="" key="0" />
                    {ratings
                      ? ratings.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/feed-back" replace color="info">
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
  ratings: storeState.rating.entities,
  feedBackEntity: storeState.feedBack.entity,
  loading: storeState.feedBack.loading,
  updating: storeState.feedBack.updating
});

const mapDispatchToProps = {
  getRatings,
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
)(FeedBackUpdate);
