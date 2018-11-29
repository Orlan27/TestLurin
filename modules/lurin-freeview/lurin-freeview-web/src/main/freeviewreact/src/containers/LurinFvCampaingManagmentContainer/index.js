/**
 *
 * LurinFvCampaingManagmentContainer
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
  Alert,
  ControlLabel
} from 'react-bootstrap';
import { FormattedMessage, intlShape  } from 'react-intl';
import LurinFvCampaingFormComponent from '../../components/LurinFvCampaingFormComponent';
import LurinModalComponent from '../../components/LurinModalComponent';
import {
  makeSelectCampaingsData,
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
  makeSelectNewCampaing,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType,
} from './selectors';
import {
  makeSelectfreeviewsData
} from '../LurinFvFreeviewManagmentContainer/selectors';

import {
  makeSelectZonesData,
  makeSelectLoadTypesData,
  makeSelectMessagesData,
  makeSelectCampaignStatusData,
  makeSelectCurrentStyle,
  makeSelectCurrentCarrier
} from '../LurinFvAppContainer/selectors';

import * as actions from './actions';
import * as generalActions from '../LurinFvAppContainer/actions';
import * as freeviewActions from '../LurinFvFreeviewManagmentContainer/actions';


import {
  StyledPanel,
  StyleForm
} from './styles';

export class LurinFvCampaingManagmentContainer extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      allCampaings: [],
      isNewRegister: false,
      carrierSelected: 0,
      isShowCampaingForm: false,
      campaingsData: {
        CampaingId: 0,
        name: '',
        dateCreate: new Date(),
        userCreate: '',
        dateModify: null,
        userModify: null,
        catSchStatus: {
          code: '1',
          name: 'ACTIVE',
          status: 'A'
        }
      },
      alertVisible: false,
      alertStyle: "success",
      alertMessage: '',
      showModal:false,
      pressNewButton:false
    };
    this.options = {
      defaultSortName: 'campaingId',
      defaultSortOrder: 'desc',
      noDataText: <FormattedMessage id="table.nodata" defaultMessage='No se han encontrado registros para mostrar '></FormattedMessage>,
      handleConfirmDeleteRow: this.customConfirm,
      insertBtn: this.createCustomInsertButton,
      deleteText: <FormattedMessage id="table.button.delete" defaultMessage='Borrar'></FormattedMessage>,
      onDeleteRow: this.delete,
    };

    this.handleEditCampaing = this.handleEditCampaing.bind(this);
    this.handleNewCampaingClick = this.handleNewCampaingClick.bind(this);
    this.onHideModal = this.onHideModal.bind(this);
    this.onConfirm = this.onConfirm.bind(this);
    this.closeModal = this.closeModal.bind(this);
  }

  static contextTypes = {
    intl: intlShape,
 }

  componentDidMount() {

  this.props.loadMessages();
  this.props.loadCampaignStatus();
  if(this.state.carrierSelected > 0)
    this.props.loadCampaings(this.state.carrierSelected);
    this.props.loadFreeviews(this.state.carrierSelected);
    this.props.loadZones(this.state.carrierSelected);
    this.props.loadTechnology(this.state.carrierSelected);
    this.props.loadLoadTypes(this.state.carrierSelected);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.campaingsData.length > 0) {
      this.setState({ allCampaings: nextProps.campaingsData });
    } else {
      this.setState({ allCampaings: [] });
    }

    if (nextProps.hasShowForm !== this.props.hasShowForm) {
      this.setState({ isShowCampaingForm: nextProps.hasShowForm });
    }
  }

  handleEditCampaing = (selectedCampaing) => {
    if (this.props.hasShowForm) {
      this.props.showFormCampaings(false);
      setTimeout(() => {
        this.props.showFormCampaings(true);
      }, 300);
    } else {
      this.props.showFormCampaings(true);
    }
    this.setState({
      isNewRegister: false,
      campaingsData: selectedCampaing,
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
      onClick={() => this.handleNewCampaingClick()}
    />
  )

  editFormatter = (cell, row) => (
    <Button bsSize="xsmall" onClick={() => { this.handleEditCampaing(row); }}><Glyphicon glyph="pencil" /></Button>
  );

  hasInvalidData(currentState) {
    if (this.state.carrierSelected == 0) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: 'campaign.form.input.operator.message.required'
      });
      return true;
    }
    if (!currentState.name) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: 'campaign.form.input.name.message.required'
      });
      return true;
    }
    if (currentState.freeViewIdSelect == 0) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: 'campaign.form.input.freeview.message.required'
      });
      return true;
    }
    if (currentState.zoneCodeSelect == 0) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: 'campaign.form.input.zone.message.required'
      });
      return true;
    }
   
    if (currentState.loadTypeCodeSelect == 0) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: 'campaign.form.input.loadType.message.required'
      });
      return true;
    }
    if (!currentState.initDate) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: 'campaign.form.input.initDate.message.required'
      });
      return true;
    }
    if (!currentState.endDate) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: 'campaign.form.input.endDate.message.required'
      });
      return true;
    }
    if (currentState.validationSelect < 1) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: 'campaign.form.input.launchValidation.message.required'
      });
      return true;
    }
    if (currentState.statusCodeSelect == 0) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: 'campaign.form.input.state.message.required'
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
      var currentCampaing = new FormData();
      currentCampaing.append('name', currentState.name);
      currentCampaing.append('zoneId', currentState.zoneCodeSelect);
      currentCampaing.append('typeLoadId',currentState.loadTypeCodeSelect);
      currentCampaing.append('freeviewId',currentState.freeViewIdSelect);
      //currentCampaing.append('freeviewId',1);
      currentCampaing.append('dateIniSchedule', currentState.initDate.format('YYYYMMDDHHmmss'));
      currentCampaing.append('dateFinSchedule', currentState.endDate.format('YYYYMMDDHHmmss'));
      currentCampaing.append('file', currentState.file);
      currentCampaing.append('initialMessage', 0);
      currentCampaing.append('finalMessage', 0);
      currentCampaing.append('priority', currentState.priority ? 1 : 0);
      currentCampaing.append('validateBefore', currentState.validationSelect);
      currentCampaing.append('userCreate', currentUserSession);
      //currentCampaing.append('userCreate', 'TEST');
      currentCampaing.append('schStatusid', currentState.statusCodeSelect);
      currentCampaing.append('carrierId', this.state.carrierSelected);
      /*const currentCampaing = {
        name: currentState.name,
        zoneId: currentState.zoneCodeSelect,
        typeLoadId: currentState.loadTypeCodeSelect,
        dateIniSchedule: currentState.initDate.toISOString(),
        dateFinSchedule: currentState.endDate.toISOString(),
        file: currentState.file,
        initialMessage: currentState.initMessageSelect,
        finalMessage: currentState.endMessageSelect,
        priority: currentState.priority,
        validateBefore: currentState.validationSelect,
        userCreate:currentUserSession,
        //userCreate: -'test',
        schStatusid:currentState.statusCodeSelect,
        carrierId:this.state.carrierSelected
      };
      */
      this.props.createCampaings(currentCampaing, this.state.carrierSelected);
    } else {
     /* const currentCampaing = {
        campaingId: this.state.campaingsData.campaingId,
        zoneId: currentState.zoneCodeSelect,
        typeLoadId: currentState.loadTypeCodeSelect,
        dateIniSchedule: currentState.initDate.toISOString(),
        dateFinSchedule: currentState.endDate.toISOString(),
        file: currentState.file,
        initialMessage: currentState.initMessageSelect,
        finalMessage: currentState.endMessageSelect,
        priority: currentState.priority,
        validateBefore: currentState.validationSelect,
        userCreate:currentUserSession,
        schStatusid:currentState.statusCodeSelect,
        carrierId:this.state.carrierSelected
      };*/
      let currentCampaing = new FormData();
      currentCampaing.append('campaingId',this.state.campaingsData.campaingId);
      currentCampaing.append('name', currentState.name);
      currentCampaing.append('zoneId', currentState.zoneCodeSelect);
      currentCampaing.append('typeLoadId',currentState.loadTypeCodeSelect);
      currentCampaing.append('dateIniSchedule', currentState.initDate.toISOString());
      currentCampaing.append('dateFinSchedule', currentState.endDate.toISOString());
      currentCampaing.append('file', currentState.file);
      currentCampaing.append('initialMessage', undefined);
      currentCampaing.append('finalMessage', undefined);
      currentCampaing.append('priority', currentState.priority);
      currentCampaing.append('validateBefore', currentState.validationSelect);
      currentCampaing.append('userCreate', currentUserSession);
      //currentCampaing.append('userCreate', 'TEST');
      currentCampaing.append('schStatusid', currentState.statusCodeSelect);
      currentCampaing.append('carrierId', this.state.carrierSelected);
      this.props.updateCampaings(currentCampaing);
    }
  }

  customConfirm = (next, dropRowKeys) => {
    this.delete();
  }

  delete = () => {
    this.setState({ showModal: true });
  }

  onConfirm() {
    if (!this.state.campaingsData.campaingId) return;
     this.props.deleteCampaings(this.state.campaingsData.campaingId);
    this.setState({ showModal: false });
  };

  onSelect = (data) => {
    this.setState({
      campaingsData: data,
    });
  }

  handleNewCampaingClick = () => {
    this.setState({ pressNewButton: true });
    if (this.props.hasShowForm) {
      this.props.showFormCampaings(false);
      setTimeout(() => {
        this.props.showFormCampaings(true);
      }, 300);
    } else {
      this.props.showFormCampaings(true);
    }
    this.setState({
      isNewRegister: true,
      campaingsData: {
        campaingId: 0,
        typeLoad: {
          code: 0,
        },
        freeViewId: {
          freeViewId: 0,
        },
        commercialZone:{
          zone: 0,
        },
        catSchStatus:{
          code: 0,
        },
        messagesInitial:{
          code: 0,
        },
        messagesFinal:{
          code: 0,
        },
        priority:true,
        validateBefore:1,
        dateIniSchedule: '',
        dateFinSchedule: ''
      },
    });
    this.props.initMessage();
  }

  handleSelectCarrier = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value });
    this.props.showFormCampaings(false);
    this.props.loadFreeviews(evt.target.value );
    this.props.loadCampaings(evt.target.value );
    this.props.loadZones(evt.target.value );
    this.props.loadTechnology(evt.target.value );
    this.props.loadLoadTypes(evt.target.value);
    this.props.loadCarrierByCode(evt.target.value);
  }
  
  showFreeviewDescription(cell, row) {
    return cell.name;
  }

  showLoadTypeDescription(cell, row) {
    return cell.name;
  }

  showZoneDescription(cell, row) {
    return cell.commercialZone;
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

  renderCampaingForm() {
    if (this.state.isShowCampaingForm) {
      let freeviews=[ { freeViewId:0,name:this.context.intl.formatMessage({id: 'combobox.first.register'})}];
      if(this.props.freeviews){
        freeviews=freeviews.concat(this.props.freeviews);
      }
      return (
        <LurinFvCampaingFormComponent
          isNewRegister={this.state.isNewRegister}
          campaingsData={this.state.campaingsData}
          zones={this.props.zones}
          loadTypes={this.props.loadTypes}
          messages={this.props.messages}
          campaignStatus={this.props.campaignStatus}
          freeviews={freeviews}
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

  render() {
    const selectRowProp = {
      mode: 'radio',
      clickToSelect: false,
      onSelect: this.onSelect,
    };
    let carrierOptionTemplate = window.currentCarriers.map(carrier => (
    	  <option key={carrier.idCarrier} value={carrier.idCarrier}>{carrier.name}</option>
    ));

    return (
      <StyledPanel currentStyles={this.props.currentStyles} className="custom-primary-bg" 
       header={<FormattedMessage id="campaign.form.header" defaultMessage='Administrar freeview'></FormattedMessage>} bsStyle="primary">
      <div className="modal-container">
            <LurinModalComponent
              showModal={this.state.showModal}
              onHideModal={this.onHideModal}
              title={this.context.intl.formatMessage({id: 'campaign.modal.delete.title'})}
              description={this.context.intl.formatMessage({id: 'campaign.modal.delete.description'})}
              onConfirm={this.onConfirm}
              closeModal={this.closeModal}
              currentStyles={this.props.currentStyles}
              />
       </div>
      <StyleForm>
		      <FormGroup controlId="formHorizontalCarrier"
          validationState={this.getValidationState('carrierSelect')}
          >
		      <Col sm={4} smOffset={8} xs={7} xsOffset={5}>
		        <FormControl
		          componentClass="select"
		          placeholder="Seleccionar operadora"
		          name="carrierSelected"
		          value={this.state.carrierSelected}
		          onChange={this.handleSelectCarrier}
		          disabled={!window.currentCarriers.length}
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
          data={this.state.allCampaings}
          search
          searchPlaceholder={this.context.intl.formatMessage({id: 'table.button.search.placeholder'})}
          selectRow={selectRowProp}
          pagination
          insertRow
          deleteRow
          options={this.options}
        >
          <TableHeaderColumn
            dataField="campaingId"
            isKey
            searchable={false}
            hidden
            width="5%"
            hiddenOnInsert
          >
            CampaingId
          </TableHeaderColumn>

          <TableHeaderColumn
            dataField="campaingId"
            dataSort
            width="5%"
            dataAlign="center"
            headerAlign="center"
          >
           ID
          </TableHeaderColumn> 
          <TableHeaderColumn
            dataField="name"
            dataSort
            width="12%"
            dataAlign="center"
            headerAlign="center"
          >
            <FormattedMessage id="campaign.grid.header.name" defaultMessage='Nombre'></FormattedMessage>
          </TableHeaderColumn>   
            <TableHeaderColumn
            dataField="commercialZone"
            dataFormat={this.showZoneDescription}
            width="10%"
            dataSort
            dataAlign="center"
            headerAlign="center"
          >
             <FormattedMessage id="campaign.grid.header.zone" defaultMessage='Zona'></FormattedMessage>
          </TableHeaderColumn>
           <TableHeaderColumn
            dataField="typeLoad"
            dataFormat={this.showLoadTypeDescription}
            width="12%"
            dataSort
            dataAlign="center"
            headerAlign="center"
          >
             <FormattedMessage id="campaign.grid.header.type" defaultMessage='Tipo'></FormattedMessage>
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="dateIniSchedule"
            dataSort
            width="15%"
            dataAlign="center"
            headerAlign="center"
          >
            <FormattedMessage id="campaign.grid.header.initDate" defaultMessage='Fecha Inicio'></FormattedMessage>
          </TableHeaderColumn>  
          <TableHeaderColumn
            dataField="dateFinSchedule"
            dataSort
            width="15%"
            dataAlign="center"
            headerAlign="center"
          >
            <FormattedMessage id="campaign.grid.header.endDate" defaultMessage='Fecha Fin'></FormattedMessage>
          </TableHeaderColumn> 
          <TableHeaderColumn
            dataFormat={this.editFormatter}
            dataSort
            width="15%"
            dataAlign="center"
            headerAlign="center"
          >
            <FormattedMessage id="campaign.grid.header.edit" defaultMessage='Editar'></FormattedMessage>
          </TableHeaderColumn>
        </BootstrapTable>   
        <br />
        {this.renderAlert()}
        {this.renderCampaingForm()}
      </StyledPanel>
    );
  }
}

LurinFvCampaingManagmentContainer.propTypes = {
  loadCampaings: PropTypes.func,
  showFormCampaings: PropTypes.func,
  hasShowForm: PropTypes.bool,
  createCampaings: PropTypes.func,
  updateCampaings: PropTypes.func,
  isNewRegister: PropTypes.bool,
  deleteCampaings: PropTypes.func
};

const mapStateToProps = createStructuredSelector({
  loading: makeSelectLoading(),
  error: makeSelectError(),
  campaingsData: makeSelectCampaingsData(),
  hasShowForm: makeSelectHasShowForm(),
  newCampaing: makeSelectNewCampaing(),
  freeviews: makeSelectfreeviewsData(),
  zones: makeSelectZonesData(),
  loadTypes: makeSelectLoadTypesData(),
  messages: makeSelectMessagesData(),
  showMessage:makeSelectShowMessage(),
  message:makeSelectMessage(),
  messageType:makeSelectMessageType(),
  campaignStatus: makeSelectCampaignStatusData(),
  currentStyles: makeSelectCurrentStyle(),
  currentCarrier: makeSelectCurrentCarrier()
});

function mapDispatchToProps(dispatch) {
  return {
    loadCampaings: (carrierId) => dispatch(actions.loadCampaings(carrierId)),
    showFormCampaings: (hasShowForm) => dispatch(actions.showFormCampaings(hasShowForm)),
    createCampaings: (newCampaing, carrierId) => dispatch(actions.createCampaings(newCampaing, carrierId)),
    updateCampaings: (campaingToUpdate) => dispatch(actions.updateCampaings(campaingToUpdate)),
    deleteCampaings: (campaingId) => dispatch(actions.deleteCampaings(campaingId)),
    loadZones: (carrierId) => dispatch(generalActions.loadZones(carrierId)),
    loadLoadTypes: (carrierId) => dispatch(generalActions.loadLoadTypes(carrierId)),
    loadMessages: () => dispatch(generalActions.loadMessages()),
    loadCampaignStatus: () => dispatch(generalActions.loadCampaignStatus()),
    loadCampaignStatus: () => dispatch(generalActions.loadCampaignStatus()),
    loadFreeviews: (carrierId) => dispatch(freeviewActions.loadFreeviews(carrierId)),
    loadTechnology: (carrierId) => dispatch(generalActions.loadTechnology(carrierId)),
    loadCarrierByCode: (code) => dispatch(generalActions.loadCarrierByCode(code)),
    initMessage: () => dispatch(actions.initMessage())
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinFvCampaingManagmentContainer);
