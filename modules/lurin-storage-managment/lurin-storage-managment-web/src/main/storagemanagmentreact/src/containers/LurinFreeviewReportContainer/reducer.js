
/*
 *
 * lurinFreeviewReportContainer reducer
 *
 */
import { fromJS } from 'immutable';
import {
  MESSAGE_500_ID,
  MESSAGE_TYPE
 } from '../../utils/messages';
import {
  LOAD_FREVIEWDATA_ACTION,
  LOAD_FREVIEWDATA_ACTION_SUCCESS,
  LOAD_FREVIEWDATA_ACTION_FAILURE,
  LOAD_FREEVIEW_EXPORT_ACTION,
  LOAD_FREEVIEW_EXPORT_ACTION_SUCCESS,
  LOAD_FREEVIEW_EXPORT_ACTION_FAILURE,
  INIT_DOWNLOAD_ACTION
} from './constants';

const initialState = fromJS({
  loading: false,
  error: false,
  freeviewData: [],
  freeviewExportData: null,
  message: '',
  messageType: '',
  showMessage: false,
  download: false
});

export const lurinFreeviewReportContainerReducer = (state = initialState, action) => {
  switch (action.type) {
    case LOAD_FREVIEWDATA_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('download', false)
        .set('error', false);
    case LOAD_FREVIEWDATA_ACTION_SUCCESS:
      return state
        .set('freeviewData', action.freeviewData)
        .set('showMessage', false)
        .set('download', false)
        .set('loading', false);
    case LOAD_FREVIEWDATA_ACTION_FAILURE:
      return state
        .set('error', action.error)
        .set('showMessage', true)
        .set('message', MESSAGE_500_ID)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('download', false)
        .set('loading', false);
    case LOAD_FREEVIEW_EXPORT_ACTION:
      return state
          .set('loading', true)
          .set('showMessage', false)
          .set('download', false)
          .set('error', false);
    case LOAD_FREEVIEW_EXPORT_ACTION_SUCCESS:
      return state
          .set('freeviewExportData', action.freeviewExportData)
          .set('showMessage', false)
          .set('download', true)
          .set('loading', false);
    case LOAD_FREEVIEW_EXPORT_ACTION_FAILURE:
      return state
          .set('error', action.error)
          .set('showMessage', true)
          .set('message', MESSAGE_500_ID)
          .set('messageType', MESSAGE_TYPE.WARNING)
          .set('download', false)
          .set('loading', false);
    case INIT_DOWNLOAD_ACTION:
      return state
              .set('download', false);
    default:
      return state;
  }
};
