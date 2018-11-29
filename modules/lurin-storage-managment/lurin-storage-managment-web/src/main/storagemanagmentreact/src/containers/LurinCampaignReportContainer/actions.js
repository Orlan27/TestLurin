/*
 *
 * LurinFvAppContainer actions
 *
 */
import Axios from 'axios';
// import { LURIN_MASTER_API } from '../../Api/config';

// API URL
const urlCampaignReport = `${window.currentApiUrl}report/`;

import {
  LOAD_CAMPAIGNDATA_ACTION_SUCCESS,
  LOAD_CAMPAIGNDATA_ACTION_FAILURE,
  LOAD_CAMPAIGNDATA_EXPORT_ACTION_SUCCESS,
  LOAD_CAMPAIGNDATA_EXPORT_ACTION_FAILURE,
  INIT_DOWNLOAD_ACTION

} from './constants';


export const loadCampaignReportDataSuccess = (campaignData) => ({
  type: LOAD_CAMPAIGNDATA_ACTION_SUCCESS,
  campaignData
});

export function loadCampaignReportDataFailure(error) {
  return {
    type: LOAD_CAMPAIGNDATA_ACTION_FAILURE,
    error
  };
}

export const loadCampaignExportReportDataSuccess = (campaignExportData) => ({
  type: LOAD_CAMPAIGNDATA_EXPORT_ACTION_SUCCESS,
  campaignExportData
});

export function loadCampaignExportReportDataFailure(error) {
  return {
    type: LOAD_CAMPAIGNDATA_EXPORT_ACTION_FAILURE,
    error
  };
}

export function loadCampaignReportData(carrierId, filter) {
  return (dispatch) => {
//     Returns a promise

     const data = JSON.stringify({
       "idStb": '',
       "idCliente": filter.idCliente,
       "campaignId": filter.campaignId,
       "freeViewId": filter.freeViewId,
       "idCarrier": filter.idCarrier,
       "statusId": filter.statusId
     });

     return Axios.post(`${urlCampaignReport}dataCampaign`,data, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken, Carrier: carrierId } })
       .then(response => {
         dispatch(loadCampaignReportDataSuccess(response.data));
       })
       .catch(error => {
         dispatch(loadCampaignReportDataFailure(error));
         throw (error);
       });
//    const data = [
//      {
//        idStb: 1,
//        campaignName: 'Campaña HBO-Verano',
//        freeViewName: 'HBO',
//        comercialZone: 'DTH',
//        campaignIni: '19-feb-2018',
//        canpmaignEnd: '30-Mar-2018',
//        operadora: 'Peru',
//        status: 'Programada',
//        idClient: 200
//      },
//      {
//        idStb: 2,
//        campaignName: 'Campaña 5',
//        freeViewName: 'FOX',
//        comercialZone: 'DTH',
//        campaignIni: '19-feb-2018',
//        canpmaignEnd: '30-Mar-2018',
//        operadora: 'Peru',
//        status: 'Programada',
//        idClient: 200
//      },
//      {
//        idStb: 3,
//        campaignName: 'Campaña 4',
//        freeViewName: 'MAX',
//        comercialZone: 'DTH',
//        campaignIni: '19-feb-2018',
//        canpmaignEnd: '30-Mar-2018',
//        operadora: 'Peru',
//        status: 'Finalizada',
//        idClient: 10
//      },
//      {
//        idStb: 3,
//        campaignName: 'Campaña 4',
//        freeViewName: 'MAX',
//        comercialZone: 'DTH',
//        campaignIni: '19-feb-2018',
//        canpmaignEnd: '30-Mar-2018',
//        operadora: 'Peru',
//        status: 'Finalizada',
//        idClient: 10
//      },
//      {
//        idStb: 4,
//        campaignName: 'Campaña 3',
//        freeViewName: 'HBO',
//        comercialZone: 'DTH',
//        campaignIni: '19-feb-2018',
//        canpmaignEnd: '30-Mar-2018',
//        operadora: 'Peru',
//        status: 'Finalizada',
//        idClient: 20
//      },
//      {
//        idStb: 5,
//        campaignName: 'Campaña 2',
//        freeViewName: 'MAX',
//        comercialZone: 'DTH',
//        campaignIni: '19-feb-2018',
//        canpmaignEnd: '30-Mar-2018',
//        operadora: 'Peru',
//        status: 'Cancelada',
//        idClient: 10
//      },
//      {
//        idStb: 6,
//        campaignName: 'Campaña Test',
//        freeViewName: 'FOX',
//        comercialZone: 'DTH',
//        campaignIni: '19-feb-2018',
//        canpmaignEnd: '30-Mar-2018',
//        operadora: 'Peru',
//        status: 'Finalizada',
//        idClient: 200
//      }
//    ];
//    dispatch(loadCampaignReportDataSuccess(data));
  };
}



export function loadCampaignExportReportData(carrierId, filter) {
  return (dispatch) => {
//     Returns a promise

     const data = JSON.stringify({
       "idStb": '',
       "idCliente": filter.idCliente,
       "campaignId": filter.campaignId,
       "freeViewId": filter.freeViewId,
       "idCarrier": filter.idCarrier,
       "statusId": filter.statusId,
       "typeReport": filter.typeReport
     });

     return Axios.post(`${urlCampaignReport}campaign`,data, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken, Carrier: carrierId } })
       .then(response => {
         dispatch(loadCampaignExportReportDataSuccess(response.data));
       })
       .catch(error => {
         dispatch(loadCampaignExportReportDataFailure(error));
         throw (error);
       });
  };
}
