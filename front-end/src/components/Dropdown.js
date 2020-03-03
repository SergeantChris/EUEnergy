import Select from 'react-select';
import Area from '../Data/Area.json'
import Type from '../Data/Type.json'
import PType from '../Data/ProductionType'
import Resolution from '../Data/Resolution'
import React, { Component } from 'react'
import Year from '../Data/Year'
import Month from '../Data/Month'
import Day29 from '../Data/Day29'
import Day30 from '../Data/Day30'
import Day31 from '../Data/Day31'

import  './Select.css';


//const AreaDescreption  = ""
//const ProductionTypeDescription = ""
//const TypeDescription = ""

const selectStyle = {
    width:350,
    padding: 10,
    textAlign: ' justify',
    justifyContent: 'space-between',
}


class Dropdown extends Component {
    constructor(props) {
        super(props)
        this.state = {
            value:"",
            label:""
        }
        this.handleChange = this.handleChange.bind(this)
        this.sendData = this.sendData.bind(this)
    }
    
    sendData = () => {
        this.props.value(this.state.value,this.state.label);
    }; 
    handleChange(inputValue){
        //console.log("Label: ",inputValue.label)
        this.setState({value: inputValue.value,label:inputValue.label},() => this.sendData())
    }


    
    render() {
        switch (this.props.type) {
            case "Type":
                this.list = Type;
                break;
            case "Area":
                this.list = Area;
                break;
            case "ProductionType":
                this.list = PType;
                break;
            case "Resolution":
                this.list = Resolution;
                break;
            case "Year":
                this.list = Year;
                break;
            case "Month":
                this.list = Month;
                break;
            case "Day":
                switch (this.props.month) {
                    case "29":
                        this.list = Day29;
                        break;
                    case "30":
                        this.list = Day30;
                        break;
                    default:
                        this.list = Day31;
                        break;
                }
                break;
            default:
                break;
        }


        return <div style = {selectStyle}>
        <Select
          onChange = {this.handleChange}
          className="basic-single"
          classNamePrefix="select"
          options={this.list}
        />

        
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
*/