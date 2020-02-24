import React from 'react'

function NameList() {
    const names =  ['George','Christina','Kostas', 'Athina']
    const nameLst = names.map((name,index) => <h3 key={index}>{name}</h3>)
    return (
        <div>
            { nameLst }
        </div>
    )
}

export default NameList
