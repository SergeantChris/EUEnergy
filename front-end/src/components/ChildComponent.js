import React from 'react'

function ChildComponent(props) {
    return (
        <div>
            <button onClick = {() => props.greetHandler('Im your kid')}>Greet parent!!</button>
        </div>
    )
}

export default ChildComponent
