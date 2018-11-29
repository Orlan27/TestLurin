import { createSelector } from 'reselect';

/**
 * Direct selector to the LurinFreeviewReportContainer state domain
 */
const selectLurinFreeviewReportContainerDomain = (state) => state.get('freeviewReportReducer');

/**
 * Other specific selectors
 */

/**
 * Default selector used by LurinUserManagmentContainer
 */

const makeSelectLurinFreeviewReportContainer = () => createSelector(
  selectLurinFreeviewReportContainerDomain,
  (substate) => substate.toJS()
);

const makeSelectFreeviewData = () => createSelector(
  selectLurinFreeviewReportContainerDomain,
  (freeviewReportState) => freeviewReportState.get('freeviewData')
);

const makeSelectFreeviewExportData = () => createSelector(
  selectLurinFreeviewReportContainerDomain,
  (freeviewReportState) => freeviewReportState.get('freeviewExportData')
);

const makeSelectLoading = () => createSelector(
  selectLurinFreeviewReportContainerDomain,
    (freeviewReportState) => freeviewReportState.get('loading')
  );

const makeSelectError = () => createSelector(
  selectLurinFreeviewReportContainerDomain,
    (freeviewReportState) => freeviewReportState.get('error')
  );

const makeSelectShowMessage = () => createSelector(
  selectLurinFreeviewReportContainerDomain,
    (freeviewReportState) => freeviewReportState.get('showMessage')
  );

const makeSelectMessage = () => createSelector(
  selectLurinFreeviewReportContainerDomain,
    (freeviewReportState) => freeviewReportState.get('message')
  );
const makeSelectMessageType = () => createSelector(
  selectLurinFreeviewReportContainerDomain,
    (freeviewReportState) => freeviewReportState.get('messageType')
  );

const makeSelectDownload = () => createSelector(
    selectLurinFreeviewReportContainerDomain,
      (freeviewReportState) => freeviewReportState.get('download')
    );


export {
  makeSelectLurinFreeviewReportContainer,
  makeSelectFreeviewData,
  makeSelectFreeviewExportData,
  makeSelectLoading,
  makeSelectError,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType,
  makeSelectDownload
};
