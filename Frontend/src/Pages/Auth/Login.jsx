import {useNavigate} from 'react-router-dom';
import {useForm} from '../../hooks/useForm';
import {api} from '../../services/api';

export default function Login({onLogin}){

    const navigate = useNavigate();

    const {dados, definirCampo, enviar, enviando} = useForm({email: '', senha: '',});

    // se o usuário for professor, ele irá para a parte para professor.
    // caso contrário, irá para a do aluno.
    function finalizarEntrada(usuarioLogado) {
        onLogin(usuarioLogado);
        if (usuarioLogado.tipo === 'PROFESSOR') {
            navigate('/professor/questoes');
        } else {
            navigate('/aluno/provas');
        }
    }

    function tratadorEnvio(e) {
        e.preventDefault();
        enviar(api.login, {
            aoSucesso: (usuarioLogado) => finalizarEntrada(usuarioLogado),
            aoErro: () => alert('Usuário ou senha inválidos.'),
        });
    }

    return (
        <div style={{ maxWidth: '380px', margin: '4rem auto', padding: '1.5rem', border: '1px solid #ccc', borderRadius: '8px', fontFamily: 'sans-serif' }}>
            <h2 style={{ marginTop: 0 }}>Entrar no Sistema</h2>

            <form onSubmit={tratadorEnvio} style={{ display: 'flex', flexDirection: 'column', gap: '0.8rem' }}>
                <div>
                    <input
                        type="email"
                        placeholder={"Email"}
                        required
                        value={dados.email}
                        onChange={(e) => definirCampo('email', e.target.value)}
                        style={{ width: '100%', padding: '0.4rem', boxSizing: 'border-box' }}
                    />
                </div>

                <div>
                    <input
                        type="password"
                        placeholder={"Senha"}
                        required
                        value={dados.senha}
                        onChange={(e) => definirCampo('senha', e.target.value)}
                        style={{ width: '100%', padding: '0.4rem', boxSizing: 'border-box' }}
                    />
                </div>

                <button type="submit" disabled={enviando} style={{ padding: '0.6rem', cursor: 'pointer' }}>
                    {enviando ? 'Entrando...' : 'Entrar'}
                </button>
            </form>

        </div>
    );
}