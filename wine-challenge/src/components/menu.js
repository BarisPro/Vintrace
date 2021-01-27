import React, { useState } from 'react'

function Menu({ onItemSelect, btnNames = []}) {
    const [btnHighlighted, setBtnHighlighted] = useState(0)

    function onItemClick(index, name) {
        setBtnHighlighted(index)
        onItemSelect(index, name)
    }

    return (
        <div className='btn-stacks'>
            {btnNames && btnNames.map((name, i) => 
                (<button 
                    className={`menu-btn ${btnHighlighted === i ? 'highlighted' : ''}`} 
                    key={i} 
                    type='button' 
                    onClick={() => onItemClick(i, name)}>{name}</button>))}
        </div>
    )
}

export default Menu