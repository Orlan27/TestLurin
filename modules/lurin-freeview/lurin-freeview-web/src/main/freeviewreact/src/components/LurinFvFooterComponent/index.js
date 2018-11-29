/**
*
* LurinFvHeaderComponent
*
*/
import React from 'react';
import { StyledFooter } from './styles';
// import styled from 'styled-components';
import { FormattedMessage } from 'react-intl';

function LurinFvFooterComponent(props) {
  return (
    <StyledFooter bsStyle="primary" currentStyles={props.currentStyles}>
      <span><FormattedMessage id="footer.title" defaultMessage="Copyright © Todos los Derechos Reservados 2017"></FormattedMessage></span>
    </StyledFooter>
  );
}

LurinFvFooterComponent.propTypes = {
	currentStyles: React.PropTypes.object
};

export default LurinFvFooterComponent;
