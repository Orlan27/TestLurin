import { createSelector } from 'reselect';

/**
 * Direct selector to the lurinFvMaintenanceZoneContainer state domain
 */
const selectLurinFvMaintenanceZoneContainerDomain = (state) => state.get('maintenanceZonesReducer');

/**
 * Other specific selectors
 */


/**
 * Default selector used by LurinFvMaintenanceZoneContainer
 */

const makeSelectLurinFvMaintenanceZoneContainer = () => createSelector(
  selectLurinFvMaintenanceZoneContainerDomain,
  (substate) => substate.toJS()
);

const makeSelectcommercialZonesData = () => createSelector(
  selectLurinFvMaintenanceZoneContainerDomain,
  (maintenanceZoneState) => maintenanceZoneState.get('commercialZonesData')
);

const makeSelectLoading = () => createSelector(
  selectLurinFvMaintenanceZoneContainerDomain,
  (maintenanceZoneState) => maintenanceZoneState.get('loading')
);

const makeSelectError = () => createSelector(
  selectLurinFvMaintenanceZoneContainerDomain,
  (maintenanceZoneState) => maintenanceZoneState.get('error')
);

const makeSelectHasShowForm = () => createSelector(
  selectLurinFvMaintenanceZoneContainerDomain,
  (maintenanceZoneState) => maintenanceZoneState.get('hasShowForm')
);

export {
  selectLurinFvMaintenanceZoneContainerDomain,
  makeSelectLurinFvMaintenanceZoneContainer,
  makeSelectcommercialZonesData,
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm
};
