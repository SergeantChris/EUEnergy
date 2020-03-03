import React, { Component } from 'react'




export default class MyButton extends Component {
    constructor(props) {
        super(props)
    
        this.state = {
            select:false
        }
    }

    handleClick = () =>{
        this.props.callBack(this.props.name);
    }
    
    btnStyle = () => {
        return{
            marginLeft:10,
            width:100,
            color:this.props.select?'white':'black',
            background: this.props.select?'#32aada':'#dadada',
            border:'white'
        }
     }
 

    render() {
        return (
        <button onClick = {this.handleClick} className="btn btn-info" style ={this.btnStyle()}>{this.props.name }</button>
        )
    }
}
