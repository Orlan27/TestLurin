/**
 *
 * LurinFvGlobalParameterContainer
 *
 */

import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import {
  Glyphicon,
  Button,
  FormGroup,
  Col,
  FormControl,
  Alert
} from 'react-bootstrap';
import { FormattedMessage, intlShape  } from 'react-intl';
import LurinSecurityPolicyFormComponent from '../../components/LurinSecurityPolicyFormComponent';
import LurinModalComponent from '../../components/LurinModalComponent';
import {
  makeSelectSecurityPolicyData,
  makeSelectLoading,
  makeSelectError,
  makeSelectNewSecurityPolicy,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType,
} from './selectors';

import {
  makeSelectCurrentStyle
} from '../LurinFvAppContainer/selectors';

import * as actions from './actions';

import {
  StyledPanel,
  StyleForm
} from './styles';

export class LurinFvSecurityPolicyContainer extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      allSecurityPolicy: [],
      alertVisible: false,
      alertStyle: "success",
      alertMessage: '',
    };
    this.handleAlertDismiss = this.handleAlertDismiss.bind(this);
  }

  componentDidMount() {
    this.props.loadSecurityPolicy();
  }

  static contextTypes = {
    intl: intlShape,
  }

  componentWillReceiveProps(nextProps) {
    
    if (nextProps.securityPolicyData.length > 0) {
      this.setState({ allSecurityPolicy: nextProps.securityPolicyData });
    } else {
      this.setState({ allSecurityPolicy: [] });
    }
  }

  hasInvalidData(currentState) {
    if (!currentState.key) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El campo Key es requerido."
      });
      return true;
    }
    return false
  }

  save = (currentState) => {
    // if (this.hasInvalidData(currentState)) {
    //   return;
    // }
    this.setState({
      alertVisible: false,
      alertMessage: ""
    });
    this.props.updateSecurityPolicy(currentState.currentSecurityPolicy);
  }
  
  handleAlertDismiss() {
    this.setState({ alertVisible: false });
  };
  
 
  renderAlert() {
    if(this.props.showMessage) {
      return (
        <div>
          <Alert bsStyle={this.props.messageType} onDismiss={this.handleAlertDismiss}>
          <span><FormattedMessage id= {this.props.message} defaultMessage='Message'></FormattedMessage></span>
          </Alert>
          <br />
        </div>
      );
    }
    return '';
  }

  render() {
    return (
      <StyledPanel currentStyles={this.props.currentStyles} className="custom-primary-bg"  
      header={<FormattedMessage id="securityPolicy.form.header" defaultMessage='Politicas de Seguridad'></FormattedMessage>} bsStyle="primary">
        {this.renderAlert()}
        <LurinSecurityPolicyFormComponent
          securityPolicyData={this.state.allSecurityPolicy}
          save={this.save}
          currentStyles={this.props.currentStyles}
        />
      </StyledPanel>
    );
  }
}

LurinFvSecurityPolicyContainer.propTypes = {
  loadSecurityPolicy: PropTypes.func,
  updateSecurityPolicy: PropTypes.func,
};

const mapStateToProps = createStructuredSelector({
  loading: makeSelectLoading(),
  error: makeSelectError(),
  securityPolicyData: makeSelectSecurityPolicyData(),
  showMessage:makeSelectShowMessage(),
  message:makeSelectMessage(),
  messageType:makeSelectMessageType(),
  currentStyles: makeSelectCurrentStyle()
});

function mapDispatchToProps(dispatch) {
  return {
    loadSecurityPolicy: () => dispatch(actions.loadSecurityPolicy()),
    updateSecurityPolicy: (securityPolicyToUpdate) => dispatch(actions.updateSecurityPolicy(securityPolicyToUpdate)),
    initMessage: () => dispatch(actions.initMessage())
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinFvSecurityPolicyContainer);