import {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import {api} from '../../services/api';

export default function ListaAvaliacoes(){

    const [avaliacoes, setAvaliacoes] = useState([]);

    useEffect(() => {
        api.listarAvaliacoes().then(setAvaliacoes);
    }, []);

    return (

        <div style={{fontFamily: 'sans-serif'}}>

            <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>

                <h2>Avaliações cadastradas</h2>

                <Link to="/professor/avaliacoes/nova"><button>Adicionar avaliação</button></Link>

            </div>

            <div>

                {avaliacoes.map((av) => (

                    <h3 key={av.idAvaliacao} style={{marginBottom: '0.6rem'}}>
                        <strong>{av.titulo} | </strong>Data: {av.data} ({av.horaInicio} às {av.horaFim})
                        {av.descricao && <p style={{color: '#555'}}>{av.descricao}</p>}
                    </h3>

                ))}

            </div>

        </div>

    );

}