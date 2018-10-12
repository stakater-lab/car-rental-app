import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './meta-data.reducer';
import { IMetaData } from 'app/shared/model/meta-data.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMetaDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MetaDataDetail extends React.Component<IMetaDataDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { metaDataEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            MetaData [<b>{metaDataEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{metaDataEntity.name}</dd>
            <dt>
              <span id="value">Value</span>
            </dt>
            <dd>{metaDataEntity.value}</dd>
            <dt>Vehicle</dt>
            <dd>{metaDataEntity.vehicleId ? metaDataEntity.vehicleId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/meta-data" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/meta-data/${metaDataEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ metaData }: IRootState) => ({
  metaDataEntity: metaData.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MetaDataDetail);
