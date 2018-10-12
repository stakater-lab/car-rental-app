import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVehicleDetails, defaultValue } from 'app/shared/model/vehicle-details.model';

export const ACTION_TYPES = {
  FETCH_VEHICLEDETAILS_LIST: 'vehicleDetails/FETCH_VEHICLEDETAILS_LIST',
  FETCH_VEHICLEDETAILS: 'vehicleDetails/FETCH_VEHICLEDETAILS',
  CREATE_VEHICLEDETAILS: 'vehicleDetails/CREATE_VEHICLEDETAILS',
  UPDATE_VEHICLEDETAILS: 'vehicleDetails/UPDATE_VEHICLEDETAILS',
  DELETE_VEHICLEDETAILS: 'vehicleDetails/DELETE_VEHICLEDETAILS',
  RESET: 'vehicleDetails/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVehicleDetails>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type VehicleDetailsState = Readonly<typeof initialState>;

// Reducer

export default (state: VehicleDetailsState = initialState, action): VehicleDetailsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VEHICLEDETAILS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VEHICLEDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_VEHICLEDETAILS):
    case REQUEST(ACTION_TYPES.UPDATE_VEHICLEDETAILS):
    case REQUEST(ACTION_TYPES.DELETE_VEHICLEDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_VEHICLEDETAILS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VEHICLEDETAILS):
    case FAILURE(ACTION_TYPES.CREATE_VEHICLEDETAILS):
    case FAILURE(ACTION_TYPES.UPDATE_VEHICLEDETAILS):
    case FAILURE(ACTION_TYPES.DELETE_VEHICLEDETAILS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_VEHICLEDETAILS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_VEHICLEDETAILS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_VEHICLEDETAILS):
    case SUCCESS(ACTION_TYPES.UPDATE_VEHICLEDETAILS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_VEHICLEDETAILS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/vehicle-details';

// Actions

export const getEntities: ICrudGetAllAction<IVehicleDetails> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_VEHICLEDETAILS_LIST,
  payload: axios.get<IVehicleDetails>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IVehicleDetails> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VEHICLEDETAILS,
    payload: axios.get<IVehicleDetails>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IVehicleDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VEHICLEDETAILS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVehicleDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VEHICLEDETAILS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVehicleDetails> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VEHICLEDETAILS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
