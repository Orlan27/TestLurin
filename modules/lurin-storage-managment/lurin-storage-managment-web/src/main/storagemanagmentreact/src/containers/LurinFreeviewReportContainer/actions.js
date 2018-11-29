/*
 *
 * LurinFvAppContainer actions
 *
 */
import Axios from 'axios';
// import { LURIN_MASTER_API } from '../../Api/config';

// API URL
const urlReport = `${window.currentApiUrl}report/`;

import {
  LOAD_FREVIEWDATA_ACTION_SUCCESS,
  LOAD_FREVIEWDATA_ACTION_FAILURE,
  LOAD_FREEVIEW_EXPORT_ACTION_SUCCESS,
  LOAD_FREEVIEW_EXPORT_ACTION_FAILURE,
  INIT_DOWNLOAD_ACTION

} from './constants';


export const loadFreeviewReportDataSuccess = (freeviewData) => ({
  type: LOAD_FREVIEWDATA_ACTION_SUCCESS,
  freeviewData
});

export function loadFreeviewReportDataFailure(error) {
  return {
    type: LOAD_FREVIEWDATA_ACTION_FAILURE,
    error
  };
}

export const loadFreeviewExportReportDataSuccess = (freeviewExportData) => ({
  type: LOAD_FREEVIEW_EXPORT_ACTION_SUCCESS,
  freeviewExportData
});

export function loadFreeviewExportReportDataFailure(error) {
  return {
    type: LOAD_FREEVIEW_EXPORT_ACTION_FAILURE,
    error
  };
}

export function loadFreeviewReportData(carrierId, statusId) {
  const data = JSON.stringify({idCarrier: carrierId, status: statusId});
  return (dispatch) => {
    // Returns a promise
    return Axios.post(`${urlReport}dataFreeView`,data,  { headers: { 'Content-Type': 'application/json',
      Authorization: currentToken, carrier: carrierId } })
      .then(response => {
        dispatch(loadFreeviewReportDataSuccess(response.data));
      })
      .catch(error => {
        dispatch(loadFreeviewReportDataFailure(error));
        throw (error);
      });
   /* const data = [
      {
        id: 1,
        freeViewName: 'test',
        tecnologyName: 'CATV',
        chanel: 'Canales HBO Premium',
        createDate: 1526526282000,
        carrierId: 1,
        operadora: 'Telef��nica Media Networks',
        userCreate: 'admin',
        modifyDate: null,
        userModify: null,
        status: 'A'
      },
      {
        id: 2,
        freeViewName: 'test',
        tecnologyName: 'CATV',
        chanel: 'Canales HBO Premium',
        createDate: 1526526282000,
        carrierId: 1,
        operadora: 'Telef��nica Media Networks',
        userCreate: 'admin',
        modifyDate: null,
        userModify: null,
        status: 'A'
      }
    ];
    dispatch(loadFreeviewReportDataSuccess(data));*/
  };
}

export function loadFreeviewExportReportData(carrierId, statusId, type) {
  const data = JSON.stringify({idCarrier: carrierId, status: statusId, typeReport: type});
  return (dispatch) => {
    // Returns a promise
    return Axios.post(`${urlReport}freeView`,data, { headers: { 'Content-Type': 'application/json',
      Authorization: currentToken,carrier: carrierId } })
      .then(response => {
        dispatch(loadFreeviewExportReportDataSuccess(response.data));
      })
      .catch(error => {
        dispatch(loadFreeviewExportReportDataFailure(error));
        throw (error);
      });
    // const data =
    // { 'contentType': 'application/x-csv', report: 'LCwsLFJlcG9ydGUgZGUgRnJlZSBXaWV3LCwsLCwsCmlkLGNoYW5lbCxjcmVhdGUgZGF0ZSxmcmVldmlldywsbW9kaWZ5IGRhdGUsb3BlcmFkb3JhLHN0YXR1cyx0ZWNub2xvZ3ksdXNlciBjcmVhdGUsdXNlciBtb2RpZnkKMSxDYW5hbGVzIEhCTyBQcmVtaXVtLDUvMjIvMTggMzoxNiBBTSxldGUsLG51bGwsVGVsZWbvv73vv71uaWNhIE1lZGlhIE5ldHdvcmtzLEEsQ0FUVixhZG1pbixudWxsCg==' };
    // dispatch(loadFreeviewExportReportDataSuccess(data));
  };
}

export function initDownload() {
  return (dispatch) => {
    dispatch({
      type: INIT_DOWNLOAD_ACTION
    });
  };
}

