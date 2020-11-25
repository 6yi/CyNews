import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import FastClick from 'fastclick'
import './mock.js/index'
FastClick.attach(document.body)


ReactDOM.render(

    <App />,

  document.getElementById('root')
);


