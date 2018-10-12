import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './feed-back.reducer';
import { IFeedBack } from 'app/shared/model/feed-back.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFeedBackProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class FeedBack extends React.Component<IFeedBackProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { feedBackList, match } = this.props;
    return (
      <div>
        <h2 id="feed-back-heading">
          Feed Backs
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Feed Back
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Score</th>
                <th>Comment</th>
                <th>Rating</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {feedBackList.map((feedBack, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${feedBack.id}`} color="link" size="sm">
                      {feedBack.id}
                    </Button>
                  </td>
                  <td>{feedBack.score}</td>
                  <td>{feedBack.comment}</td>
                  <td>{feedBack.ratingId ? <Link to={`rating/${feedBack.ratingId}`}>{feedBack.ratingId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${feedBack.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${feedBack.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${feedBack.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ feedBack }: IRootState) => ({
  feedBackList: feedBack.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FeedBack);
