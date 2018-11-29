/**
 *
 * LurinUserCarrierManagmentContainer
 *
 */

import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import LurinUserManagmentContainer from '../../containers/LurinUserManagmentContainer';

export class LurinUserCarrierManagmentContainer extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
    };  
  }

  render() {
   
    return (
      <LurinUserManagmentContainer isMultiCarrier={false}  />  
    );
  }
}

LurinUserCarrierManagmentContainer.propTypes = {

};

const mapStateToProps = createStructuredSelector({
});

function mapDispatchToProps(dispatch) {
  return {
    
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinUserCarrierManagmentContainer);
