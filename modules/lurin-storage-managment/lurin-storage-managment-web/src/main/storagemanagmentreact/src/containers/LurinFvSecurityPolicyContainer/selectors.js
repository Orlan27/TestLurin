import { createSelector } from 'reselect';

/**
 * Direct selector to the LurinFvSecurityPolicyContainer state domain
 */
const selectLurinFvSecurityPolicyContainerDomain = (state) => state.get('securityPolicyReducer');

/**
 * Other specific selectors
 */

/**
 * Default selector used by LurinFvSecurityPolicyContainer
 */

const makeSelectLurinFvSecurityPolicyManagmentContainer = () => createSelector(
  selectLurinFvSecurityPolicyContainerDomain,
  (substate) => substate.toJS()
);

const makeSelectSecurityPolicyData = () => createSelector(
  selectLurinFvSecurityPolicyContainerDomain,
  (securityPolicyState) => securityPolicyState.get('securityPolicyData')
);

const makeSelectLoading = () => createSelector(
  selectLurinFvSecurityPolicyContainerDomain,
  (securityPolicyState) => securityPolicyState.get('loading')
);

const makeSelectError = () => createSelector(
  selectLurinFvSecurityPolicyContainerDomain,
  (securityPolicyState) => securityPolicyState.get('error')
);

const makeSelectShowMessage = () => createSelector(
  selectLurinFvSecurityPolicyContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('showMessage')
);
const makeSelectMessage = () => createSelector(
  selectLurinFvSecurityPolicyContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('message')
);
const makeSelectMessageType = () => createSelector(
  selectLurinFvSecurityPolicyContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('messageType')
);

export {
  selectLurinFvSecurityPolicyContainerDomain,
  makeSelectLurinFvSecurityPolicyManagmentContainer,
  makeSelectSecurityPolicyData,
  makeSelectLoading,
  makeSelectError,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType
};
