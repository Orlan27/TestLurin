/**
*
* LurinFvSideBarComponent
*
*/
import React from 'react';

import { StyledContent } from './styles';
import {
  PanelGroup, Panel
} from 'react-bootstrap';

function LurinFvSideBarComponent(props) {
  const modules = JSON.parse(window.currentMenuSession).filter(menu => menu.url.replace(/[^a-zA-Z0-9]/g, '') === menu.name.replace(/[^a-zA-Z0-9]/g, ''));
  return (
   <StyledContent currentStyles={props.currentStyles} customCrossIcon={false} customBurgerIcon={false} isOpen={props.isMenuOpen} noOverlay disableOverlayClick disableOverlayClick={() => true} >
      <PanelGroup >
      {/* <Panel expanded header="ADMINSTRACION DE FREEVIEW" eventKey="1">
          <a href="/freeview/#adminFreeview" > <div> <img src="images/calendar.png" alt="freeview" />  Freeview</div></a>
          <a href="/freeview/#campaing">
            <div><img src="images/tv.png" alt="campania" />  Campaña</div></a>
        </Panel>
        <Panel header=" GESTIONAR CUENTA" eventKey="2">
          <a><div> <img src="images/accounts-white.png" alt="contrasena" /> Cambiar contraseña</div></a>
        </Panel>
        <Panel header="AYUDA" eventKey="3">
          <a><div><img src="images/ayuda-white.png" alt="ayuda" />Ver ayuda</div></a>
        </Panel>
        */}
      {modules.map(module => (
    	        <Panel expanded header={module.langAssigned} key={module.code} eventKey={module.code}>
    	            {
    	               JSON.parse(window.currentMenuSession).map((menu) => {
    	               if (menu.langAssigned !== module.langAssigned && menu.url.replace(/[^a-zA-Z0-9]/g, '').includes(module.url.replace(/[^a-zA-Z0-9]/g, ''))) {
    	                 return (
    	                <a key={menu.code} href={menu.url.replace('commercial', 'global')} > <div>
    	                <img src={menu.iconName} />{menu.langAssigned}</div></a>);
    	               }
    	             })
    	          }
    	      </Panel>
    	    ))}
      
      </PanelGroup>
    </StyledContent>
  );
}

LurinFvSideBarComponent.propTypes = {
  isMenuOpen: React.PropTypes.bool,
  allowEvent: React.PropTypes.bool,
  currentStyles: React.PropTypes.object
  
};

export default LurinFvSideBarComponent;
