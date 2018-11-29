/**
*
* LurinFvFreeviewFormComponent
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
import { FormattedMessage, intlShape  } from 'react-intl';
import {
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
} from '../../containers/LurinFvFreeviewManagmentContainer/selectors';
import {
  showFormFreeviews,
  initMessage,
} from '../../containers/LurinFvFreeviewManagmentContainer/actions';
import reducer from '../../containers/LurinFvFreeviewManagmentContainer/reducer';

import {
  StyleButton,
  StyledPanel
} from './styles';


export class LurinFvFreeviewFormComponent extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      title: (this.props.isNewRegister) ? <FormattedMessage id="freeview.form.add.title" defaultMessage='Agregar nuevo Freeview'></FormattedMessage> : 
      <FormattedMessage id="freeview.form.edit.title" defaultMessage='Editar Freeview'></FormattedMessage>,
      freeViewId: this.props.freeviewsData.freeViewId,
      name: this.props.freeviewsData.name,
      status: this.props.freeviewsData.status,
      statusSelect: this.props.freeviewsData.status.code,
      technology: this.props.freeviewsData.categoryTechnologies,
      technologySelect: this.props.freeviewsData.categoryTechnologies.code,
      packagesValue: this.props.freeviewsData.channelPackages,
      error: null,
      pressSaveButton:false
    };
    
    this.save = this.save.bind(this);
    this.onChange = this.onChange.bind(this);
    this.handleSelectPackage = this.handleSelectPackage.bind(this);
    this.handleCancelEditFreeview= this.handleCancelEditFreeview.bind(this);
  }

  static contextTypes = {
    intl: intlShape,
 }
  hasInvalidData() {
    if (this.state.name.length == 0) {
      return true;
    }
    if (!this.state.packagesValue.length) {
      return true;
    }
    if (this.state.technologySelect==0) {
      return true;
    }

    return false
  }
 
  save = () => {
    this.setState({ pressSaveButton: true });
    if(!this.hasInvalidData())
      this.props.save(this.state);
   }

   
   handleCancelEditFreeview = () => {
        this.props.showFormFreeviews();
       this.props.initMessage();
    }

  onChange = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value });
  }

  onChangeStatus = (evt) => {
    const index = freeviewStates.findIndex(i => i.code.toString()=== evt.target.value);
    this.setState({ [evt.target.name]: evt.target.value, status: freeviewStates[index] });
  }

  onChangeTechnology = (evt) => {
    const index = this.props.technologies.findIndex(i => i.code.toString()=== evt.target.value);
    this.setState({ [evt.target.name]: evt.target.value, technology: this.props.technologies[index] });
  }

  handleSelectPackage = (value) => {
    this.setState({ packagesValue: value });
  }

  getValidationState(field) {
    if((!this.state[field]  || this.state[field]=='0' ) && this.state.pressSaveButton){
      return 'error';
    }
    return null; 
  
  }
  getMessageValidation(field) {
    if((!this.state[field]  || this.state[field]=='0' ) && this.state.pressSaveButton){
      return <FormattedMessage id="form.required.field" defaultMessage='Campo Requerido'></FormattedMessage>;
    }
    return null; 
  }

  render() {

    let freeviewStatesIdioma = [
      { code: '1', name: this.context.intl.formatMessage({id: 'freeview.form.input.status.active.value'}), status: 'A' },
      { code: '2', name: this.context.intl.formatMessage({id: 'freeview.form.input.status.inactive.value'}), status: 'I' },
    ];
    let statusOptionTemplate = freeviewStatesIdioma.map(freeviewState => (
      <option key={freeviewState.code} value={freeviewState.code}>{freeviewState.name}</option>
    ));

    let technologiesOptionTemplate = this.props.technologies.map(technology => (
      <option key={technology.code} value={technology.code}>{technology.name}</option>
    ));

    return (
      <StyledPanel currentStyles={this.props.currentStyles} className="custom-primary-bg" header={this.state.title} bsStyle="primary">
        <Form horizontal>
          {/* <FormGroup>
        <Col smOffset={2} sm={9}>
          <label className="message" htmlFor="message"> {props.errorData.message}</label>
        </Col>
      </FormGroup>*/}
          <FormGroup className="form-group-name" controlId="formValidationErrorNombre" 
          validationState={this.getValidationState('name')} 
          >
            <Col sm={3}>
            <h5><FormattedMessage id="freeview.grid.header.name" defaultMessage='Nombre'></FormattedMessage></h5>
            </Col>
            <Col sm={2}>
            <FormControl
                type="text"
                name="prefix"
                value={ (this.props.currentCarrier.namePrefix!==undefined ? this.props.currentCarrier.namePrefix : '')+(this.state.technology.code>0 ? '_' +this.state.technology.name: '')}
                maxLength={20}
                readOnly
              />
            </Col>
            <Col sm={7}>
              <FormControl
                type="text"
                placeholder="Freeview"
                onChange={this.onChange}
                name="name"
                value={this.state.name}
                maxLength={100}
              />
              <ControlLabel>{this.getMessageValidation('name')} </ControlLabel>
            </Col>
          </FormGroup>
          <FormGroup controlId="formHorizontalTech"
           validationState={this.getValidationState('technologySelect')}
          >
            <Col sm={3}>
            <FormattedMessage id="form.input.title.technology" defaultMessage='technology'></FormattedMessage>
            </Col>
            <Col sm={9}>
            <FormControl
                componentClass="select"
                placeholder="Seleccionar"
                name="technologySelect"
                value={this.state.technologySelect}
                onChange={this.onChangeTechnology}
              >
                {technologiesOptionTemplate}
              </FormControl>
              <ControlLabel>{this.getMessageValidation('technologySelect')} </ControlLabel>
            </Col>
          </FormGroup>
          <FormGroup controlId="formValidationErrorPackage"
          validationState={this.getValidationState('packagesValue')}
          >
            <Col sm={3}>
              <h5 className="package-title"><FormattedMessage id="form.input.title.packages" 
              defaultMessage='packages'></FormattedMessage></h5>
            </Col>
            <Col sm={9}>
              <Select
                name="input-package"
                className={this.getValidationState('packagesValue') =='error'? 'package-error': "package" }
                closeOnSelect
                disabled={false}
                autosize={false}
                multi
                options={this.props.packages}
                placeholder={<FormattedMessage id="combobox.first.register" defaultMessage='Selecciar'></FormattedMessage>}
                removeSelected={false}
                rtl={false}
                simpleValue
                value={this.state.packagesValue}
                onChange={this.handleSelectPackage}
              />
               <ControlLabel className="label-package" >{this.getMessageValidation('packagesValue')} </ControlLabel>
            </Col>
          </FormGroup>
          <FormGroup controlId="formHorizontalStatus">
            <Col sm={3}>
            <FormattedMessage id="form.input.title.status" defaultMessage='state'></FormattedMessage>
            </Col>
            <Col sm={9}>
              <FormControl
                componentClass="select"
                placeholder="Seleccionar"
                name="statusSelect"
                value={this.state.statusSelect}
                onChange={this.onChangeStatus}
              >
                {statusOptionTemplate}
              </FormControl>
            </Col>
          </FormGroup>
          <FormGroup >
          <Col sm={3}>
         </Col>
         <Col sm={9}>
         <StyleButton currentStyles={this.props.currentStyles} className="custom-save-bg" bsStyle="primary" 
                onClick={ () => { this.save(); }}>
                <FormattedMessage id="form.button.save" defaultMessage='Save'></FormattedMessage>
              </StyleButton>

              <Button className="custom-cancel-bg" onClick={() => { this.handleCancelEditFreeview(); }}>
              <FormattedMessage id="form.button.cancel" defaultMessage='Cancelar'></FormattedMessage>
            </Button>
         </Col>
            </FormGroup>
        </Form>
      </StyledPanel>
    );
  }
}

const freeviewStates = [
  { code: '1', name: 'Activo', status: 'A' },
  { code: '2', name: 'Inactivo', status: 'I' },
];

LurinFvFreeviewFormComponent.propTypes = {
  showFormFreeviews: PropTypes.func,
  isNewRegister: PropTypes.bool,
  freeviewsData: PropTypes.object,
  array: PropTypes.object,
  save: PropTypes.func,
  currentStyles: PropTypes.object,
};

const mapStateToProps = createStructuredSelector({
  loading: makeSelectLoading(),
  error: makeSelectError(),
  hasShowForm: makeSelectHasShowForm(),
});

function mapDispatchToProps(dispatch) {
  return {
    showFormFreeviews: (hasShowForm) => dispatch(showFormFreeviews(hasShowForm)),  
    initMessage:() => dispatch(initMessage())    
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinFvFreeviewFormComponent);
