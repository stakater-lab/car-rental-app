import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './feed-back.reducer';
import { IFeedBack } from 'app/shared/model/feed-back.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFeedBackDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FeedBackDetail extends React.Component<IFeedBackDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { feedBackEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            FeedBack [<b>{feedBackEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="score">Score</span>
            </dt>
            <dd>{feedBackEntity.score}</dd>
            <dt>
              <span id="comment">Comment</span>
            </dt>
            <dd>{feedBackEntity.comment}</dd>
            <dt>Rating</dt>
            <dd>{feedBackEntity.ratingId ? feedBackEntity.ratingId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/feed-back" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/feed-back/${feedBackEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ feedBack }: IRootState) => ({
  feedBackEntity: feedBack.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FeedBackDetail);
