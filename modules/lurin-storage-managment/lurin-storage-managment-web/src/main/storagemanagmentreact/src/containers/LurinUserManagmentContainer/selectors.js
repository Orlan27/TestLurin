import { createSelector } from 'reselect';

/**
 * Direct selector to the LurinUserManagmentContainer state domain
 */
const selectLurinUserManagmentContainerDomain = (state) => state.get('userManagmentReducer');

/**
 * Other specific selectors
 */

/**
 * Default selector used by LurinUserManagmentContainer
 */

const makeSelectLurinUserManagmentContainer = () => createSelector(
  selectLurinUserManagmentContainerDomain,
  (substate) => substate.toJS()
);

const makeSelectUserCarrierData = () => createSelector(
  selectLurinUserManagmentContainerDomain,
  (userManagmentState) => userManagmentState.get('userCarrierData')
);


const makeSelectUsersData = () => createSelector(
  selectLurinUserManagmentContainerDomain,
  (userManagmentState) => userManagmentState.get('usersData')
);

const makeSelectUsersLDAP = () => createSelector(
  selectLurinUserManagmentContainerDomain,
  (userManagmentState) => userManagmentState.get('usersLDAP')
);

const makeSelectLoading = () => createSelector(
  selectLurinUserManagmentContainerDomain,
  (userManagmentState) => userManagmentState.get('loading')
);

const makeSelectError = () => createSelector(
  selectLurinUserManagmentContainerDomain,
  (userManagmentState) => userManagmentState.get('error')
);

const makeSelectHasShowForm = () => createSelector(
  selectLurinUserManagmentContainerDomain,
  (userManagmentState) => userManagmentState.get('hasShowForm')
);

const makeSelectNewUser = () => createSelector(
  selectLurinUserManagmentContainerDomain,
  (userManagmentState) => userManagmentState.get('NewUser')
);

const makeSelectShowMessage = () => createSelector(
  selectLurinUserManagmentContainerDomain,
  (userManagmentState) => userManagmentState.get('showMessage')
);

const makeSelectMessage = () => createSelector(
  selectLurinUserManagmentContainerDomain,
  (userManagmentState) => userManagmentState.get('message')
);
const makeSelectMessageType = () => createSelector(
  selectLurinUserManagmentContainerDomain,
  (userManagmentState) => userManagmentState.get('messageType')
);

export {
  selectLurinUserManagmentContainerDomain,
  makeSelectLurinUserManagmentContainer,
  makeSelectUsersData,
  makeSelectUsersLDAP,
  makeSelectUserCarrierData,
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
  makeSelectNewUser,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType
};
