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
  FormControl,
  FormGroup,
  ButtonGroup,
  DropdownButton,
  MenuItem,
  Col,
  Row
  ,Alert
} from 'react-bootstrap';
import DatePicker from 'react-datepicker';

import {
  makeSelectCurrentStyle,
  makeSelectedCarriers,
  makeSelectCampaignStatusData,
  makeSelectTechnologiesData,
  makeSelectfreeviewsData,
  makeSelectCampaingsData
} from '../LurinFvAppContainer/selectors';
import {
  makeSelectCampaignData,
  makeSelectLoading,
  makeSelectError,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType,
  makeSelectDownload,
  makeSelectCampaignExportData
} from './selectors';
import * as actions from './actions';
import * as generalActions from '../LurinFvAppContainer/actions';
import {
  StyledPanel,
  StyleForm,
  StyleButton
} from './styles';

export class LurinCampaignReportContainer extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      campaignData: [],
      alertVisible: false,
      alertStyle: "success",
      alertMessage: '',
      showModal:false,
      carrierSelected: 0,
      idCliente: "",
      campaignName: "",
      freeViewName: "",
      idCarrier: "",
      statusName: "",
      typeReport: ""
    };  
    this.options = {
      printToolBar: false,
      defaultSortName: 'idStb',
      defaultSortOrder: 'desc',
      noDataText: <FormattedMessage id="table.nodata" defaultMessage='No se han encontrado registros para mostrar '></FormattedMessage>,
    };
    this.handleAlertDismiss = this.handleAlertDismiss.bind(this);
    this.filter = this.filter.bind(this);
    this.export = this.export.bind(this);
  }

  static contextTypes = {
    intl: intlShape,
 }

  componentDidMount() {
    this.props.loadCarriers();
    this.props.loadCampaignStatus();
    if(window.currentCarriers.length > 0) {
        this.props.loadTechnology(window.currentCarriers[0].idCarrier);
        this.props.loadCampaings(window.currentCarriers[0].idCarrier);
        this.props.loadFreeviews(window.currentCarriers[0].idCarrier);
    }
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.campaignData.length > 0) {
      this.setState({ campaignData: nextProps.campaignData });
    } else {
      this.setState({ campaignData: [] });
    }

    if (nextProps.freeviewsData.length > 0 && this.props.freeviewsData!= nextProps.freeviewsData) {
      this.setState({ freeViewName: nextProps.freeviewsData[0].freeViewId});
    } 

    if (nextProps.campaingsData.length > 0 && this.props.campaingsData!= nextProps.campaingsData) {
      this.setState({ campaignName: nextProps.campaingsData[0].campaignId});
    } 
    if (nextProps.carriers.length > 0 && this.props.carriers!= nextProps.carriers) {
      this.setState({ idCarrier: nextProps.carriers[0].idCarrier});
    } 
  }

  getExtension = (contentType) => {
    let extension='';
    if(contentType==='application/pdf')
      extension='pdf'; 
    if(contentType==='application/vnd.ms-excel')
      extension='xls'; 
    if(contentType==='application/x-csv')
      extension='csv'; 
      return extension;
  }
  
  
  filter = () => {
    const filter = {
      idCliente: '',
      campaignId: this.state.campaignName,
      freeViewId:this.state.freeViewName,
      idCarrier:  this.state.idCarrier,
      statusId: this.state.statusName
    }

    // const filter = {
    //   idCliente: this.state.idCliente,
    //   campaignName: this.state.campaignName,
    //   freeViewName: this.state.freeViewName,
    //   idCarrier: this.state.idCarrier,
    //   statusName: this.state.statusName,
    //   typeReport: ""
    // }
    this.props.loadCampaignReportData(window.currentCarriers[0].idCarrier, filter);
  }

  showDateIni(cell, row) {
       return (cell) ? new Date(cell).toLocaleDateString("en-US") : cell;
  }
  showDateEnd(cell, row) {
    return (cell) ? new Date(cell).toLocaleDateString("en-US") : cell;
}

  export = (typeReport) => {
    // const filter = {
    //   idCliente: this.state.idCliente,
    //   campaignName: this.state.campaignName,
    //   freeViewName: this.state.freeViewName,
    //   idCarrier: this.state.idCarrier,
    //   statusName: this.state.statusName,
    //   typeReport: typeReport
    // }

    const filter = {
      idStb:'',
      idCliente: '',
      campaignId: this.state.campaignName,
      freeViewId: this.state.freeViewName,
      idCarrier: this.state.idCarrier,
      statusId: this.state.statusName,
      typeReport: typeReport
    }
    this.props.loadCampaignExportReportData(window.currentCarriers[0].idCarrier, filter);
  }
  
  handleAlertDismiss() {
    this.setState({ alertVisible: false });
  };

  onChangeCamping = (evt) => {
    this.setState({ campaignName: evt.target.value });
  }

  onChangeFreeview = (evt) => {
    this.setState({ freeViewName: evt.target.value });
  }

  onChangeState = (evt) => {
    this.setState({ statusName: evt.target.value });
  }

  onChangeCarrier = (evt) => {
    this.setState({ idCarrier: evt.target.value });
  }

  renderAlert= () => {
    if(this.state.alertVisible) {
      return (
        <div>
          <Alert bsStyle={this.state.alertStyle} onDismiss={this.handleAlertDismiss}>
            <span><FormattedMessage id= {this.state.alertMessage} defaultMessage='Message'></FormattedMessage></span>
          </Alert>
          <br />
        </div>
      );
    }
    return '';
  }


  render() {
    const selectRowProp = {
    };

    let carrierOptionTemplate = this.props.carriers.map(carrier => (
      <option key={carrier.idCarrier} value={carrier.idCarrier}>{carrier.name}</option>
    ));

    let campaignStatusOptionTemplate = this.props.campaignStatus.map(status => (
      <option key={status.idStb} value={status.idStb}>{status.name}</option>
    ));
    let technologiesOptionTemplate = this.props.technologies.map(technology => (
      <option key={technology.idStb} value={technology.idStb}>{technology.technology}</option>
    ));

    let freeviewsOptionTemplate = this.props.freeviewsData.map(freeview => (
      <option key={freeview.freeViewId} value={freeview.freeViewId}>{freeview.name}</option>
      //<option key={freeview.freeViewId} value={freeview.name}>{freeview.name}</option>
    ));

    let campaignsOptionTemplate = this.props.campaingsData.map(campaign => (
       <option key={campaign.campaingId} value={campaign.campaingId}>{campaign.name}</option>
      //<option key={campaign.campaingId} value={campaign.name}>{campaign.name}</option>
    ));

    if(this.props.download){  
      var file_path = `data:${this.props.campaignExportData.contentType};base64,${this.props.campaignExportData.report}`;
      var a = document.createElement('A');
      a.href = file_path;
      a.download = `campaign.${this.getExtension(this.props.campaignExportData.contentType)}`;
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
      setTimeout(() => {
        this.prop.initDownload();
      }, 300);
    } 
    
    return (
      <StyledPanel 
      currentStyles={this.props.currentStyles} 
      className="custom-primary-bg"  
      header={<FormattedMessage id="campaigReporte.form.header" ></FormattedMessage>} bsStyle="primary">
       {this.renderAlert()}
       <br/>
       <Row>
      
      
            <Col sm={3} xs={6}>  
            <Row>
            <Col sm={4}>  
            Campaign
            </Col>  
            <Col sm={8}>  
            <FormControl
                  componentClass="select"
                  placeholder="- Seleccionar Conexion -"
                  name="sourceTypeSelected"
                  value={this.state.campaignName}
                  onChange={this.onChangeCamping}
                >
                   {campaignsOptionTemplate}
                </FormControl>
            </Col> 
            </Row>
            </Col>  
            <Col sm={3}>  
            <Row>
            <Col sm={4}>  
            Freeview
            </Col>  
            <Col sm={8}>  
            <FormControl
                  componentClass="select"
                  placeholder="- Seleccionar Conexion -"
                  name="sourceTypeSelected"
                  value={this.state.freeViewName}
                  onChange={this.onChangeFreeview}
                >
                  {freeviewsOptionTemplate}
                </FormControl>
            </Col> 
            </Row>
            </Col>  
            {/* <Col sm={3}>  
            <Row>
            <Col sm={4}>  
            <FormattedMessage id="campaigReporte.grid.header.technology" defaultMessage=''></FormattedMessage>
            </Col>  
            <Col sm={8}>  
            <FormControl
                  componentClass="select"
                  placeholder="- Seleccionar Conexion -"
                  name="sourceTypeSelected"
                  value={null}
                >
                {technologiesOptionTemplate}
                </FormControl>
            </Col> 
            </Row>
            </Col>   */}
            <Col sm={3}>  
            <Row>
            <Col sm={4}>  
            <FormattedMessage id="campaigReporte.grid.header.carrier" defaultMessage=''></FormattedMessage>
            </Col>  
            <Col sm={8}>  
            <FormControl
                  componentClass="select"
                  placeholder="- Seleccionar Conexion -"
                  name="sourceTypeSelected"
                  value={this.state.idCarrier}
                  onChange={this.onChangeCarrier}
                >
                    {carrierOptionTemplate}
                </FormControl>
            </Col> 
            </Row>
            </Col> 
            <Col sm={12}>
            <br/>
            <Row>


            {/* <Col sm={3}>  
            <Row>
            <Col sm={4}>  
            <FormattedMessage id="campaigReporte.grid.header.campaignIni" defaultMessage=''></FormattedMessage>
            </Col>  
            <Col sm={8}>  
            <DatePicker
                dateFormat="MM/DD/YYYY hh:mm a"
                selected={null}
            />
            </Col> 
            </Row>
            </Col>   */}
            {/* <Col sm={3}>  
            <Row>
            <Col sm={4}>  
            <FormattedMessage id="campaigReporte.grid.header.canpmaignEnd" defaultMessage=''></FormattedMessage>
            </Col>  
            <Col sm={8}>  
            <DatePicker
                dateFormat="MM/DD/YYYY hh:mm a"
                selected={null}
            />
            </Col> 
            </Row>
            </Col>   */}
            <Col sm={3}>  
            <Row>
            <Col sm={4}>  
            <FormattedMessage id="campaigReporte.grid.header.state" defaultMessage=''></FormattedMessage>
            </Col>  
            <Col sm={8}>  
            <FormControl
                  componentClass="select"
                  placeholder="- Seleccionar Conexion -"
                  name="sourceTypeSelected"
                  value={this.state.statusName}
                  onChange={this.onChangeState}
                >
                   <option value="1">Activo</option>
                   <option value="0">Inactivo</option>
                </FormControl>
            </Col> 
            </Row>
            </Col>  
            <Col sm={3}>  
            <Row>
            <Col sm={8}>  
              <StyleButton currentStyles={this.props.currentStyles} bsStyle="primary" 
                  onClick={ () => { this.filter(); }}>
                  <FormattedMessage id="campaigReporte.form.generate" defaultMessage=''></FormattedMessage>
              </StyleButton>
            </Col> 
            </Row>
            </Col>  

            </Row>
            
            </Col>
            <Col sm={12} className="text-right"> 
            <br />
            <DropdownButton
      bsSize="small"
      title={this.context.intl.formatMessage({id: 'campaigReporte.button.export.title'})}
      id="dropdown-size-large"
    >
      <MenuItem eventKey="1" onClick={ () => { this.export("PDF"); } }>PDF</MenuItem>
      <MenuItem eventKey="2" onClick={ () => { this.export("XLS"); } }>XLS</MenuItem>
      <MenuItem eventKey="3" onClick={ () => { this.export("CSV"); } }>CSV</MenuItem>
    </DropdownButton>
            </Col>
       <Col sm={12}>
       <br/>  
       
       <BootstrapTable
          striped
          condensed
          bordered
          data={this.state.campaignData}
          search
          searchPlaceholder={this.context.intl.formatMessage({id: 'table.button.search.placeholder'})}
          selectRow={selectRowProp}
          pagination
          options={this.options}
        >
          <TableHeaderColumn
            dataField="idStb"
            isKey
            searchable={false}
            hidden
            width="10%"
            hiddenOnInsert
          >
            idStb
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="idStb"
            dataSort
            width="5%"
            dataAlign="left"
            headerAlign="left"
          >
           ID
          </TableHeaderColumn> 
          <TableHeaderColumn
            dataField="campaignName"
            dataSort
            width="10%"
            dataAlign="left"
            headerAlign="left"
          >
          < FormattedMessage id="campaigReporte.grid.header.name" defaultMessage=''></FormattedMessage>
          </TableHeaderColumn> 
          <TableHeaderColumn
            dataField="freeViewName"
            dataSort
            width="10%"
            dataAlign="left"
            headerAlign="left"
          >
           Freeview 
          </TableHeaderColumn> 
          <TableHeaderColumn
            dataField="comercialZone"
            dataSort
            width="10%"
            dataAlign="left"
            headerAlign="left"
          >
           < FormattedMessage id="campaigReporte.grid.header.comercialZone" defaultMessage=''></FormattedMessage>
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="campaignIni"
            dataFormat={this.showDateIni}
            dataSort
            width="10%"
            dataAlign="left"
            headerAlign="left"
          >
           < FormattedMessage id="campaigReporte.grid.header.campaignIni" defaultMessage=''></FormattedMessage>
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="canpmaignEnd"
            dataFormat={this.showDateEnd}
            dataSort
            width="10%"
            dataAlign="left"
            headerAlign="left"
          >
           < FormattedMessage id="campaigReporte.grid.header.canpmaignEnd" defaultMessage=''></FormattedMessage>
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="operadora"
           
            dataSort
            width="10%"
            dataAlign="left"
            headerAlign="left"
          >
           < FormattedMessage id="campaigReporte.grid.header.carrier" defaultMessage=''></FormattedMessage>
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="status"
            dataSort
            width="10%"
            dataAlign="left"
            headerAlign="left"
          >
           < FormattedMessage id="campaigReporte.grid.header.state" defaultMessage=''></FormattedMessage>
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="idClient"
            dataSort
            width="10%"
            dataAlign="rigth"
            headerAlign="left"
          >< FormattedMessage id="campaigReporte.grid.header.idClient" defaultMessage=''></FormattedMessage>
           
          </TableHeaderColumn>
        </BootstrapTable>
       </Col>
       </Row>
       
        <br />
      </StyledPanel>
    );
  }
}

LurinCampaignReportContainer.propTypes = {
};

const mapStateToProps = createStructuredSelector({
  currentStyles: makeSelectCurrentStyle(),
  campaignData:makeSelectCampaignData(),
  carriers: makeSelectedCarriers(),
  campaignStatus:makeSelectCampaignStatusData(),
  technologies: makeSelectTechnologiesData(),
  freeviewsData: makeSelectfreeviewsData(),
  campaingsData: makeSelectCampaingsData(),
  campaignExportData:makeSelectCampaignExportData(),
  download: makeSelectDownload()
});

function mapDispatchToProps(dispatch) {
  return {
    loadCampaignReportData: (idCarrier, filter) => dispatch(actions.loadCampaignReportData(idCarrier, filter)),
    loadCampaignExportReportData: (idCarrier, filter) => dispatch(actions.loadCampaignExportReportData(idCarrier, filter)),
    loadCarriers: () => dispatch(generalActions.loadCarriers()),
    loadCampaignStatus: () => dispatch(generalActions.loadCampaignStatus()),
    loadTechnology: (idCarrier) => dispatch(generalActions.loadTechnology(idCarrier)),
    loadFreeviews: (idCarrier) => dispatch(generalActions.loadFreeviews(idCarrier)),
    loadCampaings: (carrierId) => dispatch(generalActions.loadCampaings(carrierId)),
    initDownload: () => dispatch(actions.initDownload()),
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinCampaignReportContainer);
