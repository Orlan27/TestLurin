/**
*
* LurinFvLookFeelFormComponent
*
*/
import Axios from 'axios';
import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import { normalize, schema } from 'normalizr';
import DatePicker from 'react-datepicker';
import Select from 'react-select';
import Toggle from 'react-toggle'
import moment from 'moment';
import ColorPicker from 'rc-color-picker';
// import { MoonLoader } from 'react-spinners';
import {
  Form,
  FormControl,
  FormGroup,
  Col,
  ControlLabel,
  Button,
  FieldGroup,
  Alert
} from 'react-bootstrap';
import { FormattedMessage, intlShape  } from 'react-intl';
import inputErrorMessage from '../../utils/inputErrorMessage';
import { CONTROL_TYPE, DEFAULT_THEME, uuidv4, UPLOAD_PARAMETER } from '../../utils/theme';
import {
  makeSelectLoading,
  makeSelectError,
  makeSelectHasShowForm,
} from '../../containers/LurinFvLookFeelContainer/selectors';
import {
  showForm,
  initMessage
} from '../../containers/LurinFvLookFeelContainer/actions';
import reducer from '../../containers/LurinFvLookFeelContainer/reducer';

import {
  StyleButton,
  StyledPanel
} from './styles';
import { debug } from 'util';


export class LurinFvGlobalParameterFormComponent extends React.PureComponent {

  constructor(props) {  
    super(props);
    const themeSchema = new schema.Entity('theme');
    const themesSchema = new schema.Array(themeSchema);
    const themes = normalize(this.props.lookFeelsData.details, themesSchema);
    this.state = {
      title: (this.props.isNewRegister) ? <FormattedMessage id="lookFeel.form.add.title"
      defaultMessage='Agregar nuevo tema'></FormattedMessage> :
      <FormattedMessage id="lookFeel.form.edit.title" defaultMessage='Editar tema'></FormattedMessage>,
      name:this.props.lookFeelsData.name,
      description :this.props.lookFeelsData.description,
      details : themes.entities.theme,
      alertVisible: false,
      alertStyle: "success",
      alertMessage: '',
      error: null,
      hasInvalidFile:false,
      pressSaveButton:false,
      loading:true
    };
    this.handleCancelEditLookFeel = this.handleCancelEditLookFeel.bind(this);
    this.onChange = this.onChange.bind(this);
    this.save = this.save.bind(this);
    this.onChangeDetailText = this.onChangeDetailText.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleAlertDismiss = this.handleAlertDismiss.bind(this);
  }

  static contextTypes = {
      intl: intlShape,
    }

  hasInvalidData() {
    if (!this.state.name) {
      return true;
    }
    if (!this.state.description) {
      return true;
    }
    if(this.state.hasInvalidFile){
      return true;
    }
    return false;
  }

  save = () => {
    this.setState({ pressSaveButton: true });
    if(!this.hasInvalidData())
      this.props.save(this.state);
  }
 
  handleCancelEditLookFeel = () => {
   this.props.showForm(false);
   this.props.initMessage();
  }
  handleAlertDismiss() {
    this.setState({ alertVisible: false });
  };
   
  onChange = (evt) => {
    this.setState({ [evt.target.name]: evt.target.value });
  }

  onChangeDetailText = (evt, id) => {
   this.handleAlertDismiss();
    let details = this.state.details;
    let currentData=this.state.details[id] 
    currentData.value= evt.target.value;

    this.setState({
      details: {
        ...this.state.details,
      [id]:currentData,
      },
    });

  }

  onBlurDetailText = (evt, id) => {
     let currentData=this.state.details[id] 
     currentData.value= evt.target.value;
     if(currentData.type==CONTROL_TYPE.IMAGEURL){
      var that=this;
      fetch(evt.target.value)
      .then(function() {

      }).catch(function() {
        that.setState({ alertMessage: 'La url debe ser una imagen valida',
                alertVisible:true, alertStyle: 'warning' } );
      });
    }
  }

  handleChange = (id, value) => {
    let details = this.state.details;
    let currentData=this.state.details[id] 
    currentData.value= value.color;
    this.setState({
      details: {
        ...this.state.details,
      [id]:currentData,
      },
    });
  }

  closeHandler = (colors) => {
    //console.log(colors);
  }
  
  getInputControl=(detail) => {
    let control = null;
    switch (detail.type) {
      case CONTROL_TYPE.COLOR:
        return this.getColorPicker(detail);
      case CONTROL_TYPE.IMAGEURL:
        return this.getUploadFile(detail);

      default:
        return null;
    }
  }
  
  getColorPicker= (detail) => {
      return (
        <ColorPicker color={detail.value} 
           onChange={ (value)=> this.handleChange(detail.id,value)} 
           key={detail.id}
           placement="bottomRight"
           name={detail.name} 
           />
      );
  }

  fileIsValid = (id, evt, fileName) => {
    const currentFileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
    if(!UPLOAD_PARAMETER.ALLOWED_IMG_EXTENSIONS.split(',').includes(currentFileExtension)){
      this.setState({
        
        ['message_'+this.state.details[id].name]:'File extensions not allowed',
        hasInvalidFile:true
      });
      return false;
    }

    if(UPLOAD_PARAMETER.MAX_IMG_SIZE < (evt.target.files[0].size /1024/1024) ){
      this.setState({
        ['message_'+this.state.details[id].name]:"The file's size must not be greater than 1MB",
        hasInvalidFile:true
      });
      return false;
    }
    this.setState({
      ['message_'+this.state.details[id].name]:"",
      hasInvalidFile:false
    });
    return true;
  }

  getValidationDetailState(detail) {
    if(this.state['message_'+detail.name]){
      return 'error';
    }
    return null; 
  }
  
  onChangeFile = (id, evt) => {
    const currentFileName = evt.target.files[0].name; 
    const currentFileExtension = currentFileName.substring(currentFileName.lastIndexOf("."));
    if(this.fileIsValid(id,evt,currentFileName))
    {
    const url = `${window.currentApiUrl}lookfeelmanagement/saveImage`;
    const urlImg= window.currentApiUrl.replace("/api/rest/","");
    let expression = /[-a-zA-Z0-9@:%_\+.~#?&//=]{2,256}\.[a-z]{2,4}\b(\/[-a-zA-Z0-9@:%_\+.~#?&//=]*)?/gi;
    var regex = new RegExp(expression);

    let details = this.state.details;
    let currentData=this.state.details[id]; 
  
    let imageData = new FormData();
    let nameFile='';
    if(this.props.isNewRegister && !currentData.value )
     nameFile = uuidv4()+currentFileExtension;
    else{
      if (currentData.value.match(regex)) {
        nameFile=currentData.value.substring(url.lastIndexOf("/") + 1);
      } else {
        nameFile = uuidv4()+currentFileExtension;
      }
     }
     imageData.append('file',evt.target.files[0]);
     imageData.append('name', nameFile);
    Axios.post(`${url}`, imageData, { headers: { 
      Authorization: currentToken} })
     .then(response => {
      currentData.value = urlImg+response.data.url;  
      this.setState({
        details: {
          ...this.state.details,
        [id]:currentData,
        },
      });
     })
     .catch(error => {
       throw (error);
     });
    }
 }

  getUploadFile= (detail) => {
    return (
      <div className="file-content">
      {/* <Col sm={1}>
      <MoonLoader
      color={'#123abc'} 
      loading={this.state.loading} 
      size={25} />
         </Col> */}
         <Col sm={9}>
         <input id={detail.id} name={detail.name}
      type='file' label='Upload' accept="image/*"
      onChange={ (evnt)=> this.onChangeFile(detail.id,evnt)} 
      />
      <img src={detail.value} width="100" />
    
         </Col>
      </div>
    );
}

  getImageUrlInput= (detail) => {
    return (
      <FormControl
        type="text"
        key={detail.id}
        placeholder="http://www.logo.com/img.png"
        name={detail.name}
        onChange={(evt)=> this.onChangeDetailText(evt, detail.id)}
        onBlur={(evt)=> this.onBlurDetailText(evt, detail.id)}
        maxLength={500}
        value={detail.value}
    />
    );
  }

 

  renderAlert= () => {
    if(this.state.alertVisible) {
      return (
        <div>
          <Alert bsStyle={this.state.alertStyle} onDismiss={this.handleAlertDismiss}>
            <span>{this.state.alertMessage}</span>
          </Alert>
          <br />
        </div>
      );
    }
    return '';
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

  getMessageDetail(detail) {
    if (detail.type===CONTROL_TYPE.IMAGEURL)
    return this.state['message_'+detail.name]; 
    else
    return null;
  }


  render() {
    const that=this;
    /*Object.keys(this.state.themeDetails).map((key) 
      console.log(key);
      console.log(that.state.themeDetails[key]);
  });*/
    return (
      <StyledPanel  currentStyles={this.props.currentStyles}  header={this.state.title} bsStyle="primary">
        {this.renderAlert()}
       <Form horizontal>
      <FormGroup controlId="formHorizontalName"
       validationState={this.getValidationState('name')} 
      >
         <Col sm={3}>
         <FormattedMessage id="lookFeel.form.input.themeName.title" defaultMessage='Nombre'></FormattedMessage>
         </Col>
         <Col sm={9}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({id: 'lookFeel.form.input.themeName.placeholder'})}
                name="name"
                readOnly={this.state.name == DEFAULT_THEME}
                onChange={this.onChange}
                value={this.state.name}
                maxLength={50}
              />
              <ControlLabel>{this.getMessageValidation('name')} </ControlLabel>
         </Col>
       </FormGroup>
       <FormGroup controlId="formHorizontalDescription"
        validationState={this.getValidationState('description ')} 
       >
         <Col sm={3}>
           <FormattedMessage id="lookFeel.form.input.description.title" defaultMessage='Descripción'></FormattedMessage>
         </Col>
         <Col sm={9}>
              <FormControl
                type="text"
                placeholder={this.context.intl.formatMessage({id: 'lookFeel.form.input.description.placeholder'})}
                name="description"
                onChange={this.onChange}
                value={this.state.description}
                maxLength={200}
              />
              <ControlLabel>{this.getMessageValidation('description')} </ControlLabel>
         </Col> 
       </FormGroup>
        {
        Object.keys(this.state.details).map((key) =>
        <div>
        <FormGroup key={that.state.details[key].id} controlId={ `formHorizontal${that.state.details[key].name}`}
        validationState={this.getValidationDetailState(that.state.details[key])} 
        >
          <Col sm={3}>
            {that.state.details[key].langAssigned}
          </Col>
          <Col sm={9}>
          {this.getInputControl(that.state.details[key]) }
          </Col>
        </FormGroup>
        <Col sm={3}>
            
          </Col>
          <Col sm={9}>
          <ControlLabel style={ {color:'#a94442'}} >{this.getMessageDetail(that.state.details[key])} </ControlLabel>
          </Col>
        
        </div>
        )
      } 
          <div className="content-button">
              <StyleButton  currentStyles={this.props.currentStyles}  bsStyle="primary" 
               onClick={ () => { this.save(); }}>
                <FormattedMessage id="form.button.save" defaultMessage=''></FormattedMessage>
          </StyleButton>
          
              <Button onClick={() => { this.handleCancelEditLookFeel(); }}>
                <FormattedMessage id="form.button.cancel" defaultMessage=''></FormattedMessage>
            </Button>
           
            </div>
        </Form>
      </StyledPanel>
    );
  }
}

LurinFvGlobalParameterFormComponent.propTypes = {
  showForm: PropTypes.func,
  isNewRegister: PropTypes.bool,
  lookFeelData: PropTypes.object,
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
