import React from 'react';
import Select from 'react-select';
import test from './test.json'


const Dropdown = (props) => {
    console.log(test)
    return <div style = {selectStyle}>
        <Select
          className="basic-single"
          classNamePrefix="select"
          defaultValue={test[0]}
          name="type"
          options={test}
        />
    </div>
}

export default Dropdown

const selectStyle = {
    width:500,
    paddingRight: 30,
    paddingLeft: 30
}