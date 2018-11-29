import { createSelector } from 'reselect';

/**
 * Direct selector to the lurinFvCampaingContainer state domain
 */
const selectLurinFvAppContainerDomain = (state) => state.get('appReducer');

/**
 * Other specific selectors
 */

/**
 * Default selector used by selectLurinFvAppContainerDomain
 */

const makeSelectLurinFvAppContainerContainer = () => createSelector(
    selectLurinFvAppContainerDomain,
  (substate) => substate.toJS()
);

const makeSelectPackagesData = () => createSelector(
    selectLurinFvAppContainerDomain,
  (appState) => appState.get('packages')
);
const makeSelectTechnologiesData = () => createSelector(
  selectLurinFvAppContainerDomain,
(appState) => appState.get('technologies')
);


const makeSelectZonesData = () => createSelector(
  selectLurinFvAppContainerDomain,
(appState) => appState.get('zones')
);

const makeSelectLoadTypesData = () => createSelector(
selectLurinFvAppContainerDomain,
(appState) => appState.get('loadTypes')
);

const makeSelectMessagesData = () => createSelector(
  selectLurinFvAppContainerDomain,
  (appState) => appState.get('messages')
);

const makeSelectCampaignStatusData = () => createSelector(
  selectLurinFvAppContainerDomain,
  (appState) => appState.get('campaignStatus')
);

const makeSelectLang = () => createSelector(
  selectLurinFvAppContainerDomain,
  (appState) => appState.get('lang')
);


const makeSelectCurrentStyle = () => createSelector(
  selectLurinFvAppContainerDomain,
  (appState) => {
    const currentTheme = appState.get('currentTheme').toJS();
    if (!currentTheme) {
      return undefined;
    }
    let data = {
      themeId: currentTheme.themeId,
      name: currentTheme.name,
      description: currentTheme.description
    };

    currentTheme.details.forEach((item) => {
      data[item.name] = item.value;
    });
    
    return data;
  }
);

const makeSelectCurrentCarrier = () => createSelector(
  selectLurinFvAppContainerDomain,
  (appState) => appState.get('currentCarrier')
  );

export {
    makeSelectLurinFvAppContainerContainer,
    makeSelectPackagesData,
    makeSelectTechnologiesData,
    makeSelectZonesData,
    makeSelectLoadTypesData,
    makeSelectMessagesData,
    makeSelectCampaignStatusData,
    makeSelectCurrentStyle,
    makeSelectLang,
    makeSelectCurrentCarrier
};
