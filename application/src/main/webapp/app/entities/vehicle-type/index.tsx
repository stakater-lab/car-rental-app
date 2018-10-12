import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VehicleType from './vehicle-type';
import VehicleTypeDetail from './vehicle-type-detail';
import VehicleTypeUpdate from './vehicle-type-update';
import VehicleTypeDeleteDialog from './vehicle-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VehicleTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VehicleTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VehicleTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={VehicleType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={VehicleTypeDeleteDialog} />
  </>
);

export default Routes;
