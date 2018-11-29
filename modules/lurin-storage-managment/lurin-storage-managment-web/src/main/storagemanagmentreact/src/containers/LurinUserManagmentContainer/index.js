/**
 *
 * LurinUserManagmentContainer
 *
 */

import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import {
  BootstrapTable,
  TableHeaderColumn, InsertButton
} from 'react-bootstrap-table';
import { FormattedMessage, intlShape  } from 'react-intl';
import {
  Glyphicon,
  Button,
  FormGroup,
  Col,
  FormControl
  ,Alert
} from 'react-bootstrap';

import LurinUserFormComponent from '../../components/LurinUserFormComponent';
import LurinModalComponent from '../../components/LurinModalComponent';
import {
  makeSelectUsersData,
  makeSelectUserCarrierData,
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
  makeSelectNewUser,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType
} from './selectors';

import {
  makeSelectCurrentStyle,
  makeSelectedAuthenticationTypes,
  makeSelectedUserTypes,
  makeSelectedRoles,
  makeSelectedProfiles,
  makeSelectedCarriers,
} from '../LurinFvAppContainer/selectors';

import {
  makeSelectOperatorsData
} from '../LurinOperatorsManagementContainer/selectors';


import * as actions from './actions';
import * as generalActions from '../LurinFvAppContainer/actions';
import * as operartorActions from '../LurinOperatorsManagementContainer/actions';

import {
  StyledPanel,
  StyleForm
} from './styles';
import { loadCarriers } from '../LurinFvAppContainer/actions';

export class LurinUserManagmentContainer extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      allUsers: [],
      isNewRegister: false,
      carrierSelected: 0,
      isShowForm: false,
      carriers:[],
      usersData: {
        code: 0,
        login: '',
        firstName: '',
        middleName: '',
        lastName: '',
        secondLastName: '',
        description: '',
        phone: '',
        email: '',
        passwordExpired: 'N',
        lastUpdatePass: null,
        lang: null,
        carrierId: 1,
        roleId: 1,
        profileId: "1",
        status: {
          code: '1',
          name: 'Activo',
          status: 'A'
        },
        companies: {
          code: window.currentCompanyId
        }
      },
      isMultiCarrier: (typeof this.props.isMultiCarrier === "undefined") ? true: false,
      alertVisible: false,
      alertStyle: "success",
      alertMessage: '',
      showModal:false,
    };  
    this.options = {
      defaultSortName: 'code',
      defaultSortOrder: 'desc',
      noDataText: <FormattedMessage id="table.nodata" defaultMessage='No se han encontrado registros para mostrar '></FormattedMessage>,
      handleConfirmDeleteRow: this.customConfirm,
      insertBtn: this.createCustomInsertButton,
      deleteText: <FormattedMessage id="table.button.delete" defaultMessage='Borrar'></FormattedMessage>,
      onDeleteRow: this.delete,
    };

    this.handleEdit = this.handleEdit.bind(this);
    this.handleNew = this.handleNew.bind(this);
    this.handleAlertDismiss = this.handleAlertDismiss.bind(this);
    this.onHideModal = this.onHideModal.bind(this);
    this.onConfirm = this.onConfirm.bind(this);
    this.closeModal = this.closeModal.bind(this);
  }

  static contextTypes = {
    intl: intlShape,
 }

  componentDidMount() {
  this.props.loadUsers(!this.state.isMultiCarrier);
  this.props.loadAuthenticationType();
  this.props.loadUserType();
//  this.props.loadRoles();
  this.props.loadProfiles();
  this.props.loadCarriers();
  this.props.loadUsersCarriers();
  this.props.loadOperators();
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.usersData.length > 0) {
      this.setState({ allUsers: nextProps.usersData });
    } else {
      this.setState({ allUsers: [] });
    }

    if (nextProps.hasShowForm !== this.props.hasShowForm) {
      this.setState({ isShowForm: nextProps.hasShowForm });
    }
    if (nextProps.showMessage !== this.props.showMessage &&  nextProps.showMessage  ) {
      this.setState({ alertMessage: nextProps.message,
               alertVisible:true, alertStyle: nextProps.messageType } );
    }
   // if (nextProps.carriers !== this.props.carriers && nextProps.carriers ) {
      let carriers=[];

      for(var i=0; i<nextProps.operators.length; i++ ){
        carriers[i]={label: nextProps.operators[i].name, value: nextProps.operators[i].code.toString()}
      }

      this.setState({ carriers});
    //}
  }

  handleEdit = (selectedUser) => {
    var currentUser= selectedUser;
    let carriersValueArray = [];

    let carriersFilter = this.props.userCarrierData.filter(userCarrier => userCarrier.userId == selectedUser.code);

    carriersFilter.forEach(function(userCarrier) {
        carriersValueArray.push(userCarrier.idCarrier);
    });

    currentUser.carriersValue = carriersValueArray.join();
    currentUser.profileId = currentUser.profile.profileId.toString();
    if (this.props.hasShowForm) {
      this.props.showForm(false);
      setTimeout(() => {
        this.props.showForm(true);
      }, 300);
    } else {
      this.props.showForm(true);
    }
    this.setState({
      isNewRegister: false,
      usersData: currentUser
    });
    this.props.initMessage();
  }

  customConfirm = (next, dropRowKeys) => {
    this.delete();
  }

  delete = () => {
    this.setState({ showModal: true });
  }

  onConfirm() {
    if (!this.state.usersData.code) return;
    this.props.deleteUsers(this.state.usersData.code);
    
    this.setState({ showModal: false });
  };

  createCustomInsertButton = () => (
    <InsertButton
    btnText={<FormattedMessage id="table.button.new" defaultMessage='Nuevo'></FormattedMessage>}
      onClick={() => this.handleNew()}
    />
  )

  editFormatter = (cell, row) => (
    <Button bsSize="xsmall" onClick={() => { this.handleEdit(row); }}><Glyphicon glyph="pencil" /></Button>
  );

 
  save = (currentState) => {
    let profileEntity=null;
    let userCarrier=[];
    let rolesUserCarrier = [];

    let carriersList = currentState.carriersValue.split(',');
    if(this.state.isMultiCarrier){
      profileEntity = this.props.profiles.find(profile => profile.profileId ===  parseInt(currentState.profileId));
      if (profileEntity)
        profileEntity.profileRoles.forEach(function(roles) {
            rolesUserCarrier.push({role:{code:roles.id.roleId}});
        });  
    }
    carriersList.forEach(function(carrierId) {
      userCarrier.push({carriers:{code: parseInt(carrierId)},userCarriersRoles:rolesUserCarrier});
    });

    if (this.state.isNewRegister) {

          const currentUser = {

            login: currentState.login,
            password: (!currentState.ldap)? currentState.password:null,
            firstName: currentState.firstName,
            middleName: currentState.middleName,
            lastName: currentState.lastName,
            secondLastName: currentState.secondLastName,
            email: currentState.email,
            passwordExpired: currentState.passwordExpired ? 'S':'N',
            status: currentState.status,
            companies: {
              code: window.currentCompanyId
            },
            profile: profileEntity || {},
            userCarriers: userCarrier
          };
          this.props.createUsers(currentUser,!this.state.isMultiCarrier);
        } else {
          const currentUser = {
            code: this.state.usersData.code,
            login: currentState.login,
            password: (!currentState.ldap)? currentState.password:null,
            firstName: currentState.firstName,
            middleName: currentState.middleName,
            lastName: currentState.lastName,
            secondLastName: currentState.secondLastName,
            email: currentState.email,
            passwordExpired: currentState.passwordExpired ? 'S':'N',
            status: currentState.status,
            companies: {
              code: window.currentCompanyId
            },
            profile: profileEntity || {},
            userCarriers: userCarrier
          };
          this.props.updateUsers(currentUser, !this.state.isMultiCarrier);
        }
  }

  onSelect = (data) => {
    this.setState({
      usersData: data,
    });
  }

  handleNew = () => {
    if (this.props.hasShowForm) {
      this.props.showForm(false);
      setTimeout(() => {
        this.props.showForm(true);
      }, 300);
    } else {
      this.props.showForm(true);
    }
    this.setState({
      isNewRegister: true,
      usersData: {
        code: 0,
        login: '',
        firstName: '',
        middleName: '',
        lastName: '',
        secondLastName: '',
        description: '',
        phone: '',
        email: '',
        passwordExpired: 'N',
        lastUpdatePass: null,
        lang: null,
        carrierId: 1,
        roleId: 1,
        profileId: '',
        status: {
          code: '1',
          name: 'Activo',
          status: 'A'
        },
        companies: {
          code: window.currentCompanyId
        },
        carriersValue:''
      },
    });
    this.props.initMessage();
  }
  
  handleAlertDismiss() {
    this.setState({ alertVisible: false });
  };

  onHideModal() {
    this.setState({ showModal: false });
  };


  closeModal() {
    this.setState({ showModal: false });
  };

  renderStorageForm() {
    if (this.state.isShowForm) {
      return (
        <LurinUserFormComponent
          isNewRegister={this.state.isNewRegister}
          usersData={this.state.usersData}
          save={this.save}
          currentStyles={this.props.currentStyles}
          authenticationTypes={this.props.authenticationTypes}
          userTypes={this.props.userTypes}
          roles={this.props.roles}
          profiles={this.props.profiles}
          carriers={this.state.carriers}
          userCarrierData={this.props.userCarrierData}
          isMultiCarrier={this.state.isMultiCarrier}
        />
      );
    }
    return '';
  }

  
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
    const selectRowProp = {
      mode: 'radio',
      clickToSelect: false,
      onSelect: this.onSelect,
    };
    
    return (
      <StyledPanel 
      currentStyles={this.props.currentStyles} 
      className="custom-primary-bg"  
      header={<FormattedMessage id="user.form.header" defaultMessage='Administrar Usuario'></FormattedMessage>} bsStyle="primary">
       {this.renderAlert()}
       <div className="modal-container">
            <LurinModalComponent
              showModal={this.state.showModal}
              onHideModal={this.onHideModal}
              title={this.context.intl.formatMessage({id: 'user.grid.modal.delete.title'})}
              description={this.context.intl.formatMessage({id: 'user.grid.modal.delete.description'})}
              onConfirm={this.onConfirm}
              closeModal={this.closeModal}
              currentStyles={this.props.currentStyles}
              />
       </div>
      
        <BootstrapTable
          striped
          condensed
          bordered
          data={this.state.allUsers}
          search
          searchPlaceholder={this.context.intl.formatMessage({id: 'table.button.search.placeholder'})}
          selectRow={selectRowProp}
          pagination
          insertRow
          deleteRow
          options={this.options}
        >
          <TableHeaderColumn
            dataField="code"
            isKey
            searchable={false}
            hidden
            width="5%"
            hiddenOnInsert
          >
            code
          </TableHeaderColumn>

          <TableHeaderColumn
            dataField="code"
            dataSort
            width="10%"
            dataAlign="left"
            headerAlign="left"
          >
           ID
          </TableHeaderColumn> 
          <TableHeaderColumn
            dataField="login"
            dataSort
            width="15%"
            dataAlign="left"
            headerAlign="left"
          >
          <FormattedMessage id="user.grid.header.userName" defaultMessage='Usuario'></FormattedMessage>
          </TableHeaderColumn>   
          <TableHeaderColumn
            dataField="firstName"
            dataSort
            width="20%"
            dataAlign="left"
            headerAlign="left"
          >
            <FormattedMessage id="user.grid.header.firstName" defaultMessage='Nombre Completo'></FormattedMessage>
          </TableHeaderColumn> 
          <TableHeaderColumn
            dataField="lastName"
            dataSort
            width="20%"
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
            width="12%"
            dataAlign="center"
            headerAlign="center"
          >
            <FormattedMessage id="user.grid.header.edit" defaultMessage='Editar'></FormattedMessage>
          </TableHeaderColumn>
        </BootstrapTable>
        <br />
        {this.renderStorageForm()}
      </StyledPanel>
    );
  }
}

LurinUserManagmentContainer.propTypes = {
  loadUsers: PropTypes.func,
  showForm: PropTypes.func,
  hasShowForm: PropTypes.bool,
  createUsers: PropTypes.func,
  updateUsers: PropTypes.func,
  isNewRegister: PropTypes.bool,
  deleteUsers: PropTypes.func,
  showMessage: PropTypes.bool,
  message:PropTypes.string,
  loadUsersCarriers: PropTypes.func,
};

const mapStateToProps = createStructuredSelector({
  loading: makeSelectLoading(),
  error: makeSelectError(),
  usersData: makeSelectUsersData(),
  userCarrierData: makeSelectUserCarrierData(),
  hasShowForm: makeSelectHasShowForm(),
  newUser: makeSelectNewUser(),
  showMessage:makeSelectShowMessage(),
  message:makeSelectMessage(),
  messageType:makeSelectMessageType(),
  currentStyles: makeSelectCurrentStyle(),
  authenticationTypes: makeSelectedAuthenticationTypes(),
  userTypes: makeSelectedUserTypes(),
  roles: makeSelectedRoles(),
  profiles: makeSelectedProfiles(),
  carriers: makeSelectedCarriers(),
  operators:makeSelectOperatorsData()
});

function mapDispatchToProps(dispatch) {
  return {
    loadUsers: (isAdminLocal) => dispatch(actions.loadUsers(isAdminLocal)),
    showForm: (hasShowForm) => dispatch(actions.showForm(hasShowForm)),
    createUsers: (newUser,isAdminLocal) => dispatch(actions.createUsers(newUser,isAdminLocal)),
    updateUsers: (userToUpdate,isAdminLocal) => dispatch(actions.updateUsers(userToUpdate,isAdminLocal)),
    deleteUsers: (code) => dispatch(actions.deleteUsers(code)),
    loadAuthenticationType: () => dispatch(generalActions.loadAuthenticationType()),
    loadUserType: () => dispatch(generalActions.loadUserType()),
    loadProfiles: () => dispatch(generalActions.loadProfiles()),
    loadCarriers: () => dispatch(generalActions.loadCarriers()),
    loadUsersCarriers: () => dispatch(actions.loadUsersCarriers()),
    loadOperators: () => dispatch(operartorActions.loadOperators()),
    initMessage: () => dispatch(actions.initMessage())
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinUserManagmentContainer);
