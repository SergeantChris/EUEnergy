import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';
import {Greet,Greet2} from './components/Greet'
//import Welcome from './components/Welcome'


class App extends Component {
	render(){
		return (
			<div className="App">
			Imported greet.js
			<Greet name = 'George'/>
			<Greet name = 'Christina'/>
			<Greet name = 'Kostas'/>
			<Greet name = 'Athina'/>
			<img src = {logo} className = "App-logo" alt = "logo" />
			</div>
		);
	}
}

export default App;
