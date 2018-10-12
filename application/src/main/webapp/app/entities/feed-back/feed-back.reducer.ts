import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFeedBack, defaultValue } from 'app/shared/model/feed-back.model';

export const ACTION_TYPES = {
  FETCH_FEEDBACK_LIST: 'feedBack/FETCH_FEEDBACK_LIST',
  FETCH_FEEDBACK: 'feedBack/FETCH_FEEDBACK',
  CREATE_FEEDBACK: 'feedBack/CREATE_FEEDBACK',
  UPDATE_FEEDBACK: 'feedBack/UPDATE_FEEDBACK',
  DELETE_FEEDBACK: 'feedBack/DELETE_FEEDBACK',
  RESET: 'feedBack/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFeedBack>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type FeedBackState = Readonly<typeof initialState>;

// Reducer

export default (state: FeedBackState = initialState, action): FeedBackState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FEEDBACK_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FEEDBACK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FEEDBACK):
    case REQUEST(ACTION_TYPES.UPDATE_FEEDBACK):
    case REQUEST(ACTION_TYPES.DELETE_FEEDBACK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_FEEDBACK_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FEEDBACK):
    case FAILURE(ACTION_TYPES.CREATE_FEEDBACK):
    case FAILURE(ACTION_TYPES.UPDATE_FEEDBACK):
    case FAILURE(ACTION_TYPES.DELETE_FEEDBACK):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_FEEDBACK_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FEEDBACK):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FEEDBACK):
    case SUCCESS(ACTION_TYPES.UPDATE_FEEDBACK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FEEDBACK):
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

const apiUrl = 'api/feed-backs';

// Actions

export const getEntities: ICrudGetAllAction<IFeedBack> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FEEDBACK_LIST,
  payload: axios.get<IFeedBack>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IFeedBack> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FEEDBACK,
    payload: axios.get<IFeedBack>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFeedBack> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FEEDBACK,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFeedBack> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FEEDBACK,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFeedBack> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FEEDBACK,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
