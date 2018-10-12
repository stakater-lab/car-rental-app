import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VehicleModel from './vehicle-model';
import VehicleModelDetail from './vehicle-model-detail';
import VehicleModelUpdate from './vehicle-model-update';
import VehicleModelDeleteDialog from './vehicle-model-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VehicleModelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VehicleModelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VehicleModelDetail} />
      <ErrorBoundaryRoute path={match.url} component={VehicleModel} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={VehicleModelDeleteDialog} />
  </>
);

export default Routes;
