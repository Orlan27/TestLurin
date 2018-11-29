import { createSelector } from 'reselect';

/**
 * Direct selector to the lurinFvCampaingContainer state domain
 */
const selectLurinFvCampaingManagmentContainerDomain = (state) => state.get('campaingManagmentReducer');

/**
 * Other specific selectors
 */

/**
 * Default selector used by LurinFvCampaingManagmentContainer
 */

const makeSelectLurinFvCampaingManagmentContainer = () => createSelector(
  selectLurinFvCampaingManagmentContainerDomain,
  (substate) => substate.toJS()
);

const makeSelectCampaingsData = () => createSelector(
  selectLurinFvCampaingManagmentContainerDomain,
  (campaingManagmentState) => campaingManagmentState.get('campaingsData')
);

const makeSelectLoading = () => createSelector(
  selectLurinFvCampaingManagmentContainerDomain,
  (campaingManagmentState) => campaingManagmentState.get('loading')
);

const makeSelectError = () => createSelector(
  selectLurinFvCampaingManagmentContainerDomain,
  (campaingManagmentState) => campaingManagmentState.get('error')
);

const makeSelectHasShowForm = () => createSelector(
  selectLurinFvCampaingManagmentContainerDomain,
  (campaingManagmentState) => campaingManagmentState.get('hasShowForm')
);

const makeSelectNewCampaing = () => createSelector(
  selectLurinFvCampaingManagmentContainerDomain,
  (campaingManagmentState) => campaingManagmentState.get('newCampaing')
);


const makeSelectShowMessage = () => createSelector(
  selectLurinFvCampaingManagmentContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('showMessage')
);
const makeSelectMessage = () => createSelector(
  selectLurinFvCampaingManagmentContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('message')
);
const makeSelectMessageType = () => createSelector(
  selectLurinFvCampaingManagmentContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('messageType')
);

export {
  selectLurinFvCampaingManagmentContainerDomain,
  makeSelectLurinFvCampaingManagmentContainer,
  makeSelectCampaingsData,
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
  makeSelectNewCampaing,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType
};
