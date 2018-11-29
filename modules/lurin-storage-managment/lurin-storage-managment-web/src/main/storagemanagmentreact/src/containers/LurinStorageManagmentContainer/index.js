/**
 *
 * LurinStorageManagmentContainer
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
import {
  Glyphicon,
  Button,
  FormGroup,
  Col,
  FormControl
  ,Alert
} from 'react-bootstrap';
import { FormattedMessage, intlShape  } from 'react-intl';
import LurinStorageFormComponent from '../../components/LurinStorageFormComponent';
import LurinModalComponent from '../../components/LurinModalComponent';
import {
  makeSelectStoragesData,
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
  makeSelectNewStorage,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType,
  makeSelectSourceTypesData,
  makeSelectStatusData,
  makeSelectParmsData
} from './selectors';

import {
  makeSelectCurrentStyle
} from '../LurinFvAppContainer/selectors';

import * as actions from './actions';

import {
  StyledPanel,
  StyleForm
} from './styles';

export class LurinStorageManagmentContainer extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      allStorages: [],
      isNewRegister: false,
      carrierSelected: 0,
      isShowStorageForm: false,
      storagesData: {
        code: 0,
        jndiName: '',
        sourceTypeId: 0,
        statusId: 0,
        ip: '',
        port: '',
        sid: '',
        userName: '',
        password: '',
        status: {
          code: 1,
          name: 'ACTIVE'
        },
        sourceType: {
          sourceTypeId: 0,
          name: '',
          sourceType: "J"
        }
      },

      alertVisible: false,
      alertStyle: "success",
      alertMessage: '',
      showModal:false,
    };
    this.options = {
      defaultSortName: 'code',
      defaultSortOrder: 'desc',
      noDataText: <FormattedMessage id="table.nodata" defaultMessage='No se han encontrado registros para mostrar '></FormattedMessage>,
     // handleConfirmDeleteRow: this.customConfirm,
      insertBtn: this.createCustomInsertButton,
      deleteText: <FormattedMessage id="table.button.delete" defaultMessage='Borrar'></FormattedMessage>,
      onDeleteRow: this.delete,
    };

    this.handleEditStorage = this.handleEditStorage.bind(this);
    this.handleNewStorageClick = this.handleNewStorageClick.bind(this);
    this.handleAlertDismiss = this.handleAlertDismiss.bind(this);
    this.onHideModal = this.onHideModal.bind(this);
    this.onConfirm = this.onConfirm.bind(this);
    this.closeModal = this.closeModal.bind(this);
  }

  static contextTypes = {
    intl: intlShape,
  }

  componentDidMount() {
  this.props.loadStorages();
  this.props.loadSourceTypes();
  this.props.loadStatus();
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.storagesData.length > 0) {
      this.setState({ allStorages: nextProps.storagesData });
    } else {
      this.setState({ allStorages: [] });
    }

    if (nextProps.hasShowForm !== this.props.hasShowForm) {
      this.setState({ isShowStorageForm: nextProps.hasShowForm });
    }
  }

  handleEditStorage = (selectedStorage) => {
    if (this.props.hasShowForm) {
      this.props.showFormStorages(false);
      setTimeout(() => {
        this.props.showFormStorages(true);
        this.props.initTestDS();
      }, 300);
    } else {
      this.props.showFormStorages(true);
    }
    this.setState({
      isNewRegister: false,
      storagesData: selectedStorage
    });
    this.props.initMessage();
  }

  customConfirm = (next, dropRowKeys) => {
    if (confirm( this.context.intl.formatMessage({id: 'storageManagment.grid.modal.delete.description'}))) {
      // If the confirmation is true, call the function that
      // continues the deletion of the record.
      this.delete();
      next();
    }
  }

  delete = () => {
    this.setState({ showModal: true });
  }

  onConfirm() {
    if (!this.state.storagesData.code) return;
    this.props.deleteStorages(this.state.storagesData.code);
    
    this.setState({ showModal: false });
  };

  createCustomInsertButton = () => (
    <InsertButton
      btnText={<FormattedMessage id="table.button.new" defaultMessage='Nuevo'></FormattedMessage>}
      onClick={() => this.handleNewStorageClick()}
    />
  )

  editFormatter = (cell, row) => (
    <Button bsSize="xsmall" onClick={() => { this.handleEditStorage(row); }}><Glyphicon glyph="pencil" /></Button>
  );

  hasInvalidData(currentState) {
    if (!currentState.jndiName) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El nombre es requerido."
      });
      return true;
    }

    if (currentState.sourceTypeSelected == 0) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El Tipo de conexión es requerida."
      });
      return true;
    }
    if (!currentState.ip) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "La IP es requerida."
      });
      return true;
    }
    if (!currentState.port) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El puerto es requerido."
      });
      return true;
    }

    if (!currentState.sid) {
          this.setState({
            alertVisible: true, alertStyle: "warning",
            alertMessage: "El SID es requerido."
          });
          return true;
     }

     if (!currentState.userName) {
           this.setState({
             alertVisible: true, alertStyle: "warning",
             alertMessage: "El Nombre de usuario es requerido."
           });
           return true;
     }

     if (!currentState.password) {
           this.setState({
             alertVisible: true, alertStyle: "warning",
             alertMessage: "El password es requerido."
           });
           return true;
     }


    if (currentState.statusSelected == 0) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El estado es requerido."
      });
      return true;
    }
    return false
  }

  hasInvalidDataForTest(currentState) {
    if (!currentState.ip) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "La IP es requerida."
      });
      return true;
    }
    if (!currentState.port) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El puerto es requerido."
      });
      return true;
    }
    return false
  }


  save = (currentState) => {
    // if (this.hasInvalidData(currentState)) {
    //   return;
    // }
    if (this.state.isNewRegister) {
      const currenteStorage = {
        jndiName: currentState.jndiName,
        ip: currentState.ip,
        port: currentState.port,
        port2: currentState.port2,
        port3: currentState.port3,
        sid: currentState.sid,
        userName: currentState.userName,
        password: currentState.password,
        sourceType: currentState.sourceType,
        editable:'Y',
        status: currentState.status,
      };
      this.props.createStorages(currenteStorage);
    } else {
      const currenteStorage = {
        code: this.state.storagesData.code,
        jndiName: currentState.jndiName,
        ip: currentState.ip,
        port: currentState.port,
        port2: currentState.port2,
        port3: currentState.port3,
        sid: currentState.sid,
        userName: currentState.userName,
        password: currentState.password,
        sourceType: currentState.sourceType,
        editable:'Y',
        status: currentState.status,
      };
      this.props.updateStorages(currenteStorage);
    }
  }

  test = (currentState) => {
    // if (this.hasInvalidDataForTest(currentState)) {
    //   return;
    // }
    const currenteStorage = {
      jndiName: currentState.jndiName,
      ip: currentState.ip,
      port: currentState.port,
      port2: currentState.port2,
      port3: currentState.port3,
      sid: currentState.sid,
      userName: currentState.userName,
      password: currentState.password,
      sourceType: currentState.sourceType,
      editable:'Y',
      status: currentState.status,
    };
    this.props.sendTestDS(currenteStorage);
  }


  onSelect = (data) => {
    this.setState({
      storagesData: data,
    });
  }

  handleNewStorageClick = () => {
    if (this.props.hasShowForm) {
      this.props.showFormStorages(false);
      setTimeout(() => {
        this.props.showFormStorages(true);
      }, 300);
    } else {
      this.props.showFormStorages(true);
    }
    this.setState({
      isNewRegister: true,
      storagesData: {
        code: 0,
        jndiName: '',
        sid: '',
        userName: '',
        password: '',
        sourceType:{
          sourceTypeId:0,
        },
        status: {
          id:0
        },
        ip: undefined,
        port:undefined,
        port2:undefined,
        port3:undefined
      },
    });
    this.props.initMessage();
  }
  
  showSourceTypeDescription(cell, row) {
    return cell.name;
  }

  showStatusDescription(cell, row) {
    return cell.name;
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
    if (this.state.isShowStorageForm) {

      let sourceTypesData=[ { sourceTypeId:0,name:this.context.intl.formatMessage({id: 'combobox.first.register'})}];
      if(this.props.sourceTypesData){
        sourceTypesData=sourceTypesData.concat(this.props.sourceTypesData);
      }

      let statusData=[ { code:0,name:this.context.intl.formatMessage({id: 'combobox.first.register'})}];
      if(this.props.statusData){
        statusData=statusData.concat(this.props.statusData);
      }

      return (
        <LurinStorageFormComponent
          isNewRegister={this.state.isNewRegister}
          storagesData={this.state.storagesData}
          sourceTypesData={sourceTypesData}
          statusData={statusData}
          save={this.save}
          test={this.test}
          currentStyles={this.props.currentStyles}
        />
      );
    }

    return '';
  }

  renderAlert= () => {
    if(this.props.showMessage) {
      let messageParms =  this.props.message;
      if(this.props.message === 'CONNECTION_FAILED_MORE_THAN_ONE' ||  this.props.message === 'CONNECTION_FAILED_ONLY_ONE' ){
        messageParms = this.context.intl.formatMessage({id: this.props.message});
        messageParms = messageParms+this.props.parms.toString();
        return (
          <div>
            <Alert bsStyle={this.props.messageType} onDismiss={this.handleAlertDismiss}>
              <span>{messageParms}</span>
            </Alert>
            <br />
          </div>
        );
      }
      else{
        return (
          <div>
            <Alert bsStyle={this.props.messageType} onDismiss={this.handleAlertDismiss}>
              <span><FormattedMessage id= {messageParms} defaultMessage='Message'></FormattedMessage></span>
            </Alert>
            <br />
          </div>
        );
      }
     
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
      <StyledPanel currentStyles={this.props.currentStyles} className="custom-primary-bg"  
      header={<FormattedMessage id="storageManagment.form.header" defaultMessage='Administrar Almacenamiento'></FormattedMessage>} bsStyle="primary">
       <div className="modal-container">
            <LurinModalComponent
              showModal={this.state.showModal}
              onHideModal={this.onHideModal}
              title={this.context.intl.formatMessage({id: 'storageManagment.grid.modal.delete.title'})}
              description={this.context.intl.formatMessage({id: 'storageManagment.grid.modal.delete.description'})}
              onConfirm={this.onConfirm}
              closeModal={this.closeModal}
              currentStyles={this.props.currentStyles}
              />
       </div>
      
        <BootstrapTable
          striped
          condensed
          bordered
          data={this.state.allStorages}
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
            width="10%"
            hiddenOnInsert
          >
            code
          </TableHeaderColumn>

          <TableHeaderColumn
            dataField="code"
            dataSort
            width="8%"
            dataAlign="left"
            headerAlign="left"
          >
           ID
          </TableHeaderColumn> 
          <TableHeaderColumn
            dataField="jndiName"
            dataSort
            width="25%"
            dataAlign="left"
            headerAlign="left"
          >
            <FormattedMessage id="storageManagment.grid.header.conexionName" defaultMessage='Nombre Conexión'></FormattedMessage>
          </TableHeaderColumn>   
            <TableHeaderColumn
            dataField="sourceType"
            dataFormat={this.showSourceTypeDescription}
            width="20%"
            dataSort
            dataAlign="left"
            headerAlign="left"
          >
             <FormattedMessage id="storageManagment.grid.header.conexionType" defaultMessage='Tipo Conexión'></FormattedMessage>
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="status"
            dataFormat={this.showStatusDescription}
            width="20%"
            dataSort
            dataAlign="left"
            headerAlign="left"
          >
            <FormattedMessage id="storageManagment.grid.header.status" defaultMessage='Estado'></FormattedMessage>
          </TableHeaderColumn>
          <TableHeaderColumn
            dataFormat={this.editFormatter}
            dataSort
            width="15%"
            dataAlign="center"
            headerAlign="center"
          >
            <FormattedMessage id="storageManagment.grid.header.edit" defaultMessage='Editar'></FormattedMessage>
          </TableHeaderColumn>
        </BootstrapTable>
        <br />
        {this.renderStorageForm()}
        {this.renderAlert()}
      </StyledPanel>
    );
  }
}

LurinStorageManagmentContainer.propTypes = {
  loadStorages: PropTypes.func,
  showFormStorages: PropTypes.func,
  hasShowForm: PropTypes.bool,
  createStorages: PropTypes.func,
  updateStorages: PropTypes.func,
  isNewRegister: PropTypes.bool,
  deleteStorages: PropTypes.func,
  showMessage: PropTypes.bool,
  message:PropTypes.string
};

const mapStateToProps = createStructuredSelector({
  loading: makeSelectLoading(),
  error: makeSelectError(),
  storagesData: makeSelectStoragesData(),
  hasShowForm: makeSelectHasShowForm(),
  newStorage: makeSelectNewStorage(),
  showMessage:makeSelectShowMessage(),
  message:makeSelectMessage(),
  messageType:makeSelectMessageType(),
  sourceTypesData:makeSelectSourceTypesData(),
  statusData:makeSelectStatusData(),
  currentStyles: makeSelectCurrentStyle(),
  parms:makeSelectParmsData()
});

function mapDispatchToProps(dispatch) {
  return {
    loadStorages: () => dispatch(actions.loadStorages()),
    loadSourceTypes: () => dispatch(actions.loadSourceTypes()),
    loadStatus: () => dispatch(actions.loadStatus()),
    showFormStorages: (hasShowForm) => dispatch(actions.showFormStorages(hasShowForm)),
    createStorages: (newStorage) => dispatch(actions.createStorages(newStorage)),
    updateStorages: (storageToUpdate) => dispatch(actions.updateStorages(storageToUpdate)),
    deleteStorages: (code) => dispatch(actions.deleteStorages(code)),
    sendTestDS: (storageData) => dispatch(actions.sendTestDS(storageData)),
    initTestDS: () => dispatch(actions.initTestDS()),
    initMessage: () => dispatch(actions.initMessage())

  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinStorageManagmentContainer);
