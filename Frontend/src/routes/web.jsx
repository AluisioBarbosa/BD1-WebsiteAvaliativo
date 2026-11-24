import {Routes, Route, Navigate} from 'react-router-dom';
import AppLayout from '../Layouts/AppLayout';

import Login from '../Pages/Auth/Login';
import Questoes from '../Pages/Professor/ListaQuestoes';
import CriacaoQuestoes from '../Pages/Professor/CriacaoQuestoes';
import Avaliacoes from '../Pages/Professor/ListaAvaliacoes';
import CriacaoAvaliacoes from '../Pages/Professor/CriacaoAvaliacoes';
import AtribuirAlunos from '../Pages/Professor/AtribuirAlunos';
import CorrigirProvas from '../Pages/Professor/CorrigirProvas';
import MinhasProvas from '../Pages/Aluno/MinhasProvas';
import ResponderProva from '../Pages/Aluno/ResponderProva';
import MeuResultado from '../Pages/Aluno/MeuResultado';
import Relatorios from '../Pages/Relatorios/Index';

export default function WebRoutes({usuario, setUsuario}){
    return(
        <Routes>
            <Route path="/login" element={<Login onLogin={setUsuario} />} />

            <Route element={<AppLayout usuario={usuario} onLogout={() => setUsuario(null)} />}>

                <Route path="/professor/questoes" element={<Questoes />} />
                <Route path="/professor/questoes/nova" element={<CriacaoQuestoes />} />
                <Route path="/professor/avaliacoes" element={<Avaliacoes />} />
                <Route path={"/professor/avaliacoes/nova"} element={<CriacaoAvaliacoes usuario={usuario} />}/>
                <Route path="/professor/atribuir" element={<AtribuirAlunos />} />
                <Route path="/professor/corrigir" element={<CorrigirProvas />} />

                <Route path="/aluno/provas" element={<MinhasProvas usuario={usuario} />} />
                <Route path="/aluno/provas/:idAvaliacao/responder" element={<ResponderProva usuario={usuario} />} />
                <Route path="/aluno/resultados" element={<MeuResultado usuario={usuario} />} />

                <Route path="/relatorios" element={<Relatorios />} />

                <Route path="*" element={<Navigate to="/login" replace />} />
            </Route>

        </Routes>
    );
}