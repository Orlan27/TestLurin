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

const makeSelectCountriesData = () => createSelector(
    selectLurinFvAppContainerDomain,
  (appState) => appState.get('countries')
);

const makeSelectTablesData = () => createSelector(
    selectLurinFvAppContainerDomain,
  (appState) => appState.get('tables')
);

const makeSelectCompaniesData = () => createSelector(
    selectLurinFvAppContainerDomain,
  (appState) => appState.get('companies')
);

const makeSelectOperatorsStatusData = () => createSelector(
    selectLurinFvAppContainerDomain,
  (appState) => appState.get('operatorsStatus')
);

const makeSelectedCurrentThemeData = () => createSelector(
	    selectLurinFvAppContainerDomain,
	  (appState) => appState.get('currentTheme').toJS()
);

const makeSelectedAuthenticationTypes = () => createSelector(
  selectLurinFvAppContainerDomain,
(appState) => appState.get('authenticationTypes')
);

const makeSelectedUserTypes = () => createSelector(
  selectLurinFvAppContainerDomain,
(appState) => appState.get('userTypes')
);

const makeSelectedRoles = () => createSelector(
  selectLurinFvAppContainerDomain,
(appState) => appState.get('roles')
);

const makeSelectedProfiles = () => createSelector(
  selectLurinFvAppContainerDomain,
(appState) => appState.get('profiles')
);

const makeSelectedCarriers = () => createSelector(
  selectLurinFvAppContainerDomain,
(appState) => appState.get('carriers')
);

const makeSelectedLookFeelsData = () => createSelector(
  selectLurinFvAppContainerDomain,
(appState) => appState.get('lookFeelsData')
);




const makeSelectCampaignStatusData = () => createSelector(
  selectLurinFvAppContainerDomain,
  (appState) => appState.get('campaignStatus')
  );

const makeSelectTechnologiesData = () => createSelector(
    selectLurinFvAppContainerDomain,
  (appState) => appState.get('technologies')
  );

const makeSelectfreeviewsData = () => createSelector(
    selectLurinFvAppContainerDomain,
    (appState) => appState.get('freeviewsData')
  );

const makeSelectCampaingsData = () => createSelector(
    selectLurinFvAppContainerDomain,
    (appState) => appState.get('campaingsData')
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
  });


export {
    makeSelectLurinFvAppContainerContainer,
    makeSelectCountriesData,
    makeSelectTablesData,
    makeSelectCompaniesData,
    makeSelectOperatorsStatusData,
    makeSelectedCurrentThemeData,
    makeSelectCurrentStyle,
    makeSelectedLookFeelsData,
    makeSelectedAuthenticationTypes,
    makeSelectedUserTypes,
    makeSelectedRoles,
    makeSelectedProfiles,
    makeSelectedCarriers,
    makeSelectCampaignStatusData,
    makeSelectTechnologiesData,
    makeSelectfreeviewsData,
    makeSelectCampaingsData,
    makeSelectLang
};
