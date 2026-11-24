import { useEffect, useState } from 'react';
import { useForm } from '../../hooks/useForm';
import { api } from '../../services/api';
import {Link} from "react-router-dom";

export default function AtribuirAlunos() {

    const [avaliacoes, setAvaliacoes] = useState([]);
    const [alunos, setAlunos] = useState([]);

    const {dados, definirCampo, enviar, enviando, limpar} = useForm({
        idAvaliacao: '',
        idsAlunos: [],
    });

    useEffect(() => {
        api.listarAvaliacoes().then(setAvaliacoes);
        api.listarAlunos().then(setAlunos);
    }, []);

    function alternarAluno(idAluno) {

        const jaMarcado = dados.idsAlunos.includes(idAluno);

        if (jaMarcado) {
            definirCampo('idsAlunos', dados.idsAlunos.filter((id) => id !== idAluno));
        } else {
            definirCampo('idsAlunos', [...dados.idsAlunos, idAluno]);
        }

    }

    function selecionarTodos() {
        definirCampo('idsAlunos', alunos.map((a) => a.id));
    }

    function handleSubmit(e) {

        e.preventDefault();

        if (!dados.idAvaliacao || dados.idsAlunos.length === 0) {
            alert('Selecione uma avaliação e pelo menos um aluno.');
            return;
        }

        enviar(() => api.atribuirAlunos(dados.idAvaliacao, dados.idsAlunos), {
            aoSucesso: () => {
                alert('Alunos atribuídos com sucesso!');
                limpar();
            },
            aoErro: () => alert('Erro ao atribuir alunos.'),
        });

    }

    return (
        <div style={{fontFamily: 'sans-serif', maxWidth: '600px'}}>

            <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>

                <h2>Selecionar alunos para avaliação</h2>

            </div>

            <form onSubmit={handleSubmit} style={{display: 'flex', flexDirection: 'column', gap: '1rem'}}>

                <div>

                    <label style={{display: 'block', marginBottom: '0.5rem'}}><strong>Escolha a Avaliação:</strong></label>

                    <select
                        required
                        value={dados.idAvaliacao}
                        onChange={(e) => definirCampo('idAvaliacao', e.target.value)}
                        style={{width: '100%', padding: '0.5rem'}}
                    >
                        <option value="">Seletor</option>

                        {avaliacoes.map((av) => (
                            <option key={av.idAvaliacao} value={av.idAvaliacao}>{av.titulo} ({av.data})</option>
                        ))}

                    </select>

                </div>

                <div>

                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '0.5rem' }}>

                        <strong>Selecione os Alunos:</strong>

                        <button type="button" onClick={selecionarTodos}>Selecionar todos</button>

                    </div>

                    <div style={{ display: 'flex', flexDirection: 'column', gap: '0.4rem'}}>

                        {alunos.map((aluno) => (

                            <label key={aluno.id} style={{display: 'flex', alignItems: 'center', gap: '0.5rem'}}>

                                <input
                                    type="checkbox"
                                    checked={dados.idsAlunos.includes(aluno.id)}
                                    onChange={() => alternarAluno(aluno.id)}
                                />
                                {aluno.nome} ({aluno.email})

                            </label>

                        ))}

                    </div>

                </div>

                <button type="submit" disabled={enviando}>

                    {enviando ? 'Salvando...' : 'Confirmar Atribuição'}

                </button>

            </form>

        </div>

    );

}