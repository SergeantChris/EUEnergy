import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';
import {Greet2} from './components/greet.js'
import {Greet} from './components/greet.js'


class App extends Component {
	render(){
		return (
			<div className="App">
			Imprted greet.js
			<Greet2/>
			<Greet/>
			</div>
		);
	}
}

export default App;
