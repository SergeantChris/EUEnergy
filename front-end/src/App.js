import React, {Component} from 'react';
//import logo from './logo.svg';
import './App.css';
import {Greet,Greet2} from './components/Greet'
//import Welcome from './components/Welcome'


class App extends Component {
	render(){
		return (
			<div className="App">
			Imprted greet.js
			<Greet name = 'George'/>
			<Greet name = 'Christina'/>
			<Greet name = 'Kostas'/>
			<Greet name = 'Athina'/>
			</div>
		);
	}
}

export default App;
