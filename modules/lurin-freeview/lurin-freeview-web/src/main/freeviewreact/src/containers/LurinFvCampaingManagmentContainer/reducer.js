/*
 *
 * LurinFvCampaingManagmentContainer reducer
 *
 */
import { fromJS } from 'immutable';
import * as messages from './messages';
import {
  MESSAGE_500_ID,
  MESSAGE_TYPE
 } from '../../utils/messages';

import {
  LOAD_CAMPAINGS_ACTION,
  LOAD_CAMPAINGS_ACTION_SUCCESS,
  LOAD_CAMPAINGS_ACTION_FAILURE,
  SHOW_CAMPAINGS_FORM_ACTION,
  CREATE_CAMPAINGS_ACTION,
  CREATE_CAMPAINGS_ACTION_SUCCESS,
  CREATE_CAMPAINGS_ACTION_FAILURE,
  UPDATE_CAMPAINGS_ACTION,
  UPDATE_CAMPAINGS_ACTION_SUCCESS,
  UPDATE_CAMPAINGS_ACTION_FAILURE,
  DELETE_CAMPAINGS_ACTION,
  DELETE_CAMPAINGS_ACTION_SUCCESS,
  DELETE_CAMPAINGS_ACTION_FAILURE,
  INIT_MESSAGE_ACTION
} from './constants';

const initialState = fromJS({
  loading: false,
  error: false,
  campaingsData: [],
  hasShowForm: false,
  campaingData: {},
  newCampaing: 0,
  message: '',
  messageType: '',
  showMessage: false
});

export const lurinFvCampaingManagmentContainerReducer = (state = initialState, action) => {
  switch (action.type) {
    case LOAD_CAMPAINGS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case LOAD_CAMPAINGS_ACTION_SUCCESS:
      return state
        .set('campaingsData', action.campaingsData)
        .set('loading', false);
    case LOAD_CAMPAINGS_ACTION_FAILURE:
      return state
        .set('error', action.error)
        .set('message', MESSAGE_500_ID)
        .set('showMessage', true)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('loading', false);
    case SHOW_CAMPAINGS_FORM_ACTION:
      return state
        .set('hasShowForm', action.hasShowFormCampaings);
    case CREATE_CAMPAINGS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case CREATE_CAMPAINGS_ACTION_SUCCESS:
      return state
        .set('newCampaing', action.newCampaing)
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.CREATE_CAMPAIGN_SUCCESSFULL_ID)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case CREATE_CAMPAINGS_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.CREATE_CAMPAIGN_FAILURE_ID)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('error', true);
    case UPDATE_CAMPAINGS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case UPDATE_CAMPAINGS_ACTION_SUCCESS:
      return state
        .set('campaingData', action.campaingData)
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.UPDATE_CAMPAIGN_SUCCESSFULL_ID)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case UPDATE_CAMPAINGS_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.UPDATE_CAMPAIGN_SUCCESSFULL_ID)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', true);
    case DELETE_CAMPAINGS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case DELETE_CAMPAINGS_ACTION_SUCCESS:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.DELETE_CAMPAIGN_SUCCESSFULL_ID)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case DELETE_CAMPAINGS_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('message', messages.default.DELETE_CAMPAIGN_FAILURE_ID)
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
