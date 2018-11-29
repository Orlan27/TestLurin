/**
*
* LurinFvHeaderComponent
*
*/
import React from 'react';
// import styled from 'styled-components';

import { Col, Glyphicon
} from 'react-bootstrap';
import { StyledHeader } from './styles';
import { ITEM_THEME } from '../../utils/theme';
import { FormattedMessage } from 'react-intl';

function LurinFvHeaderComponent(props) {
  
  return (
     <StyledHeader currentStyles={props.currentStyles} bsStyle="primary" >
     <Col sm={6} xs={4} className="logo-container" >
      <Glyphicon onClick={props.toggleMenu} className="menu-icon" glyph="glyphicon glyphicon-menu-hamburger" />
       <img src={props.currentStyles[ITEM_THEME.LOGO_URL]} alt="logo" />
     </Col>
     <Col sm={6} xs={8} className="header-container">
      <img src="images/user-white.png" alt="user" />
      <span className="text-icon">
      <a href="/globalmanagement/#UserChangeData/">
      {currentUserSession}
      </a>
       
        </span>
      <a href="/logout"><img src="images/close-session-white.png" alt="close" />
        <span className="text-icon"><FormattedMessage id="header.button.closesession" defaultMessage="Cerrar Sessión"></FormattedMessage></span>
      </a>
     </Col>
   </StyledHeader>
  );
}

LurinFvHeaderComponent.propTypes = {
  toggleMenu: React.PropTypes.func,
  currentStyles: React.PropTypes.object
};

export default LurinFvHeaderComponent;
