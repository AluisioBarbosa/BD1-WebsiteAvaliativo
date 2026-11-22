const TESTE = true;

let testeQuestoes = [
    {
        idQuestao: 1,
        enunciado: 'Qual comando SQL cria uma nova tabela no PostgreSQL?',
        alternativas: [
            {
                idAlternativa: 1,
                descricao: 'CREATE TABLE',
                correta: true
            },
            {
                idAlternativa: 2,
                descricao: 'NEW TABLE',
                correta: false
            },
        ]
    }
];

let testeAvaliacoes = [

    {
        idAvaliacao: 1,
        titulo: 'Prova 1 de Banco de Dados 1',
        data: '2026-11-23',
        horaInicio: '19:15',
        horaFim: '21:00',
        idProfessor: 1,
    }
];

async function request(url, options = {}){

    const res = await fetch(url, {
        headers: {'Content-Type': 'application/json'},
        ...options,
    });

    if (!res.ok){
        return res.status === 204 ? null : res.json();
    }

}

export const api = {

    listarQuestoes: async () =>
        TESTE ? [...testeQuestoes] : request('/api/questoes'),

    criarQuestao: async (payload) => {
        if (TESTE) {
            const nova = {...payload, idQuestao: Date.now()};
            testeQuestoes.push(nova);
            return nova;
        }
        return request('/api/questoes', {method: 'POST', body: JSON.stringify(payload)});
    },

    listarAvaliacoes: async () =>
        TESTE ? [...testeAvaliacoes] : request('/api/avaliacoes'),

    criarAvaliacao: async (payload) => {
        if (TESTE){
            const nova = {...payload, idAvaliacao: Date.now()};
            testeAvaliacoes.push(nova);
            return nova;
        }
        return request('/api/avaliacoes', {method: 'POST', body: JSON.stringify(payload) });
    },

    atribuirAlunos: async (idAvaliacao, idsAlunos) =>
        TESTE
            ? {ok: true}
            : request(`/api/avaliacoes/${idAvaliacao}/alunos`, {method: 'POST', body: JSON.stringify({idsAlunos})}),

    enviarRespostas: async (payload) =>
        TESTE
            ? {ok: true}
            : request('/api/respostas', {method: 'POST', body: JSON.stringify(payload)}),

    lancarNotaDissertativa: async (idResposta, nota) =>
        TESTE
            ? {ok: true}
            : request(`/api/respostas/${idResposta}/nota`, {method: 'PUT', body: JSON.stringify({ nota }),}),

    buscarRelatorios: async (filtros = {}) => {
        if (TESTE){
            return{
                mediaPorAvaliacao: [
                    {titulo: 'Prova 1 de BD', media: 7.8, totalAlunos: 20},
                    {titulo: 'Simulado SQL', media: 6.4, totalAlunos: 18},
                ],
                evolucaoAluno: [
                    { avaliacao: 'Quiz 1', notaAluno: 6.5, mediaTurma: 7.0 },
                    { avaliacao: 'Prova 1', notaAluno: 8.0, mediaTurma: 7.8 },
                ],
                questoesMaisErradas: [
                    {enunciado: 'Diferença entre 2FN e 3FN', taxaErro: 65},
                    {enunciado: 'Subconsulta correlacionada', taxaErro: 52},
                ],
            };
        }
        const consulta = new URLSearchParams(filtros).toString();
        return request(`/api/relatorios?${consulta}`);
    },

    login: async ({email, senha}) => {
        if (TESTE) {
            if(email.includes('prof')){
                return { id: 1, nome: 'Anderson', email, tipo: 'PROFESSOR' };
            }
            return { id: 2, nome: 'Aluisio', email, tipo: 'ALUNO' };
        }
        return request('/api/login', {method: 'POST', body: JSON.stringify({ email, senha }),});
    },
}