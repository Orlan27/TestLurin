
/*
 *
 * LurinUserManagmentContainer reducer
 *
 */
import { fromJS } from 'immutable';
import {
  MESSAGE_500_ID,
  MESSAGE_TYPE
 } from '../../utils/messages';
import {
  LOAD_CAMPAIGNDATA_ACTION,
  LOAD_CAMPAIGNDATA_ACTION_SUCCESS,
  LOAD_CAMPAIGNDATA_ACTION_FAILURE,
  LOAD_CAMPAIGNDATA_EXPORT_ACTION,
  LOAD_CAMPAIGNDATA_EXPORT_ACTION_SUCCESS,
  LOAD_CAMPAIGNDATA_EXPORT_ACTION_FAILURE,
  INIT_DOWNLOAD_ACTION
} from './constants';

const initialState = fromJS({
  loading: false,
  error: false,
  campaignData: [],
  campaignExportData: null,
  message: '',
  messageType: '',
  showMessage: false,
  download: false
});

export const lurinCampaignReportContainerReducer = (state = initialState, action) => {
  switch (action.type) {
    case LOAD_CAMPAIGNDATA_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('download', false)
        .set('error', false);
    case LOAD_CAMPAIGNDATA_ACTION_SUCCESS:
      return state
        .set('campaignData', action.campaignData)
        .set('showMessage', false)
        .set('download', false)
        .set('loading', false);
    case LOAD_CAMPAIGNDATA_ACTION_FAILURE:
      return state
        .set('error', action.error)
        .set('campaignData', [])
        .set('download', false)
        .set('showMessage', true)
        .set('message', MESSAGE_500_ID)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('loading', false);
    case LOAD_CAMPAIGNDATA_EXPORT_ACTION:
        return state
            .set('loading', true)
            .set('showMessage', false)
            .set('download', false)
            .set('error', false);
    case LOAD_CAMPAIGNDATA_EXPORT_ACTION_SUCCESS:
        return state
            .set('campaignExportData', action.campaignExportData)
            .set('showMessage', false)
            .set('download', true)
            .set('loading', false);
    case LOAD_CAMPAIGNDATA_EXPORT_ACTION_FAILURE:
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
