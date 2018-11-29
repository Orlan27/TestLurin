/**
*
* LurinFvCampaingFormComponent
*
*/

import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import DatePicker from 'react-datepicker';
import Select from 'react-select';
import Toggle from 'react-toggle'
import moment from 'moment';
import {
  Form,
  FormControl,
  FormGroup,
  Col,
  ControlLabel,
  Button,
  FieldGroup
} from 'react-bootstrap';
import inputErrorMessage from '../../utils/inputErrorMessage';
import { FormattedMessage, intlShape  } from 'react-intl';
import {
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
} from '../../containers/LurinFvCampaingManagmentContainer/selectors';
import {
  showFormCampaings,
  initMessage
} from '../../containers/LurinFvCampaingManagmentContainer/actions';
import reducer from '../../containers/LurinFvCampaingManagmentContainer/reducer';

import {
  StyleButton,
  StyledPanel
} from './styles';


export class LurinFvCampaingFormComponent extends React.PureComponent {

  constructor(props) {  
    super(props);
    this.state = {
      title: (this.props.isNewRegister) ? <FormattedMessage id="campaign.form.add.title" defaultMessage='Agregar Nueva Campaña'></FormattedMessage> : 
      <FormattedMessage id="campaign.form.edit.title" defaultMessage='Editar Campaña'></FormattedMessage>,
      name:this.props.campaingsData.name,
      campaingId: this.props.campaingsData.campaingId,
      initDate: (this.props.campaingsData.dateIniSchedule ) ? new moment(this.props.campaingsData.dateIniSchedule ) :undefined ,
      endDate: (this.props.campaingsData.dateFinSchedule) ? new moment(this.props.campaingsData.dateFinSchedule ): undefined,
      file: this.props.campaingsData.file,
      zoneCodeSelect:this.props.campaingsData.commercialZone.code,
      loadTypeCodeSelect:this.props.campaingsData.typeLoad.code,
      statusCodeSelect:this.props.campaingsData.catSchStatus.code,
      freeViewIdSelect:this.props.campaingsData.freeViewId.freeViewId,
      //initMessageSelect:this.props.campaingsData.messagesInitial.code,
      initMessageSelect: 0,
      //endMessageSelect:this.props.campaingsData.messagesFinal.code,
      endMessageSelect:0,
      validationSelect:this.props.campaingsData.validateBefore,
      priority: this.props.campaingsData.priority,
      error: null,
      pressSaveButton:false,
      loading:true
    };
    this.save = this.save.bind(this);
    this.onChange = this.onChange.bind(this);
    this.handleCancelEditCampaign= this.handleCancelEditCampaign.bind(this);
  }
  static contextTypes = {
    intl: intlShape,
 }

 handleCancelEditCampaign = () => {
  this.props.showFormCampaings();
 this.props.initMessage();
}

  hasInvalidData() {
    if (!this.state.name) {
      return true;
    }
    if (!this.state.initDate) {
      return true;
    }
    if (!this.state.endDate) {
      return true;
    }
    if (this.state.freeViewIdSelect == 0) {
      return true;
    }
    if (this.state.zoneCodeSelect == 0) {
      return true;
    }
    if (this.state.loadTypeCodeSelect == 0) {
      return true;
    }
    if (!this.state.initDate) {
      return true;
    }
    if (!this.state.endDate) {
      return true;
    }
    if (this.state.validationSelect < 1 || !this.state.validationSelect  )  {
      return true;
    }
    if (this.state.statusCodeSelect == 0) {
      return true;
    }
    
    return false;
  }

  save = () => {
    this.setState({ pressSaveButton: true });
    if(!this.hasInvalidData())
      this.props.save(this.state);
  }
   
  onChange = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value });
  }

  onChangeFreeviews = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value});
  }

  onChangeInitMessage = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value});
  }

  onChangeEndMessage = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value});
  }

  onChangeZones = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value});
  }

  onChangeLoadType = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value });
  }

  onChangeStatus = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value });
  }
  onChangeFile = (evt) => {
	   /* var that=this;
	   var reader = new FileReader();
	    reader.onload = (function(theFile) {
	      return function(e) {
	     
	      var files=[];
	      files.push(e.currentTarget.result);
	      var bytes = new Base64(e.currentTarget.result);
	      console.log(bytes);
	      that.setState({file: bytes});
	      };
	    })(evt.target.files[0]);
	    reader.readAsBinaryString(evt.target.files[0]);*/
	    //this.setState({file: new Blob(['test payload'], { type: 'text/csv' })});
	  this.setState({file: evt.target.files[0], loading:false});
  }

  handleChangeInitDate= (value) => {
    this.setState({initDate:value});
  }

  handleChangeEndDate= (value) => {
    this.setState({endDate:value});
  }

  getValidationState(field) {
    if((!this.state[field]) && this.state.pressSaveButton){
      return 'error';
    }
    return null; 
  
  }
  getMessageValidation(field) {
    if((!this.state[field]  || this.state[field]==0 ) && this.state.pressSaveButton){
      return <FormattedMessage id="form.required.field" defaultMessage='Campo Requerido'></FormattedMessage>;
    }
    return null; 
  }

  getValidationNumber() {
    if(!this.state.validationSelect && this.state.pressSaveButton ){
      return 'error';
    }

    if(this.state.validationSelect< 1 && this.state.pressSaveButton ){
      return 'error';
    }
    return null; 
  }
  
  getValidationNumberMessage() {
    if(!this.state.validationSelect && this.state.pressSaveButton ){
      return <FormattedMessage id="form.required.field" defaultMessage='Campo Requerido'></FormattedMessage>;
    }

    if(this.state.validationSelect < 1 && this.state.pressSaveButton ){
      return <FormattedMessage id="campaign.form.input.launchValidation.validation.greaterThan" 
      defaultMessage='El campo debe ser mayor a 0'></FormattedMessage>;
    }
    return null; 
  }


  render() {
    
    let loadTypesOptionTemplate = this.props.loadTypes.map(loadType => (
      <option key={loadType.code} value={loadType.code}>{loadType.name}</option>
    ));

    let freeviewsOptionTemplate = this.props.freeviews.map(freeview => (
      <option key={freeview.freeViewId} value={freeview.freeViewId}>{freeview.name}</option>
    ));
     
    let zonesOptionTemplate = this.props.zones.map(zone => (
      <option key={zone.code} value={zone.zone}>{zone.commercialZone}</option>
    )); 

    let messagesOptionTemplate = this.props.messages.map(message => (
      <option key={message.code} value={message.code}>{message.name}</option>
    )); 

    let statusOptionTemplate = this.props.campaignStatus.map(sts => (
      <option key={sts.code} value={sts.code}>{sts.name}</option>
    )); 

    return (
      <StyledPanel currentStyles={this.props.currentStyles} header={this.state.title} bsStyle="primary">
        <Form horizontal>
          {/* <FormGroup>
        <Col smOffset={3} sm={9}>
          <label className="message" htmlFor="message"> {props.errorData.message}</label>
        </Col>
      </FormGroup>*/}
      <FormGroup controlId="formHorizontalName"
       validationState={this.getValidationState('name')} 
      >
         <Col sm={3}>
         <FormattedMessage id="campaign.form.input.name.title" defaultMessage='Nombre'></FormattedMessage>
         </Col>
         <Col sm={2}>
            <FormControl
                type="text"
                name="prefix"
                value={this.props.currentCarrier.namePrefix}
                maxLength={20}
                readOnly
              />
            </Col>
         <Col sm={7}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({id: 'campaign.form.input.name.placeholder'})}
                name="name"
                onChange={this.onChangeLoadType}
                value={this.state.name}
                maxLength={100}
              />
              <ControlLabel>{this.getMessageValidation('name')} </ControlLabel>
         </Col>
       </FormGroup>
         <FormGroup controlId="formHorizontalFreeview"
         validationState={this.getValidationState('freeViewIdSelect')} 
         >
         <Col sm={3}>
           Freeview
         </Col>
         <Col sm={9}>
           <FormControl
             componentClass="select"
             placeholder="- Seleccionar Freeview -"
             name="freeViewIdSelect"
             value={this.state.freeViewIdSelect}
             onChange={this.onChangeFreeviews}
           >
             {freeviewsOptionTemplate}
           </FormControl>
           <ControlLabel>{this.getMessageValidation('freeViewIdSelect')} </ControlLabel>
         </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalZone"
        validationState={this.getValidationState('zoneCodeSelect')} 
       >
         <Col sm={3}>
         <FormattedMessage id="campaign.form.input.zone.title" defaultMessage='Zona'></FormattedMessage>
         </Col>
         <Col sm={9}>
           <FormControl
             componentClass="select"
             placeholder="Seleccionar"
             name="zoneCodeSelect"
             value={this.state.zoneCodeSelect}
             onChange={this.onChangeZones}
           >
             {zonesOptionTemplate}
           </FormControl>
           <ControlLabel>{this.getMessageValidation('zoneCodeSelect')} </ControlLabel>
         </Col>
       </FormGroup>

       {/* <FormGroup controlId="formHorizontalInitMessage">
         <Col sm={3}>
           Mensaje Inicio
         </Col>
         <Col sm={9}>
           <FormControl
             componentClass="select"
             placeholder="Seleccionar"
             name="initMessageSelect"
             value={this.state.initMessageSelect}
             onChange={this.onChangeInitMessage}
           >
             {messagesOptionTemplate}
           </FormControl>
         </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalInitMessage">
         <Col sm={3}>
           Mensaje Fin
         </Col>
         <Col sm={9}>
           <FormControl
             componentClass="select"
             placeholder="Seleccionar"
             name="endMessageSelect"
             value={this.state.endMessageSelect}
             onChange={this.onChangeEndMessage}
           >
             {messagesOptionTemplate}
           </FormControl>
         </Col>
       </FormGroup> */}
          <FormGroup controlId="formHorizontalchargeType">
            <Col sm={3}>
            <FormattedMessage id="campaign.form.input.file.title" defaultMessage='Archivo'></FormattedMessage>
            </Col>
            <Col sm={9}>
            <input id="imageUpload" 
              type='file' label='Upload' accept='*.*'
              onChange={this.onChangeFile}
              />
            </Col>
          </FormGroup>
          <FormGroup controlId="formHorizontalchargeType"
           validationState={this.getValidationState('loadTypeCodeSelect')} 
          >
            <Col sm={3}>
            <FormattedMessage id="campaign.form.input.loadType.title" defaultMessage='Tipo de carga'></FormattedMessage>
            </Col>
            <Col sm={9}>
              <FormControl
                componentClass="select"
                placeholder="Seleccionar"
                name="loadTypeCodeSelect"
                value={this.state.loadTypeCodeSelect}
                onChange={this.onChangeLoadType}
              >
                {loadTypesOptionTemplate}
              </FormControl>
              <ControlLabel>{this.getMessageValidation('loadTypeCodeSelect')} </ControlLabel>
            </Col>
          </FormGroup>
          <FormGroup controlId="formHorizontalInitDate"
           validationState={this.getValidationState('initDate')} 
          >
            <Col sm={3}>
            <FormattedMessage id="campaign.form.input.initDate.title" defaultMessage='Fecha Inicio'></FormattedMessage>
            </Col>
            <Col sm={9}>
            <DatePicker
              className={this.getValidationState('initDate') =='error'? 'datepicker-error': "" }
                dateFormat="MM/DD/YYYY"
                selected={this.state.initDate}
                selectsStart
                startDate={this.state.initDate}
                endDate={this.state.endDate}
                readOnly
                onChange={this.handleChangeInitDate}
            />
            <ControlLabel>{this.getMessageValidation('initDate')} </ControlLabel>
            </Col>
          </FormGroup>
          <FormGroup controlId="formHorizontalEndDate"
           validationState={this.getValidationState('endDate')} 
          >
            <Col sm={3}>
            <FormattedMessage id="campaign.form.input.endDate.title" defaultMessage='Fecha Fin'></FormattedMessage>
            </Col>
            <Col sm={9}>
            <DatePicker
                dateFormat="MM/DD/YYYY"
                selected={this.state.endDate}
                className={this.getValidationState('endDate') =='error'? 'datepicker-error': "" }
                selectsEnd
                readOnly
                startDate={this.state.initDate}
                endDate={this.state.endDate}
                onChange={this.handleChangeEndDate}
            />
            <ControlLabel>{this.getMessageValidation('endDate')} </ControlLabel>
            </Col>
          </FormGroup>
          <FormGroup controlId="formHorizontalPriority"
          >
            <Col sm={3}>
            <FormattedMessage id="campaign.form.input.overlapPriority.title" defaultMessage='Prioridad'></FormattedMessage>
            </Col>
            <Col sm={9}>
            <Toggle
            defaultChecked={this.state.priority}
            name='priority'
            value='true' />
            </Col>
          </FormGroup>
          <FormGroup controlId="formHorizontalchargeType"
            validationState={this.getValidationNumber()}
          >
            <Col sm={3}>
            <FormattedMessage id="campaign.form.input.launchValidation.title" defaultMessage='Lanzar'></FormattedMessage>
            </Col>
            <Col sm={9}>
            <FormControl
                type="number"  
                min="1" 
                max="1000"
                placeholder="Lanzar validación (Días)"
                name="validationSelect"
                value={this.state.validationSelect}
                onChange={this.onChange}
              />
              <ControlLabel style={{    marginTop: '4px', display: (this.getValidationNumber() ? 'block':'none')}}>{this.getValidationNumberMessage()} </ControlLabel>
              <p className="info"><FormattedMessage id="campaign.form.input.launchValidation.message.info" defaultMessage='Lanzar'></FormattedMessage></p>
            </Col>
          </FormGroup>
          <FormGroup controlId="formHorizontalStatus"
          validationState={this.getValidationState('statusCodeSelect')} 
          >
            <Col sm={3}>
            <FormattedMessage id="campaign.form.input.state.title" defaultMessage='Estado'></FormattedMessage>
            </Col>
            <Col sm={9}>
              <FormControl
                componentClass="select"
                placeholder="Seleccionar"
                name="statusCodeSelect"
                value={this.state.statusCodeSelect}
                onChange={this.onChangeStatus}
              >
                {statusOptionTemplate}
              </FormControl>
              <ControlLabel>{this.getMessageValidation('statusCodeSelect')} </ControlLabel>
            </Col>
          </FormGroup>
          <FormGroup >
       <Col sm={3}>
        
         </Col>
         <Col sm={9}>
         <StyleButton currentStyles={this.props.currentStyles} bsStyle="primary" 
               onClick={ () => { this.save(); }}>
                <FormattedMessage id="form.button.save" defaultMessage='Save'></FormattedMessage>
          </StyleButton>

          <Button onClick={() => { this.handleCancelEditCampaign(); }}>
          <FormattedMessage id="form.button.cancel" defaultMessage='Cancelar'></FormattedMessage>
        </Button>
         </Col>
            </FormGroup>
        </Form>
      </StyledPanel>
    );
  }
}

LurinFvCampaingFormComponent.propTypes = {
  showFormCampaings: PropTypes.func,
  isNewRegister: PropTypes.bool,
  campaingsData: PropTypes.object,
  save: PropTypes.func,

};

const mapStateToProps = createStructuredSelector({
  loading: makeSelectLoading(),
  error: makeSelectError(),
  hasShowForm: makeSelectHasShowForm(),
});

function mapDispatchToProps(dispatch) {
  return {
    showFormCampaings: (hasShowForm) => dispatch(showFormCampaings(hasShowForm)),
    initMessage:() => dispatch(initMessage())    
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinFvCampaingFormComponent);
