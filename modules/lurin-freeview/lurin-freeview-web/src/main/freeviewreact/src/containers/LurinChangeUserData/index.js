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
  Form,
  FormGroup,
  Col,
  FormControl,
  Alert,
  ControlLabel
} from 'react-bootstrap';
import { FormattedMessage, intlShape  } from 'react-intl';
import {
  makeSelectUserData,
  makeSelectLoading,
  makeSelectError,
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
  StyleForm,
  StyleButton
} from './styles';

export class LurinChangeUserData extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      username:'',
      firstName:'',
      middleName:'',
      lastName:'',
      secondLastName:'',
      email:'',
      currentPassword:'',
      newPassword:'',
      confirmNewPassword:'',
      alertVisible: false,
      alertStyle: "success",
      alertMessage: '',
      error: null,
      pressSaveButton:false
    };
    this.handleAlertDismiss = this.handleAlertDismiss.bind(this);
  }

  componentDidMount() {
    this.props.loadUserDataByName(window.currentUserSession);
  }

  static contextTypes = {
    intl: intlShape,
  }

  hasInvalidData() {
    
    if (!this.state.currentPassword) {
      if (this.state.confirmNewPassword ||  this.state.newPassword) {
        return true;
      }
    }
    if (this.state.currentPassword) {
      if (!this.state.newPassword) {
        return true;
      }
      if (!this.state.confirmNewPassword) {
        return true;  
      }
      if (this.state.confirmNewPassword.length<6) {
        return true;
      } 
      if ( this.state.confirmNewPassword!=this.state.newPassword) {
        return true;
      }
    }
   
    if(!this.state.email){
      return true;
    }
    if(!this.state.email.toString().match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/g) ){
      return true;
    }
    return false;
  } 


  componentWillReceiveProps(nextProps) {
    if (nextProps.userData) {
      this.setState({ username: window.currentUserSession,
        firstName:nextProps.userData.firstName,
        middleName:nextProps.userData.middleName,
        lastName:nextProps.userData.lastName,
        secondLastName:nextProps.userData.secondLastName,
        email:nextProps.userData.email,
      });
    }

  }

  getValidationState(field) {
    if((!this.state[field] || this.state[field]==0 ) && this.state.pressSaveButton){
      return 'error';
    }
    return null; 
  }

  getMessageValidation(field) {
    if((!this.state[field]  || this.state[field]==0 ) && this.state.pressSaveButton){
      return <FormattedMessage id="general.form.required.validation" defaultMessage='Campo Requerido'></FormattedMessage>;
    }
    return null; 
  }

  getValidationPassword() {  
    
          if(!this.state.currentPassword && this.state.pressSaveButton  && (this.state.newPassword || this.state.confirmNewPassword) ){
              return 'error';
          }
      return null; 
    }
    
    getValidationNewPassword() {  
      if(this.state.currentPassword && this.state.pressSaveButton){
        if(!this.state.newPassword ){
          return 'error';
      }
      if(this.state.newPassword.length<6 ){
        return 'error';
      }
    }
      
    return null; 
    }
    
    getValidationConfirmPassword() {
      if(this.state.currentPassword && this.state.pressSaveButton){
        if(!this.state.confirmNewPassword ){
            return 'error';
        }
        if(this.state.confirmNewPassword.length<6 ){
          return 'error';
        }
        if( this.state.newPassword!=this.state.confirmNewPassword){
          return 'error';
        }
    return null; 
    }
    }
    
    getPasswordMessageValidation() {
      
        if(!this.state.currentPassword && this.state.pressSaveButton && (this.state.newPassword || this.state.confirmNewPassword)){
          return <FormattedMessage id="form.required.field" defaultMessage='Campo Requerido'></FormattedMessage>;
       }
       
      return null; 
    }
    
    getNewPasswordMessageValidation() {
      if(this.state.currentPassword && this.state.pressSaveButton){
        if(!this.state.newPassword ){
          return <FormattedMessage id="form.required.field" defaultMessage='Campo Requerido'></FormattedMessage>;
       }
       if( this.state.newPassword.length<6 ){
         return <FormattedMessage id="user.form.input.password.message.minLength" defaultMessage='Campo Requerido'></FormattedMessage>;
       }
       
      return null; 
    }
    }
    
    getValidationEmail() {
      if(this.state.pressSaveButton){
          if(!this.state.email ){
            return 'error';
          }
          if(!this.state.email.toString().match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/g) ){
            return 'error';
          }
      }
      return null; 
    }
    
    getConfirmPasswordMessageValidation() {
      if(this.state.currentPassword && this.state.pressSaveButton){
        if(!this.state.confirmNewPassword ){
          return <FormattedMessage id="form.required.field" defaultMessage='Campo Requerido'></FormattedMessage>;
       }
       if( this.state.confirmNewPassword.length<6 ){
         return <FormattedMessage id="user.form.input.password.message.minLength" defaultMessage='Campo Requerido'></FormattedMessage>;
       }
       if( this.state.newPassword!=this.state.confirmNewPassword){
        return <FormattedMessage id="user.form.input.confirmPassword.message.doesntMatch" defaultMessage='Campo Requerido'></FormattedMessage>;
      }
      return null; 
    }
    }
    
    getEmailValidationMessage() {
      
          if(this.state.pressSaveButton){
            if(!this.state.email ){
              return <FormattedMessage id="general.form.required.validation" defaultMessage='Campo Requerido'></FormattedMessage>;
            }
            if(!this.state.email.toString().match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/g) ){
              return <FormattedMessage id="general.form.email.validation" 
                defaultMessage='Favor ingrese un Email valido'></FormattedMessage>;
            }
          }
          return null; 
        }
      
  
  save = () => {
      this.setState({ pressSaveButton: true });
      if(!this.hasInvalidData() ){
        let data = {
            "code": this.props.userData.code,
            "email": this.state.email
          }
          if(this.state.currentPassword){
            data.oldPassword= this.state.currentPassword;
            data.newPassword= this.state.newPassword;
          }
          this.props.updateUserData(data);
          this.setState({oldPassword: '', newPassword:'',confirmNewPassword:''})
          this.setState({ pressSaveButton: false });
        }
        
      }
  
  handleAlertDismiss() {
    this.setState({ alertVisible: false });
  };
  onChange = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value });
  }
  
  renderAlert= () => {
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
      header={<FormattedMessage id="userData.form.header" defaultMessage='Datos de Usuarios'></FormattedMessage>} bsStyle="primary">
        {this.renderAlert()}
        <Form horizontal>
        <FormGroup controlId="formHorizontalUserName"
       >
        <Col sm={3}>
         <FormattedMessage id="user.form.input.userName.title" defaultMessage=''></FormattedMessage>
         </Col>
         <Col sm={9}>
              <FormControl
                readOnly
                type="text"
                placeholder={this.context.intl.formatMessage({id: 'user.form.input.userName.placeholder'})}
                name="userName"
                value={this.state.username}
                onChange={this.onChange}
                maxLength={50}
                size={20}
              />
              {/* <ControlLabel>{this.getMessageValidation('firstName')} </ControlLabel> */}
         </Col>
       </FormGroup>

       <FormGroup controlId="formHorizontalFirstName"
       >
          <Col sm={3}>
          <FormattedMessage id="user.form.input.firstName.title" defaultMessage=''></FormattedMessage>
          </Col>
          <Col sm={9}>
               <FormControl
                readOnly
                 type="text"
                 placeholder={this.context.intl.formatMessage({id: 'user.form.input.firstName.placeholder'})}
                 name="firstName"
                 value={this.state.firstName}
                 onChange={this.onChange}
                 maxLength={50}
                 size={20}
               />
               {/* <ControlLabel>{this.getMessageValidation('firstName')} </ControlLabel> */}
          </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalMiddleName"
       >
          <Col sm={3}>
          <FormattedMessage id="user.form.input.middleName.title" defaultMessage=''></FormattedMessage>
          </Col>
          <Col sm={9}>
               <FormControl
                readOnly
                 type="text"
                 placeholder={this.context.intl.formatMessage({id: 'user.form.input.middleName.placeholder'})}
                 name="middleName"
                 value={this.state.middleName}
                 onChange={this.onChange}
                 maxLength={50}
                 size={20}
               />
               {/* <ControlLabel>{this.getMessageValidation('firstName')} </ControlLabel> */}
          </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalMiddleName"
       >
          <Col sm={3}>
          <FormattedMessage id="user.form.input.lastName.title" defaultMessage=''></FormattedMessage>
          </Col>
          <Col sm={9}>
               <FormControl
                readOnly
                 type="text"
                 placeholder={this.context.intl.formatMessage({id: 'user.form.input.lastName.placeholder'})}
                 name="lastName"
                 value={this.state.lastName}
                 onChange={this.onChange}
                 maxLength={50}
                 size={20}
               />
               {/* <ControlLabel>{this.getMessageValidation('firstName')} </ControlLabel> */}
          </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalSecondLastName"
       >
          <Col sm={3}>
          <FormattedMessage id="user.form.input.secondLastName.title" defaultMessage=''></FormattedMessage>
          </Col>
          <Col sm={9}>
               <FormControl
                readOnly
                 type="text"
                 placeholder={this.context.intl.formatMessage({id: 'user.form.input.secondLastName.placeholder'})}
                 name="secondLastName"
                 value={this.state.secondLastName}
                 onChange={this.onChange}
                 maxLength={50}
                 size={20}
               />
               {/* <ControlLabel>{this.getMessageValidation('firstName')} </ControlLabel> */}
          </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalEmail"
              validationState={this.getValidationEmail()} 
       >
          <Col sm={3}>
          <FormattedMessage id="user.form.input.email.title" defaultMessage=''></FormattedMessage>
          </Col>
          <Col sm={9}>
               <FormControl
                 type="text"
                 placeholder={this.context.intl.formatMessage({id: 'user.form.input.email.placeholder'})}
                 name="email"
                 value={this.state.email}
                 onChange={this.onChange}
                 maxLength={50}
                 size={20}
               />
               <ControlLabel>{this.getEmailValidationMessage()} </ControlLabel>
          </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalPassword"
       validationState={this.getValidationPassword('currentPassword')}
       >
          <Col sm={3}>
          <FormattedMessage id="user.form.input.currentPassword.title" defaultMessage=''></FormattedMessage>
          </Col>
          <Col sm={9}>
               <FormControl
                 type="password"
                 placeholder={this.context.intl.formatMessage({id: 'user.form.input.currentPassword.placeholder'})}
                 name="currentPassword"
                 value={this.state.currentPassword}
                 onChange={this.onChange}
                 maxLength={50}
                 size={20}
               />
               <ControlLabel>{this.getPasswordMessageValidation('currentPassword')} </ControlLabel>
          </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalNewPassword"
         validationState={this.getValidationNewPassword('newPassword')}
         
       >
          <Col sm={3}>
          <FormattedMessage id="user.form.input.newPassword.title" defaultMessage=''></FormattedMessage>
          </Col>
          <Col sm={9}>
               <FormControl
                 type="password"
                 placeholder={this.context.intl.formatMessage({id: 'user.form.input.newPassword.placeholder'})}
                 name="newPassword"
                 value={this.state.newPassword}
                 onChange={this.onChange}
                 maxLength={50}
                 size={20}
               />
               <ControlLabel>{this.getNewPasswordMessageValidation('newPassword')} </ControlLabel> 
          </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalConfirmNewPassword"
        validationState={this.getValidationConfirmPassword('confirmNewPassword')}
       >
          <Col sm={3}>
          <FormattedMessage id="user.form.input.confirmNewPassword.title" defaultMessage=''></FormattedMessage>
          </Col>
          <Col sm={9}>
               <FormControl
                 type="password"
                 placeholder={this.context.intl.formatMessage({id: 'user.form.input.confirmNewPassword.placeholder'})}
                 name="confirmNewPassword"
                 value={this.state.confirmNewPassword}
                 onChange={this.onChange}
                 maxLength={50}
                 size={20}
               />
               <ControlLabel>{this.getConfirmPasswordMessageValidation('confirmNewPassword')} </ControlLabel>
          </Col>
       </FormGroup>
       <FormGroup >
       <Col sm={3}>
         </Col>
         <Col sm={9}>
         <StyleButton  currentStyles={this.props.currentStyles}  bsStyle="primary"
                onClick={ () => { this.save(); }}>
                Actualizar
          </StyleButton>
         </Col>
           </FormGroup>
       </Form>
      </StyledPanel>
    );
  }
}

LurinChangeUserData.propTypes = {
  
};

const mapStateToProps = createStructuredSelector({
  userData: makeSelectUserData(),
  loading: makeSelectLoading(),
  error: makeSelectError(),
  showMessage:makeSelectShowMessage(),
  message:makeSelectMessage(),
  messageType:makeSelectMessageType(),
  currentStyles: makeSelectCurrentStyle()
});

function mapDispatchToProps(dispatch) {
  return {
    loadUserDataByName: (userName) => dispatch(actions.loadUserDataByName(userName)),
    updateUserData: (userToUpdate) => dispatch(actions.updateUserData(userToUpdate)),
    initMessage:() => dispatch(initMessage())         
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinChangeUserData);