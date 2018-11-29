import React from 'react';
import { render } from 'react-dom';
import LurinFvAppContainer from './containers/LurinFvAppContainer';
import { Provider } from 'react-redux';
import { addLocaleData } from 'react-intl';
import en from 'react-intl/locale-data/en';
import es from 'react-intl/locale-data/es';
import pt from 'react-intl/locale-data/pt';
import configureStore from './configureStore';


window.React = React;
addLocaleData(en);
addLocaleData(es);
addLocaleData(pt);

const store = configureStore();
const MOUNT_NODE = document.getElementById('content');
render(
  (<Provider store={store}>
    <LurinFvAppContainer />
  </Provider>), MOUNT_NODE
);
