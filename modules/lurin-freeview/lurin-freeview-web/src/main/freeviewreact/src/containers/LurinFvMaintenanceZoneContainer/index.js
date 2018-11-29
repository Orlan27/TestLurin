/**
 *
 * LurinFvMaintenanceZoneContainer
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
  Button
} from 'react-bootstrap';

import LurinFvZoneFormComponent from '../../components/LurinFvZoneFormComponent';
import {
  makeSelectcommercialZonesData,
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm
} from './selectors';
import * as actions from './actions';

import {
  StyledPanel
} from './styles';
import { debug } from 'util';

export class LurinFvMaintenanceZoneContainer extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      allCommercialZones: [],
      isNewRegister: false,
      isShowZoneForm: false,
      zoneData: {
        commercialZone: "",
        packages: "",
        status :"",
        technology :"",
        zone:0
      },
      catalogStatus: [{
        code: "1",
        name: "Active",
        status: "I"
        },{
        code: "2",
        name: "INACTIVE",
        status: "I"
        }]
      }

    this.options = {
      defaultSortName: 'commercialZone',
      defaultSortOrder: 'desc',
      noDataText: 'No se han encontrado registros para mostrar.',
      handleConfirmDeleteRow: this.customConfirm,
      insertBtn: this.createCustomInsertButton,
      onDeleteRow: this.delete,
    };
    this.handleEditZone = this.handleEditZone.bind(this);
    this.handleNewZoneClick = this.handleNewZoneClick.bind(this);
    this.save= this.save.bind(this);
  }

  componentDidMount() {
    this.props.loadCommercialZones();
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.commercialZonesData.length > 0) {
      this.setState({ allCommercialZones: nextProps.commercialZonesData });
    } else {
      this.setState({ allCommercialZones: [] });
    }

    if (nextProps.hasShowForm !== this.props.hasShowForm) {
      this.setState({ isShowZoneForm: nextProps.hasShowForm });
    }
  }

  handleEditZone = (selectedZone) => {
    if (this.props.hasShowForm) {
      this.props.showFormZones(false);
      setTimeout(() => {
        this.props.showFormZones(true);
      }, 300);
    } else {
      this.props.showFormZones(true);
    }
    this.setState({
      isNewRegister: false,
      zoneData: selectedZone,
    });
  }

  customConfirm = (next, dropRowKeys) => {
    if (confirm('Esta seguro que desea eliminar el registro?')) {
      // If the confirmation is true, call the function that
      // continues the deletion of the record.
      next();
    }
  }


  createCustomInsertButton = () => (
    <InsertButton
      btnText="Nueva"
      onClick={() => this.handleNewZoneClick()}
    />
  )

  editFormatter = (cell, row) => (
    <Button bsSize="xsmall" onClick={() => { this.handleEditZone(row); }}><Glyphicon glyph="pencil" /></Button>
  );


  save = (currentState) => {
    /*const currentCommercialZone = {
      zone: currentState.zone,
      commercialZone: currentState.commercialZone,
      status: currentState.status,
      packages: currentState.packages,
      technology:currentState.technology,
    };*/
    const currentCommercialZone = {
      zone: currentState.zone,
      commercialZone: currentState.commercialZone,
      status: this.state.catalogStatus.find(function(val){ return val.status==currentState.status })
    };
    if (this.state.isNewRegister) {
      this.props.createCommercialZones(currentCommercialZone);
    } else {
      this.props.updateCommercialZones(currentCommercialZone);
    }
  }

  delete = () => {
    if (!this.state.zoneData.zone) return;
    this.props.deleteCommercialZones(this.state.zoneData.zone);
  }

  onSelect = (data) => {
    var currentZone=this.state.zoneData;
    currentZone.zone=data.zone;
    this.setState({
      zoneData: currentZone,
    });
  }

  handleSelectPackage = (value) => {
    this.setState({ packagesValue: value });
  }

  handleNewZoneClick = () => {
    if (this.props.hasShowForm) {
      this.props.showFormZones(false);
      setTimeout(() => {
        this.props.showFormZones(true);
      }, 300);
    } else {
      this.props.showFormZones(true);
    }
    this.setState({
      isNewRegister: true,
      zoneData: {
        zone: 0,
        commercialZone: '',
        status: '',
      },
    });
  }
  
  showStatusDescription(cell, row) {
	  return cell.status;
  }

  renderZoneForm() {
    if (this.state.isShowZoneForm) {
      return (
        <LurinFvZoneFormComponent
          isNewRegister={this.state.isNewRegister}
          zoneData={this.state.zoneData}
          packagesValue={this.state.packagesValue}
          handleSelectPackage={this.handleSelectPackage}
          onChange={this.onChange}
          save={this.save}
        />
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
      <StyledPanel header="Mantenimiento de Zonas" bsStyle="primary">
        <BootstrapTable
          striped
          condensed
          bordered
          data={this.state.allCommercialZones}
          search
          searchPlaceholder="Búsqueda..."
          selectRow={selectRowProp}
          pagination
          insertRow
          deleteRow
          options={this.options}
        >
          <TableHeaderColumn
            dataField="zone"
            isKey
            searchable={false}
            hidden
            hiddenOnInsert
          >
            ZoneId
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="commercialZone"
            dataSort
            width="10%"
            dataAlign="center"
            headerAlign="center"
          >
            Zona
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="packages"
            dataSort
            width="40%"
            dataAlign="center"
            headerAlign="center"
          >
            Paquetes
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="technology"
            dataSort
            width="20%"
            dataAlign="center"
            headerAlign="center"
          >
            Tecnología
          </TableHeaderColumn>
          <TableHeaderColumn
            dataField="status"
            dataFormat={this.showStatusDescription}
            dataSort
            width="20%"
            dataAlign="center"
            headerAlign="center"
          >
            Estado
          </TableHeaderColumn>
          <TableHeaderColumn
            dataFormat={this.editFormatter}
            width="10%"
            dataAlign="center"
            headerAlign="center"
          >
            Editar
          </TableHeaderColumn>
        </BootstrapTable>
        <br />
        {this.renderZoneForm()}
      </StyledPanel>
    );
  }
}

LurinFvMaintenanceZoneContainer.propTypes = {
  loadCommercialZones: PropTypes.func,
  showFormZones: PropTypes.func,
  hasShowForm: PropTypes.bool,
  createCommercialZones: PropTypes.func,
  updateCommercialZones: PropTypes.func,
  isNewRegister: PropTypes.bool,
  deleteCommercialZones: PropTypes.func
};

const mapStateToProps = createStructuredSelector({
  loading: makeSelectLoading(),
  error: makeSelectError(),
  commercialZonesData: makeSelectcommercialZonesData(),
  hasShowForm: makeSelectHasShowForm()
});

function mapDispatchToProps(dispatch) {
  return {
    loadCommercialZones: () => dispatch(actions.loadCommercialZones()),
    showFormZones: (hasShowForm) => dispatch(actions.showFormZones(hasShowForm)),
    createCommercialZones: (newCommercialZone) => dispatch(actions.createCommercialZones(newCommercialZone)),
    updateCommercialZones: (commercialZoneToUpdate) => dispatch(actions.updateCommercialZones(commercialZoneToUpdate)),
    deleteCommercialZones: (commercialZoneId) => dispatch(actions.deleteCommercialZones(commercialZoneId))
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinFvMaintenanceZoneContainer);
