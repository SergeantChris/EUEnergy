import Select from 'react-select';
import Area from './Area.json'
import Type from './Type.json'
import PType from './ProductionType'
import React, { Component } from 'react'





class Dropdown extends Component {
    constructor(props) {
        super(props)
        this.state = {
            value: ""
        }
        this.handleChange = this.handleChange.bind(this)
        this.sendData = this.sendData.bind(this)
    }
    
    sendData = () => {
        this.props.parentCallback(this.state.value);
    };
    handleChange(inputValue){
        console.log(inputValue.label)
        this.setState({value: inputValue.label})
        this.sendData()
    }



    
    render() {
        switch (this.props.type) {
            case "Type":
                this.list = Type
                break;
            case "Area":
                this.list = Area
                break;
            default:
                this.list = Type
                break;
        }


        return <div style = {selectStyle}>
        <Select
          onChange = {this.handleChange}
          className="basic-single"
          classNamePrefix="select"
          defaultValue={this.list[0]}
          name="type"
          options={this.list}
        />
        This could have been a nice place to import information about the above selection for example. I am not sure wet, but we could definately do it. Also, this test sould be updated in case of emergency. Just kidding. It sould be updated on falase selection. It could also contain a definition of the above selections.

    </div>
        
    }
}


export default Dropdown
/*
let list
 
const Dropdown = React.forwardRef((props,ref) => {
    switch (props.type) {
        case "Type":
            list = Type
            break;
        case "Area":
            list = Area
            break;
        default:
            list = Type
            break;
    }

    console.log(Type)
    return <div style = {selectStyle}>
        <Select
          ref = {ref}
          className="basic-single"
          classNamePrefix="select"
          defaultValue={list[0]}
          name="type"
          options={list}
        />
        This could have been a nice place to import information about the above selection for example. I am not sure wet, but we could definately do it. Also, this test sould be updated in case of emergency. Just kidding. It sould be updated on falase selection. It could also contain a definition of the above selections.

    </div>
})

export default Dropdown 
*/
const selectStyle = {
    width:500,
    paddingRight: 30,
    paddingLeft: 30,
    height: 250,
    padding: '0.16666666666667em 0.5em',
    textAlign: 'center',
    justifyContent: 'space-between'
}

//-------------------------------------------------------------------------------------------------------
export class DropArea extends Component {
    constructor(props) {
        super(props)
    
        this.state = {
             value : null
        }
    }
    
    render() {
        return (
            <div style = {selectStyle}>
            <Select
          className="basic-single"
          classNamePrefix="select"
          defaultValue={Area[0]}
          name="type"
          options={Area}

        />
        This could have been a nice place to import information about the above selection for example. I am not sure wet, but we could definately do it. Also, this test sould be updated in case of emergency. Just kidding. It sould be updated on falase selection. It could also contain a definition of the above selections.

    </div>
        )
    }
}
//-------------------------------------------------------------------------------------------------------------------

export class DropPType extends Component {
    constructor(props) {
        super(props)
    
        this.state = {
             value : null
        }
    }
    
    render() {
        return (
            <div style = {selectStyle}>
            <Select
          className="basic-single"
          classNamePrefix="select"
          defaultValue={PType[0]}
          name="type"
          options={PType}

        />
        This could have been a nice place to import information about the above selection for example. I am not sure wet, but we could definately do it. Also, this test sould be updated in case of emergency. Just kidding. It sould be updated on falase selection. It could also contain a definition of the above selections.

    </div>
        )
    }
}