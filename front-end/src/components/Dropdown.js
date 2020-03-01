import Select from 'react-select';
import Area from './Area.json'
import Type from './Type.json'
import PType from './ProductionType'
import React, { Component } from 'react'


class Dropdown extends Component {
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
          defaultValue={Type[0]}
          name="type"
          options={Type}

        />
        This could have been a nice place to import information about the above selection for example. I am not sure wet, but we could definately do it. Also, this test sould be updated in case of emergency. Just kidding. It sould be updated on falase selection. It could also contain a definition of the above selections.

    </div>
        )
    }
}


export default Dropdown

/*
const Dropdown = (props) => {
    console.log(Type)
    return <div style = {selectStyle}>
        <Select
          className="basic-single"
          classNamePrefix="select"
          defaultValue={Type[0]}
          name="type"
          options={Type}
        />
        This could have been a nice place to import information about the above selection for example. I am not sure wet, but we could definately do it. Also, this test sould be updated in case of emergency. Just kidding. It sould be updated on falase selection. It could also contain a definition of the above selections.

    </div>
}

export default Dropdown */

const selectStyle = {
    width:500,
    paddingRight: 30,
    paddingLeft: 30,
    height: 300,
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