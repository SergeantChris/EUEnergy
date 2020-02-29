import React from 'react';
import Select from 'react-select';
import Area from './Area.json'
import Type from './Type.json'



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

export default Dropdown

const selectStyle = {
    width:500,
    paddingRight: 30,
    paddingLeft: 30,
    height: 300
}