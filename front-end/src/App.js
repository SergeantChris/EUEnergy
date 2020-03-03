import React, { Component } from 'react';
import { HashRouter as Router, Route, Redirect } from "react-router-dom";
import './App.css';
import Nav from './Nav';
import Main from './Main';
//import Footer from './Footer';
import { Logout } from './Auth';
import { UserProvider } from './UserContext';
import Welcome from './components/Welcome';

class App extends Component {

  constructor(props) {
    super(props)
    this.state = {
      token: props.userData.token,
      username: props.userData.username,
      style: {
        backgroundColor:'#fff',
        height:'93vh'
      },
      mainStyle:{
        width:'100vh',
        paddingTop:20
      },
      setUserData: (token, username) => this.setState({
        token: token,
        username: username
      }),
    };
  }

  renderProtectedComponent(ProtectedComponent) {
    if (this.state.username !== null) {
      return  (props) => <ProtectedComponent {...props} />;
    }
    else {
      return (props) => <Redirect to='/' />;
    }
  }

  render() {
    return (
      <div >
        <div  style={this.state.style}>
          <UserProvider value={this.state}>
            <Router>
              <Nav />
              <div className = "container" style={this.state.mainStyle}>
                <Route path="/" exact component = {Welcome} />
                <Route path="/main" render={this.renderProtectedComponent(Main)} />
                {/*<Route path="/login" component={Login} />*/}
                <Route path="/logout" render={this.renderProtectedComponent(Logout)} />
              </div>
            </Router>
          </UserProvider>
          
          </div>
        </div>
    );
  }
}



export default App;
