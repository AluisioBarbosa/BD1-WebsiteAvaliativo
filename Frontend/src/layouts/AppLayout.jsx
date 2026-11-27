import {Link, Navigate, Outlet} from 'react-router-dom';

export default function AppLayout({usuario, onLogout}){

    if (!usuario) {
        return <Navigate to="/login" replace />;
    }

    console.log('Usuario logado no AppLayout:', usuario);

    const tipoUsuario = (usuario.tipo || usuario.perfil || '').toUpperCase();

    const ehProfessor = tipoUsuario === 'PROFESSOR';

    return (
        <div style={{fontFamily: 'sans-serif', maxWidth: '1000px', margin: '0 auto', padding: '1rem'}}>

            <nav style={{display: 'flex', gap: '1rem', paddingBottom: '1rem', borderBottom: '1px solid #ccc', marginBottom: '1.5rem', alignItems: 'center'}}>

                <strong>Sistema de Avaliações</strong>

                {ehProfessor ? (
                    <>
                        <Link to="/professor/questoes">Questões</Link>
                        <Link to="/professor/avaliacoes">Avaliações</Link>
                        <Link to="/professor/atribuir">Atribuir Alunos</Link>
                        <Link to="/professor/corrigir">Corrigir Provas</Link>
                        <Link to="/relatorios">Relatórios</Link>
                    </>
                ) : (
                    <>
                    <Link to="/aluno/provas">Minhas Provas</Link>
                    <Link to="/aluno/resultados">Meus Resultados</Link>
                    </>
                )}

                <div style={{ marginLeft: 'auto' }}>
                    {usuario ? (
                        <>
                            <span style={{marginRight: '0.75rem'}}>{usuario.nome}</span>
                            <button onClick = {onLogout}>Sair</button>
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