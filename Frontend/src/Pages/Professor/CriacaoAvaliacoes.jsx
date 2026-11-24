import {useEffect, useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import {useForm} from '../../hooks/useForm';
import {api} from '../../services/api';

export default function CriacaoAvaliacoes({usuario}){

    const navigate = useNavigate();
    const [questoesDisponiveis, setQuestoesDisponiveis] = useState([]);

    const {dados, definirCampo, enviar, enviando} = useForm({
        titulo: '',
        descricao: '',
        data: '',
        horaInicio: '',
        horaFim: '',
        questoesSelecionadas: [], // [{ idQuestao, valor }]
    });

    useEffect(() => {
        api.listarQuestoes().then(setQuestoesDisponiveis);
    }, []);

    function alternarQuestao(idQuestao){

        const jaExiste = dados.questoesSelecionadas.some((q) => q.idQuestao === idQuestao);

        if (jaExiste) {
            definirCampo('questoesSelecionadas', dados.questoesSelecionadas.filter((q) => q.idQuestao !== idQuestao));
        } else {
            definirCampo('questoesSelecionadas', [...dados.questoesSelecionadas, {idQuestao, valor: 2.0 },]);
        }

    }

    function alterarValorQuestao(idQuestao, novoValor) {

        const atualizadas = dados.questoesSelecionadas.map((q) =>
            q.idQuestao === idQuestao ? { ...q, valor: parseFloat(novoValor) || 0 } : q
        );

        definirCampo('questoesSelecionadas', atualizadas);

    }

    function handleSubmit(e) {

        e.preventDefault();

        if (dados.questoesSelecionadas.length === 0) {
            alert('Selecione pelo menos uma questão para a avaliação.');
            return;
        }

        const payload = {
            titulo: dados.titulo,
            descricao: dados.descricao,
            data: dados.data,
            horaInicio: dados.horaInicio,
            horaFim: dados.horaFim,
            idProfessor: usuario?.id || 1,
            questoes: dados.questoesSelecionadas.map((q, index) => ({
                idQuestao: q.idQuestao,
                valor: q.valor,
                ordemQuestoes: index + 1,
            })),
        };

        enviar(() => api.criarAvaliacao(payload), {
            aoSucesso: () => navigate('/professor/avaliacoes'),
            aoErro: () => alert('Erro ao salvar avaliação.'),
        });

    }

    return (

        <div style={{fontFamily: 'sans-serif'}}>

            <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>

                <h2>Nova avaliação</h2>
                <Link to="/professor/avaliacoes">Voltar</Link>

            </div>

            <form onSubmit={handleSubmit} style={{display: 'flex', flexDirection: 'column', gap: '0.8rem', maxWidth: '650px'}}>

                <input
                    type="text"
                    placeholder="Nome/título"
                    required
                    value={dados.titulo}
                    onChange={(e) => definirCampo('titulo', e.target.value)}
                    style={{padding: '0.5rem'}}
                />

                <textarea
                    placeholder="Descrição"
                    rows={2}
                    value={dados.descricao}
                    onChange={(e) => definirCampo('descricao', e.target.value)}
                    style={{padding: '0.5rem'}}
                />

                <div style={{display: 'flex', gap: '0.8rem'}}>

                    <div style={{flex: 1}}>

                        <label style={{display: 'block'}}>Data:</label>
                        <input
                            type="date"
                            required
                            value={dados.data}
                            onChange={(e) => definirCampo('data', e.target.value)}
                            style={{width: '100%', padding: '0.4rem', boxSizing: 'border-box'}}
                        />

                    </div>

                    <div style={{flex: 1}}>

                        <label style={{display: 'block'}}>Hora Início:</label>
                        <input
                            type="time"
                            required
                            value={dados.horaInicio}
                            onChange={(e) => definirCampo('horaInicio', e.target.value)}
                            style={{width: '100%', padding: '0.4rem', boxSizing: 'border-box'}}
                        />

                    </div>

                    <div style={{flex: 1}}>

                        <label style={{display: 'block'}}>Hora Fim:</label>
                        <input
                            type="time"
                            required
                            value={dados.horaFim}
                            onChange={(e) => definirCampo('horaFim', e.target.value)}
                            style={{width: '100%', padding: '0.4rem', boxSizing: 'border-box'}}
                        />

                    </div>

                </div>

                <div>

                    <strong>Selecione as questões e defina o valor de cada uma:</strong>

                    <div style={{display: 'flex', flexDirection: 'column', gap: '0.5rem', marginTop: '0.5rem' }}>

                        {questoesDisponiveis.map((q) => {

                            const selecionada = dados.questoesSelecionadas.find((item) => item.idQuestao === q.idQuestao);

                            return (

                                <div
                                    key={q.idQuestao}
                                    style={{
                                        display: 'flex',
                                        alignItems: 'center',
                                        gap: '0.6rem',
                                        padding: '0.5rem',
                                        border: '1px solid #ccc',
                                        borderRadius: '4px',
                                        backgroundColor: selecionada ? '#f0f7ff' : '#fff',
                                    }}
                                >
                                    <input
                                        type="checkbox"
                                        checked={Boolean(selecionada)}
                                        onChange={() => alternarQuestao(q.idQuestao)}
                                    />

                                    <span style={{flex: 1}}>

                                        <strong>[{q.tipo}]</strong> {q.enunciado}

                                    </span>

                                    {selecionada && (

                                        <label style={{display: 'flex', alignItems: 'center', gap: '0.3rem', fontSize: '0.9rem'}}>
                                            Valor:
                                            <input
                                                type="number"
                                                step="0.5"
                                                min="0.5"
                                                required
                                                value={selecionada.valor}
                                                onChange={(e) => alterarValorQuestao(q.idQuestao, e.target.value)}
                                                style={{width: '65px', padding: '0.3rem'}}
                                            />
                                        </label>

                                    )}

                                </div>

                            );

                        })}

                    </div>

                </div>

                <button type="submit" disabled={enviando} style={{ padding: '0.6rem', cursor: 'pointer', marginTop: '0.5rem' }}>
                    {enviando ? 'Salvando...' : 'Salvar Avaliação'}
                </button>

            </form>

        </div>

    );

}