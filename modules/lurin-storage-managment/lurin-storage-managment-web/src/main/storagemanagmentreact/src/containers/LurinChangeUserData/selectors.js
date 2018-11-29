import { createSelector } from 'reselect';

/**
 * Direct selector to the LurinChangeUserData state domain
 */
const selectLurinChangeUserDataDomain = (state) => state.get('userChangeDataReducer');

/**
 * Other specific selectors
 */

/**
 * Default selector used by LurinChangeUserData
 */

const makeSelectLurinChangeUserData = () => createSelector(
  selectLurinChangeUserDataDomain,
  (substate) => substate.toJS()
);

const makeSelectUserData = () => createSelector(
  selectLurinChangeUserDataDomain,
  (userDataState) => userDataState.get('userData')
);

const makeSelectLoading = () => createSelector(
  selectLurinChangeUserDataDomain,
  (userDataState) => userDataState.get('loading')
);

const makeSelectError = () => createSelector(
  selectLurinChangeUserDataDomain,
  (userDataState) => userDataState.get('error')
);

const makeSelectShowMessage = () => createSelector(
  selectLurinChangeUserDataDomain,
  (userDataState) => userDataState.get('showMessage')
);
const makeSelectMessage = () => createSelector(
  selectLurinChangeUserDataDomain,
  (userDataState) => userDataState.get('message')
);
const makeSelectMessageType = () => createSelector(
  selectLurinChangeUserDataDomain,
  (userDataState) => userDataState.get('messageType')
);

export {
  selectLurinChangeUserDataDomain,
  makeSelectLurinChangeUserData,
  makeSelectUserData,
  makeSelectLoading,
  makeSelectError,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType
};
