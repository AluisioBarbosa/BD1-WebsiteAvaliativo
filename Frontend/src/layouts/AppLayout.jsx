import {Link, Outlet} from 'react-router-dom';

export default function AppLayout({usuario, onLogout}){
    return (
        <div style={{ fontFamily: 'sans-serif', maxWidth: '1000px', margin: '0 auto', padding: '1rem' }}>
            <nav style={{ display: 'flex', gap: '1rem', paddingBottom: '1rem', borderBottom: '1px solid #ccc', marginBottom: '1.5rem', alignItems: 'center' }}>
                <strong>Sistema de Avaliações</strong>

                {usuario.tipo === 'PROFESSOR' && (
                    <>
                        <Link to="/professor/questoes">Questões</Link>
                        <Link to="/professor/avaliacoes">Avaliações</Link>
                        <Link to="/professor/atribuir">Atribuir Alunos</Link>
                        <Link to="/professor/corrigir">Corrigir Provas</Link>
                    </>
                )}

                {usuario.tipo === 'ALUNO' && (
                    <>
                        <Link to="/aluno/provas">Minhas Provas</Link>
                        <Link to="/aluno/resultados">Meus Resultados</Link>
                    </>
                )}

                <Link to="/relatorios">Relatórios</Link>

                <div style={{ marginLeft: 'auto' }}>
                    {usuario ? (
                        <>
                            <span style={{ marginRight: '0.75rem' }}>{usuario.nome} ({usuario.tipo})</span>
                            <button onClick={onLogout}>Sair</button>
                        </>
                    ) : (
                        <Link to="/login">Entrar</Link>
                    )}
                </div>
            </nav>

            <main>
                <Outlet />
            </main>

        </div>
    );
}