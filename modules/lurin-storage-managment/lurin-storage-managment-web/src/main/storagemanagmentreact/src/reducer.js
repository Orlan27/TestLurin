
import { combineReducers } from 'redux-immutable';
import { lurinFvAppContainerReducer }
from './containers/LurinFvAppContainer/reducer';
import { lurinStorageManagmentContainerReducer }
        from './containers/LurinStorageManagmentContainer/reducer';
import { lurinOperatorsManagementContainerReducer }
       from './containers/LurinOperatorsManagementContainer/reducer';        
import { lurinProfilesManagementContainerReducer }
       from './containers/LurinProfilesManagementContainer/reducer';    
import { lurinFvGlobalParameterContainerReducer }
       from './containers/LurinFvGlobalParameterContainer/reducer';
import { lurinFvLookFeelContainerReducer }
       from './containers/LurinFvLookFeelContainer/reducer';
import { lurinFvSecurityPolicyContainerReducer }
       from './containers/LurinFvSecurityPolicyContainer/reducer';
import { lurinUserManagmentContainerReducer }
       from './containers/LurinUserManagmentContainer/reducer';
import { lurinCampaignReportContainerReducer }
       from './containers/LurinCampaignReportContainer/reducer';
import { lurinFreeviewReportContainerReducer }
       from './containers/LurinFreeviewReportContainer/reducer';
import { lurinChangeUserDataReducer }
       from './containers/LurinChangeUserData/reducer';
       
export default combineReducers({
 appReducer: lurinFvAppContainerReducer,
 storageManagmentReducer: lurinStorageManagmentContainerReducer,
 operatorsManagementReducer: lurinOperatorsManagementContainerReducer,
 profilesManagementReducer: lurinProfilesManagementContainerReducer,
 globalParameterReducer: lurinFvGlobalParameterContainerReducer,
 lookFeelReducer: lurinFvLookFeelContainerReducer,
 securityPolicyReducer: lurinFvSecurityPolicyContainerReducer,
 userManagmentReducer: lurinUserManagmentContainerReducer,
 campaignReportReducer: lurinCampaignReportContainerReducer,
 freeviewReportReducer: lurinFreeviewReportContainerReducer,
 userChangeDataReducer: lurinChangeUserDataReducer
});
