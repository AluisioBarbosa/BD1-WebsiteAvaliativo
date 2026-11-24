import {useEffect, useState} from 'react';
import {api } from '../../services/api';

export default function MeuResultado({usuario}) {

    const [resultados, setResultados] = useState([]);

    useEffect(() => {
        const idAluno = usuario?.id || 2;
        api.listarResultadosAluno(idAluno).then(setResultados);
    }, [usuario]);

    return (
        <div style={{ fontFamily: 'sans-serif', maxWidth: '750px' }}>

            <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}><h2>Meus resultados</h2></div>

            {resultados.length === 0 ? (
                <p>Você ainda não possui avaliações respondidas.</p>
            ) : (
                <div style={{ display: 'flex', flexDirection: 'column', gap: '1.5rem' }}>

                    {resultados.map((res) => (
                        <div key={res.idAvaliacao}>

                            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', borderBottom: '1px solid #eee', paddingBottom: '0.6rem', marginBottom: '0.8rem' }}>

                                <h3 style={{ margin: 0 }}>{res.titulo}</h3>
                                <strong style={{ fontSize: '1.1rem' }}>
                                    Nota Total: {res.notaTotal.toFixed(1)} / {res.valorTotal.toFixed(1)}
                                </strong>

                            </div>

                            <table style={{ width: '100%', borderCollapse: 'collapse' }}>

                                <thead>
                                <tr style={{ backgroundColor: '#f5f5f5', textAlign: 'left' }}>
                                    <th style={{ padding: '0.5rem', borderBottom: '1px solid #ddd' }}>Questão</th>
                                    <th style={{ padding: '0.5rem', borderBottom: '1px solid #ddd' }}>Valor</th>
                                    <th style={{ padding: '0.5rem', borderBottom: '1px solid #ddd' }}>Nota Obtida</th>
                                </tr>
                                </thead>

                                <tbody>

                                    {res.itens.map((item, idx) => (
                                        <tr key={idx}>
                                            <td style={{ padding: '0.5rem', borderBottom: '1px solid #eee' }}>{item.enunciado}</td>
                                            <td style={{ padding: '0.5rem', borderBottom: '1px solid #eee' }}>{item.valorQuestao.toFixed(1)}</td>
                                            <td style={{ padding: '0.5rem', borderBottom: '1px solid #eee' }}>
                                                {item.notaObtida === null ? (
                                                    <span style={{ color: '#b45309' }}>Aguardando correção</span>
                                                ) : (
                                                    <strong>{item.notaObtida.toFixed(1)}</strong>
                                                )}
                                            </td>
                                        </tr>
                                    ))}

                                </tbody>

                            </table>

                        </div>

                    ))}

                </div>

            )}

        </div>

    );
}