import { createSelector } from 'reselect';

/**
 * Direct selector to the LurinStorageManagmentContainer state domain
 */
const selectLurinStorageManagmentContainerDomain = (state) => state.get('storageManagmentReducer');

/**
 * Other specific selectors
 */

/**
 * Default selector used by LurinStorageManagmentContainer
 */

const makeSelectLurinStorageManagmentContainer = () => createSelector(
  selectLurinStorageManagmentContainerDomain,
  (substate) => substate.toJS()
);

const makeSelectStoragesData = () => createSelector(
  selectLurinStorageManagmentContainerDomain,
  (storageManagmentState) => storageManagmentState.get('storagesData')
);

const makeSelectLoading = () => createSelector(
  selectLurinStorageManagmentContainerDomain,
  (storageManagmentState) => storageManagmentState.get('loading')
);

const makeSelectError = () => createSelector(
  selectLurinStorageManagmentContainerDomain,
  (storageManagmentState) => storageManagmentState.get('error')
);

const makeSelectHasShowForm = () => createSelector(
  selectLurinStorageManagmentContainerDomain,
  (storageManagmentState) => storageManagmentState.get('hasShowForm')
);

const makeSelectNewStorage = () => createSelector(
  selectLurinStorageManagmentContainerDomain,
  (storageManagmentState) => storageManagmentState.get('newStorage')
);

const makeSelectShowMessage = () => createSelector(
  selectLurinStorageManagmentContainerDomain,
  (storageManagmentState) => storageManagmentState.get('showMessage')
);
const makeSelectMessage = () => createSelector(
  selectLurinStorageManagmentContainerDomain,
  (storageManagmentState) => storageManagmentState.get('message')
);
const makeSelectMessageType = () => createSelector(
  selectLurinStorageManagmentContainerDomain,
  (storageManagmentState) => storageManagmentState.get('messageType')
);

const makeSelectSourceTypesData = () => createSelector(
  selectLurinStorageManagmentContainerDomain,
  (storageManagmentState) => storageManagmentState.get('sourceTypesData')
);

const makeSelectStatusData = () => createSelector(
  selectLurinStorageManagmentContainerDomain,
  (storageManagmentState) => storageManagmentState.get('statusData')
);

const makeSelectValidConection = () => createSelector(
  selectLurinStorageManagmentContainerDomain,
  (storageManagmentState) => storageManagmentState.get('isValidConection')
);

const makeSelectParmsData = () => createSelector(
  selectLurinStorageManagmentContainerDomain,
  (storageManagmentState) => storageManagmentState.get('testParms')
);


export {
  selectLurinStorageManagmentContainerDomain,
  makeSelectLurinStorageManagmentContainer,
  makeSelectStoragesData,
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
  makeSelectNewStorage,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType,
  makeSelectSourceTypesData,
  makeSelectStatusData,
  makeSelectValidConection,
  makeSelectParmsData
};
