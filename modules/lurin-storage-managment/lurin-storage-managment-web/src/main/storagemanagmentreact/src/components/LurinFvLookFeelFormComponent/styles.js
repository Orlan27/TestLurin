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
  .form-control {
        width: 75%;
    }
  .content-button:{
    padding-left:50px;
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
      background-color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_HEADER_FORM]};
    }
`;
