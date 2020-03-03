import React, { Component } from 'react';
import { UserContext } from './UserContext';

export class Login extends Component {        
    
    static contextType = UserContext;
    
    constructor(props) {
        super(props);
        this.username = React.createRef();
        this.password = React.createRef();
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    
    handleSubmit(event) {
        console.log('ref to username: ', this.username.current);
        
        const u = this.username.current.value;
        const p = this.password.current.value;
        console.log('Submitting...', u, p);

        fetch('https://localhost:8765/energy/api/Login',{
            method: 'POST',
            headers: {
                'Content-Type':'application/x-www-form-urlencoded',
                "User": u,
                "Pass": p
            },
            body: new URLSearchParams({
                
            })
        }).then((response) => response.json())
        .then(json => {    
            
            console.log("Response json:",json.Token);
            if(json.Token){
                            //store the user's data in local storage
            //to make them available for the next
            //user's visit
            localStorage.setItem('token', json.Token);
            localStorage.setItem('username', u);
            
            //use the setUserData function available
            //through the UserContext
            this.context.setUserData(json.Token, u);
            
            //use the history prop available through
            //the Route to programmatically navigate
            //to another route            
            this.props.navProps.history.push('/main');
            }

        }); 
        /*
       localStorage.setItem('token', 'token');
        localStorage.setItem('username', u);
        this.context.setUserData('token', u);
        this.props.history.push('/main');
        */
        
    }
    
    render() {       
        return (                                    
            <form onSubmit={this.handleSubmit}>

                <label htmlFor="username">Username</label><br/>
                <input id="username" type="text" ref={this.username} /><br/>

                <label htmlFor="password">Password</label><br/>
                <input id="password" type="password" ref={this.password} /><br/><br/>            

                <button className="btn btn-primary" type="submit">
                    Login
                </button>
            </form>
        );        
    }    
}

export class Logout extends Component {
    
    static contextType = UserContext;
    
    doLogout() {
        localStorage.removeItem('token');
        localStorage.removeItem('username');
                    
        this.context.setUserData(null, null);
        
        this.props.history.push('/');
    }
    
    componentDidMount() {
        //perform an ajax call to logout
        //and then clean up local storage and
        //context state.
        fetch('https://localhost:8765/energy/api/Logout',{
            method: 'POST',
            headers: {
                'X-OBSERVATORY-AUTH': this.context.username,
                'Content-Type':'application/x-www-form-urlencoded',
            }
        }).then(() => this.doLogout());
    }
    
    render() {
        return (<h2>Loggin out...</h2>);
    }
}