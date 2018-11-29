// ./src/reducers/index.js
import { combineReducers } from 'redux-immutable';
import { lurinFvAppContainerReducer }
from './containers/LurinFvAppContainer/reducer';

import { lurinFvMaintenanceZoneContainerReducer }
        from './containers/LurinFvMaintenanceZoneContainer/reducer';
import { lurinFvFreeviewManagmentContainerReducer }
        from './containers/LurinFvFreeviewManagmentContainer/reducer';
import { lurinFvCampaingManagmentContainerReducer }
        from './containers/LurinFvCampaingManagmentContainer/reducer';
import { lurinChangeUserDataReducer }
        from './containers/LurinChangeUserData/reducer';

export default combineReducers({
  appReducer: lurinFvAppContainerReducer,
  maintenanceZonesReducer: lurinFvMaintenanceZoneContainerReducer,
  freeviewManagmentReducer: lurinFvFreeviewManagmentContainerReducer,
  campaingManagmentReducer: lurinFvCampaingManagmentContainerReducer,
  userChangeDataReducer: lurinChangeUserDataReducer
});
