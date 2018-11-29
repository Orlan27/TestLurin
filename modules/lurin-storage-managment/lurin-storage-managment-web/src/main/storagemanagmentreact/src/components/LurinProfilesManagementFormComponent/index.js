/**
*
* LurinProfilesManagementFormComponent
*
*/

import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import Select from 'react-select';
import CheckboxTree from 'react-checkbox-tree';

import {
  Form,
  FormControl,
  FormGroup,
  Col,
  ControlLabel,
  Button,
  FieldGroup,
  Glyphicon
} from 'react-bootstrap';
import inputErrorMessage from '../../utils/inputErrorMessage';
import { FormattedMessage, intlShape  } from 'react-intl';

import {
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
} from '../../containers/LurinProfilesManagementContainer/selectors';
import {
  showForm,
  initMessage
} from '../../containers/LurinProfilesManagementContainer/actions';
import reducer from '../../containers/LurinProfilesManagementContainer/reducer';

import {
  StyleButton,
  StyledPanel
} from './styles';
import { debug } from 'util';


export class LurinProfilesManagementFormComponent extends React.PureComponent {

  constructor(props) {  
    super(props);
    this.state = {
      title: (this.props.isNewRegister) ? <FormattedMessage id="globalParameter.form.add.title" 
      defaultMessage=''></FormattedMessage> : 
      <FormattedMessage id="globalParameter.form.edit.title" 
      defaultMessage=''></FormattedMessage>,
      profileId:this.props.profilesData.profileId,
      name:this.props.profilesData.name,
      description:this.props.profilesData.description,
      categorySelect: this.props.profilesCategoriesData.categoriesProfileId,
      category: this.props.profilesCategoriesData,
      error: null,
      checked: this.props.profilesData.checked,
      expanded: [],
      error: null,
      pressSaveButton:false
    };
    this.handleCancelEditProfiles = this.handleCancelEditProfiles.bind(this);
    this.onChange = this.onChange.bind(this);
    this.onChangeCategory = this.onChangeCategory.bind(this);

  }
 
  handleCancelEditProfiles = () => {
   this.props.showForm(false);
   this.props.initMessage();
  }

  static contextTypes = {
    intl: intlShape,
  }
   
  save = () => {
    this.setState({ pressSaveButton: true });
    if(!this.hasInvalidData())
      this.props.save(this.state);
  }

  hasInvalidData() {
    
    if (!this.state.name) {
      return true;
    }
    if (this.state.description == 0) {
      return true;
    }
    if (!this.state.checked) {
      return true;
    }
    return false
  } 

  onChange = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value });
  }

  getValidationState(field) {
    if((!this.state[field]) && this.state.pressSaveButton){
      return 'error';
    }
    return null; 
  
  }

  onChangeCategory = (evt) => {
      const index = this.props.profilesCategoriesData.findIndex(i => i.categoriesProfileId.toString()=== evt.target.value);
      this.setState({ [evt.target.name]: evt.target.value, category: this.props.profilesCategoriesData[index] });
    }

  getMessageValidation(field) {
    if((!this.state[field]  || this.state[field]==0 ) && this.state.pressSaveButton){
     return <FormattedMessage id="general.form.required.validation" defaultMessage='Campo Requerido'></FormattedMessage>;
    }
    return null; 
  }

  render() {
    let  treeData=this.props.treeData;
    treeData.forEach( (role,index) => {
      treeData[index].icon=<Glyphicon glyph="glyphicon glyphicon-user" />
      role.children.forEach( (item,indexItem) => {
        treeData[index].children[indexItem].icon=<Glyphicon glyph="glyphicon glyphicon-play" />
      });
    });


    if(this.props.profilesData!=undefined){
        this.state.categorySelect=this.props.profilesData.categoriesProfile.categoriesProfileId;
    }

    let categoryOptionTemplate = this.props.profilesCategoriesData.map(profilesCategoriesData => (
          <option key={profilesCategoriesData.categoriesProfileId} value={profilesCategoriesData.categoriesProfileId}>{profilesCategoriesData.name}</option>
        ));

    return (
      <StyledPanel currentStyles={this.props.currentStyles} header={this.state.title} bsStyle="primary">
        <Form horizontal>
      <FormGroup controlId="formHorizontalName"
       validationState={this.getValidationState('name')} 
      >
         <Col sm={3}>
           <FormattedMessage id="profile.form.input.profile.title" defaultMessage=''></FormattedMessage>
         </Col>
         <Col sm={9}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({id: 'profile.form.input.profile.placeholder'})}
                name="name"
                value={this.state.name}
                onChange={this.onChange}
                maxLength={100}
              />
              <ControlLabel>{this.getMessageValidation('name')} </ControlLabel>
         </Col>
       </FormGroup>

       <FormGroup controlId="formHorizontalDescription"
       validationState={this.getValidationState('description')} 
       >
         <Col sm={3}>
         <FormattedMessage id="profile.form.input.description.title" defaultMessage=''></FormattedMessage>
         </Col>
         <Col sm={9}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({id: 'profile.form.input.description.placeholder'})}
                name="description"
                value={this.state.description}
                onChange={this.onChange}
                maxLength={200}
              />
              <ControlLabel>{this.getMessageValidation('description')} </ControlLabel>
         </Col>
       </FormGroup>


       <FormGroup controlId="formHorizontalTech"
          validationState={this.getValidationState('categorySelect')}
         >
           <Col sm={3}>
           <FormattedMessage id="form.input.title.category" defaultMessage='category'></FormattedMessage>
           </Col>
           <Col sm={9}>
           <FormControl
               componentClass="select"
               placeholder="Seleccionar"
               name="categorySelect"
               value={this.state.categorySelect}
               onChange={this.onChangeCategory}
             >
               {categoryOptionTemplate}
             </FormControl>
             <ControlLabel>{this.getMessageValidation('categorySelect')} </ControlLabel>
           </Col>
         </FormGroup>


       <FormGroup controlId="formHorizontalDescription"
        validationState={this.getValidationState('checked')} 
       >
         <Col sm={3}>
         <FormattedMessage id="profile.form.input.profile.title" defaultMessage=''></FormattedMessage>
         </Col>
         <Col sm={9}>
         <CheckboxTree
                nodes={treeData}
                checked={this.state.checked}
                expanded={this.state.expanded}
                onCheck={checked => this.setState({ checked })}
                onExpand={expanded => this.setState({ expanded })}
            />
             <ControlLabel>{this.getMessageValidation('checked')} </ControlLabel>
         </Col>
       </FormGroup>

       <FormGroup >
       <Col sm={3}>
        
         </Col>
         <Col sm={9}>
         <StyleButton currentStyles={this.props.currentStyles} bsStyle="primary" 
          onClick={ () => { this.save(); }}>
         <FormattedMessage id="form.button.save" defaultMessage=''></FormattedMessage>
           </StyleButton>

           <Button onClick={() => { this.handleCancelEditProfiles(); }}>
           <FormattedMessage id="form.button.cancel" defaultMessage=''></FormattedMessage>
           </Button>
         </Col>
            </FormGroup>
        </Form>
      </StyledPanel>
    );
  }
}

LurinProfilesManagementFormComponent.propTypes = {
  showForm: PropTypes.func,
  isNewRegister: PropTypes.bool,
  profilesData: PropTypes.object,
  profilesCategoriesData: PropTypes.object,
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
    showForm: (hasShowForm) => dispatch(showForm(hasShowForm)),
    initMessage:() => dispatch(initMessage())        
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinProfilesManagementFormComponent);
