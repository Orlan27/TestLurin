import { createSelector } from 'reselect';

/**
 * Direct selector to the LurinFvGlobalParameterContainer state domain
 */
const selectLurinFvGlobalParameterContainerDomain = (state) => state.get('globalParameterReducer');

/**
 * Other specific selectors
 */

/**
 * Default selector used by LurinFvGlobalParameterContainer
 */

const makeSelectLurinFvGlobalParameterManagmentContainer = () => createSelector(
  selectLurinFvGlobalParameterContainerDomain,
  (substate) => substate.toJS()
);

// const makeSelectGlobalParametersData = () => createSelector(
//   selectLurinFvGlobalParameterContainerDomain,
//   (globalParameterState) => globalParameterState.get('globalParametersData')
// );

const makeSelectGlobalParametersData = () => createSelector(
  selectLurinFvGlobalParameterContainerDomain,
  (globalParameterState) => {
    
    const currentGlobals = globalParameterState.get('globalParametersData').
                  filter(global => global.category.name.trim() !== 'POLITICS_PARAMETER' &&
                  global.key.trim() !== 'PORT_CAS_SERVER' &&
                  global.key.trim() !== 'IP_CAS_SERVER');
    return currentGlobals;
  }
);

const makeSelectLoading = () => createSelector(
  selectLurinFvGlobalParameterContainerDomain,
  (globalParameterState) => globalParameterState.get('loading')
);

const makeSelectError = () => createSelector(
  selectLurinFvGlobalParameterContainerDomain,
  (globalParameterState) => globalParameterState.get('error')
);

const makeSelectHasShowForm = () => createSelector(
  selectLurinFvGlobalParameterContainerDomain,
  (globalParameterState) => globalParameterState.get('hasShowForm')
);

const makeSelectNewGlobalParameter = () => createSelector(
  selectLurinFvGlobalParameterContainerDomain,
  (globalParameterState) => globalParameterState.get('newGlobalParameter')
);


const makeSelectShowMessage = () => createSelector(
  selectLurinFvGlobalParameterContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('showMessage')
);
const makeSelectMessage = () => createSelector(
  selectLurinFvGlobalParameterContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('message')
);
const makeSelectMessageType = () => createSelector(
  selectLurinFvGlobalParameterContainerDomain,
  (operatorsManagementState) => operatorsManagementState.get('messageType')
);

const makeGPCategories = () => createSelector(
  selectLurinFvGlobalParameterContainerDomain,
  (globalParameterState) => globalParameterState.get('gpCategoriesData')
);


export {
  selectLurinFvGlobalParameterContainerDomain,
  makeSelectLurinFvGlobalParameterManagmentContainer,
  makeSelectGlobalParametersData,
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
  makeSelectNewGlobalParameter,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType,
  makeGPCategories
};
