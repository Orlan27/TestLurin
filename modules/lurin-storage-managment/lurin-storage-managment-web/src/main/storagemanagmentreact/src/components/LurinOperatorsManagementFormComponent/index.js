/**
*
* LurinOperatorsManagementFormComponent
*
*/

import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import Select from 'react-select';
import DatePicker from 'react-datepicker';
import TimePicker from 'react-bootstrap-time-picker';
import moment from 'moment';
import {
  Form,
  FormControl,
  FormGroup,
  Col,
  ControlLabel,
  Button,
  FieldGroup,
} from 'react-bootstrap';
import inputErrorMessage from '../../utils/inputErrorMessage';
import convertTimePickerValueToString from '../../utils/timeUtils';
import { FormattedMessage, intlShape  } from 'react-intl';

import {
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
} from '../../containers/LurinOperatorsManagementContainer/selectors';
import {
  showForm,
  initMessage
} from '../../containers/LurinOperatorsManagementContainer/actions';
import reducer from '../../containers/LurinOperatorsManagementContainer/reducer';

import {
  StyleButton,
  StyledPanel
} from './styles';
import { debug } from 'util';


export class LurinOperatorsManagementFormComponent extends React.PureComponent {

  constructor(props) {  
    super(props);
    this.state = {
      title: (this.props.isNewRegister) ? <FormattedMessage id="operatorsManagment.form.add.title" 
        defaultMessage='Agregar nueva operadora'></FormattedMessage> : 
        <FormattedMessage id="operatorsManagment.form.edit.title" 
        defaultMessage='Editar operadora'></FormattedMessage>,
      country: this.props.operatorsData.countryId,
      countrySelected: this.props.operatorsData.countryId.code,
      name:this.props.operatorsData.name,
      startTime: (this.props.operatorsData.startTime ) ? this.props.operatorsData.startTime : "00:00",
      endTime: (this.props.operatorsData.endTime) ? this.props.operatorsData.endTime : "00:00",
      percentageShare:this.props.operatorsData.percentageShare,
      maxDailyRate:this.props.operatorsData.maxDailyRate,
      tables: this.props.operatorsData.tables,
      tableSelected: this.props.operatorsData.tables.tableId,
      jndiSelected:  this.getJndiByOperator(this.props.operatorsData.dataSources, 1),
      jndi: this.getJndiByOperator(this.props.operatorsData.dataSources, 0),
      jndiData: this.getJndiByOperatorData(this.props.operatorsData.dataSources, 1),
      casSelected: this.getCasByOperator(this.props.operatorsData.dataSources, 1),
      cas:  this.getCasByOperator(this.props.operatorsData.dataSources, 0),
      casDataEdit:  this.getCasByOperatorData(this.props.operatorsData.dataSources, 0),
      timeZone: this.props.operatorsData.utc,
      namePrefix: this.props.operatorsData.namePrefix,
      techSelected: this.props.operatorsData.categoryTechnologies,
      company: this.props.operatorsData.companies,
      companySelected: this.props.operatorsData.companies.code,
      operatorsStatus: this.props.operatorsData.status,
      operatorsStatusSelected: this.props.operatorsData.status.code,
      lookFeels: this.props.operatorsData.themes,
      lookFeelsSelected: this.props.operatorsData.themes.themeId,
      login: (!this.props.isNewRegister) ? (this.props.operatorsData.adminUser ?  this.props.operatorsData.adminUser.login:null): `${this.props.prefixAdminValue}` ,
      firstName: this.props.operatorsData.adminUser ?  this.props.operatorsData.adminUser.firstName:null,
      middleName:  this.props.operatorsData.adminUser ?  this.props.operatorsData.adminUser.middleName:null,
      lastName:  this.props.operatorsData.adminUser ?  this.props.operatorsData.adminUser.lastName:null,
      email:  this.props.operatorsData.adminUser ? this.props.operatorsData.adminUser.email:null,
      secondLastName:  this.props.operatorsData.adminUser ? this.props.operatorsData.adminUser.secondLastName:null,
      error: null,
      pressSaveButton:false,
      externalOperatorId: this.props.operatorsData.externalOperatorId ?  this.props.operatorsData.externalOperatorId:null,
      readOnly:(!this.props.isNewRegister && window.currentCarriers.length< 2)
     // startHour: 0,
      //endhour: 0
    };
    this.handleCancelEditOperators = this.handleCancelEditOperators.bind(this);
    this.onChange = this.onChange.bind(this);
    this.onChangeDataSource = this.onChangeDataSource.bind(this);
    this.onChangeCountry = this.onChangeCountry.bind(this);
    this.onChangeCompany = this.onChangeCompany.bind(this);
    this.onChangeTable = this.onChangeTable.bind(this);
    this.handleChangeStartTime = this.handleChangeStartTime.bind(this);
    this.handleChangeEndTime = this.handleChangeEndTime.bind(this);
    this.save = this.save.bind(this);
    this.onChangeOperatorStatus = this.onChangeOperatorStatus.bind(this);
  }


  getJndiByOperator = (dataSources, type) => {
    
        let data =  dataSources.find(function(data){
            return data.sourceType.sourceType==='J'
        });
        if(data!=undefined)
          return (type==0 ? data: data.code ); 
        else
          return (type==0 ? {}: 0 ); 
      }
  getCasByOperator = (dataSources, type) => {

        let data =  dataSources.find(function(data){
            return data.sourceType.sourceType==='T'
        });
        if(data!=undefined)
          return (type==0 ? data: data.code );
        else
          return (type==0 ? {}: 0 );
      }

  getJndiByOperatorData = (dataSources, type) => {

      let data =  dataSources.find(function(data){
          return data.sourceType.sourceType==='J'
      });
      return data;
    }
   getCasByOperatorData = (dataSources, type) => {

      let data =  dataSources.find(function(data){
          return data.sourceType.sourceType==='T'
      });
      return data;
    }
        
    

  static contextTypes = {
    intl: intlShape,
  }

  hasInvalidData() {
    
    if (!this.state.name) {
      return true;
    }
    if (this.state.countrySelected == 0) {
      return true;
    }
    if (!this.state.startTime) {
      return true;
    }
    if (!this.state.endTime) {
      return true;
    }
    if (!this.state.percentageShare) {
      return true;
    }
    if (this.state.percentageShare<1  || this.state.percentageShare>100  ) {
      return true;
    }
    if (!this.state.maxDailyRate) {
      return true;
    }
    if (this.state.maxDailyRate==0  ) {
      return true;
    }
    // if (this.state.companySelected == 0) {
    //   return true;
    // }
    if (this.state.operatorStatusSelected == 0) {
      return true;
    }
    if (!this.state.login) {
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
    if(!this.state.email.toString().match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/g) ){
      return true;
    }
   
    /*if (this.state.initialDataSourceIdSelected == 0) {
      return true;
    }*/

    if (this.state.jndiSelected == 0) {
      return true;
    }
    if (this.state.casSelected == 0) {
      return true;
    }
    if (!this.state.techSelected.length) {
      return true;
    }
   
    if (this.state.lookFeelsSelected == 0) {
      return true;
    }
    if(!this.state.timeZone ){
      return true;
    }
    if (this.state.timeZone<-12 || this.state.timeZone>12 ) {
      return true;
    }
    if (!this.state.namePrefix) {
      return true;
    }
    return false
  } 

  save = () => {
    this.setState({ pressSaveButton: true });
    if(!this.hasInvalidData())
      this.props.save(this.state);
  }
 
  handleCancelEditOperators = () => {
   this.props.showForm(false);
   this.props.initMessage();
  } 
   
  onChange = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value });
    if(evt.target.name=='namePrefix' && this.props.isNewRegister){
      this.setState({ login: `${this.props.prefixAdminValue}_${evt.target.value}`  });
    }
  }

  onChangeDataSource = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value});
  }

  onChangeLookFeel = (evt) => {
    const index = this.props.lookFeelsData.findIndex(i => i.themeId.toString() === evt.target.value);
    this.setState({ [evt.target.name]: evt.target.value, lookFeels: this.props.lookFeelsData[index] });
  }

  onChangeCountry = (evt) => {
    const index = this.props.countries.findIndex(i => i.code.toString() === evt.target.value);
    this.setState({ [evt.target.name]: evt.target.value, country: this.props.countries[index] });
  }

  onChangeCompany = (evt) => {
    const index = this.props.companies.findIndex(i => i.code.toString() === evt.target.value);
    
    this.setState({ [evt.target.name]: evt.target.value, company: this.props.companies[index] });
  }

  onChangeUserCompany = (evt) => {
    const index = this.props.companies.findIndex(i => i.code.toString() === evt.target.value);
    this.setState({ [evt.target.name]: evt.target.value, userCompany: this.props.companies[index] });
  }

  onChangeTable = (evt) => {
    const index = this.props.tables.findIndex(i => i.tableId.toString() === evt.target.value);
    
    this.setState({ [evt.target.name]: evt.target.value, tables: this.props.tables[index] });
  }

  onChangeOperatorStatus = (evt) => {
    const index = this.props.operatorsStatus.findIndex(i => i.code.toString() === evt.target.value);
    this.setState({ [evt.target.name]: evt.target.value, operatorsStatus: this.props.operatorsStatus[index] });
  }

  onChangeJndi = (evt) => {
    const index = this.props.jndiData.findIndex(i => i.code.toString() === evt.target.value);
    this.setState({ [evt.target.name]: evt.target.value, jndi: this.props.jndiData[index] });
    //this.setState({ [evt.target.name]: evt.target.value});
  }

  onChangeCas = (evt) => {
    const index = this.props.casData.findIndex(i => i.code.toString() === evt.target.value);
    this.setState({ [evt.target.name]: evt.target.value, cas: this.props.casData[index] });
    //this.setState({ [evt.target.name]: evt.target.value});
  }

  
  handleTech = (value) => {
    this.setState({ techSelected: value });
  }


  handleChangeStartTime= (time) => {

    let timeString = convertTimePickerValueToString(time);
    this.setState({startTime: timeString, startHour: time});
  }

  handleChangeEndTime= (time) => {
    let timeString = convertTimePickerValueToString(time);
    this.setState({endTime: timeString, endhour: time});
  }

  getValidationState(field) {
    if((!this.state[field] || this.state[field]==0 ) && this.state.pressSaveButton){
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

  getValidationCompartida() {
    if(this.state.pressSaveButton){
      if(!this.state.percentageShare ){
        return 'error';
      }
      if (this.state.percentageShare<1  || this.state.percentageShare>100  ) {
        return 'error';
      }
  }
   return null;
  }

  getMessageValidationCompartida() {
    if(this.state.pressSaveButton){
      if(!this.state.percentageShare ){
        return <FormattedMessage id="general.form.required.validation" defaultMessage='Campo Requerido'></FormattedMessage>;
      }
      if (this.state.percentageShare<1  || this.state.percentageShare>100  ) {
        return <FormattedMessage id="general.form.percentage.validation" 
          defaultMessage='El Campo deber ser un número entre 1 y 100'></FormattedMessage>;
      }
  }
   return null; 
  }

  getValidationTasa() {
    if(this.state.pressSaveButton){
      if(!this.state.maxDailyRate ){
        return 'error';
      }
      if (this.state.maxDailyRate<1 ) {
        return 'error';
      }
  }
   return null; 
  }

  getMessageValidationTasa() {
    if(this.state.pressSaveButton){
      if(!this.state.maxDailyRate ){
        return <FormattedMessage id="general.form.required.validation" defaultMessage='Campo Requerido'></FormattedMessage>;
      }
      if (this.state.maxDailyRate<1  ) {
        return <FormattedMessage id="general.form.negative.validation" 
          defaultMessage='El Campo deber ser mayor a 0'></FormattedMessage>;
      }
  }
   return null; 
  }

  getValidationTimeZone() {
    if(this.state.pressSaveButton){
      if(!this.state.timeZone ){
        return 'error';
      }
      if (this.state.timeZone<-12 || this.state.timeZone>12 ) {
        return 'error';
      }
  }
   return null; 
  }

  getMessageValidationTimeZone() {
    if(this.state.pressSaveButton){
      if(!this.state.timeZone ){
        return <FormattedMessage id="general.form.required.validation" defaultMessage='Campo Requerido'></FormattedMessage>;
      }
      if (this.state.timeZone<-12 || this.state.timeZone>12  ) {
        return <FormattedMessage id="general.form.timeZone.validation" 
          defaultMessage=''></FormattedMessage>;
      }
  }
   return null; 
  }

  getValidationEmail() {
    if(this.state.pressSaveButton){
        if(!this.state.email ){
          return 'error';
        }
        if(!this.state.email.toString().match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/g) ){
          return 'error';
        }
    }
    return null; 
  }

  getEmailValidationMessage() {

    if(this.state.pressSaveButton){
      if(!this.state.email ){
        return <FormattedMessage id="general.form.required.validation" defaultMessage='Campo Requerido'></FormattedMessage>;
      }
      if(!this.state.email.toString().match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/g) ){
        return <FormattedMessage id="general.form.email.validation" 
          defaultMessage='Favor ingrese un Email valido'></FormattedMessage>;
      }
    }
    return null; 
  }
  render() {
    
    let dataSourcesOptionTemplate = this.props.dataSourcesData.map(dataSource => (
      <option key={dataSource.code} value={dataSource.code}>{dataSource.jndiName}</option>
    ));

    let countriesOptionTemplate = this.props.countries.map(country => (
      <option key={country.code} value={country.code}>{country.country}</option>
    ));

    let companiesOptionTemplate = this.props.companies.map(company => (
      <option key={company.code} value={company.code}>{company.name}</option>
    ));

    let tablesOptionTemplate = this.props.tables.map(table => (
      <option key={table.tableId} value={table.tableId}>{table.name}</option>
    ));

    let operatorsStatusOptionTemplate = this.props.operatorsStatus.map(operatorStatus => (
      <option key={operatorStatus.code} value={operatorStatus.code}>{operatorStatus.name}</option>
    ));

    let lookFeelsOptionTemplate = this.props.lookFeelsData.map(lookfeel => (
      <option key={lookfeel.themeId} value={lookfeel.themeId}>{lookfeel.name}</option>
    ));

    let jndiOptionTemplate = this.props.jndiData.map(data => (
      <option key={data.code} value={data.code}>{data.jndiName}</option>
    ));

    if(this.state.jndiData!=undefined){
        jndiOptionTemplate.push(<option key={this.state.jndiData.code} value={this.state.jndiData.code}>{this.state.jndiData.jndiName}</option>);
    }

    let casOptionTemplate = this.props.casData.map(data => (
      <option key={data.code} value={data.code}>{data.jndiName}</option>
    ));

    if(this.state.casDataEdit!=undefined){
        casOptionTemplate.push(<option key={this.state.casDataEdit.code} value={this.state.casDataEdit.code}>{this.state.casDataEdit.jndiName}</option>);
    }

    return (
      <StyledPanel currentStyles={this.props.currentStyles} header={this.state.title} bsStyle="primary">
        <Form horizontal>
       <FormGroup controlId="formHorizontalCountrySelected"
        validationState={this.getValidationState('countrySelected')} 
       >
         <Col sm={5}>
         <FormattedMessage id="operatorsManagment.form.input.country.title" defaultMessage='País'></FormattedMessage>
         </Col>
         <Col sm={7}>
           <FormControl
           readOnly={this.state.readOnly}
             componentClass="select"
             placeholder="- Seleccionar País"
             name="countrySelected"
             value={this.state.countrySelected}
             onChange={this.onChangeCountry}
           >
             {countriesOptionTemplate}
           </FormControl>
           <ControlLabel>{this.getMessageValidation('countrySelected')} </ControlLabel>
         </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalName"
       validationState={this.getValidationState('name')} 
       >
         <Col sm={5}>
           <FormattedMessage id="operatorsManagment.form.input.carrierName.title" defaultMessage='Nombre Operadora'></FormattedMessage>
         </Col>
         <Col sm={7}>
              <FormControl
              readOnly={this.state.readOnly}
                type="text"
                placeholder={this.context.intl.formatMessage({id: 'operatorsManagment.form.input.carrierName.placeholder'})}
                name="name"
                value={this.state.name}
                onChange={this.onChange}
                maxLength={100}
              />
              <ControlLabel>{this.getMessageValidation('name')} </ControlLabel>
         </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalStartDate"
         validationState={this.getValidationState('startTime')} 
       >
            <Col sm={5}>
             <FormattedMessage id="operatorsManagment.form.input.initialTime.title" defaultMessage='Hora Inicio'></FormattedMessage>
            </Col>
            <Col sm={7}>
            {/*<DatePicker
            className={this.getValidationState('startTime') =='error'? 'datepicker-error': "" }
                dateFormat="hh:mm a"
                selected={this.state.startTime}
                showTimeSelect
                timeFormat="hh:mm a"
                timeIntervals={15}
                readOnly
                onChange={this.handleChangeStartTime}
            />*/}
            <TimePicker start="00:00" end="23:59" readOnly={this.state.readOnly} format={24} step={5} onChange={this.handleChangeStartTime}
              value={this.state.startTime} />
            <ControlLabel>{this.getMessageValidation('startTime')} </ControlLabel>
            </Col>
          </FormGroup>
          <FormGroup controlId="formHorizontalEndDate"
           validationState={this.getValidationState('endTime')} 
          >
            <Col sm={5}>
             <FormattedMessage id="operatorsManagment.form.input.endTime.title" defaultMessage='Hora Fin'></FormattedMessage>
            </Col>
            <Col sm={7}>
            {/*<DatePicker
            className={this.getValidationState('endTime') =='error'? 'datepicker-error': "" }
                dateFormat="MM/DD/YYYY hh:mm a"
                selected={this.state.endTime}
                showTimeSelect
                timeFormat="hh:mm a"
                readOnly
                onChange={this.handleChangeEndTime}
            />*/}
                 <TimePicker readOnly={this.state.readOnly} start='00:00' end="23:59" format={24} step={5}
              onChange={this.handleChangeEndTime} value={this.state.endTime} />
            {/* <TimePicker start={this.state.startTime} end="23:59" format={24} step={5}
              onChange={this.handleChangeEndTime} value={this.state.endhour} /> */}
            <ControlLabel>{this.getMessageValidation('endTime')} </ControlLabel>
            </Col>
          </FormGroup>
          <FormGroup controlId="formHorizontalPercentageShare"
           validationState={this.getValidationCompartida()} 
          >
         <Col sm={5}>
         <FormattedMessage id="operatorsManagment.form.input.percentageShare.title" defaultMessage='Compartida %'></FormattedMessage>
         </Col>
         <Col sm={7}>
              <FormControl
                type="number"  
                min="1" 
                max="100"
                readOnly={this.state.readOnly}
                placeholder={this.context.intl.formatMessage({id: 'operatorsManagment.form.input.percentageShare.placeholder'})}
                name="percentageShare"
                value={this.state.percentageShare}
                onChange={this.onChange}
              />
              <ControlLabel>{this.getMessageValidationCompartida()} </ControlLabel>
         </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalMaxDailyRate"
       validationState={this.getValidationTasa()} 
       >
         <Col sm={5}>
          <FormattedMessage id="operatorsManagment.form.input.dailyMaximumRate.title" defaultMessage='Tasa Diaria Maxima'></FormattedMessage>
         </Col>
         <Col sm={7}>
              <FormControl
                type="number"  
                readOnly={this.state.readOnly}
                min="1" 
                max="100"
                placeholder={this.context.intl.formatMessage({id: 'operatorsManagment.form.input.dailyMaximumRate.placeholder'})}
                name="maxDailyRate"
                value={this.state.maxDailyRate}
                onChange={this.onChange}
              />
              <ControlLabel>{this.getMessageValidationTasa()} </ControlLabel>
         </Col>
       </FormGroup>
       {/* <FormGroup controlId="formHorizontalTableSelected">
         <Col sm={5}>
         Tabla
         </Col>
         <Col sm={7}>
           <FormControl
             componentClass="select"
             placeholder="- Seleccionar Tabla"
             name="tableSelected"
             value={this.state.tableSelected}
             onChange={this.onChangeTable}
           >
             {tablesOptionTemplate}
           </FormControl>
         </Col>
       </FormGroup> */}
       {/* <FormGroup controlId="formHorizontalCompanySelected"
        validationState={this.getValidationState('companySelected')} 
       >
         <Col sm={5}>
         Compañía Operadora
         </Col>
         <Col sm={7}>
           <FormControl
             componentClass="select"
             placeholder="- Seleccionar Compañía"
             name="companySelected"
             value={this.state.companySelected}
             onChange={this.onChangeCompany}
           >
             {companiesOptionTemplate}
           </FormControl>
           <ControlLabel>{this.getMessageValidation('companySelected')} </ControlLabel>
         </Col>
       </FormGroup> */}
       <FormGroup controlId="formHorizontalOperatorsStatusSelected">
         <Col sm={5}>
         <FormattedMessage id="operatorsManagment.form.input.status.title" defaultMessage='Estado operadora'></FormattedMessage>
         </Col>
         <Col sm={7}>
           <FormControl
             componentClass="select"
             readOnly={this.state.readOnly}
             placeholder="- Seleccionar Estado operadora"
             name="operatorsStatusSelected"
             value={this.state.operatorsStatusSelected}
             onChange={this.onChangeOperatorStatus}
           >
             {operatorsStatusOptionTemplate}
           </FormControl>
         </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalLogin"
       validationState={this.getValidationState('login')} 
       >
         <Col sm={5}>
          <FormattedMessage id="operatorsManagment.form.input.login.title" defaultMessage='Login'></FormattedMessage>
         </Col>
         <Col sm={7}>
              <FormControl
                readOnly
                type="text"
                placeholder={this.context.intl.formatMessage({id: 'operatorsManagment.form.input.login.placeholder'})}
                name="login"
                value={this.state.login}
                onChange={this.onChange}
                maxLength={50}
              />
            <ControlLabel>{this.getMessageValidation('login')} </ControlLabel>
         </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalFirstName"
       validationState={this.getValidationState('firstName')} 
       >
         <Col sm={5}>
         <FormattedMessage id="operatorsManagment.form.input.firstName.title" defaultMessage='Primer Nombre'></FormattedMessage>
         </Col>
         <Col sm={7}>
              <FormControl
                type="text"
                
                placeholder={this.context.intl.formatMessage({id: 'operatorsManagment.form.input.firstName.placeholder'})}
                name="firstName"
                value={this.state.firstName}
                onChange={this.onChange}
                maxLength={50}
              />
          <ControlLabel>{this.getMessageValidation('firstName')} </ControlLabel>
         </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalMiddleName">
         <Col sm={5}>
         <FormattedMessage id="operatorsManagment.form.input.secondName.title" defaultMessage='Segundo Nombre'></FormattedMessage>
         </Col>
         <Col sm={7}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({id: 'operatorsManagment.form.input.secondName.placeholder'})}
                name="middleName"
                value={this.state.middleName}
                onChange={this.onChange}
                maxLength={50}
              />
         </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalLastName"
        validationState={this.getValidationState('lastName')} 
       >
         <Col sm={5}>
         <FormattedMessage id="operatorsManagment.form.input.firstSurname.title" defaultMessage='Primer Apellido'></FormattedMessage>
         </Col>
         <Col sm={7}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({id: 'operatorsManagment.form.input.firstSurname.placeholder'})}
                name="lastName"
                value={this.state.lastName}
                onChange={this.onChange}
                maxLength={50}
              />
             <ControlLabel>{this.getMessageValidation('lastName')} </ControlLabel>
         </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalSecondLastName">
         <Col sm={5}>
         <FormattedMessage id="operatorsManagment.form.input.secondSurname.title" defaultMessage='Segundo Apellido'></FormattedMessage>
         </Col>
         <Col sm={7}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({id: 'operatorsManagment.form.input.secondSurname.placeholder'})}
                name="secondLastName"
                value={this.state.secondLastName}
                onChange={this.onChange}
                maxLength={50}
              />
         </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalSecondLastName"
       validationState={this.getValidationEmail()} 
       >
         <Col sm={5}>
        Email
         </Col>
         <Col sm={7}>
              <FormControl
                type="email"
                placeholder="Email"
                name="email"
                value={this.state.email}
                onChange={this.onChange}
                maxLength={250}
              />
          <ControlLabel>{this.getEmailValidationMessage()} </ControlLabel>
         </Col>
       </FormGroup>
         {/* <FormGroup controlId="formHorizontalDataSource"
         validationState={this.getValidationState('initialDataSourceIdSelected')} 
         >
         <Col sm={5}>
         <FormattedMessage id="operatorsManagment.form.input.datasource.title" defaultMessage='Origen de Datos'></FormattedMessage>
         </Col>
         <Col sm={7}>
           <FormControl
             componentClass="select"
             placeholder="- Seleccionar Origen de Datos -"
             name="initialDataSourceIdSelected"
             value={this.state.initialDataSourceIdSelected}
             onChange={this.onChangeDataSource}
           >
             {dataSourcesOptionTemplate}
           </FormControl>
           <ControlLabel>{this.getMessageValidation('initialDataSourceIdSelected')} </ControlLabel>
         </Col>
       </FormGroup> */}
     
       <FormGroup controlId="formHorizontalJndi"
         validationState={this.getValidationState('jndiSelected')} 
         >
         <Col sm={5}>
         JNDI
         </Col>
         <Col sm={7}>
           <FormControl
           readOnly={this.state.readOnly}
             componentClass="select"
             placeholder="- Seleccionar JNDI -"
             name="jndiSelected"
             value={this.state.jndiSelected}
             onChange={this.onChangeJndi}
           >
             {jndiOptionTemplate}
           </FormControl>
           <ControlLabel>{this.getMessageValidation('jndiSelected')} </ControlLabel>
         </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalJndi"
         validationState={this.getValidationState('casSelected')} 
         >
         <Col sm={5}>
         CAS
         </Col>
         <Col sm={7}>
           <FormControl
           readOnly={this.state.readOnly}
             componentClass="select"
             placeholder="- Seleccionar CAS -"
             name="casSelected"
             value={this.state.casSelected}
             onChange={this.onChangeCas}
           >
             {casOptionTemplate}
           </FormControl>
           <ControlLabel>{this.getMessageValidation('casSelected')} </ControlLabel>
         </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalTech"
         validationState={this.getValidationState('techSelected')} 
         >
         <Col sm={5}>
         <h5 className="tech-title">
         <FormattedMessage id="operatorsManagment.form.input.availableTechnology.title" defaultMessage='Tema'></FormattedMessage>
         </h5>

         </Col>
         <Col sm={7}>

         <Select
                name="techSelected"
                className={this.getValidationState('techSelected') =='error'? 'tech-error': "tech" }
                closeOnSelect
                disabled={this.state.readOnly}
                autosize={false}
                multi
                options={this.props.technologies}
                placeholder={<FormattedMessage id="combobox.first.register" defaultMessage='Selecciar'></FormattedMessage>}
                removeSelected={false}
                rtl={false}
                simpleValue
                value={this.state.techSelected}
                onChange={this.handleTech}
              />
           <ControlLabel>{this.getMessageValidation('techSelected')} </ControlLabel>
         </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalTheme"
       validationState={this.getValidationState('lookFeelsSelected')} 
       >
         <Col sm={5}>
         <FormattedMessage id="operatorsManagment.form.input.theme.title" defaultMessage='Tema'></FormattedMessage>
         </Col>
         <Col sm={7}>
           <FormControl
             componentClass="select"
             readOnly={this.state.readOnly}
             placeholder="- Seleccionar Tema -"
             name="lookFeelsSelected"
             value={this.state.lookFeelsSelected}
             onChange={this.onChangeLookFeel}
           >
             {lookFeelsOptionTemplate}
           </FormControl>
           <ControlLabel>{this.getMessageValidation('lookFeelsSelected')} </ControlLabel>
         </Col>
       </FormGroup>

     

       <FormGroup controlId="formHorizontalTimeZone"
       validationState={this.getValidationTimeZone()} 
       >
         <Col sm={5}>
          <FormattedMessage id="operatorsManagment.form.input.timeZone.title" defaultMessage='Zona Horaria (GMT)'></FormattedMessage>
         </Col>
         <Col sm={7}>
              <FormControl
                type="number"  

                min="-12" 
                max="12"
                placeholder={this.context.intl.formatMessage({id: 'operatorsManagment.form.input.timeZone.placeholder'})}
                name="timeZone"
                value={this.state.timeZone}
                onChange={this.onChange}
              />
              <ControlLabel>{this.getMessageValidationTimeZone()} </ControlLabel>
         </Col>
       </FormGroup>
       

       <FormGroup controlId="formHorizontalNamePrefix"
       validationState={this.getValidationState('namePrefix')} 
       >
         <Col sm={5}>
               <FormattedMessage id="operatorsManagment.form.input.operatorPrefix.title" defaultMessage='Nombre Operadora'></FormattedMessage>
         </Col>
         <Col sm={7}>
              <FormControl
                type="text"
                readOnly={this.state.readOnly}
                placeholder={this.context.intl.formatMessage({id: 'operatorsManagment.form.input.operatorPrefix.placeholder'})}
                name="namePrefix"
                value={this.state.namePrefix}
                onChange={this.onChange}
                maxLength={50}
              />
              <ControlLabel>{this.getMessageValidation('namePrefix')} </ControlLabel>
         </Col>
       </FormGroup>


       <FormGroup controlId="formHorizontalOperatorIdExternal"
          validationState={this.getValidationState('externalOperatorId')}
          >
            <Col sm={5}>
                  <FormattedMessage id="operatorsManagment.form.input.externalOperatorId.title" defaultMessage='Codigo Externo'></FormattedMessage>
            </Col>
            <Col sm={7}>
                 <FormControl
                   type="text"
                   readOnly={this.state.readOnly}
                   placeholder={this.context.intl.formatMessage({id: 'operatorsManagment.form.input.externalOperatorId.placeholder'})}
                   name="externalOperatorId"
                   value={this.state.externalOperatorId}
                   onChange={this.onChange}
                   maxLength={50}
                 />
                 <ControlLabel>{this.getMessageValidation('externalOperatorId')} </ControlLabel>
            </Col>
          </FormGroup>

       <FormGroup >
       <Col sm={5}>
         </Col>
         <Col sm={7}>
         <StyleButton currentStyles={this.props.currentStyles} bsStyle="primary" 
             onClick={ () => { this.save(); }}>
                <FormattedMessage id="form.button.save" defaultMessage=''></FormattedMessage>
           </StyleButton>

           <Button onClick={() => { this.handleCancelEditOperators(); }}>
           <FormattedMessage id="form.button.cancel" defaultMessage=''></FormattedMessage>
       </Button>
      
         </Col>
            </FormGroup>
        </Form>
      </StyledPanel>
    );
  }
}

LurinOperatorsManagementFormComponent.propTypes = {
  showForm: PropTypes.func,
  isNewRegister: PropTypes.bool,
  operatorsData: PropTypes.object,
  save: PropTypes.func,
  test: PropTypes.func,
  currentStyles: PropTypes.object,
};

const mapStateToProps = createStructuredSelector({
  loading: makeSelectLoading(),
  error: makeSelectError(),
  hasShowForm: makeSelectHasShowForm(),
});

function mapDispatchToProps(dispatch) {
  return {
    showForm: (hasShowForm) => dispatch(showForm(hasShowForm))  ,
    initMessage:() => dispatch(initMessage())         
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinOperatorsManagementFormComponent);
