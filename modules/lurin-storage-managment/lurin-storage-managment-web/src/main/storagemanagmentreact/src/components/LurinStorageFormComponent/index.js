/**
*
* LurinStorageFormComponent
*
*/

import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import Select from 'react-select';

import {
  Form,
  FormControl,
  FormGroup,
  Col,
  ControlLabel,
  Button,
  FieldGroup,
  Checkbox,
} from 'react-bootstrap';
import inputErrorMessage from '../../utils/inputErrorMessage';
import { FormattedMessage, intlShape  } from 'react-intl';
import {
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
  makeSelectValidConection
} from '../../containers/LurinStorageManagmentContainer/selectors';
import {
  showFormStorages,
  initMessage
} from '../../containers/LurinStorageManagmentContainer/actions';
import reducer from '../../containers/LurinStorageManagmentContainer/reducer';

import {
  StyleButton,
  StyledPanel
} from './styles';
import { debug } from 'util';


export class LurinStorageFormComponent extends React.PureComponent {

  constructor(props) {  
    super(props);
    this.state = {
      title: (this.props.isNewRegister) ? <FormattedMessage id="storageManagment.form.add.title" 
      defaultMessage='Agregar nueva conexión'></FormattedMessage> : 
      <FormattedMessage id="storageManagment.form.edit.title" defaultMessage='Editar conexión'></FormattedMessage>,
      jndiName:this.props.storagesData.jndiName,
      sourceTypeSelected: this.props.storagesData.sourceType.sourceTypeId,
      status: this.props.storagesData.status,
      sourceType: this.props.storagesData.sourceType,
      statusSelected: this.props.storagesData.status.code,
      ip:this.props.storagesData.ip,
      port:this.props.storagesData.port,
      port2:this.props.storagesData.port2,
      port3:this.props.storagesData.port3,
      sid: this.props.storagesData.sid,
      userName: this.props.storagesData.userName,
      password: this.props.storagesData.password,
      error: null,
      pressSaveButton:false,
      isValid: this.props.isValidConection || this.props.storagesData.status.code === 2,
      isDisableName: (!this.props.isNewRegister 
        && this.props.storagesData.sourceType.name === "JDBC") ? true : false,
      type: 'password'
    };
    this.handleCancelEditStorage = this.handleCancelEditStorage.bind(this);
    this.onChange = this.onChange.bind(this);
    this.save = this.save.bind(this);
    this.test = this.test.bind(this);
    this.onChangeSourceType = this.onChangeSourceType.bind(this);
    this.onChangeStatuses = this.onChangeStatuses.bind(this);
    this.showHide = this.showHide.bind(this);
  }

  showHide(e){
      e.preventDefault();
      e.stopPropagation();
      if(!this.props.isNewRegister){
        if( this.state.type === 'password'){
          this.setState({
            password: ''
          });
        }
        else{
          this.setState({
            password: this.props.storagesData.password
          });
        }
      }
      
      this.setState({
        type: this.state.type === 'input' ? 'password' : 'input',
      })
      
      
    }

  static contextTypes = {
    intl: intlShape,
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.isValidConection !== this.props.isValidConection) {
      this.setState({
        isValid:  nextProps.isValidConection
      });
    }
  }

  hasInvalidData() {
    if (!this.state.jndiName) {
      return true;
    }

    if (this.state.sourceTypeSelected == 0) {
      return true;
    }

    if (!this.state.ip) {
      return true;
    }

    if((!this.state.ip.match(/^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$/g)) ){
        
      return true;
    }

    if (!this.state.port) {
      return true;
    }

    if(!this.state.port.toString().match(/^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$/g)){
      return true;
    }

    if (this.state.statusSelected == 0) {
      return true;
    }

    return false
  }

  hasInvalidIpAndPort() {
    if (!this.state.ip) {
      return true;
    }
    if((!this.state.ip.match(/^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$/g)) ){
      return true;
    }
    if (!this.state.port) {
      return true;
    }
    if(!this.state.port.toString().match(/^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$/g)){
      return true;
    }
    return false
  }


  save = () => {
    this.setState({ pressSaveButton: true });
    if(!this.hasInvalidData())
      this.props.save(this.state);
  }

  test = () => {
    this.setState({ pressSaveButton: true });
    if(!this.hasInvalidData())
      this.props.test(this.state);
  }
 
  handleCancelEditStorage = () => {
   this.props.showFormStorages(false);
   this.props.initMessage();
  }

  onChange = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value });
  }

  onChangeSourceType = (evt) => {
    const index = this.props.sourceTypesData.findIndex(i => i.sourceTypeId.toString() === evt.target.value);
    
    this.setState({ [evt.target.name]: evt.target.value, sourceType: this.props.sourceTypesData[index] });
  }

  onChangeStatuses = (evt) => {
    const index = this.props.statusData.findIndex(i => i.code.toString()  === evt.target.value);
    const isValid = evt.target.value === "2";
    this.setState({ [evt.target.name]: evt.target.value, status: this.props.statusData[index], 
    isValid : isValid});
  }

  getValidationState(field) {
    if((!this.state[field]) && this.state.pressSaveButton){
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

  getValidationIp() {
    if(this.state.pressSaveButton){
      if(!this.state.ip ){
        return 'error';
      }
      if((!this.state.ip.match(/^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$/g)) && this.state.pressSaveButton){
        return 'error';
      }
    }
    return null; 
  
  }

  getIpValidationMessage() {
    if(this.state.pressSaveButton){
      if(!this.state.ip){
        return <FormattedMessage id="general.form.required.validation" defaultMessage='Campo Requerido'></FormattedMessage>;
      }
        if(!this.state.ip.match(/^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$/g)){
          return <FormattedMessage id="storageManagment.form.validation.ip" defaultMessage='Favor ingrese una ip valida'></FormattedMessage>;
        }
    }
    return null; 
  }

  getValidationPort() {
    if(this.state.pressSaveButton){
        if(!this.state.port ){
          return 'error';
        }
        if(!this.state.port.toString().match(/^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$/g) ){
          return 'error';
        }
    }
    return null; 
  }

  getPortValidationMessage() {

    if(this.state.pressSaveButton){
      if(!this.state.port ){
        return <FormattedMessage id="general.form.required.validation" defaultMessage='Campo Requerido'></FormattedMessage>;
      }
      if(!this.state.port.toString().match(/^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$/g) ){
        return <FormattedMessage id="storageManagment.form.validation.port" defaultMessage='Favor ingrese una Puerto valido'></FormattedMessage>;
      }
    }
    return null; 
  }

  getEmptyValidation() {
    return null;
  }

  renderPortBySourceType() {
    if(this.state.sourceType.sourceType === 'J') {
      return (
        <FormGroup controlId="formHorizontalPort"
        validationState={this.getValidationPort()} 
        >
          <Col sm={3}>
            <FormattedMessage id="storageManagment.form.input.port.title" defaultMessage='Nombre'></FormattedMessage>
          </Col>
          <Col sm={9}>
                <FormControl
                  type="text"
                  placeholder={this.context.intl.formatMessage({id: 'storageManagment.form.input.port.placeholder'})}
                  name="port"
                  value={this.state.port}
                  onChange={this.onChange}
                  maxLength={10}
                />
              <ControlLabel>{this.getPortValidationMessage()} </ControlLabel>
          </Col>
        </FormGroup>
      ); 
    } else if(this.state.sourceType.sourceType === 'T') {
      return (
        <FormGroup controlId="formHorizontalPort"
        validationState={this.getValidationPort()} 
        >
          <Col sm={3}>
            <FormattedMessage id="storageManagment.form.input.port1.title" defaultMessage='Nombre'></FormattedMessage>
          </Col>
          <Col sm={9}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({id: 'storageManagment.form.input.port1.placeholder'})}
                name="port"
                value={this.state.port}
                onChange={this.onChange}
                maxLength={10}
              />
              <ControlLabel>{this.getPortValidationMessage()} </ControlLabel>
          </Col>
        </FormGroup>
      );
    }

    return '';
  }

  renderPortOrSIDBySourceType() {
    if(this.state.sourceType.sourceType === 'J') {
      return (
        <FormGroup controlId="formHorizontalSID"
          validationState={this.getValidationState('sid')}
          >
            <Col sm={3}>
              SID
            </Col>
            <Col sm={9}>
                  <FormControl
                    type="text"
                    placeholder="SID"
                    name="sid"
                    value={this.state.sid}
                    onChange={this.onChange}
                    maxLength={50}
                  />
                  <ControlLabel>{this.getMessageValidation('sid')} </ControlLabel>
            </Col>
          </FormGroup>
      ); 
    } else if(this.state.sourceType.sourceType === 'T') {
      return (
        <FormGroup controlId="formHorizontalPort2">
         <Col sm={3}>
           <FormattedMessage id="storageManagment.form.input.port2.title" defaultMessage='Nombre'></FormattedMessage>
         </Col>
         <Col sm={9}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({id: 'storageManagment.form.input.port2.placeholder'})}
                name="port2"
                value={this.state.port2}
                onChange={this.onChange}
                maxLength={10}
              />
             <ControlLabel>{this.getEmptyValidation()} </ControlLabel>
         </Col>
       </FormGroup>
      );
    }

    return '';
  }

  renderPortOrUsernameBySourceType() {
    if(this.state.sourceType.sourceType === 'J') {
      return (
        <FormGroup controlId="formHorizontalUserName"
          validationState={this.getValidationState('userName')}
          >
            <Col sm={3}>
              <FormattedMessage id="storageManagment.form.input.userName.title" defaultMessage=''></FormattedMessage>
            </Col>
            <Col sm={9}>
                  <FormControl
                    type="text"
                    placeholder={this.context.intl.formatMessage({id: 'storageManagment.form.input.userName.placeholder'})}
                    name="userName"
                    value={this.state.userName}
                    onChange={this.onChange}
                    maxLength={50}
                  />
                  <ControlLabel>{this.getMessageValidation('userName')} </ControlLabel>
            </Col>
          </FormGroup>
      ); 
    } else if(this.state.sourceType.sourceType === 'T') {
      return (
        <FormGroup controlId="formHorizontalPort3">
            <Col sm={3}>
              <FormattedMessage id="storageManagment.form.input.port3.title" defaultMessage='Nombre'></FormattedMessage>
            </Col>
            <Col sm={9}>
                  <FormControl
                    type="text"
                    placeholder={this.context.intl.formatMessage({id: 'storageManagment.form.input.port3.placeholder'})}
                    name="port3"
                    value={this.state.port3}
                    onChange={this.onChange}
                    maxLength={10}
                  />
                <ControlLabel>{this.getEmptyValidation()} </ControlLabel>
            </Col>
          </FormGroup>
      );
    }

    return '';
  }

  renderPasswordBySourceType() {
    if(this.state.sourceType.sourceType === 'J') {
      return (
        <FormGroup controlId="formHorizontalPassword"
          validationState={this.getValidationState('password')}
         >
            <Col sm={3}>
              Password
            </Col>
            <Col sm={9}>
                 <FormControl
                   type={this.state.type}
                   placeholder="Password"
                   name="password"
                   value={this.state.password}
                   onChange={this.onChange}
                   maxLength={50}
                 />
                 <Checkbox onClick={this.showHide}>{this.state.type === 'input' ? <FormattedMessage id='storageManagment.messages.hide.password' defaultMessage=''></FormattedMessage> : <FormattedMessage id='storageManagment.messages.show.password' defaultMessage=''></FormattedMessage>}</Checkbox>
                 <ControlLabel>{this.getMessageValidation('password')} </ControlLabel>
            </Col>
          </FormGroup>
      ); 
    } 

    return '';
  }

//  renderCheckboxShowHidePassword() {
//      if(this.state.sourceType.sourceType === 'J') {
//        return (
//          <FormGroup controlId="formShowPasswordPassword"
//           >
//              <Col sm={3}>
//                Password
//              </Col>
//              <Col sm={9}>
//                   <FormControl
//                     type="checkbox"
//                     name="checkboxPassword"
//                     onClick={this.showHide}
//                   />
//                   <ControlLabel>Show Password</ControlLabel>
//              </Col>
//            </FormGroup>
//        );
//      }
//
//      return '';
//    }

  render() {
    
    let sourceTypesOptionTemplate = this.props.sourceTypesData.map(sourceType => (
      <option key={sourceType.sourceTypeId} value={sourceType.sourceTypeId}>{sourceType.name}</option>
    ));

    let statusesOptionTemplate = this.props.statusData.map(status => (
      <option key={status.code} value={status.code}>{status.name}</option>
    ));
     
    return (
      <StyledPanel currentStyles={this.props.currentStyles} header={this.state.title} bsStyle="primary">
        <Form horizontal>
        
          <FormGroup controlId="formHorizontalName"
          validationState={this.getValidationState('jndiName')} 
          >
            <Col sm={3}>
              <FormattedMessage id="storageManagment.form.input.conexionName.title" defaultMessage='Nombre'></FormattedMessage>
            </Col>
            <Col sm={9}>
                  <FormControl
                    type="text"
                    placeholder={this.context.intl.formatMessage({id: 'storageManagment.form.input.conexionName.placeholder'})}
                    name="jndiName"
                    value={this.state.jndiName}
                    onChange={this.onChange}
                    maxLength={50}
                    disabled={this.state.isDisableName}
                  />
                  <ControlLabel>{this.getMessageValidation('jndiName')} </ControlLabel>
            </Col>
          </FormGroup>

          <FormGroup controlId="formHorizontalsourceType"
          validationState={this.getValidationState('sourceTypeSelected')} 
          >
            <Col sm={3}>
              <FormattedMessage id="storageManagment.form.input.conexionType.title" defaultMessage=''></FormattedMessage>
            </Col>
            <Col sm={9}>
              <FormControl
                componentClass="select"
                placeholder={<FormattedMessage id="combobox.first.register" defaultMessage=''></FormattedMessage>}
                name="sourceTypeSelected"
                value={this.state.sourceTypeSelected}
                onChange={this.onChangeSourceType}
              >
                {sourceTypesOptionTemplate}
              </FormControl>
              <ControlLabel>{this.getMessageValidation('sourceTypeSelected')} </ControlLabel>
            </Col>
          </FormGroup>

          <FormGroup controlId="formHorizontalIP"
          validationState={this.getValidationIp()} 
          >
            <Col sm={3}>
              IP
            </Col>
            <Col sm={9}>
                <FormControl
                  type="text"
                  placeholder="IP"
                  name="ip"
                  value={this.state.ip}
                  onChange={this.onChange}
                  maxLength={15}
                />
                <ControlLabel>{this.getIpValidationMessage()} </ControlLabel>
            </Col>
          </FormGroup>
          {this.renderPortBySourceType()}
          {this.renderPortOrSIDBySourceType()}
          {this.renderPortOrUsernameBySourceType()}
          {this.renderPasswordBySourceType()}
          <FormGroup controlId="formHorizontalStatus"
          validationState={this.getValidationState('statusSelected')} 
          >
            <Col sm={3}>
              <FormattedMessage id="storageManagment.form.input.status.title" defaultMessage=''></FormattedMessage>
            </Col>
            <Col sm={9}>
              <FormControl
                componentClass="select"
                placeholder={<FormattedMessage id="combobox.first.register" defaultMessage=''></FormattedMessage>}
                name="statusSelected"
                value={this.state.statusSelected}
                onChange={this.onChangeStatuses}
              >
                {statusesOptionTemplate}
              </FormControl>
              <ControlLabel>{this.getMessageValidation('statusSelected')} </ControlLabel>
            </Col>
          </FormGroup>

          <FormGroup>
            <Col sm={3} >
            </Col>
              <Col sm={9} >
              <StyleButton currentStyles={this.props.currentStyles} bsStyle="primary" 
                  onClick={ () => { this.save(); }} disabled={!this.state.isValid}>
                  <FormattedMessage id="form.button.save" defaultMessage=''></FormattedMessage>
              </StyleButton>
              <StyleButton currentStyles={this.props.currentStyles} bsStyle="success" 
                onClick={ () => { this.test(); }}>
                  <FormattedMessage id="storageManagment.form.button.test" defaultMessage=''></FormattedMessage>
              </StyleButton>
              <Button onClick={() => { this.handleCancelEditStorage(); }}>
                  <FormattedMessage id="form.button.cancel" defaultMessage=''></FormattedMessage>
              </Button>
            </Col>
          </FormGroup>
        </Form>
      </StyledPanel>
    );
  }
}

LurinStorageFormComponent.propTypes = {
  showFormStorages: PropTypes.func,
  isNewRegister: PropTypes.bool,
  storageData: PropTypes.object,
  save: PropTypes.func,
  test: PropTypes.func,
  currentStyles: PropTypes.object,
};

const mapStateToProps = createStructuredSelector({
  loading: makeSelectLoading(),
  error: makeSelectError(),
  hasShowForm: makeSelectHasShowForm(),
  isValidConection: makeSelectValidConection()
});

function mapDispatchToProps(dispatch) {
  return {
    showFormStorages: (hasShowForm) => dispatch(showFormStorages(hasShowForm)) ,
    initMessage:() => dispatch(initMessage())          
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinStorageFormComponent);
