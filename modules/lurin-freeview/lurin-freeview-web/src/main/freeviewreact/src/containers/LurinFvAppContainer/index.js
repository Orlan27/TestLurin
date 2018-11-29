/**
 *
 * LurinFvAppContainer
 *
 */

import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
// import {  Route,  Router , hashHistory} from 'react-router-dom';
import { Router, Route, hashHistory } from 'react-router';
import { IntlProvider } from 'react-intl'
import { Col
} from 'react-bootstrap';
import LurinFvHeaderComponent from '../../components/LurinFvHeaderComponent';
import LurinFvMaintenanceZoneContainer from '../LurinFvMaintenanceZoneContainer';
import LurinFvFreeviewManagmentContainer from '../LurinFvFreeviewManagmentContainer';
import LurinFvCampaingManagmentContainer from '../LurinFvCampaingManagmentContainer';
import LurinFvFooterComponent from '../../components/LurinFvFooterComponent';
import LurinFvSideBarComponent from '../../components/LurinFvSideBarComponent';
import LurinChangeUserData from '../LurinChangeUserData';
import About from '../../components/About';
import { StyledCol, StyledArticle } from './styles';
import {
    makeSelectCurrentStyle,
    makeSelectLang
  } from './selectors';
  
import langMessages from '../../utils/langMessages';

export class LurinFvAppContainer extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      isMenuOpen: !(window.innerWidth<1025)
    };
    this.toggleMenu=this.toggleMenu.bind(this);
  }

  toggleMenu = () => {
    const mq = window.matchMedia('(min-width: 1025px)');
    if(!mq.matches){
      this.setState( {
        isMenuOpen: !this.state.isMenuOpen
      });
    }
  }

  closeMenu = () => {
    const mq = window.matchMedia('(min-width: 1025px)');
    if(!mq.matches){
      this.setState( {
        isMenuOpen: false
      });
    }
  }

  render() {
    const mq = window.matchMedia('(min-width: 1024px)');
    
    const mqH500 = window.matchMedia('(min-height: 500px)');
    const mqH768 = window.matchMedia('(min-height: 768px)');
    const mqH890 = window.matchMedia('(min-height: 890px)');
    let maxHeigth=650;
    if(mqH500.matches)
      maxHeigth=500;  
    if(mqH768.matches)
      maxHeigth=600;
    if(mqH890.matches)
      maxHeigth=700;
    return (
      <IntlProvider locale={this.props.lang} messages={langMessages[this.props.lang]}>
      <StyledArticle currentStyles={this.props.currentStyles} maxHeigth={maxHeigth} >

      <LurinFvHeaderComponent toggleMenu={this.toggleMenu} currentStyles={this.props.currentStyles} />
        <StyledCol sm={3} xs={3}>
         <LurinFvSideBarComponent 
         isMenuOpen={this.state.isMenuOpen} allowEvent={mq.matches} currentStyles={this.props.currentStyles} />
      </StyledCol>
         <Col sm={9} xs={9} onClick={this.closeMenu} className="main-content" >
         <Router history={hashHistory}>
	         <Route exact path="/" component={LurinFvFreeviewManagmentContainer} />
	         <Route exact path="adminFreeview" component={LurinFvFreeviewManagmentContainer} />
	         <Route path="/about" component={About} />
	         <Route path="/CampaignManagement" component={LurinFvCampaingManagmentContainer} />
           <Route path="/UserChangeData" component={LurinChangeUserData} />
       </Router>
       </Col>
      <LurinFvFooterComponent
      currentStyles={this.props.currentStyles} 
      />
    </StyledArticle >
    </IntlProvider>
    );
  }
}

LurinFvAppContainer.propTypes = {
  hashHistory: PropTypes.object,
  lang: PropTypes.string,
};

const mapStateToProps = createStructuredSelector({
   currentStyles: makeSelectCurrentStyle(),
   lang: makeSelectLang()
});

function mapDispatchToProps(dispatch) {
  return {
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LurinFvAppContainer);
