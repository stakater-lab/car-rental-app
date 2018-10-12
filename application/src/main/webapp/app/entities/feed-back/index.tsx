import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FeedBack from './feed-back';
import FeedBackDetail from './feed-back-detail';
import FeedBackUpdate from './feed-back-update';
import FeedBackDeleteDialog from './feed-back-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FeedBackUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FeedBackUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FeedBackDetail} />
      <ErrorBoundaryRoute path={match.url} component={FeedBack} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FeedBackDeleteDialog} />
  </>
);

export default Routes;
