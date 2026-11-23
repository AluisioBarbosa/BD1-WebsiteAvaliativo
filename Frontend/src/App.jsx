import {useState} from 'react'
import {BrowserRouter} from 'react-router-dom';
import WebRoutes from './routes/web';

function App() {
  const [usuario, setUsuario] = useState(null);

  return (

    <>

      <BrowserRouter>

        <WebRoutes usuario={usuario} setUsuario={setUsuario} />

      </BrowserRouter>

    </>

  )

}

export default App
