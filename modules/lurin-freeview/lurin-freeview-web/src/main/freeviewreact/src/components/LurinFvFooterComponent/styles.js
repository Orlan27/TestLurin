import styled from 'styled-components';
import {
    Panel
} from 'react-bootstrap';
import { ITEM_THEME } from '../../utils/theme';

export const StyledFooter = styled(Panel)`
    position: fixed; 
    width: 100%;
    padding: 0;
    margin: 0;
    background-color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_FOOTER_PAGE]};
    border-radius: 0;
    bottom: 0;
    margin-top: 50px;
    border: none;
    border-radius: 0; 
    z-index:2;  
    .panel-body{
        text-align:center;
        color:white;
    }
`;
