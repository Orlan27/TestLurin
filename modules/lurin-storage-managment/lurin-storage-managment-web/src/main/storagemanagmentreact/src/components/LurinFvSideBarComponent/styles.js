import styled from 'styled-components';
import { ITEM_THEME } from '../../utils/theme';
import { slide as Menu } from 'react-burger-menu';

export const StyledContent = styled(Menu) `
  background-color: ${props => props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_SIDEBAR_PAGE]};
  border-radius: 0; 
  border: none;
  height:650px;
  color:white;
  padding:0;
  width:300px!important;
  z-index:0!important;
  .panel{
    background-color:transparent;
  }
  .panel-heading{
    background-image:none;
    background-color: transparent;
    color:white;
    padding: 10px 5px;
    padding-bottom: 0;
    font-weight: bold;
  }
  .panel-default{
    border-color:transparent;
    padding-left:25px;
  }
  .panel-title{
    font-size:15px;
  }
  .panel-body{
    padding-top: 5px;
    div{
      margin-top:6px;
      font-size:13px;
    }
  }
  img{
    width: 32px;
    margin-right:5px;
  }
  .panel-group{
    padding-top: 15px;
    padding-bottom:130px;
  }
  .bm-burger-button {
    display:none;
  }
  a{
    text-decoration:none;
    cursor:pointer;
    color:white;
  }
  @media (max-width: 450px) {

  }
  @media (max-width: 1024px) {
    width:235px!important;
    .panel-default{
      padding-left:10px;
    }
    .panel-heading{
      font-size:12px;  
    }
  }

`;
