import React, { useEffect, useState } from 'react'
import SearchBar from './components/searchbar'
import { createWine } from './domain/wine'

function Search() {
  const [wines, setWines] = useState([])
  const [searchedWines, setSearchedWines] = useState([])

  useEffect(() => {
    async function getWines() {
      try {
        const response = await fetch('http://localhost:9191/api/breakdown/year/all', {
          method: 'GET',
          mode: 'cors'
        })
        const wineList = await response.json()
        setWines(wineList.map(w => createWine(w)))
      } catch {
        console.error('Cannot retrieve the list of wines.')
      }
    }
    getWines()
  }, [])

  function onSearch(text) {
    setSearchedWines(wines.filter(w => (w.lotCode && w.lotCode.includes(text)) || (w.description && w.description.includes(text))))
  }

  return (
    <div className='search-main'>
        <div className='search-title'>
            <h1>Wine search</h1>
            <svg className='glass-icon' viewBox="0 0 17 27" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M16.5 7.92676V0.969727C16.5 0.703125 16.2812 0.5 16.0077 0.5H0.992308C0.718803 0.5 0.5 0.703125 0.5 0.969727V7.92676C0.5 11.875 3.8094 15.0869 7.99402 15.3281V25.5732H0.992308C0.718803 25.5732 0.5 25.7764 0.5 26.0303C0.5 26.2969 0.718803 26.5 0.992308 26.5H16.0077C16.2812 26.5 16.5 26.2969 16.5 26.0303C16.5 25.7764 16.2812 25.5732 16.0077 25.5732H9.00598V15.3281C13.1906 15.0869 16.5 11.875 16.5 7.92676ZM1.49829 1.42676H15.5017V3.72461C14.941 3.88965 13.6145 4.21973 12.0419 4.21973C8.80085 4.21973 8.03504 2.81055 4.75299 2.81055C3.33077 2.81055 2.18205 3.11523 1.49829 3.35645V1.42676ZM1.49829 7.92676V4.34668C2.03162 4.13086 3.22137 3.7373 4.75299 3.7373C7.84359 3.7373 8.25385 5.12109 12.0829 5.12109C13.1769 5.12109 14.353 4.98145 15.5017 4.67676V7.92676C15.5017 11.5195 12.3701 14.4268 8.5 14.4268C4.62991 14.4268 1.49829 11.5195 1.49829 7.92676Z" fill="#3A3B3B"/>
            </svg>
        </div>
        <SearchBar onSearch={onSearch} wines={searchedWines}/>
    </div>
  );
}

export default Search;