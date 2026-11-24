import {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import { api } from '../../services/api';

export default function ResponderProva({ usuario }) {

    const { idAvaliacao } = useParams();
    const navigate = useNavigate();

    const [prova, setProva] = useState(null);
    const [respostas, setRespostas] = useState({});
    const [enviando, setEnviando] = useState(false);

    useEffect(() => {
        api.buscarProvaParaResponder(idAvaliacao).then(setProva);
    }, [idAvaliacao]);

    function marcarAlternativa(idQuestao, idAlternativa) {
        setRespostas((prev) => ({
            ...prev,
            [idQuestao]: { idAlternativa, textoResposta: null },
        }));
    }

    function digitarTexto(idQuestao, texto) {
        setRespostas((prev) => ({
            ...prev,
            [idQuestao]: { idAlternativa: null, textoResposta: texto },
        }));
    }

    async function handleSubmit(e) {

        e.preventDefault();
        setEnviando(true);

        const payload = {
            idAluno: usuario?.id || 2,
            idAvaliacao: Number(idAvaliacao),
            respostas: prova.questoes.map((q) => ({
                idQuestao: q.idQuestao,
                idAlternativa: respostas[q.idQuestao]?.idAlternativa ?? null,
                textoResposta: respostas[q.idQuestao]?.textoResposta ?? null,
            })),
        };

        try {
            await api.enviarRespostas(payload);
            alert('Respostas enviadas com sucesso!');
            navigate('/aluno/resultados');
        } catch (erro) {
            console.error(erro);
            alert('Erro ao enviar respostas.');
        } finally {
            setEnviando(false);
        }

    }

    if (!prova) return (
        <p>Carregando prova...</p>
    );

    return (

        <div style={{fontFamily: 'sans-serif', maxWidth: '600px'}}>

            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>

                <h2>{prova.titulo}</h2>
                <Link to="/aluno/provas"><button>Cancelar</button></Link>

            </div>

            {prova.descricao && <p style={{color: '#555'}}>{prova.descricao}</p>}

            <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1.2rem' }}>

                {prova.questoes.map((q, index) => (

                    <div key={q.idQuestao}>
                        <p><strong>Questão {index + 1} ({q.valor} pts):</strong> {q.enunciado}</p>

                        {q.tipo === 'MULTIPLA_ESCOLHA' ? (

                            <div style={{ display: 'flex', flexDirection: 'column', gap: '0.4rem' }}>

                                {q.alternativas.map((alt) => (

                                    <label key={alt.idAlternativa} style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', cursor: 'pointer' }}>

                                        <input
                                            type="radio"
                                            name={`questao_${q.idQuestao}`}
                                            required
                                            checked={respostas[q.idQuestao]?.idAlternativa === alt.idAlternativa}
                                            onChange={() => marcarAlternativa(q.idQuestao, alt.idAlternativa)}
                                        />
                                        {alt.descricao}

                                    </label>

                                ))}

                            </div>
                        ) : (
                            <textarea
                                rows={4}
                                required
                                placeholder="Digite sua resposta aqui..."
                                value={respostas[q.idQuestao]?.textoResposta || ''}
                                onChange={(e) => digitarTexto(q.idQuestao, e.target.value)}
                                style={{width: '100%', padding: '0.5rem', boxSizing: 'border-box'}}
                            />

                        )}

                    </div>

                ))}

                <button type="submit" disabled={enviando}>

                    {enviando ? 'Enviando...' : 'Enviar Prova'}

                </button>

            </form>

        </div>

    );

}