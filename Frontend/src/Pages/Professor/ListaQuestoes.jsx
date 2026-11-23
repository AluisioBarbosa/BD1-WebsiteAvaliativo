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
            <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem'}}>
                <h2 style={{margin: 0}}>Questões Cadastradas</h2>
                <Link to="/professor/questoes/nova">
                    <button style={{padding: '0.5rem 1rem', cursor: 'pointer'}}>Adicionar questão</button>
                </Link>
            </div>

            <ul>
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
            </ul>
        </div>
    );
}