import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMetaData, defaultValue } from 'app/shared/model/meta-data.model';

export const ACTION_TYPES = {
  FETCH_METADATA_LIST: 'metaData/FETCH_METADATA_LIST',
  FETCH_METADATA: 'metaData/FETCH_METADATA',
  CREATE_METADATA: 'metaData/CREATE_METADATA',
  UPDATE_METADATA: 'metaData/UPDATE_METADATA',
  DELETE_METADATA: 'metaData/DELETE_METADATA',
  RESET: 'metaData/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMetaData>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type MetaDataState = Readonly<typeof initialState>;

// Reducer

export default (state: MetaDataState = initialState, action): MetaDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_METADATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_METADATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_METADATA):
    case REQUEST(ACTION_TYPES.UPDATE_METADATA):
    case REQUEST(ACTION_TYPES.DELETE_METADATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_METADATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_METADATA):
    case FAILURE(ACTION_TYPES.CREATE_METADATA):
    case FAILURE(ACTION_TYPES.UPDATE_METADATA):
    case FAILURE(ACTION_TYPES.DELETE_METADATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_METADATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_METADATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_METADATA):
    case SUCCESS(ACTION_TYPES.UPDATE_METADATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_METADATA):
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

const apiUrl = 'api/meta-data';

// Actions

export const getEntities: ICrudGetAllAction<IMetaData> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_METADATA_LIST,
  payload: axios.get<IMetaData>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IMetaData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_METADATA,
    payload: axios.get<IMetaData>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMetaData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_METADATA,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMetaData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_METADATA,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMetaData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_METADATA,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
