import React, { useContext, useState, useEffect } from 'react'
import Menu from './components/menu'
import { ScreenContext } from './App'

function WineDetail() {
    const { setWineDetail, wineDetail: { lotCode, description, tankCode, volume, productState, ownerName} } = useContext(ScreenContext)
    const [customLotCode, setCustomLotCode] = useState('')
    const [isEditable, setIsEditable] = useState(false)
    const [percentInfo, setPercentInfo] = useState([])
    const [menuBtnName, setMenuBtnName] = useState('Year')

    useEffect(() => {
        console.log(percentInfo)
    }, [percentInfo])

    useEffect(() => {
        setCustomLotCode(lotCode)
        getPercentInfo('year')
    }, [])
    
    function onLotChange(event) {
        const val = event.target.value
        setCustomLotCode(val)
    }

    function onMenuItemSelect(index, btnNameClicked) {
        setMenuBtnName(btnNameClicked)
        let apiPath = 'year'
        switch (index) {
            case 0:
                apiPath = 'year'
                break;
            case 1:
                apiPath = 'variety'
                break;
            case 2:
                apiPath = 'region'
                break;
            case 3:
                apiPath = 'year-variety'
                break;
        }
        getPercentInfo(apiPath)
    }

    async function getPercentInfo(apiPath) {
        console.log(apiPath)
        try {
            const response = await fetch(`http://localhost:9191/api/breakdown/${apiPath}/11YVCHAR001`, {
              method: 'GET',
              mode: 'cors'
            })
            const { breakdown } = await response.json()
            console.log(breakdown || [])
            setPercentInfo(breakdown || [])
          } catch (error){
            console.error(error)
          }
    }

    return (
        <div className='wine-detail'>
            <div className='wine-detail-container'>
                <button onClick={() => setWineDetail(null)} type='button' className='back-btn'>
                    <svg viewBox="0 0 22 22" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M21.6875 9.6875V12.3125H5.4375L12.875 19.8125L11 21.6875L0.3125 11L11 0.3125L12.875 2.1875L5.4375 9.6875H21.6875Z" fill="#3A3B3B"/>
                    </svg>
                </button>
                <div className='wine-detail-header'>
                    <div className='left-side-header'>
                        <div className='wine-btn'>W</div>
                        <input className='editable-title' disabled={!isEditable} onChange={onLotChange} type='text' value={customLotCode}></input>
                    </div>
                    <button className='edit-btn' type='button' onClick={() => setIsEditable(!isEditable)}>
                        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path d="M23.625 5.375L21.1875 7.8125L16.1875 2.8125L18.625 0.375C18.875 0.125 19.1875 0 19.5625 0C19.9375 0 20.25 0.125 20.5 0.375L23.625 3.5C23.875 3.75 24 4.0625 24 4.4375C24 4.8125 23.875 5.125 23.625 5.375ZM0 19L14.75 4.25L19.75 9.25L5 24H0V19Z" fill="white"/>
                        </svg>
                    </button>
                </div>
                <div>{description}</div>
                <table className='info-table'>
                    <tr>
                        <th></th>
                        <th></th>
                    </tr>
                    <tr>
                        <td>Volume</td>
                        <td>{volume}</td>
                    </tr>
                    <tr>
                        <td>Tank Code</td>
                        <td>{tankCode}</td>
                    </tr>
                    <tr>
                        <td>Product state</td>
                        <td>{productState}</td>
                    </tr>
                    <tr>
                        <td>Owner</td>
                        <td>{ownerName}</td>
                    </tr>
                </table>
                <div>
                    <Menu onItemSelect={onMenuItemSelect} btnNames={['Year', 'Variety', 'Region', 'Year & Variety']}/>
                    <table className='percent-table no-spacing' cellspacing='0'>
                        <thead>
                            <tr>
                                <th>{menuBtnName}</th>
                                <th>Percentage</th>
                            </tr>
                        </thead>
                        <tbody>
                            {percentInfo && percentInfo.length > 0 && percentInfo.map((info, i) => {
                                const { key, percentage } = info
                                return (
                                    <tr key={i}>
                                        <td>{key}</td>
                                        <td>{percentage}%</td>
                                    </tr>
                                )
                            })}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    )
}

export default WineDetail