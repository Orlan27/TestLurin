import styled from 'styled-components';
import { ITEM_THEME } from '../../utils/theme';
import {
    Col
} from 'react-bootstrap';
import { slide as Menu } from 'react-burger-menu';


export const StyledCol = styled(Col)`
  padding:0;
  z-index:1;
`;

export const StyledArticle = styled.article`
.main-content{
  padding-top:20px;
  /*max-height: ${props => props.maxHeigth}px;*/
  height: 100%;
  overflow-y: auto;
  max-height: 890px;
  overflow-y: scroll;
  padding-bottom: 150px;

  @media (max-height: 890px) {
    max-height: 750px;
 }
 @media (max-height: 768px) {
    max-height: 600px;
 }
  @media (max-height: 500px) {
    max-height: 450px;
 }
};

`;

export const StyledMenuMobile = styled(Menu)`
  background-color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_SIDEBAR_PAGE]};
  border-radius: 0; 
  border: none;
  height:650px;
  color:white;
  padding:0;
  width:80%;
  z-index:1!important;
`;

