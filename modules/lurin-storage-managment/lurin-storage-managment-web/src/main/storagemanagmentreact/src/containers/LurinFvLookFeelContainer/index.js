/**
 *
 * LurinFvLookFeelContainer
 *
 */

import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import { normalize, schema, denormalize } from 'normalizr';
import {
  BootstrapTable,
  TableHeaderColumn, InsertButton
} from 'react-bootstrap-table';
import {
  Glyphicon,
  Button,
  FormGroup,
  Col,
  FormControl,
  Alert
} from 'react-bootstrap';
import { FormattedMessage, intlShape  } from 'react-intl';
import LurinFvLookFeelFormComponent from '../../components/LurinFvLookFeelFormComponent';
import LurinModalComponent from '../../components/LurinModalComponent';
import {
  makeSelectLookFeelsData,
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
  makeSelectNewLookFeel,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType,
} from './selectors';

import {
  makeSelectCurrentStyle,
  makeSelectedCurrentThemeData
} from '../LurinFvAppContainer/selectors';

import { CONTROL_TYPE, DEFAULT_THEME } from '../../utils/theme';

import * as actions from './actions';

import * as generalActions from '../LurinFvAppContainer/actions';

import {
  StyledPanel,
  StyleForm
} from './styles';
import { makeSelectCurrentThemeData } from '../LurinFvAppContainer/selectors';

export class LurinFvLookFeelContainer extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      allLookFeels: [],
      isNewRegister: false,
      isShowForm: false,
      lookFeelsData: {
        themeId: 0,
        name: '',
        description: '',
        details:[]
      },
      alertVisible: false,
      alertStyle: "success",
      alertMessage: '',
      showModal:false,
    };
    this.options = {
      defaultSortName: 'themeId',
      defaultSortOrder: 'desc',
      noDataText: <FormattedMessage id="table.nodata" defaultMessage='No se han encontrado registros para mostrar'></FormattedMessage>,
      handleConfirmDeleteRow: this.customConfirm,
      insertBtn: this.createCustomInsertButton,
      deleteText: <FormattedMessage id="table.button.delete" defaultMessage='Borrar'></FormattedMessage>,
      onDeleteRow: this.delete,
      
    };

    this.handleEdit = this.handleEdit.bind(this);
    this.handleNew = this.handleNew.bind(this);
    this.onHideModal = this.onHideModal.bind(this);
    this.onConfirm = this.onConfirm.bind(this);
    this.closeModal = this.closeModal.bind(this);
    this.handleAlertDismiss= this.handleAlertDismiss.bind(this);
  }

  static contextTypes = {
      intl: intlShape,
    }

  componentDidMount() {
  this.props.loadLookFeels();
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.lookFeelsData.length > 0) {
      this.setState({ allLookFeels: nextProps.lookFeelsData });
    } else {
      this.setState({ allLookFeels: [] });
    }

    if (nextProps.hasShowForm !== this.props.hasShowForm) {
      this.setState({ isShowForm: nextProps.hasShowForm });
    }
  }

  handleEdit = (selectedData) => {
    if (this.props.hasShowForm) {
      this.props.showForm(false);
      setTimeout(() => {
        this.props.showForm(true);
      }, 300);
    } else {
      this.props.showForm(true);
    }

    let currentDetail=[];
    for(let i=0; i<selectedData.details.length;){
      currentDetail.push(selectedData.details[i]);
      if(selectedData.details[i].type===CONTROL_TYPE.IMAGEURL){
        currentDetail[i]['message_'+currentDetail[i].name] = '';
      }
      i++;
    }
    let currentSelectedData= {
      themeId: selectedData.themeId,
      name: selectedData.name,
      description: selectedData.description,
      details: currentDetail
    };

    this.setState({
      isNewRegister: false,
      lookFeelsData: currentSelectedData,
    });
    this.props.initMessage();
  }

  customConfirm = (next, dropRowKeys) => {
    if (confirm(this.context.intl.formatMessage({id: 'lookFeel.grid.modal.delete.description'}))) {
      // If the confirmation is true, call the function that
      // continues the deletion of the record.
      next();
    }
  }

  createCustomInsertButton = () => (
    <InsertButton
      btnText={<FormattedMessage id="table.button.new" defaultMessage='Nuevo'></FormattedMessage>}
      onClick={() => this.handleNew()}
    />
  )

  editFormatter = (cell, row) => (
    <Button bsSize="xsmall" onClick={() => { this.handleEdit(row); }}><Glyphicon glyph="pencil" /></Button>
  );

  hasInvalidData(currentState) {
    if (!currentState.name) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El nombre es requerido."
      });
      return true;
    }
    if (currentState.name == DEFAULT_THEME) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "lookFeel.validation.update.defaultheme"
      });
      return true;
    }
    let details = Object.keys(currentState.details);
    let isInvalid = false;
    for (var i=0; i<details.length; i++){
      switch (currentState.details[details[i]].type) {
        case CONTROL_TYPE.IMAGEURL:
        if (!currentState.details[details[i]].value) {
          this.setState({
            alertVisible: true, alertStyle: "warning",
            alertMessage: `El campo ${currentState.details[details[i]].title} es requerid@.`
          });
          isInvalid=true;
          break;
        }
        default:
      }
      if(isInvalid){
        break;
      }
    }
    return isInvalid;
  }

  save = (currentState) => {
    if (this.hasInvalidData(currentState)) {
      return;
    }
    this.setState({
      alertVisible: false,
      alertMessage: ""
    });

    const details = new schema.Entity('details');
    const mySchema = { details: [ details ] }
    const entities = { details: currentState.details};
    const denormalizedData = denormalize({ details: Object.keys(currentState.details) }, mySchema, entities);
    const detailsProcess = this.proccessDetail(denormalizedData.details);
    if (this.state.isNewRegister) {
      const currentData = {
        name: currentState.name,
        description: currentState.description,
        details: detailsProcess
      };
      this.props.createLookFeels(currentData);
    } else {
      const currentData = {
        themeId: this.state.lookFeelsData.themeId,
        name: currentState.name,
        description: currentState.description,
        details:detailsProcess
      };
      this.props.updateLookFeels(currentData);
    }
  }

  proccessDetail= (data)=>{
    let dataProccess=[];
    const urlImg= window.currentApiUrl.replace("/api/rest/","");
    for (var i=0; i< data.length; i++){
      dataProccess[i]={
        name : data[i].name,
        title : data[i].title,
        type : data[i].type,
        value :  (CONTROL_TYPE.IMAGEURL==data[i].type ) ? data[i].value.replace(urlImg,'') :data[i].value,
        theme: 0
      };
    } 
   return dataProccess;
  }

  customConfirm = (next, dropRowKeys) => {
    this.delete();
  }

  delete = () => {
    this.setState({ showModal: true });
  }

  onConfirm() {
    if (!this.state.lookFeelsData.themeId) return;
     this.props.deleteLookFeels(this.state.lookFeelsData.themeId);
    this.setState({ showModal: false });
  };

  onSelect = (data) => {
    this.setState({
      lookFeelsData: data,
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
    let currentDetail=[];
    for(let i=0; i<this.props.currentTheme.details.length;){
      currentDetail.push(this.props.currentTheme.details[i]);
      if(this.props.currentTheme.details[i].type===CONTROL_TYPE.IMAGEURL){
        currentDetail[i].value='';
        currentDetail[i]['message_'+currentDetail[i].name] = '';
      }
      i++;
    }
    this.setState({
      isNewRegister: true,
      lookFeelsData: {
        themeId: 0,
        name: '',
        description: '',
        details: currentDetail
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

  renderForm() {
    if (this.state.isShowForm) {
      return (
        <LurinFvLookFeelFormComponent
          isNewRegister={this.state.isNewRegister}
          lookFeelsData={this.state.lookFeelsData}
          save={this.save}
          currentStyles={this.props.currentStyles}
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
      <StyledPanel currentStyles={this.props.currentStyles} className="custom-primary-bg"
      header={<FormattedMessage id="lookFeel.form.header" defaultMessage='Administrar Tema'></FormattedMessage>} bsStyle="primary">
      <div className="modal-container">
            <LurinModalComponent
              showModal={this.state.showModal}
              onHideModal={this.onHideModal}
              title={this.context.intl.formatMessage({id: 'lookFeel.grid.modal.delete.title'})}
              description={this.context.intl.formatMessage({id: 'lookFeel.grid.modal.delete.description'})}
              onConfirm={this.onConfirm}
              closeModal={this.closeModal}
              currentStyles={this.props.currentStyles}
              />
       </div>
        <BootstrapTable
          striped
          condensed
          bordered
          data={this.state.allLookFeels}
          search
          searchPlaceholder={this.context.intl.formatMessage({id: 'table.button.search.placeholder'})}
          selectRow={selectRowProp}
          pagination
          insertRow
          deleteRow 
          options={this.options}
        >
          <TableHeaderColumn
            dataField="themeId"
            isKey
            searchable={false}
            hidden
            width="10%"
            hiddenOnInsert
          >
            ID
          </TableHeaderColumn>

          <TableHeaderColumn
            dataField="name"
            dataSort
            width="25%"
            dataAlign="left"
            headerAlign="left"
          >
            <FormattedMessage id="lookFeel.grid.header.themeName" defaultMessage='Nombre Tema'></FormattedMessage>
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="description"
            dataSort
            width="50%"
            dataAlign="left"
            headerAlign="left"
          >
            <FormattedMessage id="lookFeel.grid.header.description" defaultMessage='Descripción'></FormattedMessage>
          </TableHeaderColumn>    
          <TableHeaderColumn
            dataFormat={this.editFormatter}
            dataSort
            width="15%"
            dataAlign="center"
            headerAlign="center"
          >
            <FormattedMessage id="lookFeel.grid.header.edit" defaultMessage='Editar'></FormattedMessage>
          </TableHeaderColumn>
        </BootstrapTable>
        <br />
        {this.renderAlert()}
        {this.renderForm()}
      </StyledPanel>
    );
  }
}

LurinFvLookFeelContainer.propTypes = {
  loadLookFeels: PropTypes.func,
  showForm: PropTypes.func,
  hasShowForm: PropTypes.bool,
  createLookFeels: PropTypes.func,
  updateLookFeels: PropTypes.func,
  isNewRegister: PropTypes.bool,
  deleteLookFeels: PropTypes.func
};

const mapStateToProps = createStructuredSelector({
  loading: makeSelectLoading(),
  error: makeSelectError(),
  lookFeelsData: makeSelectLookFeelsData(),
  hasShowForm: makeSelectHasShowForm(),
  newLookFeel: makeSelectNewLookFeel(),
  showMessage:makeSelectShowMessage(),
  message:makeSelectMessage(),
  messageType:makeSelectMessageType(),
  currentTheme:makeSelectedCurrentThemeData(),
  currentStyles: makeSelectCurrentStyle()
});

function newFunction() {
  ;
}

function mapDispatchToProps(dispatch) {
  return {
    loadLookFeels: () => dispatch(actions.loadLookFeels()),
    showForm: (hasShowForm) => dispatch(actions.showForm(hasShowForm)),
    createLookFeels: (newLookFeel) => dispatch(actions.createLookFeels(newLookFeel)),
    updateLookFeels: (lookFeelToUpdate) => dispatch(actions.updateLookFeels(lookFeelToUpdate)),
    deleteLookFeels: (code) => dispatch(actions.deleteLookFeels(code)),
    initMessage: () => dispatch(actions.initMessage())
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinFvLookFeelContainer);
