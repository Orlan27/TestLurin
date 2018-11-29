import styled from 'styled-components';
import { ITEM_THEME } from '../../utils/theme';

import {
    Panel,
    Button,
    Form
} from 'react-bootstrap';
export const StyledPanel = styled(Panel) `
    width: 85%;
    margin: auto;
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
`;

export const StyleButton = styled(Button) `
    color: #fff;
`;

export const StyleForm = styled(Form) `
position: relative;
top: 30px;
`;
