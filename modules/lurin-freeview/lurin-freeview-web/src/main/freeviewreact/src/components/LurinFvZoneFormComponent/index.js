/**
*
* LurinFvZoneFormComponent
*
*/

import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import Select from 'react-select';
import {
  Form,
  FormControl,
  FormGroup,
  Col,
  ControlLabel,
  Button,
} from 'react-bootstrap';
import inputErrorMessage from '../../utils/inputErrorMessage';

import {
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
} from '../../containers/LurinFvMaintenanceZoneContainer/selectors';
import {
  showFormZones,
} from '../../containers/LurinFvMaintenanceZoneContainer/actions';
import reducer from '../../containers/LurinFvMaintenanceZoneContainer/reducer';

import {
  StyleButton,
  StyledPanel
} from './styles';


export class LurinFvZoneFormComponent extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      title: (this.props.isNewRegister) ? 'Agregar Nueva Zona' : 'Editar Zona',
      zone: this.props.zoneData.zone,
      commercialZone: this.props.zoneData.commercialZone,
      status: this.props.zoneData.status,
      technology :this.props.zoneData.technology,
      packages:this.props.zoneData.packages,
      error: null,
    };

    this.handleCancelEditZone = this.handleCancelEditZone.bind(this);
    this.onChange = this.onChange.bind(this);
  }
 
  handleCancelEditZone = () => {
  this.props.showFormZones(false);
  }

  onChange = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value });
  }

  render() {
    const packages = [
      { label: 'Paquete 1', value: 'Paquete 1' },
      { label: 'Paquete 2', value: 'Paquete 2' },
      { label: 'Paquete 3', value: 'Paquete 3' },
      { label: 'Paquete 4', value: 'Paquete 4' },
      { label: 'Paquete 5', value: 'Paquete 5' },
      { label: 'Paquete 6', value: 'Paquete 6' },
      { label: 'Paquete 7', value: 'Paquete 7' },
      { label: 'Paquete 8', value: 'Paquete 8' },
      { label: 'Paquete 9', value: 'Paquete 9' },
      { label: 'Paquete 10', value: 'Paquete 10' },
    ];

    return (
      <StyledPanel header={this.state.title} bsStyle="primary">
        <Form horizontal>
          {/* <FormGroup>
        <Col smOffset={2} sm={10}>
          <label className="message" htmlFor="message"> {props.errorData.message}</label>
        </Col>
      </FormGroup>*/}
          <FormGroup className="form-group-name" controlId="formValidationErrorNombre" validationState={inputErrorMessage('commercialZone', this.state.error) ? 'error' : null} >
            <Col sm={2}>
              <h5>Nombre</h5>
            </Col>
            <Col sm={10}>
              <FormControl
                type="text"
                placeholder="Nombre de la zona"
                onChange={this.onChange}
                name="commercialZone"
                value={this.state.commercialZone}
              />
              <ControlLabel>{inputErrorMessage('commercialZone', this.state.error)}</ControlLabel>
            </Col>
          </FormGroup>
          <FormGroup controlId="formValidationError1" validationState={inputErrorMessage('commercialZone', this.state.error) ? 'error' : null} >
            <Col sm={2}>
              <h5 className="package-title">Paquetes</h5>
            </Col>
            <Col sm={10}>
              <Select
                className="package"
                closeOnSelect
                disabled={false}
                multi
                onChange={this.props.handleSelectPackage}
                options={packages}
                placeholder="Seleccionar"
                removeSelected={false}
                rtl={false}
                simpleValue
                value={this.props.packagesValue}
              />
              <ControlLabel>{inputErrorMessage('commercialZone', this.state.error)}</ControlLabel>
            </Col>
          </FormGroup>
          <FormGroup controlId="formHorizontalTech">
            <Col sm={2}>
              Tecnología
            </Col>
            <Col sm={10}>
              <FormControl
                componentClass="select"
                placeholder="Seleccionar"
                name="tech"
              >
                <option value="0">DTH</option>
                <option value="1">CATV</option>
              </FormControl>
            </Col>
          </FormGroup>
          <FormGroup controlId="formHorizontalStatus">
            <Col sm={2}>
              Estado
            </Col>
            <Col sm={10}>
              <FormControl
                componentClass="select"
                placeholder="Seleccionar"
                name="status"
                value={this.state.status}
                onChange={this.onChange}
              >
                <option value="A">A</option>
                <option value="I">I</option>
              </FormControl>
            </Col>
          </FormGroup>
          <FormGroup>
            <Col xs={3} xsOffset={1} sm={2} smOffset={5}>
              <StyleButton bsStyle="primary" onClick={ ()=>this.props.save(this.state)}>
                Guardar
          </StyleButton>
            </Col>
            <Col xs={3} sm={3}>
              <Button onClick={() => { this.handleCancelEditZone(); }}>
                Cancelar
            </Button>
            </Col>
            <Col xs={5} sm={2}>
            </Col>
          </FormGroup>
        </Form>
      </StyledPanel>
    );
  }
}

LurinFvZoneFormComponent.propTypes = {
  showFormZones: PropTypes.func,
  isNewRegister: PropTypes.bool,
  zoneData: PropTypes.object,
  save: PropTypes.func,
  packagesValue: PropTypes.string,
  handleSelectPackage: PropTypes.func,

};

const mapStateToProps = createStructuredSelector({
  loading: makeSelectLoading(),
  error: makeSelectError(),
  hasShowForm: makeSelectHasShowForm(),
});

function mapDispatchToProps(dispatch) {
  return {
    showFormZones: (hasShowForm) => dispatch(showFormZones(hasShowForm))    
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinFvZoneFormComponent);



//export default LurinFvZoneFormComponent;

// const mapStateToProps = createStructuredSelector({
//   lurinFvMaintenanceZoneContainer: makeSelectLurinFvMaintenanceZoneContainer(),
//   loading: makeSelectLoading(),
//   error: makeSelectError(),
//   hasShowForm: makeSelectHasShowForm(),
// });

// function mapDispatchToProps(dispatch) {
//   return {
//     showFormZones: (hasShowForm) => dispatch(showFormZones(hasShowForm)),

//   };
// }

// const withConnect = connect(mapStateToProps, mapDispatchToProps);

// const withReducer = injectReducer({ key: 'LurinFvZoneFormComponent', reducer });

// export default compose(
//   withReducer,
//   withConnect,
// )(LurinFvZoneFormComponent);
