import styled from 'styled-components';
import { ITEM_THEME } from '../../utils/theme';

import {
    Panel,
    Button,
    Form
} from 'react-bootstrap';
export const StyledPanel = styled(Panel) `
    margin: auto;
    width: 85%;
     @media (max-width: 768px) { 
        width: 100%;
    }
     .panel-heading{
        background-color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_HEADER_FORM]};
        background-image:none;
        border-color:${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_HEADER_FORM]};
    }
    .btn-info{
        background-color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_BUTTON_NEW]};
        background-image: none;
        margin-right:2px;
    }
    .btn-warning{
        background-color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_BUTTON_DELETE]};
        background-image: none;
    }
    .page-link{
        background-color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_BUTTON_NEW]}!important;
        color: white;
    }
    .label-carrier{
       font-size :1.2rem;
   }
   .has-error .form-control {
        border-color: #004259;
        box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
   }
    .has-error .form-control:focus {
        border-color: #004259;
        box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 6px #004259;
    }
   // margin-bottom:50px;
`;

export const StyleButton = styled(Button) `
    color: #fff;
`;

export const StyleForm = styled(Form) `
    position: relative;
    top: 30px;
`;

