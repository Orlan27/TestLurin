import styled from 'styled-components';
import { ITEM_THEME } from '../../utils/theme';

import {
    Panel,
    Button,
    Form
} from 'react-bootstrap';
export const StyledPanel = styled(Panel) `
    width: 98%;
   
     @media (max-width: 768px) { 
        width: 100%;
    }
    .panel-heading{
        background-color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_HEADER_FORM]};
        background-image:none;
        border-color:${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_HEADER_FORM]};
    }
    .page-link{
        background-color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_BUTTON_NEW]}!important;
    }
    .react-bs-table-tool-bar{
        display:none;
    }
    .react-datepicker{
        font-size:1.0rem;
    }

    .react-datepicker__input-container{
        input{
            width:139px;
        }
        
    }
`;

export const StyleButton = styled(Button) `
    color: #fff;
    padding-left:10px;
    margin-right :10px;
    background-color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_BUTTON_SAVE]};
    background-image: none;
`;

export const StyleForm = styled(Form) `
position: relative;
top: 30px;`;
