import {useEffect, useState} from 'react';
import {api} from '../../services/api';
import {Link} from "react-router-dom";

export default function CorrigirProvas() {

    const [respostas, setRespostas] = useState([]);
    const [notasDigitadas, setNotasDigitadas] = useState({});

    useEffect(() => {

        api.listarRespostasDissertativas().then((lista) => {
            setRespostas(lista);
            const iniciais = {};
            lista.forEach((r) => {
                iniciais[r.idResposta] = r.nota ?? '';
            });
            setNotasDigitadas(iniciais);
        });
    }, []);

    async function salvarNota(item) {

        const valorNota = parseFloat(notasDigitadas[item.idResposta]);

        if (isNaN(valorNota) || valorNota < 0 || valorNota > item.valorMaximo) {
            alert(`Digite uma nota válida entre 0 e ${item.valorMaximo}.`);
            return;
        }

        await api.lancarNotaDissertativa(item.idResposta, valorNota);
        alert(`Nota ${valorNota} salva para ${item.nomeAluno}!`);

    }

    return (

        <div style={{fontFamily: 'sans-serif', maxWidth: '600px' }}>

            <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>

                <h2>Corrigir questões de texto livre</h2>

            </div>

            {respostas.length === 0 ? (
                <p>Nenhuma resposta dissertativa encontrada.</p>
            ) : (
                <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>

                    {respostas.map((item) => (

                        <div key={item.idResposta}>

                            <div>

                                <strong>Aluno:</strong> {item.nomeAluno} | <strong>Avaliação:</strong> {item.tituloAvaliacao}

                            </div>

                            <p><strong>Questão:</strong> {item.enunciado}</p>

                            <div>
                                <strong>Resposta:</strong> {item.textoResposta}
                            </div>

                            <div style={{display: 'flex', alignItems: 'center', gap: '0.6rem', marginTop: '0.8rem'}}>

                                <label>

                                    <strong>Nota (máx. {item.valorMaximo} pts):</strong>{' '}
                                    <input
                                        type="number"
                                        step="0.5"
                                        min="0"
                                        max={item.valorMaximo}
                                        value={notasDigitadas[item.idResposta] ?? ''}
                                        onChange={(e) =>
                                            setNotasDigitadas({ ...notasDigitadas, [item.idResposta]: e.target.value })
                                        }
                                        style={{ width: '70px', padding: '0.3rem' }}
                                    />

                                </label>

                                <button
                                    type="button"
                                    onClick={() => salvarNota(item)}
                                >

                                    Salvar Nota

                                </button>

                            </div>

                        </div>

                    ))}

                </div>

            )}

        </div>

    );

}