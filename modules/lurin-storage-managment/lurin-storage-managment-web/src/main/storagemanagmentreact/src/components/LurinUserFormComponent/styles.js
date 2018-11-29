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
     }
     
     .message{
         font-size:12px;
         color:gray;    
     }
     .form-horizontal{
        width: 80%;
     }
     .Select{
        width: 100%;
    }
     .form-control {
        width: 100%;
     }
     .control-checkbox {
      height:15px;
     }
     
     .carrier-title{
      margin-top:3px;
    }
    .label-carrier{
         margin-top: 20px;
    }
    .react-toggle{
        margin-bottom: 10px;
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
  .carrier-error{
     height: auto;
     margin-bottom: -17px;
     .Select-control{
         border: 1px solid #004259;
     }
  }
     .form-group-name{
       margin-bottom: 0;
     }
     .input-file{
         display:none;
     }
     @media (max-width: 1024px) {
     .form-horizontal{
        width: 100%;
     }
 }

`;
