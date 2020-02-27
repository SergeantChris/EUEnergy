import React, { Component } from 'react';
import { Link, withRouter } from "react-router-dom";
import { UserConsumer } from './UserContext';

const css = {
    width:'100%',
    paddingLeft: 40,
    paddingRight: 40
    

}

/*
 * This is a React component, realized as function.
 */
const NavLink = props => {    
    const link = <Link className="nav-link" to={props.to}>{props.label}</Link>;
    if (props.to === props.location) {
        return <li className="nav-item active">{link}</li>
    }
    else {
        return <li className="nav-item">{link}</li>
    }    
}

/*
 * This is the same as above, more verbose.
 */
class NavMenu extends Component {
    render() {
        if (this.props.context.username) {
            return (
                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav mr-auto">               
                        <NavLink label="Home" to="/main" location={this.props.location.pathname} />
                        <NavLink label="Logout" to='/logout' location={this.props.location.pathname} />                        
                    </ul>                                      
                </div>
            );
        }
        else {
            return null;
        }
    }
}

/*
 * Back to minimum verbosity!
 */
const UserAvatar = props => {
    if (props.context.username) {
        return (
            <span className='navbar-text'>{props.context.username}</span>
        );
    }
    else {
        return null;
    }
}

class Nav extends Component {    
    
    render() {
        console.log("Nav Props:",this.props)
        return (            
            <div className="row" >
                <nav className="navbar navbar-expand-lg navbar-dark bg-dark" style={css}>
                    <Link className="navbar-brand" to="/" >Power Ranging through Europe</Link>
                    <button id = "btn1" className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>

                    <UserConsumer>
                    { context => 
                        <React.Fragment>
                            <NavMenu 
                                location={this.props.location} 
                                context={context}
                            />
                            <UserAvatar location={this.props.location} context={context} />
                        </React.Fragment>
                    }
                    </UserConsumer>
                </nav>
            </div>
        );
    }
    
}

export default withRouter(Nav);
