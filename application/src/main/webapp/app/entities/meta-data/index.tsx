import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MetaData from './meta-data';
import MetaDataDetail from './meta-data-detail';
import MetaDataUpdate from './meta-data-update';
import MetaDataDeleteDialog from './meta-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MetaDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MetaDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MetaDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={MetaData} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MetaDataDeleteDialog} />
  </>
);

export default Routes;
