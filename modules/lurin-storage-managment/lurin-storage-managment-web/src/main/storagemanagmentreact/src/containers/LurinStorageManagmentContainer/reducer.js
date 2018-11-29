/*
 *
 * LurinStorageManagmentContainer reducer
 *
 */
import { fromJS } from 'immutable';
import * as messages from './messages';
import {
  MESSAGE_500_ID,
  MESSAGE_TYPE
 } from '../../utils/messages';
import {
  LOAD_STORAGES_ACTION,
  LOAD_STORAGES_ACTION_SUCCESS,
  LOAD_STORAGES_ACTION_FAILURE,
  SHOW_STORAGES_FORM_ACTION,
  RESET_ALERT_MESSAGE_ACTION,
  CREATE_STORAGES_ACTION,
  CREATE_STORAGES_ACTION_SUCCESS,
  CREATE_STORAGES_ACTION_FAILURE,
  UPDATE_STORAGES_ACTION,
  UPDATE_STORAGES_ACTION_SUCCESS,
  UPDATE_STORAGES_ACTION_FAILURE,
  DELETE_STORAGES_ACTION,
  DELETE_STORAGES_ACTION_SUCCESS,
  DELETE_STORAGES_ACTION_FAILURE,
  LOAD_SOURCETYPES_ACTION,
  LOAD_SOURCETYPES_ACTION_SUCCESS,
  LOAD_SOURCETYPES_ACTION_FAILURE,
  LOAD_STATUS_ACTION,
  LOAD_STATUS_ACTION_SUCCESS,
  LOAD_STATUS_ACTION_FAILURE,
  SEND_TESTDS_ACTION,
  SEND_TESTDS_ACTION_SUCCESS,
  SEND_TESTDS_ACTION_FAILURE,
  INIT_TESTDS_ACTION,
  INIT_MESSAGE_ACTION
} from './constants';

const initialState = fromJS({
  loading: false,
  error: false,
  storagesData: [],
  hasShowForm: false,
  message: '',
  messageType: '',
  showMessage: false,
  storageData: {},
  newStorage: 0,
  sourceTypesData: [],
  statusData: [],
  isValidConection: false,
  testParms:[]
});

export const lurinStorageManagmentContainerReducer = (state = initialState, action) => {
  switch (action.type) {
    case LOAD_STORAGES_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case LOAD_STORAGES_ACTION_SUCCESS:
      return state
        .set('storagesData', action.storagesData)
        .set('loading', false);
    case LOAD_STORAGES_ACTION_FAILURE:
      return state
        .set('error', action.error)
        .set('showMessage', true)
        .set('message', MESSAGE_500_ID)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('loading', false);
    case LOAD_SOURCETYPES_ACTION:
      return state
          .set('loading', true)
          .set('showMessage', false)
          .set('error', false);
    case LOAD_SOURCETYPES_ACTION_SUCCESS: {
      const initSourceTypeData = [{ sourceTypeId: 0, name: '- Seleccionar -' }];
      return state
          .set('sourceTypesData', initSourceTypeData.concat(action.sourceTypesData))
          .set('showMessage', false)
          .set('loading', false);
    }
    case LOAD_SOURCETYPES_ACTION_FAILURE:
      return state
          .set('error', action.error)
          .set('showMessage', true)
          .set('message', MESSAGE_500_ID)
          .set('messageType', MESSAGE_TYPE.WARNING)
          .set('loading', false);
    case LOAD_STATUS_ACTION:
      return state
              .set('loading', true)
              .set('showMessage', false)
              .set('error', false);
    case LOAD_STATUS_ACTION_SUCCESS:
      return state
              .set('statusData', action.statusData)
              .set('showMessage', false)
              .set('loading', false);
    case LOAD_STATUS_ACTION_FAILURE:
      return state
              .set('error', action.error)
              .set('showMessage', true)
              .set('message', MESSAGE_500_ID)
              .set('messageType', MESSAGE_TYPE.WARNING)
              .set('loading', false);
    case SEND_TESTDS_ACTION:
      return state
              .set('loading', true)
              .set('showMessage', false)
              .set('error', false);
    case SEND_TESTDS_ACTION_SUCCESS:
    {
      let parms =[];
      if (action.testData.message=='CONNECTION_FAILED_MORE_THAN_ONE' || action.testData.message=='CONNECTION_FAILED_ONLY_ONE'  ){
        parms=action.testData.params;
      }
      return state
              .set('showMessage', true)
              .set('message', action.testData.message)
              .set('isValidConection', action.testData.valid)
              .set('testParms', parms)
              .set('messageType', MESSAGE_TYPE.WARNING)
              .set('error', false)
              .set('loading', false);
   }
    case SEND_TESTDS_ACTION_FAILURE:
      {
        return state
              .set('error', action.error)
              .set('showMessage', true)
              .set('message', messages.default.TEST_CONNECTION_FAILURE_ID)
              .set('messageType', MESSAGE_TYPE.WARNING)
              .set('loading', false);
      }
    case SHOW_STORAGES_FORM_ACTION:
      return state
        .set('hasShowForm', action.hasShowFormStorages)
        .set('showMessage', false);
    case RESET_ALERT_MESSAGE_ACTION:
        return state
          .set('showMessage', false);
    case CREATE_STORAGES_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case CREATE_STORAGES_ACTION_SUCCESS:
      return state
        .set('newStorage', action.newStorage)
        .set('loading', false)
        .set('showMessage', true)
        .set('isValidConection', false)
        .set('message', messages.default.CREATE_STORAGE_SUCCESSFULL_ID)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case CREATE_STORAGES_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.CREATE_STORAGE_FAILURE_ID)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('error', true);
    case UPDATE_STORAGES_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case UPDATE_STORAGES_ACTION_SUCCESS:
      return state
        .set('storageData', action.storageData)
        .set('loading', false)
        .set('showMessage', true)
        .set('isValidConection', false)
        .set('message', messages.default.UPDATE_STORAGE_SUCCESSFULL_ID)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case UPDATE_STORAGES_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('message', messages.default.UPDATE_STORAGE_FAILURE_ID)
        .set('error', true);
    case DELETE_STORAGES_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case DELETE_STORAGES_ACTION_SUCCESS:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.DELETE_STORAGE_SUCCESSFULL_ID)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case DELETE_STORAGES_ACTION_FAILURE:
    let message = messages.default.DELETE_STORAGE_FAILURE_ID;
    if (action.data!=undefined){
      if(action.data.code=='0004')
          message = messages.default.DELETE_STORAGE_FAILURE_0004_ID;
    }
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('message', message)
        .set('error', true);
      case INIT_TESTDS_ACTION:
        return state .set('isValidConection', false);
    case INIT_MESSAGE_ACTION:
        return state
            .set('showMessage', false)
            .set('messageType', '')
            .set('message', '');
    default:
      return state;
  }
};
