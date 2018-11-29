/**
 *
 * LurinFvFreeviewManagmentContainer
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
  Alert,
  Button,
  Col,
  FormGroup,
  FormControl,
  Glyphicon,
  ControlLabel
} from 'react-bootstrap';

import { FormattedMessage, intlShape } from 'react-intl';

import LurinFvFreeviewFormComponent from '../../components/LurinFvFreeviewFormComponent';
import {
  makeSelectfreeviewsData,
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
  makeSelectNewFreeview,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType,
} from './selectors';
import {
  makeSelectPackagesData,
  makeSelectTechnologiesData,
  makeSelectCurrentStyle,
  makeSelectCurrentCarrier
 } from '../LurinFvAppContainer/selectors';

import * as actions from './actions';
import * as generalActions from '../LurinFvAppContainer/actions';

import {
  StyledPanel,
  StyleForm
} from './styles';
import { loadCarrierByCode } from '../LurinFvAppContainer/actions';

export class LurinFvFreeviewManagmentContainer extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      allFreevies: [],
      isNewRegister: false,
      isShowFreeviewForm: false,
      carrierSelected: 0,
      alertVisible: false,
      alertStyle: "success",
      alertMessage: "",
      freeviewsData: {
        freeViewId: 0,
        name: '',
        dateCreate: new Date(),
        userCreate: '',
        dateModify: null,
        userModify: null,
        status: {
          code: '1',
          name: 'ACTIVE',
          status: 'A'
        }
      },
      packages:[],
      pressNewButton:false
    };
    this.options = {
      defaultSortName: 'name',
      defaultSortOrder: 'desc',
      noDataText: <FormattedMessage id="table.nodata" defaultMessage='No se han encontrado registros para mostrar '></FormattedMessage>,
      handleConfirmDeleteRow: this.customConfirm,
      insertBtn: this.createCustomInsertButton,
      deleteText: <FormattedMessage id="table.button.delete" defaultMessage='Borrar'></FormattedMessage>,
      onDeleteRow: this.delete,
      searchPosition: 'bottom',
    };
    this.handleNewFreeviewClick = this.handleNewFreeviewClick.bind(this);
    this.handleAlertDismiss = this.handleAlertDismiss.bind(this);
    this.handleEditFreeview = this.handleEditFreeview.bind(this);
  }

    
  static contextTypes = {
    intl: intlShape,
 }

  componentDidMount() {
    if(window.currentCarriers.length == 1) {
      this.setState({ carrierSelected: window.currentCarriers[0].idCarrier });
      this.props.loadPackagesByCarrierId(window.currentCarriers[0].idCarrier)
      //this.props.loadFreeviews(window.currentCarriers[0].idCarrier);
      this.props.loadTechnology(window.currentCarriers[0].idCarrier);
    }    
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.freeviewsData.length > 0) {
      this.setState({ allFreevies: nextProps.freeviewsData });
    } else {
      this.setState({ allFreevies: [] });
    }

    if (nextProps.hasShowForm !== this.props.hasShowForm) {
      this.setState({ isShowFreeviewForm: nextProps.hasShowForm });
    }

    if (nextProps.packages !== this.props.packages && nextProps.packages ) {
      let packages=[];

      for(var i=0; i<nextProps.packages.length; i++ ){
        packages[i]={label: nextProps.packages[i].productName, value: nextProps.packages[i].code.toString()}
      }

      this.setState({ packages});
    }
  }

  handleEditFreeview = (selectedFreeview) => {

    var currentFreeview = selectedFreeview;
    var channel = selectedFreeview.channelPackages.map(function(elem){ return elem.code; }).join(",");
    currentFreeview.channelPackages = channel;

    if (this.props.hasShowForm) {
      this.props.showFormFreeviews(false);
      setTimeout(() => {  
        this.props.showFormFreeviews(true);
      }, 300);
    } else {
      this.props.showFormFreeviews(true);
    }
    this.setState({
      isNewRegister: false,
      freeviewsData: currentFreeview,
    });
    this.props.initMessage();
  }

  customConfirm = (next, dropRowKeys) => {
    if (confirm(this.context.intl.formatMessage({id: 'table.confirm.delete.message'}))) {
      // If the confirmation is true, call the function that
      // continues the deletion of the record.
      next();
    }
  }

  createCustomInsertButton = () => (
    <InsertButton
    btnText={<FormattedMessage id="table.button.new" defaultMessage='Nuevo'></FormattedMessage>}
      onClick={() => this.handleNewFreeviewClick()}
    />
  )

  editFormatter = (cell, row) => (
    <Button bsSize="xsmall" onClick={() => { this.handleEditFreeview(row); }}><Glyphicon glyph="pencil" /></Button>
  );


  hasInvalidData(currentState) {
    if (this.state.carrierSelected == 0) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: this.context.intl.formatMessage({id: 'freeview.form.input.operator.message.required'})
      });
      return true;
    }
    if (currentState.name.length == 0) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: this.context.intl.formatMessage({id: 'freeview.form.input.name.message.required'})
      });
      return true;
    }
    if (!currentState.packagesValue) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: this.context.intl.formatMessage({id: 'freeview.form.input.package.message.required'})
      });
      return true;
    }
    if (currentState.technologySelect=="0") {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: this.context.intl.formatMessage({id: 'freeview.form.input.technology.message.required'})
      });
      return true;
    }
    return false
  }

  save = (currentState) => {
    if (this.hasInvalidData(currentState)) {
      return;
    }
    let listPackage=[];
    if(currentState.packagesValue.includes(',')){
     let packageArray = currentState.packagesValue.split(',');
      packageArray.forEach(function(p) {
       listPackage.push({ code : parseInt(p) });
     });
    }
    else
    {
     listPackage.push({ code : parseInt(currentState.packagesValue) });
    }

    if (this.state.isNewRegister) {
      const currentFreeview = {
        name: currentState.name,
        status: currentState.status,
        userCreate: currentUserSession,
        //idCarrier: this.state.carrierSelected,
        categoryTechnologies: currentState.technology,
        channelPackages: listPackage
        //packagesId:[1]

      };
      this.props.createFreeviews(currentFreeview,this.state.carrierSelected);
    } else {
      const currentFreeview = {
        freeViewId: this.state.freeviewsData.freeViewId,
        name: currentState.name,
        status: currentState.status,
        dateCreate: this.state.freeviewsData.dateCreate,
        userCreate: this.state.freeviewsData.userCreate,
        //idCarrier: this.state.freeviewsData.idCarrier,
        userModify: currentUserSession,
        categoryTechnologies: currentState.technology,
        channelPackages: listPackage
        //packagesId:[1]
      };
    
      this.props.updateFreeviews(currentFreeview, this.state.carrierSelected);
    }
  }

  delete = () => {
    if (!this.state.freeviewsData.freeViewId) return;
    this.props.deleteFreeviews(this.state.freeviewsData.idCarrier, this.state.freeviewsData.freeViewId);
  }

  onSelect = (data) => {
    this.setState({
      freeviewsData: data,
    });
  }

  handleNewFreeviewClick = () => {
    this.setState({ pressNewButton: true });
    if (this.props.hasShowForm) {
      this.props.showFormFreeviews(false);
      setTimeout(() => {
        this.props.showFormFreeviews(true);
      }, 300);
    } else {
      this.props.showFormFreeviews(true);
    }
    this.setState({
      isNewRegister: true,
      freeviewsData: {
        freeViewId: 0,
        name: '',
        dateCreate: new Date(),
        userCreate: '',
        dateModify: null,
        userModify: null,
        status: {
          code: '1',
          name: 'ACTIVE',
          status: 'A'
        },
        categoryTechnologies: {
          code: '0',
          name: "CATV"
        }
      },
    });
    this.props.initMessage();
  }

  handleSelectCarrier = (evt) => {
    this.setState({ carrierSelected: evt.target.value, allFreevies: [] });
    this.props.showFormFreeviews(false);
    if(evt.target.value > 0) {
      this.props.loadFreeviews(evt.target.value);
      this.props.loadPackagesByCarrierId(evt.target.value);
      this.props.loadTechnology(evt.target.value );
      this.props.loadCarrierByCode(evt.target.value);
    }
  }

  handleAlertDismiss() {
    this.setState({ alertVisible: false });
  };

  showStatusDescription(cell, row) {
    return cell.name;
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

  getValidationState(field) {
    if((!this.state.carrierSelected) && this.state.pressNewButton){
      return 'error';
    }
    return null; 
  
  }
  getMessageValidation(field) {
    if((!this.state[field]  || this.state[field]==0 ) && this.state.pressNewButton){
      return 'Campo requerido';
    }
    return null; 
  }


  renderFreeviewForm() {
    if (this.state.isShowFreeviewForm) {
      return (
        <LurinFvFreeviewFormComponent
          isNewRegister={this.state.isNewRegister}
          freeviewsData={this.state.freeviewsData}
          packages={this.state.packages}
          technologies={this.props.technologies}
          save={this.save}
          currentStyles={this.props.currentStyles} 
          currentCarrier={this.props.currentCarrier} 
        />
      );
    }

    return '';
  }

  css: string = `.react-bs-table-tool-bar .col-xs-6.col-sm-6.col-md-6.col-lg-4 {
    margin-left: 1px;
    margin-top: 6px;
  }`

  render() {
    const selectRowProp = {
      mode: 'radio',
      clickToSelect: false,
      onSelect: this.onSelect,  
    };
    let carrierOptionTemplate = window.currentCarriers.map(carrier => (
      <option value={carrier.idCarrier}>{carrier.name}</option>
    ));

    return (
      <StyledPanel currentStyles={this.props.currentStyles} className="custom-primary-bg" header={<FormattedMessage id="freeview.form.header" defaultMessage='Administrar freeview'></FormattedMessage>} bsStyle="primary">
        <StyleForm>
          <FormGroup controlId="formHorizontalCarrier"
           validationState={this.getValidationState('carrierSelect')}
          >
            <Col sm={4} smOffset={8} xs={7} xsOffset={5}>
              <FormControl
                componentClass="select"
                placeholder="Seleccionar operadora"
                name="carrierSelect"
                value={this.state.carrierSelected}
                onChange={this.handleSelectCarrier}
                disabled={window.currentCarriers.length <= 1}
              >
                <option value={0} selected><FormattedMessage id="freeview.grid.operator.select.placeholder" defaultMessage='Seleccionar operadora'></FormattedMessage></option>
                {carrierOptionTemplate}
              </FormControl>
            </Col>
          </FormGroup>
        </StyleForm>
        <style>
            {this.css}
        </style>
        <BootstrapTable
          striped
          condensed
          bordered
          data={this.state.allFreevies}
          search
          searchPlaceholder={this.context.intl.formatMessage({id: 'table.button.search.placeholder'})}
          selectRow={selectRowProp}
          pagination
          insertRow
          deleteRow
          options={this.options}
        >
          <TableHeaderColumn
            dataField="freeViewId"
            isKey
            searchable={false}
            hidden
            hiddenOnInsert
          >
            freeViewId
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="name"
            dataSort
            width="40%"
            dataAlign="center"
            headerAlign="center"
          >
             <FormattedMessage id="freeview.grid.header.name" defaultMessage='Nombre'></FormattedMessage> 
             </TableHeaderColumn>
             <TableHeaderColumn
               dataField="status"
               dataFormat={this.showStatusDescription}
               dataSort
               width="30%"
               dataAlign="center"
               headerAlign="center"
             >
               <FormattedMessage id="freeview.grid.header.status" defaultMessage='Estado'></FormattedMessage>
             </TableHeaderColumn>
             <TableHeaderColumn
               dataFormat={this.editFormatter}
               width="20%"
               dataAlign="center"
               headerAlign="center"
             >
               <FormattedMessage id="freeview.grid.header.edit" defaultMessage='Editar'></FormattedMessage>
          </TableHeaderColumn>
        </BootstrapTable>
        <br />
        {this.renderAlert()}
        {this.renderFreeviewForm()}
      </StyledPanel>
    );
  }
}

LurinFvFreeviewManagmentContainer.propTypes = {
  loadFreeviews: PropTypes.func,
  showFormFreeviews: PropTypes.func,
  hasShowForm: PropTypes.bool,
  createFreeviews: PropTypes.func,
  updateFreeviews: PropTypes.func,
  isNewRegister: PropTypes.bool,
  deleteFreeviews: PropTypes.func
};

const mapStateToProps = createStructuredSelector({
  loading: makeSelectLoading(),
  error: makeSelectError(),
  freeviewsData: makeSelectfreeviewsData(),
  packages: makeSelectPackagesData(),
  hasShowForm: makeSelectHasShowForm(),
  newFreeview: makeSelectNewFreeview(),
  technologies: makeSelectTechnologiesData(),
  currentStyles: makeSelectCurrentStyle(),
  showMessage:makeSelectShowMessage(),
  message:makeSelectMessage(),
  messageType:makeSelectMessageType(),
  currentCarrier: makeSelectCurrentCarrier()
});

function mapDispatchToProps(dispatch) {
  return {
    loadFreeviews: (idCarrier) => dispatch(actions.loadFreeviews(idCarrier)),
    showFormFreeviews: (hasShowForm) => dispatch(actions.showFormFreeviews(hasShowForm)),
    createFreeviews: (newFreeview,idCarrier) => dispatch(actions.createFreeviews(newFreeview,idCarrier)),
    updateFreeviews: (freeviewToUpdate,idCarrier) => dispatch(actions.updateFreeviews(freeviewToUpdate,idCarrier)),
    deleteFreeviews: (idCarrier, freeviewId) => dispatch(actions.deleteFreeviews(idCarrier, freeviewId)),
    loadPackagesByCarrierId: (carrierId) => dispatch(generalActions.loadPackagesByCarrierId(carrierId)),
    loadTechnology: (carrierId) => dispatch(generalActions.loadTechnology(carrierId)),
    loadCarrierByCode: (code) => dispatch(generalActions.loadCarrierByCode(code)),
    initMessage: () => dispatch(actions.initMessage())
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinFvFreeviewManagmentContainer);
