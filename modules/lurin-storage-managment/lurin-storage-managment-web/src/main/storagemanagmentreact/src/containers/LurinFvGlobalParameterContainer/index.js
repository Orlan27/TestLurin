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
import LurinFvGlobalParameterFormComponent from '../../components/LurinFvGlobalParameterFormComponent';
import LurinModalComponent from '../../components/LurinModalComponent';
import {
  makeSelectGlobalParametersData,
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
  makeSelectNewGlobalParameter,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType,
  makeGPCategories
} from './selectors';

import {
  makeSelectCurrentStyle
} from '../LurinFvAppContainer/selectors';

import * as actions from './actions';

import {
  StyledPanel,
  StyleForm
} from './styles';

export class LurinFvGlobalParameterContainer extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      allGlobalParameters: [],
      isNewRegister: false,
      carrierSelected: 0,
      isShowGlobalParameterForm: false,
      globalParametersData: {
        code: 0,
        key: '',
        value: '',
        description: '',
        category: {
          code:'0'
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
      handleConfirmDeleteRow: this.customConfirm,
      insertBtn: this.createCustomInsertButton,
      deleteText: <FormattedMessage id="table.button.delete" defaultMessage='Borrar'></FormattedMessage>,
      onDeleteRow: this.delete,
    };

    this.handleEditGlobalParameter = this.handleEditGlobalParameter.bind(this);
    this.handleNewGlobalParameterClick = this.handleNewGlobalParameterClick.bind(this);
    this.onHideModal = this.onHideModal.bind(this);
    this.onConfirm = this.onConfirm.bind(this);
    this.closeModal = this.closeModal.bind(this);
    this.handleAlertDismiss= this.handleAlertDismiss.bind(this);
  }
  static contextTypes = {
    intl: intlShape,
  }

  componentDidMount() {
  this.props.loadGlobalParameters();
  this.props.loadGPCategories();
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.globalParametersData.length > 0) {
      this.setState({ allGlobalParameters: nextProps.globalParametersData });
    } else {
      this.setState({ allGlobalParameters: [] });
    }

    if (nextProps.hasShowForm !== this.props.hasShowForm) {
      this.setState({ isShowGlobalParameterForm: nextProps.hasShowForm });
    }
  }

  handleEditGlobalParameter = (selectedGlobalParameter) => {
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
      globalParametersData: selectedGlobalParameter,
    });
    this.props.initMessage();
  }

  customConfirm = (next, dropRowKeys) => {
    if (confirm( this.context.intl.formatMessage({id: 'globalParameter.grid.modal.delete.description'}))) {
      // If the confirmation is true, call the function that
      // continues the deletion of the record.
      next();
    }
  }

  createCustomInsertButton = () => (
    <InsertButton
      btnText={<FormattedMessage id="table.button.new" defaultMessage='Nuevo'></FormattedMessage>}
      onClick={() => this.handleNewGlobalParameterClick()}
    />
  )

  editFormatter = (cell, row) => (
    <Button bsSize="xsmall" onClick={() => { this.handleEditGlobalParameter(row); }}><Glyphicon glyph="pencil" /></Button>
  );

  hasInvalidData(currentState) {
    if (!currentState.key) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El campo Key es requerido."
      });
      return true;
    }
    if (!currentState.value) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El valor es requerido."
      });
      return true;
    }
    if (!currentState.description) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El campo descripción es requerido."
      });
      return true;
    }
    if (!currentState.categorySelected) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El campo categoria es requerido."
      });
      return true;
    }
    return false
  }

  save = (currentState) => {
    if (this.hasInvalidData(currentState)) {
      return;
    }
    this.setState({
      alertVisible: false,
      alertMessage: ""
    });
    if (this.state.isNewRegister) {
      const currenteGlobalParameter = {
        key: currentState.key,
        value: currentState.value,
        description: currentState.description,
        category: currentState.category
      };
      
      this.props.createGlobalParameters(currenteGlobalParameter);
    } else {
      const currenteGlobalParameter = {
        code: this.state.globalParametersData.code,
        key: currentState.key,
        value: currentState.value,
        description: currentState.description,
        category: currentState.category,
      };
     
      this.props.updateGlobalParameters(currenteGlobalParameter);
    }
  }

  customConfirm = (next, dropRowKeys) => {
    this.delete();
  }

  delete = () => {
    this.setState({ showModal: true });
  }

  onConfirm() {
    if (!this.state.globalParametersData.code) return;
     this.props.deleteGlobalParameters(this.state.globalParametersData.code);
    this.setState({ showModal: false });
  };

  onSelect = (data) => {
    this.setState({
      globalParametersData: data,
    });
  }

  handleNewGlobalParameterClick = () => {
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
      globalParametersData: {
        code: 0,
        key: '',
        value: '',
        description: '',
        category: {
          code:'0'
        }
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

  showCategoryDescription(cell, row) {
    return cell.name;
  }

  renderGlobalParameterForm() {
    if (this.state.isShowGlobalParameterForm) {
      return (
        <LurinFvGlobalParameterFormComponent
          isNewRegister={this.state.isNewRegister}
          globalParametersData={this.state.globalParametersData}
          save={this.save}
          currentStyles={this.props.currentStyles}
          gpCategories={this.props.gpCategories}
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
      header={<FormattedMessage id="globalParameter.form.header" defaultMessage='Administrar Parámetro Global'></FormattedMessage>} bsStyle="primary">
      <div className="modal-container">
            <LurinModalComponent
              showModal={this.state.showModal}
              onHideModal={this.onHideModal}
              title={this.context.intl.formatMessage({id: 'globalParameter.grid.modal.delete.title'})}
              description={this.context.intl.formatMessage({id: 'globalParameter.grid.modal.delete.title'})}
              onConfirm={this.onConfirm}
              closeModal={this.closeModal}
              currentStyles={this.props.currentStyles}
              />
       </div>
        <BootstrapTable
          striped
          condensed
          bordered
          data={this.state.allGlobalParameters}
          search
          searchPlaceholder={this.context.intl.formatMessage({id: 'table.button.search.placeholder'})}
          selectRow={selectRowProp}
          pagination
          insertRow={false}
          deleteRow={false}
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
            <FormattedMessage id="globalParameter.grid.header.code" defaultMessage='code'></FormattedMessage>
          </TableHeaderColumn>

          <TableHeaderColumn
            dataField="key"
            dataSort
            width="25%"
            dataAlign="left"
            headerAlign="left"
          >
            Key
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="value"
            dataSort
            width="25%"
            dataAlign="left"
            headerAlign="left"
          >
            <FormattedMessage id="globalParameter.grid.header.value" defaultMessage='code'></FormattedMessage>
          </TableHeaderColumn>  
          <TableHeaderColumn
            dataField="category"
            dataSort
            width="25%"
            dataFormat={this.showCategoryDescription}
            dataAlign="left"
            headerAlign="left"
          >
            <FormattedMessage id="globalParameter.grid.header.category" defaultMessage='code'></FormattedMessage>
          </TableHeaderColumn>    
          <TableHeaderColumn
            dataFormat={this.editFormatter}
            dataSort
            width="15%"
            dataAlign="center"
            headerAlign="center"
          >
            <FormattedMessage id="globalParameter.grid.header.edit" defaultMessage='code'></FormattedMessage>
          </TableHeaderColumn>
        </BootstrapTable>
        <br />
        {this.renderAlert()}
        {this.renderGlobalParameterForm()}
      </StyledPanel>
    );
  }
}

LurinFvGlobalParameterContainer.propTypes = {
  loadGlobalParameters: PropTypes.func,
  showForm: PropTypes.func,
  hasShowForm: PropTypes.bool,
  createGlobalParameters: PropTypes.func,
  updateGlobalParameters: PropTypes.func,
  isNewRegister: PropTypes.bool,
  deleteGlobalParameters: PropTypes.func
};

const mapStateToProps = createStructuredSelector({
  loading: makeSelectLoading(),
  error: makeSelectError(),
  globalParametersData: makeSelectGlobalParametersData(),
  hasShowForm: makeSelectHasShowForm(),
  newGlobalParameter: makeSelectNewGlobalParameter(),
  showMessage:makeSelectShowMessage(),
  message:makeSelectMessage(),
  messageType:makeSelectMessageType(),
  gpCategories:makeGPCategories(),
  currentStyles: makeSelectCurrentStyle()
});

function mapDispatchToProps(dispatch) {
  return {
    loadGlobalParameters: () => dispatch(actions.loadGlobalParameters()),
    showForm: (hasShowForm) => dispatch(actions.showForm(hasShowForm)),
    createGlobalParameters: (newGlobalParameter) => dispatch(actions.createGlobalParameters(newGlobalParameter)),
    updateGlobalParameters: (globalParameterToUpdate) => dispatch(actions.updateGlobalParameters(globalParameterToUpdate)),
    deleteGlobalParameters: (code) => dispatch(actions.deleteGlobalParameters(code)),
    loadGPCategories: () => dispatch(actions.loadGPCategories()),
    initMessage: () => dispatch(actions.initMessage())
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinFvGlobalParameterContainer);
