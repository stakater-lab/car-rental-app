import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Vehicle from './vehicle';
import VehicleType from './vehicle-type';
import Rate from './rate';
import MetaData from './meta-data';
import VehicleDetails from './vehicle-details';
import VehicleModel from './vehicle-model';
import Rating from './rating';
import FeedBack from './feed-back';
import Provider from './provider';
import Address from './address';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/vehicle`} component={Vehicle} />
      <ErrorBoundaryRoute path={`${match.url}/vehicle-type`} component={VehicleType} />
      <ErrorBoundaryRoute path={`${match.url}/rate`} component={Rate} />
      <ErrorBoundaryRoute path={`${match.url}/meta-data`} component={MetaData} />
      <ErrorBoundaryRoute path={`${match.url}/vehicle-details`} component={VehicleDetails} />
      <ErrorBoundaryRoute path={`${match.url}/vehicle-model`} component={VehicleModel} />
      <ErrorBoundaryRoute path={`${match.url}/rating`} component={Rating} />
      <ErrorBoundaryRoute path={`${match.url}/feed-back`} component={FeedBack} />
      <ErrorBoundaryRoute path={`${match.url}/provider`} component={Provider} />
      <ErrorBoundaryRoute path={`${match.url}/address`} component={Address} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
