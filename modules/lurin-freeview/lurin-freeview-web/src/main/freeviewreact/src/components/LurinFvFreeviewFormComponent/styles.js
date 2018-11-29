import styled from 'styled-components';
import { ITEM_THEME } from '../../utils/theme';

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
      color: #004259;
  }
  .message{
      font-size:12px;
      color:gray;
  }
  .form-horizontal{
    width: 75%;
 }
 .form-control {
    width: 100%;
 }
 .Select{
    width: 100%;
  }
  .package{
    height: auto;
    margin-bottom: -17px;
  }
  .package-title{
      margin-top:3px;
  }
  .form-group-name{
    margin-bottom: 0;
  }
  .label-package{
    margin-top: 20px;
  }
  .package-error{
     height: auto;
     margin-bottom: -17px;
     .Select-control{
         border: 1px solid #004259;
     }
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
