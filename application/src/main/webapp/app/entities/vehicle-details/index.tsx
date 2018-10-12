import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VehicleDetails from './vehicle-details';
import VehicleDetailsDetail from './vehicle-details-detail';
import VehicleDetailsUpdate from './vehicle-details-update';
import VehicleDetailsDeleteDialog from './vehicle-details-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VehicleDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VehicleDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VehicleDetailsDetail} />
      <ErrorBoundaryRoute path={match.url} component={VehicleDetails} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={VehicleDetailsDeleteDialog} />
  </>
);

export default Routes;
