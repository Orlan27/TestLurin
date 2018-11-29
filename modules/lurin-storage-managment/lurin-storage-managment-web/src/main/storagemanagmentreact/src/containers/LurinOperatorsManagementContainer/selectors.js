import { createSelector } from 'reselect';

/**
 * Direct selector to the LurinOperatorsManagementContainer state domain
 */
const selectLurinOperatorsManagementContainerDomain = (state) => state.get('operatorsManagementReducer');

/**
 * Other specific selectors
 */

/**
 * Default selector used by LurinOperatorsManagementContainer
 */

const makeSelectLurinOperatorsManagementContainer = () => createSelector(
  selectLurinOperatorsManagementContainerDomain,
  (substate) => substate.toJS()
);

const makeSelectOperatorsData = () => createSelector(
  selectLurinOperatorsManagementContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('operatorsData')
);

const makeSelectLoading = () => createSelector(
  selectLurinOperatorsManagementContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('loading')
);

const makeSelectError = () => createSelector(
  selectLurinOperatorsManagementContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('error')
);

const makeSelectHasShowForm = () => createSelector(
  selectLurinOperatorsManagementContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('hasShowForm')
);

const makeSelectNewOperator = () => createSelector(
  selectLurinOperatorsManagementContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('newOperator')
);

const makeSelectShowMessage = () => createSelector(
  selectLurinOperatorsManagementContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('showMessage')
);
const makeSelectMessage = () => createSelector(
  selectLurinOperatorsManagementContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('message')
);
const makeSelectMessageType = () => createSelector(
  selectLurinOperatorsManagementContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('messageType')
);

const makeSelectDataSourcesData = () => createSelector(
  selectLurinOperatorsManagementContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('dataSourcesData')
);

const makeSelectJndiData = () => createSelector(
  selectLurinOperatorsManagementContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('jndiData')
);

const makeSelectCasData = () => createSelector(
  selectLurinOperatorsManagementContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('casData')
);
export {
  selectLurinOperatorsManagementContainerDomain,
  makeSelectLurinOperatorsManagementContainer,
  makeSelectOperatorsData,
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
  makeSelectNewOperator,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType,
  makeSelectDataSourcesData,
  makeSelectJndiData,
  makeSelectCasData
};
