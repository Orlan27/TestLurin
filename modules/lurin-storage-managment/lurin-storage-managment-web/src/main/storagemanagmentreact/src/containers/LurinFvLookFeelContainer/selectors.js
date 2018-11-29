import { createSelector } from 'reselect';

/**
 * Direct selector to the LurinFvLookFeelContainer state domain
 */
const selectLurinFvLookFeelContainerDomain = (state) => state.get('lookFeelReducer');

/**
 * Other specific selectors
 */

/**
 * Default selector used by LurinFvLookFeelContainer
 */

const makeSelectLurinFvLookFeelContainer = () => createSelector(
  selectLurinFvLookFeelContainerDomain,
  (substate) => substate.toJS()
);

const makeSelectLookFeelsData = () => createSelector(
  selectLurinFvLookFeelContainerDomain,
  (lookFeelState) => lookFeelState.get('lookFeelsData')
);

const makeSelectLoading = () => createSelector(
  selectLurinFvLookFeelContainerDomain,
  (lookFeelState) => lookFeelState.get('loading')
);

const makeSelectError = () => createSelector(
  selectLurinFvLookFeelContainerDomain,
  (lookFeelState) => lookFeelState.get('error')
);

const makeSelectHasShowForm = () => createSelector(
  selectLurinFvLookFeelContainerDomain,
  (lookFeelState) => lookFeelState.get('hasShowForm')
);

const makeSelectNewLookFeel = () => createSelector(
  selectLurinFvLookFeelContainerDomain,
  (lookFeelState) => lookFeelState.get('newLookFeel')
);


const makeSelectShowMessage = () => createSelector(
  selectLurinFvLookFeelContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('showMessage')
);
const makeSelectMessage = () => createSelector(
  selectLurinFvLookFeelContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('message')
);
const makeSelectMessageType = () => createSelector(
  selectLurinFvLookFeelContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('messageType')
);

export {
  selectLurinFvLookFeelContainerDomain,
  makeSelectLurinFvLookFeelContainer,
  makeSelectLookFeelsData,
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
  makeSelectNewLookFeel,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType
};
