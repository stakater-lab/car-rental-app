import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from './user-management';
// prettier-ignore
import vehicle, {
  VehicleState
} from 'app/entities/vehicle/vehicle.reducer';
// prettier-ignore
import vehicleType, {
  VehicleTypeState
} from 'app/entities/vehicle-type/vehicle-type.reducer';
// prettier-ignore
import rate, {
  RateState
} from 'app/entities/rate/rate.reducer';
// prettier-ignore
import metaData, {
  MetaDataState
} from 'app/entities/meta-data/meta-data.reducer';
// prettier-ignore
import vehicleDetails, {
  VehicleDetailsState
} from 'app/entities/vehicle-details/vehicle-details.reducer';
// prettier-ignore
import vehicleModel, {
  VehicleModelState
} from 'app/entities/vehicle-model/vehicle-model.reducer';
// prettier-ignore
import rating, {
  RatingState
} from 'app/entities/rating/rating.reducer';
// prettier-ignore
import feedBack, {
  FeedBackState
} from 'app/entities/feed-back/feed-back.reducer';
// prettier-ignore
import provider, {
  ProviderState
} from 'app/entities/provider/provider.reducer';
// prettier-ignore
import address, {
  AddressState
} from 'app/entities/address/address.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly vehicle: VehicleState;
  readonly vehicleType: VehicleTypeState;
  readonly rate: RateState;
  readonly metaData: MetaDataState;
  readonly vehicleDetails: VehicleDetailsState;
  readonly vehicleModel: VehicleModelState;
  readonly rating: RatingState;
  readonly feedBack: FeedBackState;
  readonly provider: ProviderState;
  readonly address: AddressState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  vehicle,
  vehicleType,
  rate,
  metaData,
  vehicleDetails,
  vehicleModel,
  rating,
  feedBack,
  provider,
  address,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
