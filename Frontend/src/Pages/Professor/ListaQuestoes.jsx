import {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import {api} from '../../services/api';

export default function ListaQuestoes(){

    const [questoes, setQuestoes] = useState([]);

    useEffect(() => {
        api.listarQuestoes().then(setQuestoes);
    }, []);

    return (

        <div style={{fontFamily: 'sans-serif'}}>

            <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>

                <h2>Questões cadastradas</h2>

                <Link to="/professor/questoes/nova"><button>Adicionar questão</button></Link>

            </div>

            <div>

                {questoes.map((q) => (

                    <h3 key={q.idQuestao} style={{marginBottom: '0.5rem'}}>
                        {q.tipo === "MULTIPLA_ESCOLHA" && (
                            <>
                                <strong>Objetiva |</strong> {q.enunciado}
                            </>
                        )}

                        {q.tipo === "TEXTO_LIVRE" && (
                            <>
                                <strong>Subjetiva |</strong> {q.enunciado}
                            </>
                        )}
                    </h3>

                ))}

            </div>

        </div>

    );

}