/*
 *
 * LurinFvFreeviewManagmentContainer reducer
 *
 */

import { fromJS } from 'immutable';
import * as messages from './messages';
import {
  LOAD_FREEVIEWS_ACTION,
  LOAD_FREEVIEWS_ACTION_SUCCESS,
  LOAD_FREEVIEWS_ACTION_FAILURE,
  SHOW_FREEVIEWS_FORM_ACTION,
  CREATE_FREEVIEWS_ACTION,
  CREATE_FREEVIEWS_ACTION_SUCCESS,
  CREATE_FREEVIEWS_ACTION_FAILURE,
  UPDATE_FREEVIEWS_ACTION,
  UPDATE_FREEVIEWS_ACTION_SUCCESS,
  UPDATE_FREEVIEWS_ACTION_FAILURE,
  DELETE_FREEVIEWS_ACTION,
  DELETE_FREEVIEWS_ACTION_SUCCESS,
  DELETE_FREEVIEWS_ACTION_FAILURE,
  INIT_MESSAGE_ACTION
} from './constants';

import {
  MESSAGE_500_ID,
  MESSAGE_TYPE
 } from '../../utils/messages';

const initialState = fromJS({
  loading: false,
  error: false,
  freeviewsData: [],
  hasShowForm: false,
  freeviewData: {},
  newFreeview: 0,
  message: '',
  messageType: '',
  showMessage: false
});

export const lurinFvFreeviewManagmentContainerReducer = (state = initialState, action) => {
  switch (action.type) {
    case LOAD_FREEVIEWS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case LOAD_FREEVIEWS_ACTION_SUCCESS:
      return state
        .set('freeviewsData', action.freeviewsData)
        .set('loading', false);
    case LOAD_FREEVIEWS_ACTION_FAILURE:
      return state
        .set('error', action.error)
        .set('message', MESSAGE_500_ID)
        .set('showMessage', true)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('loading', false);
    case SHOW_FREEVIEWS_FORM_ACTION:
      return state
        .set('hasShowForm', action.hasShowFormFreeviews);
    case CREATE_FREEVIEWS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case CREATE_FREEVIEWS_ACTION_SUCCESS:
      return state
        .set('newFreeview', action.newFreeview)
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.CREATE_FREEVIEW_SUCCESSFULL_ID)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case CREATE_FREEVIEWS_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.CREATE_FREEVIEW_FAILURE_ID)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('error', true);
    case UPDATE_FREEVIEWS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case UPDATE_FREEVIEWS_ACTION_SUCCESS:
      return state
        .set('freeviewData', action.freeviewData)
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.UPDATE_FREEVIEW_SUCCESSFULL_ID)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case UPDATE_FREEVIEWS_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.UPDATE_FREEVIEW_FAILURE_ID)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('error', true);
    case DELETE_FREEVIEWS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case DELETE_FREEVIEWS_ACTION_SUCCESS:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('message', messages.default.DELETE_FREEVIEW_FAILURE_ID)
        .set('error', false);
    case DELETE_FREEVIEWS_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('message', messages.default.DELETE_FREEVIEW_FAILURE_ID)
        .set('error', true);
    case INIT_MESSAGE_ACTION:
      return state
          .set('showMessage', false)
          .set('messageType', '')
          .set('message', '');

    default:
      return state;
  }
};
