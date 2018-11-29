import { ITEM_THEME } from '../../utils/theme';
import styled from 'styled-components';

import {
    Button,
    Panel
} from 'react-bootstrap';

export const StyleButton = styled(Button) `
    color: #fff;
     background-color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_BUTTON_SAVE]};
    background-image: none;
    margin-right:15px;
`;

export const StyledPanel = styled(Panel) `
    width: 100%;
    .control-label{
        padding-top:0px;
        font-size:10px;
        text-align:left!important;
    }
    .info{
      margin-top: 5px;
      font-size: 10px;
      color: black;
      text-align: justify;
      width: 75%;
  
    }
  .message{
      font-size:12px;
      color:gray;
  }
  .form-horizontal{
    width: 90%;
 }
 .form-control {
    width: 100%;
 }
  .form-group-name{
    margin-bottom: 0;
  }
  .input-file{
      display:none;
  }
  .react-toggle--checked .react-toggle-track {
    background-color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_HEADER_FORM]};
  }
  .react-toggle--checked .react-toggle-thumb {  
    background-color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_HEADER_FORM]};
   }
   .react-toggle--checked:hover:not(.react-toggle--disabled) .react-toggle-track {
     background-color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_HEADER_FORM]}!important;
    }
    .datepicker-error{
         border: 1px solid #004259;
  }
  .has-error .form-control {
        border-color: #004259;
        box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
   }
    .has-error .form-control:focus {
        border-color: #004259;
        box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 6px #004259;
    }

    @media (max-width: 1024px) {
     .form-horizontal{
        width: 100%;
     }
 }
`;
