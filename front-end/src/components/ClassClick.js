import React, { Component } from 'react'

class ClassClick extends Component {
    clickHandler(){
        console.log("Yep you did it again")
    }

    render() {
        return (
            <div>
                <br/><button onClick = {this.clickHandler}>Click me again</button>
            </div>
        )
    }
}

export default ClassClick
