import React, { Component } from 'react'

class EventBind extends Component {
    constructor(props) {
        super(props)
    
        this.state = {
             message: 'Hello'
        }
        this.clickHandle = this.clickHandle.bind(this)
    }
    
    clickHandle(){
        this.setState(prevState => ({
            message: (prevState.message==='Hello'?'Goodbye':'Hello')
        }))
    
    }
    render() {
        return (
            <div>
                <p>{this.state.message}</p>
                <button onClick = {this.clickHandle}>Click the bind Button </button>
            </div>
        )
    }
}

export default EventBind
