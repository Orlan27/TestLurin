/**
*
* LurinFvGlobalParameterFormComponent
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
} from '../../containers/LurinFvGlobalParameterContainer/selectors';
import {
  showForm,
  initMessage
} from '../../containers/LurinFvGlobalParameterContainer/actions';
import reducer from '../../containers/LurinFvGlobalParameterContainer/reducer';

import {
  StyleButton,
  StyledPanel
} from './styles';

export class LurinFvGlobalParameterFormComponent extends React.PureComponent {

  constructor(props) {  
    super(props);
   
    this.state = {
      title: (this.props.isNewRegister) ? <FormattedMessage id="globalParameter.form.add.title" 
      defaultMessage=''></FormattedMessage> : 
      <FormattedMessage id="globalParameter.form.edit.title" 
      defaultMessage=''></FormattedMessage>,
      key:this.props.globalParametersData.key,
      value :this.props.globalParametersData.value,
      description :this.props.globalParametersData.description,
      category :this.props.globalParametersData.category,
      categorySelected :this.props.globalParametersData.category.code,
      error: null,
      pressSaveButton:false
    };

    this.handleCancelEditGlobalParameter = this.handleCancelEditGlobalParameter.bind(this);
    this.save = this.save.bind(this);
    this.onChange = this.onChange.bind(this);
  }

  static contextTypes = {
    intl: intlShape,
  }

  hasInvalidData() {
    if (!this.state.value) {
      return true;
    }
    if (!this.state.description) {
      return true;
    }
    if (!this.state.categorySelected) {
      return true;
    }
    return false
  }

  save = () => {
    this.setState({ pressSaveButton: true });
    if(!this.hasInvalidData())
      this.props.save(this.state);
  }
 
  handleCancelEditGlobalParameter = () => {
   this.props.showForm(false);
   this.props.initMessage();
  }
   
  onChange = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value });
  }

  onChangeCategory = (evt) => {
    const index = this.props.gpCategories.findIndex(i => i.code.toString() === evt.target.value);
    
    this.setState({ [evt.target.name]: evt.target.value, category: this.props.gpCategories[index] });
  }

  getValidationState(field) {
    if((!this.state[field]) && this.state.pressSaveButton){
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

  render() {
    let categoriesOptionTemplate = this.props.gpCategories.map(i => (
      <option key={i.code} value={i.code}>{i.name}</option>
    ));

    return (
      <StyledPanel currentStyles={this.props.currentStyles} header={this.state.title} bsStyle="primary">
        <Form horizontal>
          {/* <FormGroup>
        <Col smOffset={3} sm={9}>
          <label className="message" htmlFor="message"> {props.errorData.message}</label>
        </Col>
      </FormGroup>*/}
      <FormGroup controlId="formHorizontalKey">
         <Col sm={3}>
           Key
         </Col>
         <Col sm={9}>
              <FormControl
                type="text"
                placeholder="key"
                name="key"
                readOnly
                onChange={this.onChange}
                value={this.state.key}
              />
         </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalValue"
        validationState={this.getValidationState('value')} 
       >
         <Col sm={3}>
         <FormattedMessage id="globalParameter.form.input.value.title" defaultMessage=''></FormattedMessage>
         </Col>
         <Col sm={9}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({id: 'globalParameter.form.input.value.placeholder'})}
                name="value"
                onChange={this.onChange}
                value={this.state.value}
              />
          <ControlLabel>{this.getMessageValidation('value')} </ControlLabel>
         </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalDescription"
       validationState={this.getValidationState('description')} 
       >
         <Col sm={3}>
         <FormattedMessage id="globalParameter.form.input.description.title" defaultMessage=''></FormattedMessage>
         </Col>
         <Col sm={9}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({id: 'globalParameter.form.input.description.placeholder'})}
                name="description"
                onChange={this.onChange}
                value={this.state.description}
              />
          <ControlLabel>{this.getMessageValidation('description')} </ControlLabel>
         </Col>
       </FormGroup>
       {/* <FormGroup controlId="formHorizontalDescription"
       validationState={this.getValidationState('category')} 
       >
         <Col sm={3}>
         <FormattedMessage id="globalParameter.form.input.category.title" defaultMessage=''></FormattedMessage>
         </Col>
         <Col sm={9}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({id: 'globalParameter.form.input.category.placeholder'})}
                name="category"
                onChange={this.onChange}
                value={this.state.category}
              />
            <ControlLabel>{this.getMessageValidation('category')} </ControlLabel>
         </Col>
       </FormGroup> */}
        <FormGroup controlId="formHorizontalCategory"
       validationState={this.getValidationState('categorySelected')} 
       >
         <Col sm={3}>
         <FormattedMessage id="globalParameter.form.input.category.title" defaultMessage=''></FormattedMessage>
         </Col>
         <Col sm={9}>
           <FormControl
             componentClass="select"
             placeholder="- Seleccionar Category -"
             name="categorySelected"
             value={this.state.categorySelected}
             onChange={this.onChangeCategory}
           >
             {categoriesOptionTemplate}
           </FormControl>
           <ControlLabel>{this.getMessageValidation('categorySelected')} </ControlLabel>
         </Col>
       </FormGroup>

       <FormGroup >
       <Col sm={3}>
        
         </Col>
         <Col sm={9}>
         <StyleButton currentStyles={this.props.currentStyles}  bsStyle="primary" 
                  onClick={ () => { this.save(); }}>
                <FormattedMessage id="form.button.save" defaultMessage=''></FormattedMessage>
          </StyleButton>

          <Button onClick={() => { this.handleCancelEditGlobalParameter(); }}>
          <FormattedMessage id="form.button.cancel" defaultMessage=''></FormattedMessage>
      </Button>
         </Col>
            </FormGroup>
        </Form>
      </StyledPanel>
    );
  }
}

LurinFvGlobalParameterFormComponent.propTypes = {
  showForm: PropTypes.func,
  isNewRegister: PropTypes.bool,
  globalParametersData: PropTypes.object,
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
    showForm: (hasShowForm) => dispatch(showForm(hasShowForm)),
    initMessage:() => dispatch(initMessage())           
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinFvGlobalParameterFormComponent);
