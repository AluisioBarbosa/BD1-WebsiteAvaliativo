import {useEffect, useState} from 'react';
import {api} from '../../services/api';

function Barra({rotulo, valor, maximo, texto}){

    const pct = maximo > 0 ? Math.min(100, Math.round((valor / maximo) * 100)) : 0;

    return (


        <div style={{display: 'flex', alignItems: 'center', gap: 10, marginBottom: 6}}>

            <span style={{width: 200, overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap', fontSize: 14}}>{rotulo}</span>

            <div style={{flex: 1, background: '#e5e7eb', height: 14, borderRadius: 4}}>

                <div style={{ width: `${pct}%`, background: '#2563eb', height: '100%', borderRadius: 4 }} />

            </div>

            <span style={{ width: 75, textAlign: 'right', fontSize: 13 }}>{texto}</span>

        </div>

    );

}

const th = { border: '1px solid #ddd', padding: 8, background: '#f5f5f5', textAlign: 'left' };
const td = { border: '1px solid #ddd', padding: 8 };

export default function Relatorios() {

    const [idAvaliacao, setIdAvaliacao] = useState('');
    const [dados, setDados] = useState({ geral: null, avaliacoes: [], notas: [], questoes: [] });

    useEffect(() => {
        api.buscarRelatorios(idAvaliacao || null).then((res) => {
            setDados(res);
            if (!idAvaliacao && res.avaliacoes?.length > 0) {
                setIdAvaliacao(String(res.avaliacoes[0].id_avaliacao));
            }
        });
    }, [idAvaliacao]);

    const {geral, avaliacoes, notas, questoes} = dados;

    return (

        <div style={{fontFamily: 'sans-serif', maxWidth: '600px'}}>

            <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>

                <h2>Relatórios</h2>

            </div>

            {geral && (

                <div style={{ display: 'flex', gap: 16, marginBottom: 24 }}>

                    <div style={td}>Avaliações: <strong>{geral.total_avaliacoes}</strong></div>
                    <div style={td}>Questões: <strong>{geral.total_questoes}</strong></div>
                    <div style={td}>Provas concluídas: <strong>{geral.provas_entregues}</strong></div>
                    <div style={td}>Dissertativas pendentes: <strong>{geral.texto_livre_pendentes}</strong></div>

                </div>

            )}

            <section>

                <h3>Desempenho por Avaliação</h3>

                <div style={{margin: '12px 0'}}>

                    {avaliacoes.map((av) => (
                        <Barra
                            key={av.id_avaliacao}
                            rotulo={av.titulo}
                            valor={Number(av.media_notas || 0)}
                            maximo={10}
                            texto={`Média ${Number(av.media_notas || 0).toFixed(1)}`}
                        />
                    ))}

                </div>

                <table>

                    <thead>

                        <tr>

                            <th style={th}>Avaliação</th>
                            <th style={th}>Atribuídos</th>
                            <th style={th}>Concluídas</th>
                            <th style={th}>Média</th>
                            <th style={th}>Maior</th>
                            <th style={th}>Menor</th>

                        </tr>

                    </thead>

                    <tbody>

                        {avaliacoes.map((av) => (

                            <tr key={av.id_avaliacao}>

                                <td style={td}>{av.titulo}</td>
                                <td style={td}>{av.total_alunos}</td>
                                <td style={td}>{av.total_respondidas}</td>
                                <td style={td}>{Number(av.media_notas || 0).toFixed(2)}</td>
                                <td style={td}>{Number(av.maior_nota || 0).toFixed(2)}</td>
                                <td style={td}>{Number(av.menor_nota || 0).toFixed(2)}</td>

                            </tr>

                        ))}

                    </tbody>

                </table>

            </section>

            <div style={{marginBottom: 20}}>

                <label>Avaliação: </label>

                <select value={idAvaliacao} onChange={(e) => setIdAvaliacao(e.target.value)}>

                    {avaliacoes.map((av) => (
                        <option key={av.id_avaliacao} value={av.id_avaliacao}>{av.titulo}</option>
                    ))}

                </select>

            </div>

            <section>

                <h3>Notas dos Alunos</h3>

                <div>

                    {notas.map((n) => (
                        <Barra
                            key={n.id_aluno}
                            rotulo={n.nome_aluno}
                            valor={Number(n.nota_obtida || 0)}
                            maximo={Number(n.valor_total || 10)}
                            texto={`${Number(n.nota_obtida || 0).toFixed(1)} / ${Number(n.valor_total || 0).toFixed(1)}`}
                        />
                    ))}

                </div>

                <table>

                    <thead>

                        <tr>
                            <th style={th}>Aluno</th>
                            <th style={th}>Status</th>
                            <th style={th}>Nota</th>
                            <th style={th}>Total</th>
                            <th style={th}>Pendentes</th>

                        </tr>

                    </thead>

                    <tbody>

                    {notas.map((n) => (

                        <tr key={n.id_aluno}>
                            <td style={td}>{n.nome_aluno}</td>
                            <td style={td}>{n.status_avaliacao}</td>
                            <td style={td}>{Number(n.nota_obtida || 0).toFixed(2)}</td>
                            <td style={td}>{Number(n.valor_total || 0).toFixed(2)}</td>
                            <td style={td}>{n.pendentes_correcao}</td>
                        </tr>

                    ))}

                    </tbody>

                </table>

            </section>

            <section>

                <h3>Desempenho por Questão</h3>

                <div>

                    {questoes.map((q, i) => (
                        <Barra
                            key={q.id_questao}
                            rotulo={`Q${i + 1}. ${q.enunciado}`}
                            valor={Number(q.media_nota || 0)}
                            maximo={Number(q.valor_maximo || 1)}
                            texto={`${Number(q.media_nota || 0).toFixed(1)} / ${Number(q.valor_maximo || 0).toFixed(1)}`}
                        />
                    ))}

                </div>

                <table>

                    <thead>

                        <tr>
                            <th style={th}>Questão</th>
                            <th style={th}>Tipo</th>
                            <th style={th}>Valor</th>
                            <th style={th}>Respostas</th>
                            <th style={th}>Média</th>
                            <th style={th}>Acertos</th>
                        </tr>

                    </thead>

                    <tbody>

                        {questoes.map((q, i) => (

                            <tr key={q.id_questao}>
                                <td style={td}>Q{i + 1}: {q.enunciado}</td>
                                <td style={td}>

                                    {q.tipo === "DISSERTATIVA" ? (
                                        "Subjetiva"
                                    ) : (
                                        "Objetiva"
                                    )}

                                </td>
                                <td style={td}>{Number(q.valor_maximo || 0).toFixed(2)}</td>
                                <td style={td}>{q.total_respostas}</td>
                                <td style={td}>{Number(q.media_nota || 0).toFixed(2)}</td>
                                <td style={td}>{q.total_acertos}</td>
                            </tr>

                        ))}

                    </tbody>

                </table>

            </section>

        </div>

    );

}