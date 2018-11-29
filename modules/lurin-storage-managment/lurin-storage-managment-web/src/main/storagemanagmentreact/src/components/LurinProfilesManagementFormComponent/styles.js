import { ITEM_THEME } from '../../utils/theme';
import styled from 'styled-components';

import {
    Button,
    Panel
} from 'react-bootstrap';

export const StyleButton = styled(Button) `
    color: #fff;
    padding-left:10px;
    margin-right :10px;
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
  .form-horizontal{
    width: 75%;
 }
 .form-control {
    width: 100%;
 }
  .subtitle{
    color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_HEADER_FORM]};
  }
  .message{
      font-size:12px;
      color:gray;
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
    border-color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_HEADER_FORM]};
   }
   .react-toggle--checked:hover:not(.react-toggle--disabled) .react-toggle-track {
      background-color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_HEADER_FORM]}!important;
   }
   .react-checkbox-tree{
      label{
        font-weight: normal;
      }
      
   .glyphicon{
       color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_HEADER_FORM]}!important;
   }
   .glyphicon-play{
        display:none;
    }
}
@media (max-width: 1024px) {
     .form-horizontal{
        width: 100%;
     }
 }
`;
