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

import LurinOperatorsManagementFormComponent from '../../components/LurinOperatorsManagementFormComponent';
import LurinModalComponent from '../../components/LurinModalComponent';
import {
  makeSelectOperatorsData,
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
  makeSelectNewOperator,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType,
  makeSelectDataSourcesData,
  makeSelectJndiData,
  makeSelectCasData
} from './selectors';

import {
  makeSelectCountriesData,
  makeSelectTablesData,
  makeSelectCompaniesData,
  makeSelectOperatorsStatusData,
  makeSelectCurrentStyle,
  makeSelectedLookFeelsData,
  makeSelectTechnologiesData
} from '../LurinFvAppContainer/selectors';

import {
  makeSelectGlobalParametersData,
} from '../LurinFvGlobalParameterContainer/selectors';


import * as actions from './actions';

import * as generalActions from '../LurinFvAppContainer/actions';
import * as gparametersActions from '../LurinFvGlobalParameterContainer/actions';

import {
  StyledPanel,
  StyleForm
} from './styles';
import { debug } from 'util';

export class LurinOperatorsManagementContainer extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      allOperators: [],
      isNewRegister: false,
      isShowForm: false,
      operatorsData: {
        code: 0,
        countryId: {
          code: "1",
          country: "Nicaragua"
        },
        name: 'Operator 3',
        startTime: "00:00",
        endTime: "00:00",
        percentageShare: 50,
        maxDailyRate: 10,
        tables: {
          tableId: 1,
          name: "table 1"
        },
        companies: {
          code: 1,
          name: "Movistar"
        },
        status: {
          code: "1",
          name: "Active",
          status: "A"
        },
      },
       dataSources:[],
       adminUser: {
        code: 0,
        login: "mlopez",
        firstName: "Miguel",
        middleName: "Alejandro",
        lastName: "Lopez",
        secondLastName: "",
        email: 'mlopez@gmail.com'
      },
      tech:[],
      prefixAdminValue:'',
      alertVisible: false,
      alertStyle: "success",
      alertMessage: '',
      showModal:false,
    };
    this.options = {
      defaultSortName: 'code',
      defaultSortOrder: 'desc',
      noDataText: <FormattedMessage id="table.nodata" defaultMessage='No se han encontrado registros para mostrar'></FormattedMessage>,
      handleConfirmDeleteRow: this.customConfirm,
      insertBtn: this.createCustomInsertButton,
      deleteText: <FormattedMessage id="table.button.delete" defaultMessage='Borrar'></FormattedMessage>,
      onDeleteRow: this.delete,
    };

    this.handleEditOperators = this.handleEditOperators.bind(this);
    this.handleNewOperatorClick = this.handleNewOperatorClick.bind(this);
    this.handleAlertDismiss = this.handleAlertDismiss.bind(this);
    this.onHideModal = this.onHideModal.bind(this);
    this.onConfirm = this.onConfirm.bind(this);
    this.closeModal = this.closeModal.bind(this);
  }

  static contextTypes = {
    intl: intlShape,
  }

  componentDidMount() {
    this.props.loadOperators();
    this.props.loadDataSources();
    this.props.loadCountries();
    this.props.loadCompanies();
    this.props.loadTables();
    this.props.loadOperatorsStatus();
    this.props.loadLookFeels();
    this.props.loadJndi("J",0);
    this.props.loadCas("T",0);
    this.props.loadTechnology();
    this.props.loadGlobalParameters();
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.operatorsData.length > 0) {
      this.setState({ allOperators: nextProps.operatorsData });
    } else {
      this.setState({ allOperators: [] });
    }

    if (nextProps.hasShowForm !== this.props.hasShowForm) {
      this.setState({ isShowForm: nextProps.hasShowForm });
    }

    if (nextProps.technologies !== this.props.technologies && nextProps.technologies ) {
      let tech=[];

      for(var i=0; i<nextProps.technologies.length; i++ ){
        if(nextProps.technologies[i].code.toString()!= '0'){
          tech.push({label: nextProps.technologies[i].name, value: nextProps.technologies[i].code.toString()});
        }
          
      }

      this.setState({ tech});
    }
    if (nextProps.gparameters){
      let currentParameter = nextProps.gparameters.filter(function(gp){
        return gp.key==='PREFIX.ADMIN.LOCAL';
      });
      if(currentParameter.length){
        this.setState({ prefixAdminValue: currentParameter[0].value});
      }
    }
    
  }

  handleEditOperators = (selecteOperator) => {
    var currentSelectedOperator = selecteOperator;
    var tech = selecteOperator.categoryTechnologies.map(function(elem){ return elem.code; }).join(",");
    currentSelectedOperator.categoryTechnologies = tech;
    this.props.loadJndi("J",selecteOperator.code);
    this.props.loadCas("T",selecteOperator.code);
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
      operatorsData: selecteOperator,
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
    if (!this.state.operatorsData.code) return;
     this.props.deleteOperators(this.state.operatorsData.code);
    this.setState({ showModal: false });
  };

  createCustomInsertButton = () => (
    <InsertButton
      btnText={<FormattedMessage id="table.button.new" defaultMessage='Nuevo'></FormattedMessage>}
      onClick={() => this.handleNewOperatorClick()}
    />
  )

  editFormatter = (cell, row) => (
    <Button bsSize="xsmall" onClick={() => { this.handleEditOperators(row); }}><Glyphicon glyph="pencil" /></Button>
  );

  hasInvalidData(currentState) {
    if (!currentState.name) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El nombre es requerido."
      });
      return true;
    }
    if (currentState.countrySelected == 0) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El País es requerido."
      });
      return true;
    }
    if (!currentState.startTime) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "La Fecha Inicio es requerida."
      });
      return true;
    }
    if (!currentState.endTime) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "La Fecha Fin es requerida."
      });
      return true;
    }
    if (currentState.percentageShare<1  || currentState.percentageShare>100  ) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El % compartida debe ser entre 1 y 100."
      });
      return true;
    }
    if (currentState.maxDailyRate==0  ) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "La Tasa debe ser mayor a 0."
      });
      return true;
    }
  
    // if (currentState.companySelected == 0) {
    //   this.setState({
    //     alertVisible: true, alertStyle: "warning",
    //     alertMessage: "El Compañía de la operadora es requerida."
    //   });
    //   return true;
    // }
    if (currentState.operatorStatusSelected == 0) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El estado de la operadora es requerido."
      });
      return true;
    }
    if (!currentState.login) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El Login es requerido."
      });
      return true;
    }
    if (!currentState.firstName) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El Primer nombre es requerido."
      });
      return true;
    }
    if (!currentState.lastName) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El Primer Apellido es requerido."
      });
      return true;
    }
    if (!currentState.email) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El email es requerido."
      });
      return true;
    }
    if (currentState.lookFeelsSelected == 0) {
      this.setState({
        alertVisible: true, alertStyle: "warning",
        alertMessage: "El tema de la operadora es requerido."
      });
      return true;
    }
      
    return false
  }



  save = (currentState) => {
    // if (this.hasInvalidData(currentState)) {
    //   return;
    // }

    let currentLookFeel={
      themeId : currentState.lookFeels.themeId,
      name : currentState.lookFeels.name,
      description : currentState.lookFeels.description
    };

    let dataSources=[];
    dataSources.push(currentState.jndi);
    dataSources.push(currentState.cas);
    let categoriesTech=null;
    categoriesTech=this.props.technologies.filter(function(tech){
       return currentState.techSelected.includes(tech.code.toString())
    });

    if (this.state.isNewRegister) {
     
      const currentOperator = {
        countryId: currentState.country,
        name: currentState.name,
        startTime: currentState.startTime,
        endTime: currentState.endTime,
        percentageShare: currentState.percentageShare,
        maxDailyRate: currentState.maxDailyRate,
        tables:this.props.tables[1] ,
        companies:this.props.companies[1],
        status:currentState.operatorsStatus,
        adminUser: {
          code: 0,
          login: currentState.login,
          firstName: currentState.firstName,
          middleName: currentState.middleName,
          lastName: currentState.lastName,
          secondLastName: currentState.secondLastName,
          email: currentState.email,
          status: currentState.operatorsStatus,
          companies:this.props.companies[1]
        },
        utc:currentState.timeZone,
        namePrefix:currentState.namePrefix,
        categoryTechnologies: categoriesTech,
        dataSources:dataSources,
        themes: currentLookFeel,
        externalOperatorId: currentState.externalOperatorId
      };
     this.props.createOperators(currentOperator);
    } else {
      const currentOperator = {
        code: this.state.operatorsData.code,
        countryId: currentState.country,
        name: currentState.name,
        startTime: currentState.startTime,
        endTime: currentState.endTime,
        percentageShare: currentState.percentageShare,
        maxDailyRate: currentState.maxDailyRate,
        tables:this.props.tables[1] ,
        companies:this.props.companies[1],
        status:currentState.operatorsStatus,
        adminUser: {
          code: this.state.operatorsData.adminUser ? this.state.operatorsData.adminUser.code:0,
          login: currentState.login,
          firstName: currentState.firstName,
          middleName: currentState.middleName,
          lastName: currentState.lastName,
          secondLastName: currentState.secondLastName,
          email: currentState.email,
          status: currentState.operatorsStatus,
          companies:  this.props.companies[1]
        },
        utc:currentState.timeZone,
        namePrefix:currentState.namePrefix,
        categoryTechnologies:categoriesTech,
        dataSources:dataSources,
        themes: currentLookFeel,
        externalOperatorId: currentState.externalOperatorId
      };
      this.props.updateOperators(currentOperator);
    }
  }
  
  onSelect = (data) => {
    this.setState({
      operatorsData: data,
    });
  }

  handleNewOperatorClick = () => {
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
      operatorsData: {
        code: 0,
        countryId: {
          code: "0",
        },
        name: '',
        startTime: "00:00",
        endTime: "00:00",
        percentageShare: 1,
        maxDailyRate: 1,
        tables: {
          tableId: 0,
          name: ""
        },
        companies: {
          code: 0,
          name: ""
        },
        status: {
          code: "0"
        },
       adminUser: {
        code: 0,
        login: "",
        firstName: "",
        middleName: "",
        lastName: "",
        secondLastName: "",
        email: ''
      },
       themes: {
        themeId: 0
      },
      dataSources:[  
        {  
           code:0,
           sourceType:{  
            sourceType:"J"
         } 
        },
        {  
           code:0,
           sourceType:{  
            sourceType:""
         }
        }
     ],
     categoryTechnologies:[]
      },
    });
    this.props.initMessage();
  }
  
  showDataSourceDescription(cell, row) {
    return cell.name;
  }
  showCountryDescription(cell, row) {
    return cell.country;
  }

  showCompanyDescription(cell, row) {
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

  renderOperatorsForm() {
    if (this.state.isShowForm) {
      
      let dataSourcesData=[ { code:0,jndiName:this.context.intl.formatMessage({id: 'combobox.first.register'})}];
      if(this.props.dataSourcesData){
        dataSourcesData=dataSourcesData.concat(this.props.dataSourcesData);
      }

      let countries=[ { code:0,country:this.context.intl.formatMessage({id: 'combobox.first.register'})}];
      if(this.props.countries){
        countries=countries.concat(this.props.countries);
      }

      let companies=[ { code:0,name:this.context.intl.formatMessage({id: 'combobox.first.register'})}];
      if(this.props.companies){
        companies=companies.concat(this.props.companies);
      }

      let operatorsStatus=[ { code:0,name:this.context.intl.formatMessage({id: 'combobox.first.register'})}];
      if(this.props.operatorsStatus){
        operatorsStatus=operatorsStatus.concat(this.props.operatorsStatus);
      }

      let tables=[ { tableId:0,name:this.context.intl.formatMessage({id: 'combobox.first.register'})}];
      if(this.props.tables){
        tables=tables.concat(this.props.tables);
      }

      let lookFeelsData=[ { themeId:0,name:this.context.intl.formatMessage({id: 'combobox.first.register'})}];
      if(this.props.lookFeelsData){
        lookFeelsData=lookFeelsData.concat(this.props.lookFeelsData);
      }

      return (
        <LurinOperatorsManagementFormComponent
          isNewRegister={this.state.isNewRegister}
          operatorsData={this.state.operatorsData}
          dataSourcesData={dataSourcesData}
          countries={countries}
          companies={companies}
          operatorsStatus={operatorsStatus}
          tables={tables}
          save={this.save}
          currentStyles={this.props.currentStyles}
          lookFeelsData={lookFeelsData}
          jndiData={this.props.jndiData}
          casData={this.props.casData}
          technologies={this.state.tech}
          prefixAdminValue={this.state.prefixAdminValue}
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
        header={<FormattedMessage id="operatorsManagment.form.header" defaultMessage='Administrar Operadora'></FormattedMessage>} bsStyle="primary">
       <div className="modal-container">
            <LurinModalComponent
              showModal={this.state.showModal}
              onHideModal={this.onHideModal}
              title={this.context.intl.formatMessage({id: 'operatorsManagment.grid.modal.delete.title'})}
              description={this.context.intl.formatMessage({id: 'operatorsManagment.grid.modal.delete.title'})}
              onConfirm={this.onConfirm}
              closeModal={this.closeModal}
              currentStyles={this.props.currentStyles}
              />
       </div>
      
        <BootstrapTable
          striped
          condensed
          bordered
          data={this.state.allOperators}
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
            dataField="name"
            dataSort
            width="15%"gparameters
            dataAlign="left"
            headerAlign="left"
          >
            <FormattedMessage id="operatorsManagment.grid.header.carrier" defaultMessage='Operadora'></FormattedMessage>
          </TableHeaderColumn>   
          <TableHeaderColumn
            dataField="countryId"
            dataSort
            dataFormat={this.showCountryDescription}
            width="15%"
            dataAlign="left"
            headerAlign="left"
          >
           <FormattedMessage id="operatorsManagment.grid.header.country" defaultMessage='País'></FormattedMessage>
          </TableHeaderColumn> 
          <TableHeaderColumn
            dataField="companies"
            dataSort
            dataFormat={this.showCompanyDescription}
            width="15%"
            dataAlign="left"
            headerAlign="left"
          >
           <FormattedMessage id="operatorsManagment.grid.header.company" defaultMessage='Compañía'></FormattedMessage>
          </TableHeaderColumn> 
          <TableHeaderColumn
            dataField="status"
            dataSort
            dataFormat={this.showStatusDescription}
            width="15%"
            dataAlign="left"
            headerAlign="left"
            >
            <FormattedMessage id="operatorsManagment.grid.header.status" defaultMessage='Estado'></FormattedMessage>
          </TableHeaderColumn> 
          <TableHeaderColumn
            dataFormat={this.editFormatter}
            dataSort
            width="10%"
            dataAlign="center"
            headerAlign="center"
          >
            <FormattedMessage id="operatorsManagment.grid.header.edit" defaultMessage='Editar'></FormattedMessage>
          </TableHeaderColumn>
        </BootstrapTable>
        <br />
        {this.renderAlert()}
        {this.renderOperatorsForm()}
      </StyledPanel>
    );
  }
}

LurinOperatorsManagementContainer.propTypes = {
  loadOperators: PropTypes.func,
  showForm: PropTypes.func,
  hasShowForm: PropTypes.bool,
  createOperators: PropTypes.func,
  updateOperators: PropTypes.func,
  isNewRegister: PropTypes.bool,
  deleteOperators: PropTypes.func,
  showMessage: PropTypes.bool,
  message:PropTypes.string
};

const mapStateToProps = createStructuredSelector({
  loading: makeSelectLoading(),
  error: makeSelectError(),
  operatorsData: makeSelectOperatorsData(),
  hasShowForm: makeSelectHasShowForm(),
  newOperator: makeSelectNewOperator(),
  showMessage:makeSelectShowMessage(),
  message:makeSelectMessage(),
  messageType:makeSelectMessageType(),
  dataSourcesData:makeSelectDataSourcesData(),
  countries: makeSelectCountriesData(),
  companies: makeSelectCompaniesData(),
  operatorsStatus: makeSelectOperatorsStatusData(),
  tables: makeSelectTablesData(),
  currentStyles: makeSelectCurrentStyle(),
  lookFeelsData: makeSelectedLookFeelsData(),
  jndiData:makeSelectJndiData(),
  casData:makeSelectCasData(),
  technologies:makeSelectTechnologiesData(),
  gparameters:makeSelectGlobalParametersData()
});

function mapDispatchToProps(dispatch) {
  return {
    loadOperators: () => dispatch(actions.loadOperators()),
    loadDataSources: () => dispatch(actions.loadDataSources()),
    showForm: (hasShowForm) => dispatch(actions.showForm(hasShowForm)),
    createOperators: (newOperator) => dispatch(actions.createOperators(newOperator)),
    updateOperators: (operatorToUpdate) => dispatch(actions.updateOperators(operatorToUpdate)),
    deleteOperators: (code) => dispatch(actions.deleteOperators(code)),
    save: (operatorData) => dispatch(actions.save(operatorData)),
    loadCountries: () => dispatch(generalActions.loadCountries()),
    loadTables: () => dispatch(generalActions.loadTables()),
    loadCompanies: () => dispatch(generalActions.loadCompanies()),
    loadOperatorsStatus: () => dispatch(generalActions.loadOperatorsStatus()),
    loadLookFeels: () => dispatch(generalActions.loadLookFeels()),
    loadTechnology: () => dispatch(generalActions.loadTechnology()),
    loadJndi: (type, code ) => dispatch(actions.loadJndi(type,code)),
    loadCas: (type,code) => dispatch(actions.loadCas(type,code)),
    initMessage: () => dispatch(actions.initMessage()),
    loadGlobalParameters: () => dispatch(gparametersActions.loadGlobalParameters()),
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinOperatorsManagementContainer);
