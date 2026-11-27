const TESTE = false;

async function request(url, options = {}) {
    const res = await fetch(url, {
        headers: { 'Content-Type': 'application/json' },
        ...options,
    });

    if (!res.ok) {
        throw new Error(`Erro HTTP ${res.status}`);
    }

    const texto = await res.text();
    return texto ? JSON.parse(texto) : null;
}

function normalizarDataHora(av) {
    const inicioStr = av.horaInicio || av.hora_inicio || '';
    const fimStr = av.horaFim || av.hora_fim || '';

    const data = av.data || (inicioStr.includes('T') ? inicioStr.split('T')[0] : inicioStr.split(' ')[0]) || '';
    const extrairHora = (str) => {
        if (!str) return '';
        const parteHora = str.includes('T') ? str.split('T')[1] : (str.split(' ')[1] || str);
        return parteHora.slice(0, 5);
    };

    return {
        ...av,
        idAvaliacao: av.idAvaliacao ?? av.id,
        data,
        horaInicio: extrairHora(inicioStr),
        horaFim: extrairHora(fimStr),
    };
}

export const api = {
    login: async ({email, senha}) => {
        const usuario = await request('/api/usuarios/login', {
            method: 'POST',
            body: JSON.stringify({email, senha}),
        });
        return {
            ...usuario,
            tipo: (usuario.tipo || '').toUpperCase(),
        };
    },

    listarQuestoes: async () => {
        const lista = await request('/api/questoes');
        return (lista || []).map((q) => ({
            ...q,
            idQuestao: q.idQuestao ?? q.id,
            alternativas: (q.alternativas || []).map((alt) => ({
                ...alt,
                idAlternativa: alt.idAlternativa ?? alt.id,
            })),
        }));
    },

    criarQuestao: async (payload) =>
        request('/api/questoes', {
            method: 'POST',
            body: JSON.stringify(payload),
        }),

    listarAvaliacoes: async () => {
        const lista = await request('/api/avaliacoes');
        return (lista || []).map(normalizarDataHora);
    },

    criarAvaliacao: async (payload) =>
        request('/api/avaliacoes', {
            method: 'POST',
            body: JSON.stringify(payload),
        }),

    listarAlunos: async () => request('/api/alunos'),

    atribuirAlunos: async (idAvaliacao, idsAlunos) =>
        request(`/api/avaliacoes/${idAvaliacao}/alunos`, {
            method: 'POST',
            body: JSON.stringify(idsAlunos.map(Number)),
        }),

    listarRespostasDissertativas: async () => {
        const lista = await request('/api/respostas/textoLivre');
        return (lista || []).map((r) => ({
            idResposta: r.id,
            nomeAluno: r.nome_aluno,
            tituloAvaliacao: r.titulo_avaliacao,
            enunciado: r.enunciado,
            textoResposta: r.texto_resposta,
            valorMaximo: r.valor_maximo,
            nota: r.nota,
        }));
    },

    lancarNotaDissertativa: async (idResposta, nota) =>
        request(`/api/respostas/${idResposta}/nota?nota=${encodeURIComponent(nota)}`, {
            method: 'PATCH',
        }),

    listarProvasAluno: async (idAluno) => {
        const lista = await request(`/api/avaliacoes/aluno/${idAluno}`);
        return (lista || []).map((p) => ({
            ...normalizarDataHora(p),
            respondida: p.status_avaliacao === 'CONCLUIDA',
        }));
    },

    buscarProvaParaResponder: async (idAvaliacao) => {
        const dados = await request(`/api/avaliacoes/${idAvaliacao}/responder`);
        const av = normalizarDataHora(dados.avaliacao || {});
        return {
            idAvaliacao: av.idAvaliacao,
            titulo: av.titulo,
            descricao: av.descricao,
            questoes: (dados.questoes || []).map((q) => ({
                idQuestao: q.idQuestao ?? q.id,
                enunciado: q.enunciado,
                tipo: q.tipo,
                valor: q.valor,
                alternativas: (q.alternativas || []).map((alt) => ({
                    idAlternativa: alt.idAlternativa ?? alt.id,
                    descricao: alt.descricao,
                })),
            })),
        };
    },

    enviarRespostas: async (payload) =>
        request('/api/respostas/salvarRespostaProva', {
            method: 'POST',
            body: JSON.stringify(payload),
        }),

    listarResultadosAluno: async (idAluno) => {
        const resumos = await request(`/api/respostas/aluno/${idAluno}/resultados`);
        const concluidas = (resumos || []).filter((r) => r.status_avaliacao === 'CONCLUIDA');

        return Promise.all(
            concluidas.map(async (res) => {
                const [prova, respostasAluno] = await Promise.all([
                    request(`/api/avaliacoes/${res.id_avaliacao}/responder`),
                    request(`/api/respostas/aluno/${idAluno}/avaliacao/${res.id_avaliacao}`),
                ]);

                const mapaRespostas = new Map(
                    (respostasAluno || []).map((resp) => [resp.idQuestao, resp])
                );

                const itens = (prova?.questoes || []).map((q) => {
                    const idQ = q.idQuestao ?? q.id;
                    const resp = mapaRespostas.get(idQ);
                    return {
                        enunciado: q.enunciado,
                        tipo: q.tipo,
                        valorQuestao: q.valor,
                        notaObtida: resp ? resp.nota : null,
                    };
                });

                return {
                    idAvaliacao: res.id_avaliacao,
                    titulo: res.titulo,
                    notaTotal: res.nota_obtida ?? 0,
                    valorTotal: res.valor_total ?? 0,
                    itens,
                };
            })
        );
    },

    buscarRelatorios: async (idAvaliacao) => {
        const [geral, avaliacoes, notas, questoes] = await Promise.all([
            request('/api/relatorios/geral'),
            request('/api/relatorios/avaliacoes'),
            idAvaliacao ? request(`/api/relatorios/avaliacoes/${idAvaliacao}/notas`) : Promise.resolve([]),
            idAvaliacao ? request(`/api/relatorios/avaliacoes/${idAvaliacao}/questoes`) : Promise.resolve([]),
        ]);
        return {geral, avaliacoes, notas, questoes};
    },
};
