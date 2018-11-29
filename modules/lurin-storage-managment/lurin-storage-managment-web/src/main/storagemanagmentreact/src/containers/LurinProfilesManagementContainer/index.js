/**
 *
 * LurinStorageManagmentContainer
 *
 */

import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import { rolesActions } from '../../utils/rolesUtil';
import {
  BootstrapTable,
  TableHeaderColumn, InsertButton
} from 'react-bootstrap-table';
import {
  Glyphicon,
  Button,
  FormGroup,
  Col,
  FormControl
  ,Alert
} from 'react-bootstrap';
import { FormattedMessage, intlShape  } from 'react-intl';
import LurinProfilesManagementFormComponent from '../../components/LurinProfilesManagementFormComponent';
import LurinModalComponent from '../../components/LurinModalComponent';
import {
  makeSelectProfilesCategoriesData,
  makeSelectProfilesData,
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
  makeSelectNewProfiles,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType
} from './selectors';

import {
  makeSelectCurrentStyle,
  makeSelectedRoles
} from '../LurinFvAppContainer/selectors';

import * as actions from './actions';
import * as generalActions from '../LurinFvAppContainer/actions';

import {
  StyledPanel,
  StyleForm
} from './styles';
import { debug } from 'util';

export class LurinProfilesManagementContainer extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      allProfiles: [],
      isNewRegister: false,
      isShowForm: false,
      profilesData: {
        profileId: 0,
        name: '',
        description: '',
        profileRoles:[],
        checked:[],
        categoriesProfile:{
            categoriesProfileId: 0,
            name:''
        }
      },
      treeData:[],
      alertVisible: false,
      alertStyle: "success",
      alertMessage: '',
      showModal:false,
      profilesCategoriesData:[],
    };
    this.options = {
      defaultSortName: 'profileId',
      defaultSortOrder: 'desc',
      noDataText: <FormattedMessage id="table.nodata" defaultMessage='No se han encontrado registros para mostrar '></FormattedMessage>,
      handleConfirmDeleteRow: this.customConfirm,
      insertBtn: this.createCustomInsertButton,
      deleteText: <FormattedMessage id="table.button.delete" defaultMessage='Borrar'></FormattedMessage>,
      onDeleteRow: this.delete,
    };

    this.handleEditProfiles = this.handleEditProfiles.bind(this);
    this.handleNewProfileClick = this.handleNewProfileClick.bind(this);
    this.handleAlertDismiss = this.handleAlertDismiss.bind(this);
    this.onHideModal = this.onHideModal.bind(this);
    this.onConfirm = this.onConfirm.bind(this);
    this.closeModal = this.closeModal.bind(this);
  }
  static contextTypes = {
    intl: intlShape,
  }

  componentDidMount() {
  this.props.loadProfiles();
  this.props.loadProfilesCategories();
  this.props.loadRoles();
  }

  componentWillReceiveProps(nextProps) {



    if (nextProps.profilesCategoriesData.length > 0) {
          this.setState({ profilesCategoriesData: nextProps.profilesCategoriesData });
        } else {
          this.setState({ profilesCategoriesData: [] });
        }

    if (nextProps.profilesData.length > 0) {
      this.setState({ allProfiles: nextProps.profilesData });
    } else {
      this.setState({ allProfiles: [] });
    }

    if (nextProps.hasShowForm !== this.props.hasShowForm) {
      this.setState({ isShowForm: nextProps.hasShowForm });
    }
    if( nextProps.roles) {
      let roleArray=[];
      let children=[]
      nextProps.roles.forEach(role => {
        if(role.code>0){
            rolesActions.forEach(item => {
              children.push({ value: `${role.code}_${item.id}` , label: <FormattedMessage id={item.value} defaultMessage=''></FormattedMessage>,icon :null });
            }
          ); 
          roleArray.push( { value: role.code.toString(), label: role.dictionary.langAssigned,icon :null,children : children });
          children=[];
    }  
      });
      this.setState({ treeData: roleArray})
    }
  }

  handleEditProfiles = (selectProfile) => {

    if (this.props.hasShowForm) {
      this.props.showForm(false);
      setTimeout(() => {
        this.props.showForm(true);
      }, 300);
    } else {
      this.props.showForm(true);
    }
    let currentProfile=selectProfile;
    let checked=[];
    let currentAction={};
    selectProfile.profileRoles.forEach(role => {
       for (var property in role.id) {
         if(property!='roleId' && property!='profileId' && role.id[property]){
          currentAction = rolesActions.find(rolesAction => rolesAction.field===property);
          if(currentAction!=undefined){
            checked.push(`${role.id.roleId}_${currentAction.id}`);
          }
         }
      }
    });

    currentProfile.checked=checked;
    this.setState({
      isNewRegister: false,
      profilesData: selectProfile
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
    if (!this.state.profilesData.profileId) return;
     this.props.deleteProfiles(this.state.profilesData.profileId);
    this.setState({ showModal: false });
  };

  createCustomInsertButton = () => (
    <InsertButton
      btnText={<FormattedMessage id="table.button.new" defaultMessage='Nuevo'></FormattedMessage>}
      onClick={() => this.handleNewProfileClick()}
    />
  )

  editFormatter = (cell, row) => (
    <Button bsSize="xsmall" onClick={() => { this.handleEditProfiles(row); }}><Glyphicon glyph="pencil" /></Button>
  );



  save = (currentState) => {
    let rolesId=[];
    let roleId=0;
    let currentRoleId='';
    

    let currentAction={};
    currentState.checked.forEach(item=> {
      currentRoleId =item.split('_')[0]; 
      if(!rolesId.includes(currentRoleId))
        rolesId.push(currentRoleId)
     });
     var dataRole = new Array(rolesId.length);
     for (var i=0; i<rolesId.length;) {
      var currentProfileRole= {id :{ profileId :currentState.profileId, roleId:0 }};
      for (var property in rolesActions) {
        currentProfileRole.id[rolesActions[property].field]=false;
      }
      for (var chk in  currentState.checked) {
         if (currentState.checked[chk].split('_')[0] === rolesId[i]){
           
           currentAction = rolesActions.filter(function(action){
            return action.id===parseInt(currentState.checked[chk].split('_')[1]);
          });
          currentProfileRole.id.roleId = parseInt(rolesId[i]);
          currentProfileRole.id[currentAction[0].field] = true;
         }  
       }
       dataRole[i]=currentProfileRole;  
       i++;
     }

     dataRole.forEach(dataRoleId=> {
        dataRoleId.id.allowAll =true;
        let ctrlAllowFalse = 0;
        rolesActions.forEach(currentAction=> {
            if(!currentProfileRole.id[currentAction.field]){
            ctrlAllowFalse++;
            }
        });
        if(ctrlAllowFalse>0){
            dataRoleId.id.allowAll =false;
        }
     });

    if (this.state.isNewRegister) {
     
      const currentProfile = {
        profileId: 0,
        name: currentState.name,
        description : currentState.description,
        visible: true,
        categoriesProfile: currentState.category,
        profileRoles : dataRole
      };
      this.props.createProfiles(currentProfile);
    } else {
      const currentProfile = {
        profileId: this.state.profilesData.profileId,
        name: currentState.name,
        description: currentState.description,
        visible: true,
        categoriesProfile: currentState.category,
        profileRoles : dataRole
      };
      this.props.updateProfiles(currentProfile);
    }
  }
  
  onSelect = (data) => {
    this.setState({
      profilesData: data,
    });
  }

  handleNewProfileClick = () => {
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
      profilesData: {
        profileId: 0,
        name: '',
        description: '',
        profileRoles:[],
        checked:[],
        categoriesProfile: {}
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

  renderProfilesForm() {
    if (this.state.isShowForm) {
      return (
        <LurinProfilesManagementFormComponent
          isNewRegister={this.state.isNewRegister}
          profilesData={this.state.profilesData}
          profilesCategoriesData={this.state.profilesCategoriesData}
          save={this.save}
          currentStyles={this.props.currentStyles}
          roles={this.props.roles}
          treeData={this.state.treeData}
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
      <StyledPanel currentStyles={this.props.currentStyles} 
      className="custom-primary-bg"   
      header={<FormattedMessage id="profile.form.header" defaultMessage='Administrar Parámetro Global'></FormattedMessage>} bsStyle="primary">
       <div className="modal-container">
            <LurinModalComponent
              showModal={this.state.showModal}
              onHideModal={this.onHideModal}
              title={this.context.intl.formatMessage({id: 'profile.grid.modal.delete.title'})}
              description={this.context.intl.formatMessage({id: 'profile.grid.modal.delete.description'})}
              onConfirm={this.onConfirm}
              closeModal={this.closeModal}
               currentStyles={this.props.currentStyles}
              />
       </div>
      
        <BootstrapTable
          striped
          condensed
          bordered
          data={this.state.allProfiles}
          search
          searchPlaceholder={this.context.intl.formatMessage({id: 'table.button.search.placeholder'})}
          selectRow={selectRowProp}
          pagination
          insertRow
          deleteRow
          options={this.options}
        >
          <TableHeaderColumn
            dataField="profileId"
            isKey
            searchable={false}
            hidden
            width="15%"
            hiddenOnInsert
          >
            code
          </TableHeaderColumn>

          <TableHeaderColumn
            dataField="profileId"
            dataSort
            width="15%"
            dataAlign="left"
            headerAlign="left"
          >
           ID
          </TableHeaderColumn> 
          <TableHeaderColumn
            dataField="name"
            dataSort
            width="30%"
            dataAlign="left"
            headerAlign="left"
          >
            <FormattedMessage id="profile.grid.header.profile" defaultMessage='code'></FormattedMessage>
          </TableHeaderColumn>   
          <TableHeaderColumn
            dataField="description"
            dataSort
            width="30%"
            dataAlign="left"
            headerAlign="left"
          >
            <FormattedMessage id="profile.grid.header.description" defaultMessage='code'></FormattedMessage>
          </TableHeaderColumn>   
          <TableHeaderColumn
            dataFormat={this.editFormatter}
            dataSort
            width="15%"
            dataAlign="center"
            headerAlign="center"
          >
            <FormattedMessage id="profile.grid.header.edit" defaultMessage='code'></FormattedMessage>
          </TableHeaderColumn>
        </BootstrapTable>
        <br />
        {this.renderAlert()}
        {this.renderProfilesForm()}
      </StyledPanel>
    );
  }
}

LurinProfilesManagementContainer.propTypes = {
  loadProfiles: PropTypes.func,
  loadProfilesCategories: PropTypes.func,
  showForm: PropTypes.func,
  hasShowForm: PropTypes.bool,
  crateProfiles: PropTypes.func,
  updateProfiles: PropTypes.func,
  isNewRegister: PropTypes.bool,
  deleteProfiles: PropTypes.func,
  showMessage: PropTypes.bool,
  message:PropTypes.string
};

const mapStateToProps = createStructuredSelector({
  loading: makeSelectLoading(),
  error: makeSelectError(),
  profilesData: makeSelectProfilesData(),
  profilesCategoriesData: makeSelectProfilesCategoriesData(),
  hasShowForm: makeSelectHasShowForm(),
  newProfile: makeSelectNewProfiles(),
  showMessage:makeSelectShowMessage(),
  message:makeSelectMessage(),
  messageType:makeSelectMessageType(),
  currentStyles: makeSelectCurrentStyle(),
  roles: makeSelectedRoles()
});

function mapDispatchToProps(dispatch) {
  return {
    loadProfiles: () => dispatch(actions.loadProfiles()),
    loadProfilesCategories: () => dispatch(actions.loadProfilesCategories()),
    showForm: (hasShowForm) => dispatch(actions.showForm(hasShowForm)),
    createProfiles: (newProfile) => dispatch(actions.createProfiles(newProfile)),
    updateProfiles: (profileToUpdate) => dispatch(actions.updateProfiles(profileToUpdate)),
    deleteProfiles: (code) => dispatch(actions.deleteProfiles(code)),
    save: (profileData) => dispatch(actions.save(profileData)),
    loadRoles:() => dispatch(generalActions.loadRoles()),
    initMessage: () => dispatch(actions.initMessage())
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinProfilesManagementContainer);
