import React, {Component} from 'react'
import { withRouter } from "react-router-dom";
import { UserConsumer } from '../UserContext';
import {Login} from '../Auth'
import {Table} from 'react-bootstrap'

const guest = 'Please login to see the featurs of our app'



export class Welcome extends Component {
    render() {
        console.log("Welcome Props",this.props)
        return (
            <div className="container">
                <Table >
                    <tbody>
                        <tr>    
                            <UserConsumer>
                                { context => 
                                        <React.Fragment>
                                            <td>
                                                <h1>Welcome </h1>
                                                <p>Welcome to the Power Ranging through Europe Application. This is a web application designed to provide data about the power consumption and production both visually and as text. To use the app, you have to be logged in, if you want to to get access to the data. For each data request, you can choose between the data type, the duration of the data as well as the resolution of it. This application also offers a command line tool and a restful API, which we would highly suggest that you try. We hope you enjoy our work and all the effort we put in it.
                                              
                                               <p>{context.username?'':guest} </p>
                                                {context.token}
                                            </td>
                                            {context.username?null:<td><Login navProps = {this.props}/></td>}
                                        </React.Fragment>
                                    }
                            </UserConsumer>    
                        </tr>
                </tbody>
            </Table>
          </div>
        )
    }
}

export default withRouter(Welcome)

