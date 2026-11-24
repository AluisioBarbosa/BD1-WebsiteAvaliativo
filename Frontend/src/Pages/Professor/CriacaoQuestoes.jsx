import {Link, useNavigate} from 'react-router-dom';
import {useForm} from '../../hooks/useForm';
import {api} from '../../services/api';

export default function CriacaoQuestoes(){

    const navigate = useNavigate();

    const {dados, definirCampo, enviar, enviando} = useForm({
        enunciado: '',
        tipo: 'MULTIPLA_ESCOLHA',
        alternativas: [
            {descricao: '', correta: true},
            {descricao: '', correta: false},
            {descricao: '', correta: false},
            {descricao: '', correta: false},
        ],
    });

    function atualizarTextoAlternativa(indice, texto) {

        const novas = dados.alternativas.map((alt, i) =>
            i === indice ? {...alt, descricao: texto} : alt
        );

        definirCampo('alternativas', novas);

    }

    function marcarCorreta(indice) {

        const novas = dados.alternativas.map((alt, i) => ({
            ...alt,
            correta: i === indice,
        }));

        definirCampo('alternativas', novas);

    }

    function handleSubmit(e) {
        e.preventDefault();

        const payload = {
            enunciado: dados.enunciado,
            tipo: dados.tipo,
            alternativas: dados.tipo === 'MULTIPLA_ESCOLHA' ? dados.alternativas : [],
        };

        enviar(() => api.criarQuestao(payload), {
            aoSucesso: () => navigate('/professor/questoes'),
            aoErro: () => alert('Erro ao salvar questão.'),
        });

    }

    function adicionarAlternativa() {

        definirCampo('alternativas', [...dados.alternativas, {descricao: '', correta: false},]);

    }

    function removerAlternativa(indice) {

        if (dados.alternativas.length <= 2) {
            return;
        }

        const novas = dados.alternativas.filter((_, i) => i !== indice);

        if (!novas.some((alt) => alt.correta)) {
            novas[0].correta = true;
        }

        definirCampo('alternativas', novas);

    }

    return (

        <div style={{fontFamily: 'sans-serif'}}>

            <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>

                <h2>Nova questão</h2>
                <Link to="/professor/questoes"><button>Voltar</button></Link>

            </div>

            <form onSubmit={handleSubmit} style={{display: 'flex', flexDirection: 'column', gap: '0.8rem', maxWidth: '600px'}}>

                <textarea
                    placeholder="Enunciado"
                    rows={3}
                    required
                    value={dados.enunciado}
                    onChange={(e) => definirCampo('enunciado', e.target.value)}
                    style={{padding: '0.5rem'}}
                />

                <select
                    value={dados.tipo}
                    onChange={(e) => definirCampo('tipo', e.target.value)}
                    style={{padding: '0.5rem'}}
                >
                    <option value="MULTIPLA_ESCOLHA">Múltipla escolha</option>
                    <option value="TEXTO_LIVRE">Texto livre</option>
                </select>

                {dados.tipo === 'MULTIPLA_ESCOLHA' && (

                    <div style={{display: 'flex', flexDirection: 'column', gap: '0.5rem'}}>

                        <strong>Alternativas (marque a correta):</strong>

                        {dados.alternativas.map((alt, index) => (

                            <div key={index} style={{display: 'flex', alignItems: 'center', gap: '0.5rem'}}>
                                <input
                                    type="radio"
                                    name="alternativaCorreta"
                                    checked={alt.correta}
                                    onChange={() => marcarCorreta(index)}
                                />
                                <input
                                    type="text"
                                    placeholder={`Alternativa ${index + 1}`}
                                    required
                                    value={alt.descricao}
                                    onChange={(e) => atualizarTextoAlternativa(index, e.target.value)}
                                    style={{flex: 1, padding: '0.4rem'}}
                                />

                                {dados.alternativas.length > 2 && (
                                    <button
                                        type="button"
                                        onClick={() => removerAlternativa(index)}
                                        style={{padding: '0.4rem 0.6rem', cursor: 'pointer'}}
                                    >
                                        Remover
                                    </button>
                                )}
                            </div>

                        ))}

                        <button
                            type="button"
                            onClick={adicionarAlternativa}
                            style={{alignSelf: 'flex-start', padding: '0.4rem 0.8rem', cursor: 'pointer'}}
                        >
                            Adicionar alternativa
                        </button>

                    </div>

                )}

                <button type="submit" disabled={enviando} style={{padding: '0.6rem', cursor: 'pointer'}}>
                    {enviando ? 'Salvando...' : 'Salvar Questão'}
                </button>

            </form>

        </div>

    );

}