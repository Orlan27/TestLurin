import styled from 'styled-components';
import { BACKGROUND_COLOR_FORM, BACKGROUND_ADD_BUTTON, BACKGROUND_DELETE_BUTTON } from '../../utils/theme';

import {
    Panel,
    Button
} from 'react-bootstrap';
export const StyledPanel = styled(Panel) `
    
    width: 85%;
     @media (max-width: 768px) { 
        width: 100%;
    }

    .panel-heading {
        background-image: none;
        background-repeat: none;
        background-color: ${BACKGROUND_COLOR_FORM};
    }
    .react-bs-table-add-btn{
        background-color:${BACKGROUND_ADD_BUTTON};
        background-image:none;
        border-color:none!important;
        margin-right: 2px;
    }
    .react-bs-table-del-btn{
        background-color:${BACKGROUND_DELETE_BUTTON};
        background-image:none;
        border-color:none!important;
    }
}
`;

export const StyleButton = styled(Button) `
    color: #fff;
`;

