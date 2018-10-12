import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVehicleModel, defaultValue } from 'app/shared/model/vehicle-model.model';

export const ACTION_TYPES = {
  FETCH_VEHICLEMODEL_LIST: 'vehicleModel/FETCH_VEHICLEMODEL_LIST',
  FETCH_VEHICLEMODEL: 'vehicleModel/FETCH_VEHICLEMODEL',
  CREATE_VEHICLEMODEL: 'vehicleModel/CREATE_VEHICLEMODEL',
  UPDATE_VEHICLEMODEL: 'vehicleModel/UPDATE_VEHICLEMODEL',
  DELETE_VEHICLEMODEL: 'vehicleModel/DELETE_VEHICLEMODEL',
  RESET: 'vehicleModel/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVehicleModel>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type VehicleModelState = Readonly<typeof initialState>;

// Reducer

export default (state: VehicleModelState = initialState, action): VehicleModelState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VEHICLEMODEL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VEHICLEMODEL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_VEHICLEMODEL):
    case REQUEST(ACTION_TYPES.UPDATE_VEHICLEMODEL):
    case REQUEST(ACTION_TYPES.DELETE_VEHICLEMODEL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_VEHICLEMODEL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VEHICLEMODEL):
    case FAILURE(ACTION_TYPES.CREATE_VEHICLEMODEL):
    case FAILURE(ACTION_TYPES.UPDATE_VEHICLEMODEL):
    case FAILURE(ACTION_TYPES.DELETE_VEHICLEMODEL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_VEHICLEMODEL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_VEHICLEMODEL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_VEHICLEMODEL):
    case SUCCESS(ACTION_TYPES.UPDATE_VEHICLEMODEL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_VEHICLEMODEL):
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

const apiUrl = 'api/vehicle-models';

// Actions

export const getEntities: ICrudGetAllAction<IVehicleModel> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_VEHICLEMODEL_LIST,
  payload: axios.get<IVehicleModel>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IVehicleModel> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VEHICLEMODEL,
    payload: axios.get<IVehicleModel>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IVehicleModel> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VEHICLEMODEL,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVehicleModel> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VEHICLEMODEL,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVehicleModel> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VEHICLEMODEL,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
