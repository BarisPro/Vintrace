import React, { useRef, useState, useContext } from 'react'
import SearchBarItem from './searchbar-item'
import { ScreenContext } from '../App'

function SearchBar({ onSearch, wines = [] }) {
    const inputEl = useRef(null)
    const [searchText, setSearchText] = useState('')
    const { setWineDetail } = useContext(ScreenContext)

    function onSearchEnter(event) {
        event.preventDefault()
        onSearch(searchText)
        // clear()
    }

    function onSearchChange(event) {
        const text = event.target.value
        onSearch(text)
        setSearchText(text)
    }

    function clearSearch() {
        clear()
    }

    function clear() {
        setSearchText('')
    }


    return (
        <div className='searchbar-container'>
            <form className='searchbar-textbox' onSubmit={onSearchEnter}>
                <svg onClick={onSearchEnter} className='lookup-icon' viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M3.27344 8.58594C4.0026 9.3151 4.88802 9.67969 5.92969 9.67969C6.97135 9.67969 7.85677 9.3151 8.58594 8.58594C9.3151 7.85677 9.67969 6.97135 9.67969 5.92969C9.67969 4.88802 9.3151 4.0026 8.58594 3.27344C7.85677 2.54427 6.97135 2.17969 5.92969 2.17969C4.88802 2.17969 4.0026 2.54427 3.27344 3.27344C2.54427 4.0026 2.17969 4.88802 2.17969 5.92969C2.17969 6.97135 2.54427 7.85677 3.27344 8.58594ZM10.9297 9.67969L15.0703 13.8203L13.8203 15.0703L9.67969 10.9297V10.2656L9.44531 10.0312C8.45573 10.8906 7.28385 11.3203 5.92969 11.3203C4.41927 11.3203 3.13021 10.7995 2.0625 9.75781C1.02083 8.71615 0.5 7.4401 0.5 5.92969C0.5 4.41927 1.02083 3.14323 2.0625 2.10156C3.13021 1.03385 4.41927 0.5 5.92969 0.5C7.4401 0.5 8.71615 1.03385 9.75781 2.10156C10.7995 3.14323 11.3203 4.41927 11.3203 5.92969C11.3203 6.47656 11.1901 7.10156 10.9297 7.80469C10.6693 8.48177 10.3698 9.02865 10.0313 9.44531L10.2656 9.67969H10.9297Z" fill="#CCCCCC"/>
                </svg>
                <input ref={inputEl} value={searchText} onChange={onSearchChange} type='text' name='wine' className='search-input' placeholder='Search by lot and description'/>
                <svg onClick={clearSearch} className={`close-icon ${searchText.length > 0 ? '' : 'hidden'}`} viewBox="0 0 12 12" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M11.8203 1.35156L7.17188 6L11.8203 10.6484L10.6484 11.8203L6 7.17188L1.35156 11.8203L0.179688 10.6484L4.82812 6L0.179688 1.35156L1.35156 0.179688L6 4.82812L10.6484 0.179688L11.8203 1.35156Z" fill="#3A3B3B"/>
                </svg>
            </form>
            <ul className='searchbar-list'>
                {wines.map((w, i) => (<SearchBarItem key={i} onClick={() => setWineDetail(w)} wine={w}/>))} 
            </ul>
        </div>
    );
}

export default SearchBar;