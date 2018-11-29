import { createSelector } from 'reselect';

/**
 * Direct selector to the LurinCampaignReportContainer state domain
 */
const selectLurinCampaignReportContainerDomain = (state) => state.get('campaignReportReducer');

/**
 * Other specific selectors
 */

/**
 * Default selector used by LurinUserManagmentContainer
 */

const makeSelectLurinCampaignReportContainerContainer = () => createSelector(
    selectLurinCampaignReportContainerDomain,
  (substate) => substate.toJS()
);

const makeSelectCampaignData = () => createSelector(
    selectLurinCampaignReportContainerDomain,
  (campaignReportState) => campaignReportState.get('campaignData')
);

const makeSelectCampaignExportData = () => createSelector(
  selectLurinCampaignReportContainerDomain,
  (campaignReportState) => campaignReportState.get('campaignExportData')
);



const makeSelectLoading = () => createSelector(
    selectLurinCampaignReportContainerDomain,
    (campaignReportState) => campaignReportState.get('loading')
  );

const makeSelectError = () => createSelector(
    selectLurinCampaignReportContainerDomain,
    (campaignReportState) => campaignReportState.get('error')
  );

const makeSelectShowMessage = () => createSelector(
    selectLurinCampaignReportContainerDomain,
    (campaignReportState) => campaignReportState.get('showMessage')
  );

const makeSelectMessage = () => createSelector(
    selectLurinCampaignReportContainerDomain,
    (campaignReportState) => campaignReportState.get('message')
  );
const makeSelectMessageType = () => createSelector(
    selectLurinCampaignReportContainerDomain,
    (campaignReportState) => campaignReportState.get('messageType')
  );


  const makeSelectDownload = () => createSelector(
    selectLurinCampaignReportContainerDomain,
      (campaignReportState) => campaignReportState.get('download')
    );

export {
    makeSelectLurinCampaignReportContainerContainer,
    makeSelectCampaignData,
    makeSelectCampaignExportData,
  makeSelectLoading,
  makeSelectError,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType,
  makeSelectDownload
};
