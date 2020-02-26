import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';
import {Greet,Greet2} from './components/Greet'
//import Message from './components/Message';
//import FunctionClick from './components/FunctionClick';
//import ClassClick from './components/ClassClick';
//import EventBind from './components/EventBind';
import ParentComponent from './components/ParentComponent';
import NameList from './components/NameList';
import RefsDemo from './components/RefsDemo';
import ClickCounter from './components/ClickCounter';
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

			<ClickCounter />

			<img src = {logo} className = "App-logo" alt = "logo" />
			</div>
		);
	}
}

export default App;
