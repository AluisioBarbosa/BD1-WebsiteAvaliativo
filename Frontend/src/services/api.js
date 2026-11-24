const TESTE = true;

let testeProvasAluno = [

    {
        idAvaliacao: 1,
        titulo: 'Prova 1 de Banco de  1',
        descricao: 'Modelagem, DDL e Consultas SQL',
        data: '2026-11-23',
        horaInicio: '19:15',
        horaFim: '21:00',
        respondida: false,
    }

];

let testeResultadosAluno = [

    {
        idAvaliacao: 99,
        titulo: 'Simulado Inicial de SQL',
        notaTotal: 8.5,
        valorTotal: 10.0,
        itens: [
            {enunciado: 'O que significa DDL?', tipo: 'MULTIPLA_ESCOLHA', valorQuestao: 5.0, notaObtida: 5.0},
            {enunciado: 'Explique a 3ª Forma Normal.', tipo: 'TEXTO_LIVRE', valorQuestao: 5.0, notaObtida: 3.5},
        ],
    },

];

let testeQuestoes = [

    {
        idQuestao: 1,
        enunciado: 'Qual comando SQL cria uma nova tabela no PostgreSQL?',
        tipo: 'MULTIPLA_ESCOLHA',
        alternativas:[
            {idAlternativa: 1, descricao: 'CREATE TABLE', correta: true},
            {idAlternativa: 2, descricao: 'NEW TABLE', correta: false},
        ]
    },
    {
        idQuestao: 2,
        enunciado: 'Explique a diferença entre INNER JOIN e LEFT JOIN.',
        tipo: 'TEXTO_LIVRE',
        alternativas: [],
    },

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

    const res = await fetch(url, {headers: { 'Content-Type': 'application/json' }, ...options,});

    if (!res.ok) {
        throw new Error(`Erro HTTP ${res.status}`);
    }

    return res.status === 204 ? null : res.json();

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
                    {avaliacao: 'Quiz 1', notaAluno: 6.5, mediaTurma: 7.0},
                    {avaliacao: 'Prova 1', notaAluno: 8.0, mediaTurma: 7.8},
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
        return request('/api/login', {method: 'POST', body: JSON.stringify({email, senha}),});
    },

    listarAlunos: async () =>
        TESTE
            ? [
                {id: 2, nome: 'Ana Silva', email: 'ana@uel.br'},
                {id: 3, nome: 'Bruno Costa', email: 'bruno@uel.br'},
                {id: 4, nome: 'Carla Mendes', email: 'carla@uel.br'},
            ]
            : request('/api/alunos'),

    listarRespostasDissertativas: async () =>
        TESTE
            ? [
                {
                    idResposta: 101,
                    nomeAluno: 'Ana Silva',
                    tituloAvaliacao: 'Prova 1 de Banco de Dados 1',
                    enunciado: 'Explique a diferença entre INNER JOIN e LEFT JOIN.',
                    textoResposta: 'O INNER JOIN traz apenas registros com correspondência nas duas tabelas; o LEFT JOIN traz todos da tabela à esquerda.',
                    valorMaximo: 3.0,
                    nota: null,
                },
                {
                    idResposta: 102,
                    nomeAluno: 'Bruno Costa',
                    tituloAvaliacao: 'Prova 1 de Banco de Dados 1',
                    enunciado: 'Explique a diferença entre INNER JOIN e LEFT JOIN.',
                    textoResposta: 'Não sei.',
                    valorMaximo: 3.0,
                    nota: null,
                },
            ]
            : request('/api/respostas/dissertativas'),

    listarProvasAluno: async (idAluno) =>
        TESTE
            ? [...testeProvasAluno]
            : request(`/api/aluno/${idAluno}/avaliacoes`),

    buscarProvaParaResponder: async (idAvaliacao) => {
        if (TESTE) {
            return {
                idAvaliacao: Number(idAvaliacao),
                titulo: 'Prova 1 de Banco de Dados 1',
                descricao: 'Modelagem, DDL e Consultas SQL',
                questoes: [
                    {
                        idQuestao: 1,
                        enunciado: 'Qual comando SQL cria uma nova tabela no PostgreSQL?',
                        tipo: 'MULTIPLA_ESCOLHA',
                        valor: 4.0,
                        alternativas: [
                            {idAlternativa: 1, descricao: 'CREATE TABLE'},
                            {idAlternativa: 2, descricao: 'NEW TABLE'},
                            {idAlternativa: 3, descricao: 'INSERT TABLE'},
                        ],
                    },
                    {
                        idQuestao: 2,
                        enunciado: 'Explique a diferença entre INNER JOIN e LEFT JOIN.',
                        tipo: 'TEXTO_LIVRE',
                        valor: 6.0,
                        alternativas: [],
                    },
                ],
            };
        }
        return request(`/api/aluno/avaliacoes/${idAvaliacao}`);
    },

    enviarRespostas: async (payload) => {
        if (TESTE) {
            testeProvasAluno = testeProvasAluno.map((p) =>
                p.idAvaliacao === payload.idAvaliacao ? { ...p, respondida: true } : p
            );

            // Simula a correção automática da objetiva e deixa a dissertativa pendente
            const acertouObj = payload.respostas.find((r) => r.idQuestao === 1)?.idAlternativa === 1;
            testeResultadosAluno.push({
                idAvaliacao: payload.idAvaliacao,
                titulo: 'Prova 1 de Banco de Dados',
                notaTotal: acertouObj ? 4.0 : 0.0,
                valorTotal: 10.0,
                itens: [
                    {
                        enunciado: 'Qual comando SQL cria uma nova tabela no PostgreSQL?',
                        tipo: 'MULTIPLA_ESCOLHA',
                        valorQuestao: 4.0,
                        notaObtida: acertouObj ? 4.0 : 0.0,
                    },
                    {
                        enunciado: 'Explique a diferença entre INNER JOIN e LEFT JOIN.',
                        tipo: 'TEXTO_LIVRE',
                        valorQuestao: 6.0,
                        notaObtida: null, // Aguardando nota do professor
                    },
                ],
            });
            return { ok: true };
        }
        return request('/api/aluno/respostas', {
            method: 'POST',
            body: JSON.stringify(payload),
        });
    },

    listarResultadosAluno: async (idAluno) =>
        TESTE
            ? [...testeResultadosAluno]
            : request(`/api/aluno/${idAluno}/resultados`),
}