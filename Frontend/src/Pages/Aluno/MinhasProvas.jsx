import {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import {api} from '../../services/api';

export default function MinhasProvas({usuario}){

    const [provas, setProvas] = useState([]);

    useEffect(() => {
        const idAluno = usuario?.id || 2;
        api.listarProvasAluno(idAluno).then(setProvas);
    }, [usuario]);

    return (

        <div style={{fontFamily: 'sans-serif', maxWidth: '600px'}}>


            <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}><h2>Minhas Avaliações</h2></div>

            {provas.length === 0 ? (
                <p>Nenhuma avaliação atribuída no momento.</p>
            ) : (
                <div style={{display: 'flex', flexDirection: 'column', gap: '0.8rem'}}>

                    {provas.map((prova) => (
                        <div
                            key={prova.idAvaliacao}
                            style={{
                                display: 'flex',
                                justifyContent: 'space-between',
                                alignItems: 'center',
                            }}
                        >
                            <div>

                                <strong>{prova.titulo}</strong>

                                <div style={{ fontSize: '0.9rem', color: '#555'}}>
                                    Data: {prova.data} ({prova.horaInicio} às {prova.horaFim})
                                </div>

                                {prova.descricao && (
                                    <p style={{fontSize: '0.9rem'}}>{prova.descricao}</p>
                                )}

                            </div>

                            <div>

                                {prova.respondida ? (
                                    <Link to="/aluno/resultados">
                                        <button>
                                            Ver Resultado
                                        </button>
                                    </Link>
                                ) : (
                                    <Link to={`/aluno/provas/${prova.idAvaliacao}/responder`}>
                                        <button>
                                            Responder Prova
                                        </button>
                                    </Link>
                                )}

                            </div>

                        </div>

                    ))}

                </div>

            )}

        </div>

    );

}