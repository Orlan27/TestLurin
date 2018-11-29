import styled from 'styled-components';
import { ITEM_THEME } from '../../utils/theme';
import {
  Panel
} from 'react-bootstrap';

export const StyledHeader = styled(Panel) `
  height: 88px;
  background-color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_HEADER_PAGE]};
  border-radius: 0; 
  border: none;

    margin-bottom: 0;
  img{
    width:180px;
  }
  span { 
   color:white;
   margin-right: 24px;
   padding-top:5px;
  }
  .user-data{
    text-align: right;
  }

  .header-icon{
    padding-top:20px;
    margin-right: 8px;
  }
  .header-container
  {
    text-align:right;
    margin-top: 20px;
    img{
      width:32px;
      margin-right:10px;
      cursor: pointer ;
    }
  }
  a{
    text-decoration: none;
    color: white;
    .text-icon{
      margin-right:10px;
    }
  }
  .logo-container{
    display: -webkit-inline-box;
  }
  .menu-icon{
    display:none;
    font-size:20px;
    margin-top: 15px;
  }
 
  @media (max-width: 1024px) {
    .menu-icon{
      display: block;
    }
  }
  @media (max-width: 520px) {
    span { 
      margin-right: 10px;
      padding-top:5px;
    }
    .menu-icon{
      margin-top: 10px;
      margin-right: 0;
    }
    img{
        width:154px;
    }
    .header-container
    {
      margin-top: 12px;
      padding-right: 0;
      img{
        width:24px!important;
      }
      span{
        font-size: 12px;
      }
    }
    }
   
  

`;
