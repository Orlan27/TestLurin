/**
*
* LurinUserFormComponent
*
*/

import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import Select from 'react-select';
import { FormattedMessage, intlShape } from 'react-intl';
import Toggle from 'react-toggle'
import {
  BootstrapTable,
  TableHeaderColumn
} from 'react-bootstrap-table';
import {
  Form,
  FormControl,
  FormGroup,
  Col,
  ControlLabel,
  Button,
  FieldGroup,
  Checkbox,
  Glyphicon,
  Modal
} from 'react-bootstrap';
import inputErrorMessage from '../../utils/inputErrorMessage';

import {
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
  makeSelectUsersLDAP,
} from '../../containers/LurinUserManagmentContainer/selectors';
import {
  showForm,
  loadUsersLdap,
  initMessage
} from '../../containers/LurinUserManagmentContainer/actions';
import reducer from '../../containers/LurinUserManagmentContainer/reducer';

import {
  StyleButton,
  StyledPanel
} from './styles';
import { debug } from 'util';


export class LurinUserFormComponent extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      title: (this.props.isNewRegister) ? <FormattedMessage id="user.form.add.title" defaultMessage='Agregar nuevo Freeview'></FormattedMessage> :
        <FormattedMessage id="user.form.edit.title" defaultMessage='Editar Freeview'></FormattedMessage>,
      allUsersLdap: [],
      login: this.props.usersData.login,
      password: this.props.usersData.password,
      firstName: this.props.usersData.firstName,
      middleName: this.props.usersData.middleName,
      lastName: this.props.usersData.lastName,
      secondLastName: this.props.usersData.secondLastName,
      passwordExpired: !(this.props.usersData.passwordExpired == 'N'),
      confirmPassword: this.props.usersData.password,
      //description:this.props.usersData.description,
      //phone:this.props.usersData.phone,
      email: this.props.usersData.email,
      //authenticationTypeCode:this.props.usersData.authenticationType.code,
      //userTypeCode:this.props.usersData.userType.code,
      roleId: this.props.usersData.roleId,
      carrierId: this.props.usersData.carrierId,
      carriersValue: this.props.usersData.carriersValue,
      status: this.props.usersData.status,
      statusSelect: this.props.usersData.status.code,
      error: null,
      ldap: (this.props.isMultiCarrier) ? ((this.props.usersData.password == null) ? true : false):false,
      profileId: (this.props.profiles.length==1)? 0:this.props.usersData.profileId,
      pressSaveButton: false,
      isShowSearchLdap: false,
    };

    this.options = {
      defaultSortName: 'userName',
      defaultSortOrder: 'desc',
      noDataText: <FormattedMessage id="table.nodata" defaultMessage='No se han encontrado registros para mostrar '></FormattedMessage>
    };

    this.handleCancelEdit = this.handleCancelEdit.bind(this);
    this.onChange = this.onChange.bind(this);
    this.showSearchLdap = this.showSearchLdap.bind(this);
  }

  static contextTypes = {
    intl: intlShape,
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.usersLDAP.length > 0) {
      this.setState({ allUsersLdap: nextProps.usersLDAP });
    } else {
      this.setState({ allUsersLdap: [] });
    }
  }

  hasInvalidData() {
    if (!this.state.login) {
      return true;
    }

    if (!this.state.ldap && !this.state.password) {
      return true;
    }
    if (!this.state.ldap && this.state.password.length < 6) {
      return true;
    }
    if (!this.state.ldap && !this.state.confirmPassword) {
      return true;
    }
    if (!this.state.ldap && this.state.confirmPassword.length < 6) {
      return true;
    }
    if (!this.state.ldap && this.state.confirmPassword != this.state.password) {
      return true;
    }
    if (!this.state.firstName) {
      return true;
    }
    if (!this.state.lastName) {
      return true;
    }
    if (!this.state.email) {
      return true;
    }
    if (!this.state.carriersValue) {
      return true;
    }
    if(this.props.isMultiCarrier){
      if (!this.state.profileId) {
        return true;
      }
    }
    return false;
  }

  getValidationState(field) {
    if ((!this.state[field] || this.state[field] == '0') && this.state.pressSaveButton) {
      return 'error';
    }
    return null;

  }
  getMessageValidation(field) {
    if ((!this.state[field] || this.state[field] == '0') && this.state.pressSaveButton) {
      return <FormattedMessage id="form.required.field" defaultMessage='Campo Requerido'></FormattedMessage>;
    }
    return null;
  }

  getPasswordMessageValidation() {
    if (!this.state.ldap) {
      if (!this.state.password && this.state.pressSaveButton) {
        return <FormattedMessage id="form.required.field" defaultMessage='Campo Requerido'></FormattedMessage>;
      }
      if (this.state.password != undefined && (this.state.password.length < 6 && this.state.pressSaveButton)) {
        return <FormattedMessage id="user.form.input.password.message.minLength" defaultMessage='Campo Requerido'></FormattedMessage>;
      }
    }
    return null;
  }

  getValidationPassword() {
    if (!this.state.ldap) {
      if (!this.state.password && this.state.pressSaveButton) {
        return 'error';
      }

      if (this.state.password != undefined && (this.state.password.length < 6 && this.state.pressSaveButton)) {
        return 'error';
      }
    }
    return null;
  }


  getConfirmPasswordMessageValidation() {
    if (!this.state.ldap) {
      if (!this.state.confirmPassword && this.state.pressSaveButton) {
        return <FormattedMessage id="form.required.field" defaultMessage='Campo Requerido'></FormattedMessage>;
      }
      if (this.state.confirmPassword != undefined && (this.state.confirmPassword.length < 6 && this.state.pressSaveButton)) {
        return <FormattedMessage id="user.form.input.password.message.minLength" defaultMessage='Campo Requerido'></FormattedMessage>;
      }
      //if (this.state.password != undefined && (this.state.confirmPassword && this.state.pressSaveButton)) {
        if (this.state.password != this.state.confirmPassword &&   this.state.pressSaveButton) {
        return <FormattedMessage id="user.form.input.confirmPassword.message.doesntMatch" defaultMessage='Campo Requerido'></FormattedMessage>;
      }
    }
    return null;
  }

  getValidationConfirmPassword() {
    if (!this.state.ldap) {
      if (!this.state.password && this.state.pressSaveButton) {
        return 'error';
      }
      if (this.state.password != undefined && (this.state.password.length < 6 && this.state.pressSaveButton)) {
        return 'error';
      }
      if (this.state.password != undefined && (this.state.password != this.state.confirmPassword && this.state.pressSaveButton)) {
        return 'error';
      }
    }
    return null;
  }

  editFormatter = (cell, row) => (
    <Button bsSize="small" bsClass="btn-img-grid" onClick={() => { this.handleUserLdapSelected(row); }}><Glyphicon glyph="plus" /></Button>
  );

  showSearchLdap = () => {
    this.setState({ isShowSearchLdap: true });
    this.props.loadUsersLdap();
  }

  handleHideSeachLdap = () => {
    this.setState({ isShowSearchLdap: false });
  }

  save = () => {
    this.setState({ pressSaveButton: true });
    if (!this.hasInvalidData())
      this.props.save(this.state);
  }

  handleCancelEdit = () => {
    this.props.showForm(false);
    this.props.initMessage();
  }

  handleUserLdapSelected = (selectedUser) => {
    var currentUser= selectedUser;
    
    this.setState({
      login: currentUser.userName,
      firstName: currentUser.firstName,
      middleName: currentUser.secondName,
      lastName: currentUser.surname,
      secondLastName: currentUser.lastName,
      email: currentUser.email,
      isShowSearchLdap: false,
    });
  }

  onChange = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value });
  }

  onChangeLDAP = (evt) => {
    this.setState({ ldap: !this.state.ldap });
  }

  onChangePasswordExpired = (evt) => {
    this.setState({ passwordExpired: !this.state.passwordExpired });
  }

  // onChangeAuthenticationType = (evt) => {
  //   this.setState({ [evt.target.name]: evt.target.value });
  // }

  // onChangeUserType = (evt) => {
  //   this.setState({ [evt.target.name]: evt.target.value });
  // }

  //  onChangeRole = (evt) => {
  //    this.setState({ [evt.target.name]: evt.target.value });
  //  }

  onChangeProfile = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value });
  }


  onChangeStatus = (evt) => {
    const index = statusUser.findIndex(i => i.code.toString() === evt.target.value);
    this.setState({ [evt.target.name]: evt.target.value, status: statusUser[index] });
  }


  handleSelectCarrier = (evt) => {
    this.setState({ carriersValue: evt });
  }

  render() {
    // let authenticationTypeOptionTemplate = this.props.authenticationTypes.map(authenticationType => (
    //   <option key={authenticationType.code} value={authenticationType.code}>{authenticationType.name}</option>
    // ));

    // let userTypeOptionTemplate = this.props.userTypes.map(userType => (
    //   <option key={userType.code} value={userType.code}>{userType.name}</option>
    // ));

    let profilesOptionTemplate = this.props.profiles.map(profile => (
      <option key={profile.profileId} value={profile.profileId}>{profile.name}</option>
    ));

    //    let rolesOptionTemplate = this.props.roles.map(role => (
    //      <option key={role.code} value={role.code}>{role.name}</option>
    //    ));
    let carrierOptionTemplate = this.props.carriers.map(carrier => (
      <option key={carrier.carrierId} value={carrier.carrierId}>{carrier.name}</option>
    ));

    let statusIdioma = [
      { code: 1, name: this.context.intl.formatMessage({ id: 'user.form.input.active.title' }), status: 'A' },
      { code: 2, name: this.context.intl.formatMessage({ id: 'user.form.input.inactive.title' }), status: 'I' },
    ];

    let ProfileFormGroup = null; 
    if(this.props.isMultiCarrier)
      ProfileFormGroup = (<FormGroup controlId="formHorizontalProfiles" 
    validationState={this.getValidationState('profileId')}
  >
    <Col sm={3}>
      <FormattedMessage id="user.form.input.profile.title" defaultMessage=''></FormattedMessage>
    </Col>
    <Col sm={9}>
      <FormControl
        componentClass="select"
        placeholder="Seleccionar" 
        name="profileId"
        value={this.state.profileId}
        onChange={this.onChangeProfile}
      >
        {profilesOptionTemplate}
      </FormControl>
      <ControlLabel>{this.getMessageValidation('profileId')} </ControlLabel>
    </Col>
  </FormGroup>);

    let passwordComponent = null;
    let confirmPasswordComponent = null;
    let buttonSearchLdap = null;
    if (!this.state.ldap) {
      passwordComponent = (<FormGroup controlId="formHorizontalPassword"
        validationState={this.getValidationPassword('password')}
      >
        <Col sm={3}>
          <FormattedMessage id="user.form.input.password.title" defaultMessage=''></FormattedMessage>
        </Col>
        <Col sm={9}>
          <FormControl
            type="password"
            placeholder={this.context.intl.formatMessage({ id: 'user.form.input.password.placeholder' })}
            name="password"
            value={this.state.password}
            onChange={this.onChange}
            maxLength={255}
          />
          <ControlLabel>{this.getPasswordMessageValidation('password')} </ControlLabel>
        </Col>
      </FormGroup>);
      confirmPasswordComponent = (<FormGroup controlId="formHorizontalConfirmPassword"
        validationState={this.getValidationConfirmPassword('confirmPassword')}
      >
        <Col sm={3}>
          <FormattedMessage id="user.form.input.confirmPassword.title" defaultMessage=''></FormattedMessage>
        </Col>
        <Col sm={9}>
          <FormControl
            type="password"
            placeholder={this.context.intl.formatMessage({ id: 'user.form.input.confirmPassword.placeholder' })}
            name="confirmPassword"
            value={this.state.confirmPassword}
            onChange={this.onChange}
            maxLength={255}
          />
          <ControlLabel>{this.getConfirmPasswordMessageValidation('confirmPassword')} </ControlLabel>
        </Col>
      </FormGroup>);
    } else {
      buttonSearchLdap = (
        <Button bsSize="medium" onClick={() => { this.showSearchLdap(); }}><Glyphicon glyph="search" /></Button>
      );
    }

    let statusOptionTemplate = statusIdioma.map(status => (
      <option key={status.code} value={status.code}>{status.name}</option>
    ));

    let ldapToggle=null;
    if (this.props.isMultiCarrier)
      ldapToggle=(<FormGroup controlId="formHorizontalUserName"
      validationState={this.getValidationState('login')} >
      <Col sm={3}>
        LDAP
  </Col>
      <Col sm={9}>
        <Toggle
          defaultChecked={this.state.ldap}
          onChange={this.onChangeLDAP}
          name='ldap'
          value='true' />
        <ControlLabel> </ControlLabel>
      </Col>
    </FormGroup>);

    

    return (
      <StyledPanel currentStyles={this.props.currentStyles} header={this.state.title} bsStyle="primary">
        <Form horizontal>
          {ldapToggle}
          <FormGroup controlId="formHorizontalUserName"
            validationState={this.getValidationState('login')} >
            <Col sm={3}>
              <FormattedMessage id="user.form.input.userName.title" defaultMessage=''></FormattedMessage>
            </Col>
            <Col sm={6}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({ id: 'user.form.input.userName.placeholder' })}
                name="login"
                value={this.state.login}
                onChange={this.onChange}
                maxLength={50}
              />
              <ControlLabel>{this.getMessageValidation('login')} </ControlLabel>
            </Col>
            <Col sm={3}>
              {buttonSearchLdap}
            </Col>
          </FormGroup>
          <FormGroup controlId="formHorizontalFirstName"
            validationState={this.getValidationState('firstName')}
          >
            <Col sm={3}>
              <FormattedMessage id="user.form.input.firstName.title" defaultMessage=''></FormattedMessage>
            </Col>
            <Col sm={9}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({ id: 'user.form.input.firstName.placeholder' })}
                name="firstName"
                value={this.state.firstName}
                onChange={this.onChange}
                maxLength={50}
              />
              <ControlLabel>{this.getMessageValidation('firstName')} </ControlLabel>
            </Col>
          </FormGroup>
          <FormGroup controlId="formHorizontalMiddleName"
            validationState={this.getValidationState('middleName')}
          >
            <Col sm={3}>
              <FormattedMessage id="user.form.input.middleName.title" defaultMessage=''></FormattedMessage>
            </Col>
            <Col sm={9}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({ id: 'user.form.input.middleName.placeholder' })}
                name="middleName"
                value={this.state.middleName}
                onChange={this.onChange}
                maxLength={50}
              />
              <ControlLabel>{this.getMessageValidation('middleName')} </ControlLabel>
            </Col>
          </FormGroup>
          <FormGroup controlId="formHorizontalLastName"
            validationState={this.getValidationState('lastName')}
          >
            <Col sm={3}>
              <FormattedMessage id="user.form.input.lastName.title" defaultMessage=''></FormattedMessage>
            </Col>
            <Col sm={9}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({ id: 'user.form.input.lastName.placeholder' })}
                name="lastName"
                value={this.state.lastName}
                onChange={this.onChange}
                maxLength={50}
              />
              <ControlLabel>{this.getMessageValidation('lastName')} </ControlLabel>
            </Col>
          </FormGroup>
          <FormGroup controlId="formHorizontalSecondLastName"
            validationState={this.getValidationState('secondLastName')}
          >
            <Col sm={3}>
              <FormattedMessage id="user.form.input.secondLastName.title" defaultMessage=''></FormattedMessage>
            </Col>
            <Col sm={9}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({ id: 'user.form.input.secondLastName.placeholder' })}
                name="secondLastName"
                value={this.state.secondLastName}
                onChange={this.onChange}
                maxLength={50}
              />
              <ControlLabel>{this.getMessageValidation('secondLastName')} </ControlLabel>
            </Col>
          </FormGroup>
          <FormGroup controlId="formHorizontalPhone"
            validationState={this.getValidationState('email')}
          >
            <Col sm={3}>
              Email
         </Col>
            <Col sm={9}>
              <FormControl
                type="text"
                placeholder="Email"
                name="email"
                value={this.state.email}
                onChange={this.onChange}
                maxLength={250}
              />
              <ControlLabel>{this.getMessageValidation('email')} </ControlLabel>
            </Col>
          </FormGroup>
          {passwordComponent}
          {confirmPasswordComponent}

          {/*<FormGroup controlId="formHorizontalPasswordExpire" >
         <Col sm={3}>
         <FormattedMessage id="user.form.input.passwordExpired.title" defaultMessage=''></FormattedMessage>
         </Col>
         <Col sm={9}>
         <Toggle
         defaultChecked={this.state.passwordExpired}
         onChange={this.onChangePasswordExpired}
         name='passwordExpired'
         value='true' />
         <ControlLabel> </ControlLabel>
         </Col>
       </FormGroup>*/}



          {/* <FormGroup controlId="formHorizontalDescription" >
         <Col sm={3}>
         <FormattedMessage id="user.form.input.description.title" defaultMessage=''></FormattedMessage> 
         </Col>
         <Col sm={9}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({id: 'user.form.input.description.placeholder'})}
                name="description"
                value={this.state.description}
                onChange={this.onChange}
              />
               <ControlLabel></ControlLabel>
         </Col>
       </FormGroup> */}
          {/* <FormGroup controlId="formHorizontalPhone"
       validationState={this.getValidationState('phone')}
       >
         <Col sm={3}>
         <FormattedMessage id="user.form.input.phone.title" defaultMessage=''></FormattedMessage>  
         </Col>
         <Col sm={9}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({id: 'user.form.input.phone.placeholder'})}
                name="phone"
                value={this.state.phone}
                onChange={this.onChange}
              />
              <ControlLabel>{this.getMessageValidation('phone')} </ControlLabel>
         </Col>
       </FormGroup> */}
          <FormGroup controlId="formValidationCarriers"
            validationState={this.getValidationState('carriersValue')}
          >
            <Col sm={3}>
              <h5 className="carrier-title"><FormattedMessage id="user.form.input.operators.placeholder"
                defaultMessage=''></FormattedMessage></h5>
            </Col>
            <Col sm={9}>
              <Select
                name="input-carrier"
                className={this.getValidationState('carriersValue') == 'error' ? 'carrier-error' : "carrier"}
                closeOnSelect
                disabled={false}
                autosize={false}
                multi={this.props.isMultiCarrier}
                options={this.props.carriers}
                placeholder={<FormattedMessage id="combobox.first.register" defaultMessage=''></FormattedMessage>}
                removeSelected={false}
                rtl={false}
                simpleValue
                value={this.state.carriersValue}
                onChange={this.handleSelectCarrier}
              />
              <ControlLabel className="label-carrier" >{this.getMessageValidation('carriersValue')} </ControlLabel>
            </Col>
          </FormGroup>
          {/* <FormGroup controlId="formHorizontalCarrier"
           validationState={this.getValidationState('carrierId')}
          >
            <Col sm={3}>
            <FormattedMessage id="user.form.input.carrier.title" defaultMessage=''></FormattedMessage>
            </Col>
            <Col sm={9}>
            <FormControl
                componentClass="select"
                placeholder="Seleccionar"
                name="carrierId"
                value={this.state.carrierId}
                onChange={this.handleSelectCarrier}
              >
                {carrierOptionTemplate}
              </FormControl>
              <ControlLabel>{this.getMessageValidation('carrierId')} </ControlLabel>
            </Col>
        </FormGroup>  */}

         {ProfileFormGroup}

          {/*<FormGroup controlId="formHorizontalRoles"
           validationState={this.getValidationState('roleId')}
          >
            <Col sm={3}>
            <FormattedMessage id="user.form.input.rol.title" defaultMessage=''></FormattedMessage>
            </Col>
            <Col sm={9}>
            <FormControl
                componentClass="select"
                placeholder="Seleccionar"
                name="roleId"
                value={this.state.roleId}
                onChange={this.onChangeRole}
              >
                {rolesOptionTemplate}
              </FormControl>
              <ControlLabel>{this.getMessageValidation('roleId')} </ControlLabel>
            </Col>
        </FormGroup>*/}

          <FormGroup controlId="formHorizontalStatus"
            validationState={this.getValidationState('statusSelect')}
          >
            <Col sm={3}>
              <FormattedMessage id="form.input.title.status" defaultMessage='State'></FormattedMessage>
            </Col>
            <Col sm={9}>
              <FormControl
                componentClass="select"
                placeholder={"Seleccionar"}
                name="statusSelect"
                value={this.state.statusSelect}
                onChange={this.onChangeStatus}
              >
                {statusOptionTemplate}
              </FormControl>
              <ControlLabel>{this.getMessageValidation('statusSelect')} </ControlLabel>
            </Col>
          </FormGroup>
          {/* <FormGroup controlId="formHorizontalAuthenticationType"
           validationState={this.getValidationState('authenticationTypeCode')}
          >
            <Col sm={3}>
            <FormattedMessage id="user.form.input.authenticationType.title" defaultMessage=''></FormattedMessage>
            </Col>
            <Col sm={9}>
            <FormControl
                componentClass="select"
                placeholder="Seleccionar"
                name="authenticationTypeCode"
                value={this.state.authenticationTypeCode}
                onChange={this.onChangeAuthenticationType}
              >
                {authenticationTypeOptionTemplate}
              </FormControl>
              <ControlLabel>{this.getMessageValidation('authenticationTypeCode')} </ControlLabel>
            </Col>
        </FormGroup> */}
          {/* <FormGroup controlId="formHorizontalUserType"
           validationState={this.getValidationState('userTypeCode')}
          >
            <Col sm={3}>
            <FormattedMessage id="user.form.input.userType.title" defaultMessage=''></FormattedMessage>
            </Col>
            <Col sm={9}>
            <FormControl
                componentClass="select"
                placeholder="Seleccionar"
                name="userTypeCode"
                value={this.state.userTypeCode}
                onChange={this.onChangeUserType}
              >
                {userTypeOptionTemplate}
              </FormControl>
              <ControlLabel>{this.getMessageValidation('userTypeCode')} </ControlLabel>
            </Col>
          </FormGroup>
          <FormGroup controlId="formHorizontalProfile"
           validationState={this.getValidationState('profileCode')}
          >
            <Col sm={3}>
            <FormattedMessage id="user.form.input.profile.title" defaultMessage=''></FormattedMessage>
            </Col>
            <Col sm={9}>
            <FormControl
                componentClass="select"
                placeholder="Seleccionar"
                name="profileCode"
                value={this.state.profileCode}
                onChange={this.onChangeProfile}
              >
                {profileOptionTemplate}
              </FormControl>
              <ControlLabel>{this.getMessageValidation('userTypeCode')} </ControlLabel>
            </Col>
          </FormGroup> */}
          {/* <FormGroup controlId="formHorizontalConfirmPassword"
          validationState={this.getValidationState('confirmPassword')} >
         <Col sm={3}>
         <FormattedMessage id="user.form.input.confirmPassword.title" defaultMessage=''></FormattedMessage>
         </Col>
         <Col sm={9}>
              <FormControl
                type="password"
                placeholder={this.context.intl.formatMessage({id: 'user.form.input.confirmPassword.placeholder'})}
                name="confirmPassword"
                value={this.state.confirmPassword}
                onChange={this.onChange}
              />
              <ControlLabel>{this.getMessageValidation('confirmPassword')} </ControlLabel>
         </Col>
       </FormGroup> */}
          {/* <FormGroup controlId="formValidationCarriers"
          validationState={this.getValidationState('carriersValue')}
          >
            <Col sm={3}>
              <h5 className="carrier-title"><FormattedMessage id="user.form.input.operators.placeholder" 
              defaultMessage=''></FormattedMessage></h5>
            </Col>
            <Col sm={9}>
              <Select
                name="input-carrier"
                className={this.getValidationState('carriersValue') =='error'? 'carrier-error': "carrier" }
                closeOnSelect
                disabled={false}
                autosize={false}
                multi
                options={this.props.carriers}
                placeholder={<FormattedMessage id="combobox.first.register" defaultMessage=''></FormattedMessage>}
                removeSelected={false}
                rtl={false}
                simpleValue
                value={this.state.carriersValue}
                onChange={this.handleSelectCarrier}
              />
               <ControlLabel className="label-carrier" >{this.getMessageValidation('carriersValue')} </ControlLabel>
            </Col>
          </FormGroup> */}
          <FormGroup >
            <Col sm={3}>
            </Col>
            <Col sm={9}>
              <StyleButton currentStyles={this.props.currentStyles} bsStyle="primary"
                onClick={() => { this.save(); }}>
                Guardar
          </StyleButton>
              <Button onClick={() => { this.handleCancelEdit(); }}>
                Cancelar
            </Button>
            </Col>
          </FormGroup>
        </Form>
        <Modal
          show={this.state.isShowSearchLdap}
          onHide={this.handleHideSeachLdap}
          bsSize="large"
          container={this}
          aria-labelledby="contained-modal-title"
        >
          <Modal.Header closeButton>
            <Modal.Title id="contained-modal-title">
              Buscar Usuario LDAP
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <BootstrapTable
              striped
              condensed
              bordered
              data={this.state.allUsersLdap}
              search
              searchPlaceholder={this.context.intl.formatMessage({ id: 'table.button.search.placeholder' })}
              pagination
              options={this.options}
            >
              <TableHeaderColumn
                dataField="userName"
                isKey
                dataSort
                width="20%"
                dataAlign="left"
                headerAlign="left"
              >
                <FormattedMessage id="user.grid.header.userName" defaultMessage='Usuario'></FormattedMessage>
              </TableHeaderColumn>
              <TableHeaderColumn
                dataField="firstName"
                dataSort
                width="22%"
                dataAlign="left"
                headerAlign="left"
              >
                <FormattedMessage id="user.grid.header.firstName" defaultMessage='Nombre Completo'></FormattedMessage>
              </TableHeaderColumn>
              <TableHeaderColumn
                dataField="surname"
                dataSort
                width="25%"
                dataAlign="left"
                headerAlign="left">
                <FormattedMessage id="user.grid.header.lastName" defaultMessage=''></FormattedMessage>
              </TableHeaderColumn>
              <TableHeaderColumn
                dataField="email"
                dataSort
                width="25%"
                dataAlign="left"
                headerAlign="left">
                <FormattedMessage id="user.grid.header.email" defaultMessage='Email'></FormattedMessage>
              </TableHeaderColumn>
              <TableHeaderColumn
                dataFormat={this.editFormatter}
                dataSort
                width="5%"
                dataAlign="center"
                headerAlign="center"
              >
              </TableHeaderColumn>
            </BootstrapTable>
          </Modal.Body>
        </Modal>
      </StyledPanel>
    );
  }
}
const statusUser = [
  { code: 1, name: 'Activo', status: 'A' },
  { code: 2, name: 'Inactivo', status: 'I' },
];

LurinUserFormComponent.propTypes = {
  showForm: PropTypes.func,
  loadUsersLdap: PropTypes.func,
  isNewRegister: PropTypes.bool,
  userData: PropTypes.object,
  save: PropTypes.func,
};

const mapStateToProps = createStructuredSelector({
  loading: makeSelectLoading(),
  error: makeSelectError(),
  hasShowForm: makeSelectHasShowForm(),
  usersLDAP: makeSelectUsersLDAP(),
});

function mapDispatchToProps(dispatch) {
  return {
    showForm: (hasShowForm) => dispatch(showForm(hasShowForm)),
    loadUsersLdap: () => dispatch(loadUsersLdap()),
    initMessage:() => dispatch(initMessage())          
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinUserFormComponent);

