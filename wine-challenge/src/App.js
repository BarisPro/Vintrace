import React, { useState } from 'react'
import Search from './search'
import WineDetail from './wine-detail'

export const ScreenContext = React.createContext({
  wineDetail: null
})

function App() {
  const [wineDetail, setWineDetail] = useState(null)

  return (
    <ScreenContext.Provider value={{ wineDetail: wineDetail, setWineDetail: setWineDetail}}>
      <div className='app'>
        {!(!!wineDetail) && <Search />}
        {wineDetail && <WineDetail lotCode='BA1997' description='Baris Monvoisin'/>}
      </div>
    </ScreenContext.Provider>
  );
}

export default App;
