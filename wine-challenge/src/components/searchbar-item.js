import React from 'react'

function SearchBarItem({ onClick, wine }) {
    const { lotCode, description, volume, tankCode } = wine
    return (
        <li onClick={onClick} className='searchbar-item'>
            <div className='row'>
                <h2 className='primary'>{lotCode}</h2>
                <div className='secondary'>{volume}</div>
            </div>
            <div className='row'>
                <div className='description'>{description}</div>
                <div className='secondary'>{tankCode}</div>
            </div>
        </li>
    )
}

export default SearchBarItem