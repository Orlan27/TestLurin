import React from 'react';
import { Link } from 'react-router';
import { version } from '../../package.json';

const App = ({ children }) => (
  <div>
    <header>
      <h1>Vista Principal  {version}</h1>
      <Link to="/about">About</Link>
      <Link to="/poweredby">Powered by</Link>
    </header>
    <section>
      {children || 'Welcome to React Starterify'}
    </section>
    <section>
      <a href="/logout" >Logout</a>
    </section>
  </div>
);

App.propTypes = { children: React.PropTypes.object };

export default App;
