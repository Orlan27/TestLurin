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

import {
  makeSelectCurrentStyle,
  makeSelectedCarriers,
} from '../LurinFvAppContainer/selectors';
import {
  makeSelectFreeviewData,
  makeSelectLoading,
  makeSelectError,
  makeSelectShowMessage,
  makeSelectMessage,
  makeSelectMessageType,
  makeSelectDownload,
  makeSelectFreeviewExportData
} from './selectors';
import * as actions from './actions';
import * as generalActions from '../LurinFvAppContainer/actions';
import {
  StyledPanel,
  StyleForm
} from './styles';

export class LurinFreeviewReportContainer extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      freeviewData: [],
      alertVisible: false,
      alertStyle: "success",
      alertMessage: '',
      showModal:false,
      carrierSelected:'',
      statusSelected:'A'
    };  
    this.options = {
      printToolBar: false,
      defaultSortName: 'id',
      defaultSortOrder: 'desc',
      noDataText: <FormattedMessage id="table.nodata" defaultMessage='No se han encontrado registros para mostrar '></FormattedMessage>,
    };
    this.handleAlertDismiss = this.handleAlertDismiss.bind(this);
    this.handleSelect = this.handleSelect.bind(this);
    
  }

  static contextTypes = {
    intl: intlShape,
 }

  componentDidMount() {

    //this.props.loadFreeviewReportData();
    this.props.loadCarriers();
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.freeviewData.length > 0) {
      this.setState({ freeviewData: nextProps.freeviewData });
    } else {
      this.setState({ freeviewData: [] });
    }
    if (nextProps.carriers.length > 0 && this.props.carriers!= nextProps.carriers) {
      this.setState({ carrierSelected: nextProps.carriers[0].code});
      this.props.loadFreeviewReportData(nextProps.carriers[0].code,this.state.statusSelected);
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


  onChangeCarrier = (evt) => {
    this.setState({ carrierSelected: evt.target.value });
    this.props.loadFreeviewReportData(this.state.carrierSelected,this.state.statusSelected);
  }
  
  onChangeStatus = (evt) => {
    this.setState({ statusSelected: evt.target.value });
    this.props.loadFreeviewReportData(this.state.carrierSelected,this.state.statusSelected);
  }
  
  handleAlertDismiss() {
    this.setState({ alertVisible: false });
  };

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

  handleSelect = (value) => {
    this.props.loadFreeviewExportReportData(this.state.carrierSelected,this.state.statusSelected,value);
  };


  showCreateDate(cell, row) {
 
    return (cell) ? new Date(cell).toLocaleDateString("en-US") : cell;
  }
  showModifyDate(cell, row) {
    return (cell) ? new Date(cell).toLocaleDateString("en-US") : cell;
  }

  render() {
    const selectRowProp = {
    };

    let carrierOptionTemplate = this.props.carriers.map(carrier => (
      <option key={carrier.code} value={carrier.code}>{carrier.name}</option>
    ));

    let freeviewStatesIdioma = [
      { code: '1', name: this.context.intl.formatMessage({id: 'freeview.form.input.status.active.value'}), status: 'A' },
      { code: '2', name: this.context.intl.formatMessage({id: 'freeview.form.input.status.inactive.value'}), status: 'I' },
    ];
    let statusOptionTemplate = freeviewStatesIdioma.map(freeviewState => (
      <option key={freeviewState.code} value={freeviewState.code}>{freeviewState.name}</option>
    ));

    if(this.props.download){  
      var file_path = `data:${this.props.freeviewExportData.contentType};base64,${this.props.freeviewExportData.report}`;
      var a = document.createElement('A');
      a.href = file_path;
      a.download = `freeview.${this.getExtension(this.props.freeviewExportData.contentType)}`;
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
      header={<FormattedMessage id="freeviewReport.form.header" ></FormattedMessage>} bsStyle="primary">
       {this.renderAlert()}
       <br/>
       <Row>
            <Col sm={3} xs={6}>  
            <Row>
            <Col sm={4}>  
            Carrier 
            </Col>  
            <Col sm={8}>  
            <FormControl
                  componentClass="select"
                  placeholder="- Seleccionar Carrier -"
                  name="carrierSelected"
                  value={this.state.carrierSelected}
                  onChange={this.onChangeCarrier}
                >
                   {carrierOptionTemplate}
                </FormControl>
            </Col> 
            </Row>
            </Col>  
            <Col sm={3}>  
            <Row>
            <Col sm={4}>  
            Status
            </Col>  
            <Col sm={8}>  
            <FormControl
                  componentClass="select"
                  placeholder="- Seleccionar status -"
                  name="statusSelected"
                  value={this.state.statusSelected}
                  onChange={this.onChangeStatus}
                >
                  {statusOptionTemplate}
                </FormControl>
            </Col> 
            </Row> 
            </Col> 
            <Col sm={12} className="text-right"> 
            <br />
            <DropdownButton
      bsSize="small"
      title={this.context.intl.formatMessage({id: 'freeviewReport.button.export.title'})}
      id="dropdown-size-large"
      onSelect={this.handleSelect} 
    >
      <MenuItem eventKey="PDF">PDF</MenuItem>
      <MenuItem eventKey="XLS">XLS</MenuItem>
      <MenuItem eventKey="CSV">CSV</MenuItem>
    </DropdownButton>
            </Col>
       <Col sm={12}>
       <br/>  
       
       <BootstrapTable
          striped
          condensed
          bordered
          data={this.state.freeviewData}
          search
          searchPlaceholder={this.context.intl.formatMessage({id: 'table.button.search.placeholder'})}
          selectRow={selectRowProp}
          pagination
          options={this.options}
        >
          <TableHeaderColumn
            dataField="id"
            isKey
            searchable={false}
            hidden
            width="10%"
            hiddenOnInsert
          >
            ID
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="id"
            dataSort
            width="5%"
            dataAlign="left"
            headerAlign="left"
          >
           ID
          </TableHeaderColumn> 
          <TableHeaderColumn
            dataField="freeViewName"
            dataSort
            width="10%"
            dataAlign="left"
            headerAlign="left"
          >
          < FormattedMessage id="freeviewReport.grid.header.name" defaultMessage=''></FormattedMessage>
          </TableHeaderColumn> 
          <TableHeaderColumn
            dataField="tecnologyName"
            dataSort
            width="10%"
            dataAlign="left"
            headerAlign="left"
          >
           < FormattedMessage id="freeviewReport.grid.header.tecnologyName" defaultMessage=''></FormattedMessage> 
          </TableHeaderColumn> 
          <TableHeaderColumn
            dataField="chanel"
            dataSort
            width="10%"
            dataAlign="left"
            headerAlign="left"
          >
           < FormattedMessage id="freeviewReport.grid.header.channel" defaultMessage=''></FormattedMessage>
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="createDate"
            dataFormat={this.showCreateDate}
            dataSort
            width="10%"
            dataAlign="left"
            headerAlign="left"
          >
           < FormattedMessage id="freeviewReport.grid.header.creationDate" defaultMessage=''></FormattedMessage>
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="userCreate"
            dataSort
            width="10%"
            dataAlign="left"
            headerAlign="left"
          >
           < FormattedMessage id="freeviewReport.grid.header.userCreate" defaultMessage=''></FormattedMessage>
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="modifyDate"
            dataFormat={this.showModifyDate}
            dataSort
            width="10%"
            dataAlign="left"
            headerAlign="left"
          >
           < FormattedMessage id="freeviewReport.grid.header.modifyDate" defaultMessage=''></FormattedMessage>
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="userModify"
            dataSort
            width="10%"
            dataAlign="left"
            headerAlign="left"
          >
           < FormattedMessage id="freeviewReport.grid.header.userModify" defaultMessage=''></FormattedMessage>
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="status"
            dataSort
            width="10%"
            dataAlign="rigth"
            headerAlign="left"
          >< FormattedMessage id="freeviewReport.grid.header.status" defaultMessage=''></FormattedMessage>
           
          </TableHeaderColumn>
        </BootstrapTable>
       </Col>
       </Row>
       
        <br />
      </StyledPanel>
    );
  }
}

LurinFreeviewReportContainer.propTypes = {
};

const mapStateToProps = createStructuredSelector({
  currentStyles: makeSelectCurrentStyle(),
  freeviewData:makeSelectFreeviewData(),
  carriers: makeSelectedCarriers(),
  download: makeSelectDownload(),
  freeviewExportData:makeSelectFreeviewExportData()
});

function mapDispatchToProps(dispatch) {
  return {
    loadFreeviewReportData: (carrierId, statusId) => dispatch(actions.loadFreeviewReportData(carrierId, statusId)),
    loadFreeviewExportReportData: (carrierId, statusId, type) => dispatch(actions.loadFreeviewExportReportData(carrierId, statusId, type)),
    loadCarriers: () => dispatch(generalActions.loadCarriers()),
    initDownload: () => dispatch(actions.initDownload()),
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinFreeviewReportContainer);
