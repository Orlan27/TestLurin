import { createSelector } from 'reselect';

/**
 * Direct selector to the LurinProfilesManagementContainer state domain
 */
const selectLurinProfilesManagementContainerDomain = (state) => state.get('profilesManagementReducer');

/**
 * Other specific selectors
 */

/**
 * Default selector used by LurinProfilesManagementContainer
 */

const makeSelectLurinProfilesManagementContainer = () => createSelector(
  selectLurinProfilesManagementContainerDomain,
  (substate) => substate.toJS()
);


const makeSelectProfilesCategoriesData = () => createSelector(
  selectLurinProfilesManagementContainerDomain,
  (profilesManagementState) => profilesManagementState.get('profilesCategoriesData')
);

const makeSelectProfilesData = () => createSelector(
  selectLurinProfilesManagementContainerDomain,
  (profilesManagementState) => profilesManagementState.get('profilesData')
);

const makeSelectLoading = () => createSelector(
  selectLurinProfilesManagementContainerDomain,
  (profilesManagementState) => profilesManagementState.get('loading')
);

const makeSelectError = () => createSelector(
  selectLurinProfilesManagementContainerDomain,
  (profilesManagementState) => profilesManagementState.get('error')
);

const makeSelectHasShowForm = () => createSelector(
  selectLurinProfilesManagementContainerDomain,
  (profilesManagementState) => profilesManagementState.get('hasShowForm')
);

const makeSelectNewProfiles = () => createSelector(
  selectLurinProfilesManagementContainerDomain,
  (profilesManagementState) => profilesManagementState.get('newProfile')
);

const makeSelectShowMessage = () => createSelector(
  selectLurinProfilesManagementContainerDomain,
  (profilesManagementState) => profilesManagementState.get('showMessage')
);
const makeSelectMessage = () => createSelector(
  selectLurinProfilesManagementContainerDomain,
  (profilesManagementState) => profilesManagementState.get('message')
);
const makeSelectMessageType = () => createSelector(
  selectLurinProfilesManagementContainerDomain,
  (profilesManagementState) => profilesManagementState.get('messageType')
);

const makeSelectModulesData = () => createSelector(
  selectLurinProfilesManagementContainerDomain,
  (profilesManagementState) => profilesManagementState.get('modulesData')
);
export {
  selectLurinProfilesManagementContainerDomain,
  makeSelectLurinProfilesManagementContainer,
  makeSelectProfilesCategoriesData,
  makeSelectProfilesData,
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
  makeSelectNewProfiles,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType,
  makeSelectModulesData
};
