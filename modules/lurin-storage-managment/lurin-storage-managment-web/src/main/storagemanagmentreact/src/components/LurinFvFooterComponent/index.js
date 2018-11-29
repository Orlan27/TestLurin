/**
*
* LurinFvHeaderComponent
*
*/
import React from 'react';
import { StyledFooter } from './styles';
// import styled from 'styled-components';


function LurinFvFooterComponent(props) {
  return (
    <StyledFooter bsStyle="primary" currentStyles={props.currentStyles}>
      <span> Copyright © Todos los Derechos Reservados 2017</span>
    </StyledFooter>
  );
}

LurinFvFooterComponent.propTypes = {
  currentStyles: React.PropTypes.object
};

export default LurinFvFooterComponent;
