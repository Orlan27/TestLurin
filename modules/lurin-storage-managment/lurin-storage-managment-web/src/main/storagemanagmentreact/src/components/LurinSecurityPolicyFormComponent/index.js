/**
*
* LurinStorageFormComponent
*
*/

import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import Select from 'react-select';

import {
  Button,
  Col,
  ControlLabel,
  Form,
  FormControl,
  FormGroup,
  FieldGroup,
  PanelGroup
} from 'react-bootstrap';
import inputErrorMessage from '../../utils/inputErrorMessage';
import { FormattedMessage, intlShape  } from 'react-intl';
import {
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
} from '../../containers/LurinFvSecurityPolicyContainer/selectors';
import {
  showFormStorages,
} from '../../containers/LurinFvSecurityPolicyContainer/actions';
import reducer from '../../containers/LurinFvSecurityPolicyContainer/reducer';

import {
  result,
  find,
  findIndex
} from 'lodash';

import {
  NAME_LENGTH_MINIMUM_KEY,
  NAME_LENGTH_MAXIMUN_KEY,
  NAME_COUNT_MINIMUM_CHAR_UNIQUE_KEY,
  NAME_COUNT_MINIMUM_CHAR_ALFA_KEY,
  KEY_LENGTH_MINIMUM_KEY,
  KEY_LENGTH_MAXIMUM_KEY,
  KEY_COUNT_MINIMUM_CHAR_UNIQUE_KEY,
  KEY_COUNT_MINIMUM_CHAR_ALFA_KEY,
  KEY_BLOCK_ACCOUNT_TRY_FAILED_KEY,
  KEY_MINUTES_BLOCK_TRY_FAILED_KEY
} from './constantsParameters';

import {
  StyleButton,
  StyledPanel
} from './styles';
import { debug } from 'util';


export class LurinSecurityPolicyFormComponent extends React.PureComponent {

  constructor(props) {  
    super(props);
    this.state = {
      minLengthName: 0,
      maxLengthName: 0,
      minUniqueCharacterQuantityName: 0,
      minNumericAlphabetCharacterQuantityName: 0,
      minLengthKey: 0,
      maxLengthKey: 0,
      minUniqueCharacterQuantityKey: 0,
      minNumericlAphabetCharacterQuantityKey: 0,
      failedTryBlockAccountKey: 0,
      userMinutesBlockAccountKey: 0,
      currentSecurityPolicy: this.props.securityPolicyData,
      error: null,
      pressSaveButton:false
    };
    this.save = this.save.bind(this);
  }

  static contextTypes = {
    intl: intlShape,
  }

  componentWillReceiveProps(nextProps) {

    if (nextProps.securityPolicyData.length > 0) {
      this.setState({
        currentSecurityPolicy:  nextProps.securityPolicyData,
        minLengthName: this.getGloblaParameterValue(NAME_LENGTH_MINIMUM_KEY, nextProps.securityPolicyData),
        maxLengthName: this.getGloblaParameterValue(NAME_LENGTH_MAXIMUN_KEY, nextProps.securityPolicyData),
        minUniqueCharacterQuantityName: this.getGloblaParameterValue(NAME_COUNT_MINIMUM_CHAR_UNIQUE_KEY, nextProps.securityPolicyData),
        minNumericAlphabetCharacterQuantityName: this.getGloblaParameterValue(NAME_COUNT_MINIMUM_CHAR_ALFA_KEY, nextProps.securityPolicyData),
        minLengthKey: this.getGloblaParameterValue(KEY_LENGTH_MINIMUM_KEY, nextProps.securityPolicyData),
        maxLengthKey: this.getGloblaParameterValue(KEY_LENGTH_MAXIMUM_KEY, nextProps.securityPolicyData),
        minUniqueCharacterQuantityKey: this.getGloblaParameterValue(KEY_COUNT_MINIMUM_CHAR_UNIQUE_KEY, nextProps.securityPolicyData),
        minNumericlAphabetCharacterQuantityKey: this.getGloblaParameterValue(KEY_COUNT_MINIMUM_CHAR_ALFA_KEY, nextProps.securityPolicyData),
        failedTryBlockAccountKey: this.getGloblaParameterValue(KEY_BLOCK_ACCOUNT_TRY_FAILED_KEY, nextProps.securityPolicyData),
        userMinutesBlockAccountKey: this.getGloblaParameterValue(KEY_MINUTES_BLOCK_TRY_FAILED_KEY, nextProps.securityPolicyData)
      });
    }
  }

  getGloblaParameterValue(key, securityPolicy) {
    return result(find(securityPolicy, {'key':key}), 'value');
  }

  setGlobalParameterValue(key, newValue, securityPolicy) {
    let parameterToUpdate = find(securityPolicy, {'key':key});
    parameterToUpdate.value = newValue;
    let index = findIndex(securityPolicy, {'key':key});
    securityPolicy.splice(index, 1, parameterToUpdate);
    this.setState({ currentSecurityPolicy: securityPolicy });
  }

  getKeyByNameState(nameState) {
    switch (nameState) {
      case 'minLengthName':
       return NAME_LENGTH_MINIMUM_KEY;
      case 'maxLengthName':
        return NAME_LENGTH_MAXIMUN_KEY;
      case 'minUniqueCharacterQuantityName': 
          return NAME_COUNT_MINIMUM_CHAR_UNIQUE_KEY;
      case 'minNumericAlphabetCharacterQuantityName': 
        return NAME_COUNT_MINIMUM_CHAR_ALFA_KEY;
      case 'minLengthKey': 
        return KEY_LENGTH_MINIMUM_KEY;
      case 'maxLengthKey': 
        return KEY_LENGTH_MAXIMUM_KEY;
      case 'minUniqueCharacterQuantityKey': 
        return KEY_COUNT_MINIMUM_CHAR_UNIQUE_KEY;
      case 'minNumericlAphabetCharacterQuantityKey': 
        return KEY_COUNT_MINIMUM_CHAR_ALFA_KEY;
      case 'failedTryBlockAccountKey': 
        return KEY_BLOCK_ACCOUNT_TRY_FAILED_KEY;
      case 'userMinutesBlockAccountKey': 
        return KEY_MINUTES_BLOCK_TRY_FAILED_KEY;
    }
  }

  onChange = (evt) => {
    let key = this.getKeyByNameState(evt.target.name);
    this.setGlobalParameterValue(key, evt.target.value, this.state.currentSecurityPolicy);
    this.setState({ [evt.target.name]: evt.target.value });
  }

  hasInvalidData() {
    
    if (!this.state.minLengthName) {
      return true;
    }

    if (!this.state.maxLengthName) {
      return true;
    }

    if (!this.state.minUniqueCharacterQuantityName) {
      return true;
    }

    if (!this.state.minNumericAlphabetCharacterQuantityName) {
      return true;
    }

    if (!this.state.minLengthKey) {
      return true;
    }

    if (!this.state.maxLengthKey) {
      return true;
    }

    if (!this.state.minUniqueCharacterQuantityKey) {
      return true;
    }

    if (!this.state.minNumericlAphabetCharacterQuantityKey) {
      return true;
    }

    if (!this.state.failedTryBlockAccountKey) {
      return true;
    }

    if (!this.state.userMinutesBlockAccountKey) {
      return true;
    }

    return false
  }

  save = () => {
    this.setState({ pressSaveButton: true });
    if(!this.hasInvalidData())
      this.props.save(this.state);
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

  getNumericValidation(field) {
    if(this.state.pressSaveButton){
      
      if(!this.state[field]){
        return 'error';
      }

      if (this.state[field]<0 ) {
        return 'error';
      }

    }
   return null; 
  }

  getMessageNumericValidation(field) {
    if(this.state.pressSaveButton){
      
      if(typeof this.state[field] === "undefined" || this.state[field] == null){
        return <FormattedMessage id="general.form.required.validation" defaultMessage='Campo Requerido'></FormattedMessage>;
      }

      if (this.state[field]<0  ) {
        return <FormattedMessage id="general.form.negative.zero.validation" defaultMessage='El Campo debe ser mayor o igual 0'></FormattedMessage>;
      }

    }
    return null; 
  }

  render() {
  
    return (
      <div>
        <PanelGroup>
          <StyledPanel currentStyles={this.props.currentStyles} bsStyle="primary" 
            header={<FormattedMessage id="securityPolicy.form.header.name" defaultMessage='Nombres'></FormattedMessage>} key={1} 
            collapsible defaultExpanded>
              <Form horizontal>
                <FormGroup controlId="formHorizontalMinLengthName"
                validationState={this.getNumericValidation('minLengthName')} 
                >
                  <Col sm={9}>
                    <FormattedMessage id="securityPolicy.form.label.minimum.length" defaultMessage=''></FormattedMessage>
                  </Col>
                  <Col sm={2}>
                        <FormControl
                          type="number"  
                          min="0" 
                          max="9999"
                          placeholder={this.context.intl.formatMessage({id: 'securityPolicy.form.label.minimum.length'})}
                          name="minLengthName"
                          value={this.state.minLengthName}
                          onChange={this.onChange}
                        />
                        <ControlLabel>{this.getMessageNumericValidation('minLengthName')} </ControlLabel>
                  </Col>
                </FormGroup>
                <FormGroup controlId="formHorizontalMaxLengthName"
                validationState={this.getNumericValidation('maxLengthName')} 
                >
                  <Col sm={9}>
                    <FormattedMessage id="securityPolicy.form.label.maximum.length" defaultMessage=''></FormattedMessage>
                  </Col>
                  <Col sm={2}>
                        <FormControl
                          type="number"  
                          min="0" 
                          max="9999"
                          placeholder={this.context.intl.formatMessage({id: 'securityPolicy.form.label.maximum.length'})}
                          name="maxLengthName"
                          value={this.state.maxLengthName}
                          onChange={this.onChange}
                        />
                        <ControlLabel>{this.getMessageNumericValidation('maxLengthName')} </ControlLabel>
                  </Col>
                </FormGroup>
                <FormGroup controlId="formHorizontalMinUniqueCharacterQuantityName"
                validationState={this.getNumericValidation('minUniqueCharacterQuantityName')} 
                >
                  <Col sm={9}>
                    <FormattedMessage id="securityPolicy.form.label.unique.character.amount" defaultMessage=''></FormattedMessage>
                  </Col>
                  <Col sm={2}>
                        <FormControl
                          type="number"  
                          min="0" 
                          max="9999"
                          placeholder={this.context.intl.formatMessage({id: 'securityPolicy.form.label.unique.character.amount'})}
                          name="minUniqueCharacterQuantityName"
                          value={this.state.minUniqueCharacterQuantityName}
                          onChange={this.onChange}
                        />
                        <ControlLabel>{this.getMessageNumericValidation('minUniqueCharacterQuantityName')} </ControlLabel>
                  </Col>
                </FormGroup>
                <FormGroup controlId="formHorizontalMinNumericAlphabetCharacterQuantityName"
                validationState={this.getNumericValidation('minNumericAlphabetCharacterQuantityName')} 
                >
                  <Col sm={9}>
                    <FormattedMessage id="securityPolicy.form.label.alpha.numeric.char" defaultMessage=''></FormattedMessage>
                  </Col>
                  <Col sm={2}>
                        <FormControl
                          type="number"  
                          min="0" 
                          max="9999"
                          placeholder={this.context.intl.formatMessage({id: 'securityPolicy.form.label.alpha.numeric.char'})}
                          name="minNumericAlphabetCharacterQuantityName"
                          value={this.state.minNumericAlphabetCharacterQuantityName}
                          onChange={this.onChange}
                        />
                        <ControlLabel>{this.getMessageNumericValidation('minNumericAlphabetCharacterQuantityName')} </ControlLabel>
                  </Col>
                </FormGroup>
              </Form>
          </StyledPanel>
          <StyledPanel currentStyles={this.props.currentStyles} bsStyle="primary" 
            header={<FormattedMessage id="securityPolicy.form.header.key" defaultMessage='Claves'></FormattedMessage>} key={2} 
            collapsible defaultExpanded>
              <Form horizontal>
                <FormGroup controlId="formHorizontalMinLengthKey"
                validationState={this.getNumericValidation('minLengthKey')} 
                >
                  <Col sm={9}>
                    <FormattedMessage id="securityPolicy.form.label.minimum.length" defaultMessage=''></FormattedMessage>
                  </Col>
                  <Col sm={2}>
                        <FormControl
                          type="number"  
                          min="0" 
                          max="9999"
                          placeholder={this.context.intl.formatMessage({id: 'securityPolicy.form.label.minimum.length'})}
                          name="minLengthKey"
                          value={this.state.minLengthKey}
                          onChange={this.onChange}
                        />
                        <ControlLabel>{this.getMessageNumericValidation('minLengthKey')} </ControlLabel>
                  </Col>
                </FormGroup>
                <FormGroup controlId="formHorizontalMaxLengthKey"
                validationState={this.getNumericValidation('maxLengthKey')} 
                >
                  <Col sm={9}>
                    <FormattedMessage id="securityPolicy.form.label.maximum.length" defaultMessage=''></FormattedMessage>
                  </Col>
                  <Col sm={2}>
                        <FormControl
                          type="number"  
                          min="0" 
                          max="9999"
                          placeholder={this.context.intl.formatMessage({id: 'securityPolicy.form.label.maximum.length'})}
                          name="maxLengthKey"
                          value={this.state.maxLengthKey}
                          onChange={this.onChange}
                        />
                        <ControlLabel>{this.getMessageNumericValidation('maxLengthKey')} </ControlLabel>
                  </Col>
                </FormGroup>
                <FormGroup controlId="formHorizontalMinUniqueCharacterQuantityKey"
                validationState={this.getNumericValidation('minUniqueCharacterQuantityKey')} 
                >
                  <Col sm={9}>
                    <FormattedMessage id="securityPolicy.form.label.unique.character.amount" defaultMessage=''></FormattedMessage>
                  </Col>
                  <Col sm={2}>
                        <FormControl
                          type="number"  
                          min="0" 
                          max="9999"
                          placeholder={this.context.intl.formatMessage({id: 'securityPolicy.form.label.unique.character.amount'})}
                          name="minUniqueCharacterQuantityKey"
                          value={this.state.minUniqueCharacterQuantityKey}
                          onChange={this.onChange}
                        />
                        <ControlLabel>{this.getMessageNumericValidation('minUniqueCharacterQuantityKey')} </ControlLabel>
                  </Col>
                </FormGroup>
                <FormGroup controlId="formHorizontalMinNumericAlphabetCharacterQuantityKey"
                validationState={this.getNumericValidation('minNumericlAphabetCharacterQuantityKey')} 
                >
                  <Col sm={9}>
                    <FormattedMessage id="securityPolicy.form.label.alpha.numeric.char" defaultMessage=''></FormattedMessage>
                  </Col>
                  <Col sm={2}>
                        <FormControl
                          type="number"  
                          min="0" 
                          max="9999"
                          placeholder={this.context.intl.formatMessage({id: 'securityPolicy.form.label.alpha.numeric.char'})}
                          name="minNumericlAphabetCharacterQuantityKey"
                          value={this.state.minNumericlAphabetCharacterQuantityKey}
                          onChange={this.onChange}
                        />
                        <ControlLabel>{this.getMessageNumericValidation('minNumericlAphabetCharacterQuantityKey')} </ControlLabel>
                  </Col>
                </FormGroup>
                <FormGroup controlId="formHorizontalFailedTryBlockAccountKey"
                validationState={this.getNumericValidation('failedTryBlockAccountKey')}>
                  <Col sm={9}>
                    <FormattedMessage id="securityPolicy.form.label.failed.try" defaultMessage=''></FormattedMessage>
                  </Col>
                  <Col sm={2}>
                        <FormControl
                          type="number"  
                          min="0" 
                          max="9999"
                          placeholder={this.context.intl.formatMessage({id: 'securityPolicy.form.label.failed.try'})}
                          name="failedTryBlockAccountKey"
                          value={this.state.failedTryBlockAccountKey}
                          onChange={this.onChange}
                        />
                        <ControlLabel>{this.getMessageNumericValidation('failedTryBlockAccountKey')} </ControlLabel>
                  </Col>
                </FormGroup>
                <FormGroup controlId="formHorizontalUserMinutesBlockAccountKey"
                validationState={this.getNumericValidation('userMinutesBlockAccountKey')} >
                  <Col sm={9}>
                    <FormattedMessage id="securityPolicy.form.label.minutes.block" defaultMessage=''></FormattedMessage>
                  </Col>
                  <Col sm={2}>
                        <FormControl
                          type="number"  
                          min="0" 
                          max="9999"
                          placeholder={this.context.intl.formatMessage({id: 'securityPolicy.form.label.minutes.block'})}
                          name="userMinutesBlockAccountKey"
                          value={this.state.userMinutesBlockAccountKey}
                          onChange={this.onChange}
                        />
                        <ControlLabel>{this.getMessageNumericValidation('userMinutesBlockAccountKey')} </ControlLabel>
                  </Col>
                </FormGroup>
              </Form>
          </StyledPanel>
        </PanelGroup>
          <Form horizontal>
            <FormGroup>
              <Col sm={12} >
              <StyleButton currentStyles={this.props.currentStyles} bsStyle="primary" 
                onClick={ () => { this.save(); }}>
                  <FormattedMessage id="securityPolicy.form.button.update" defaultMessage=''></FormattedMessage>
              </StyleButton>
              </Col>
            </FormGroup>
          </Form>
        </div>
    );
  }
}

LurinSecurityPolicyFormComponent.propTypes = {
  securityPolicyData: PropTypes.object,
  save: PropTypes.func,
  currentStyles: PropTypes.object,
};

const mapStateToProps = createStructuredSelector({
  loading: makeSelectLoading(),
  error: makeSelectError(),
});

function mapDispatchToProps(dispatch) {
  return { };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinSecurityPolicyFormComponent);
