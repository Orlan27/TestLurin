import { createSelector } from 'reselect';

/**
 * Direct selector to the lurinFvMaintenanceZoneContainer state domain
 */
const selectLurinFvFreeviewManagmentContainerDomain = (state) => state.get('freeviewManagmentReducer');

/**
 * Other specific selectors
 */


/**
 * Default selector used by LurinFvFreeviewManagmentContainer
 */

const makeSelectLurinFvFreeviewManagmentContainer = () => createSelector(
  selectLurinFvFreeviewManagmentContainerDomain,
  (substate) => substate.toJS()
);

const makeSelectfreeviewsData = () => createSelector(
  selectLurinFvFreeviewManagmentContainerDomain,
  (freeviewManagmentState) => freeviewManagmentState.get('freeviewsData')
);

const makeSelectLoading = () => createSelector(
  selectLurinFvFreeviewManagmentContainerDomain,
  (freeviewManagmentState) => freeviewManagmentState.get('loading')
);

const makeSelectError = () => createSelector(
  selectLurinFvFreeviewManagmentContainerDomain,
  (freeviewManagmentState) => freeviewManagmentState.get('error')
);

const makeSelectHasShowForm = () => createSelector(
  selectLurinFvFreeviewManagmentContainerDomain,
  (freeviewManagmentState) => freeviewManagmentState.get('hasShowForm')
);

const makeSelectNewFreeview = () => createSelector(
  selectLurinFvFreeviewManagmentContainerDomain,
  (freeviewManagmentState) => freeviewManagmentState.get('newFreeview')
);


const makeSelectShowMessage = () => createSelector(
  selectLurinFvFreeviewManagmentContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('showMessage')
);
const makeSelectMessage = () => createSelector(
  selectLurinFvFreeviewManagmentContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('message')
);
const makeSelectMessageType = () => createSelector(
  selectLurinFvFreeviewManagmentContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('messageType')
);

export {
  selectLurinFvFreeviewManagmentContainerDomain,
  makeSelectLurinFvFreeviewManagmentContainer,
  makeSelectfreeviewsData,
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
  makeSelectNewFreeview,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType
};
