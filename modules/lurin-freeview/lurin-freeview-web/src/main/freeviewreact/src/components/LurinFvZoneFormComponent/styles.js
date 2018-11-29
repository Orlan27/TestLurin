import styled from 'styled-components';

import {
    Button,
    Panel
} from 'react-bootstrap';

export const StyleButton = styled(Button) `
    color: #fff;
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
`;
