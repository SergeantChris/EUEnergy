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
            <Table >
                <tbody>
                    <tr>    
                        <UserConsumer>
                                { context => 
                                    <React.Fragment>
                                        <td>
                                            <h1>Welcome </h1>
                                            <p>This app helps you see the energy consumtion through out europe. Again checking the length of this line in each case:
                                                logge in or not. This is small sooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo </p>
                                            <p>{context.username?'':guest} </p>
                                        </td>
                                        {context.username?null:<td><Login props = {this.props}/></td>}
                                    </React.Fragment>
                                }
                        </UserConsumer>    
                    </tr>
              </tbody>
          </Table>
        )
    }
}

export default withRouter(Welcome)

