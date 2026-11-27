
-- 1. USUÁRIOS
INSERT INTO Usuario (nome, email, senha) VALUES
-- Professores
('Prof. Carlos Silva', 'carlos.silva@uel.br', '123456'),
('Profa. Mariana Costa', 'mariana.costa@uel.br', '123456'),
('Prof. Roberto Almeida', 'roberto.almeida@uel.br', '123456'),
('Profa. Fernanda Lima', 'fernanda.lima@uel.br', '123456'),
-- Alunos
('Renan José', 'renan@uel.br', '123456'),
('Aluisio Barbosa', 'aluisio@uel.br', '123456'),
('Ana Beatriz Souza', 'ana.souza@uel.br', '123456'),
('Bruno Henrique Martins', 'bruno.martins@uel.br', '123456'),
('Camila Oliveira', 'camila.oliveira@uel.br', '123456'),
('Diego Ferreira', 'diego.ferreira@uel.br', '123456'),
('Eduarda Santos', 'eduarda.santos@uel.br', '123456'),
('Felipe Rodrigues', 'felipe.rodrigues@uel.br', '123456'),
('Gabriela Pereira', 'gabriela.pereira@uel.br', '123456'),
('Henrique Carvalho', 'henrique.carvalho@uel.br', '123456'),
('Isabela Gomes', 'isabela.gomes@uel.br', '123456'),
('João Pedro Ribeiro', 'joao.ribeiro@uel.br', '123456'),
('Larissa Mendes', 'larissa.mendes@uel.br', '123456'),
('Lucas Barbosa', 'lucas.barbosa@uel.br', '123456'),
('Matheus Cardoso', 'matheus.cardoso@uel.br', '123456'),
('Natália Teixeira', 'natalia.teixeira@uel.br', '123456'),
('Otávio Nascimento', 'otavio.nascimento@uel.br', '123456'),
('Paula Moreira', 'paula.moreira@uel.br', '123456'),
('Rafael Correia', 'rafael.correia@uel.br', '123456'),
('Sofia Azevedo', 'sofia.azevedo@uel.br', '123456');

-- 2. PROFESSORES E ALUNOS

INSERT INTO Professor (id) VALUES (1), (2), (3), (4);

INSERT INTO Aluno (id) VALUES
(5), (6), (7), (8), (9), (10), (11), (12), (13), (14),
(15), (16), (17), (18), (19), (20), (21), (22), (23), (24);

-- 3. AVALIAÇÕES (5 registros: IDs 1 a 5)
INSERT INTO avaliacao (titulo, descricao, hora_inicio, hora_fim, id_professor) VALUES
('Prova 1 - Banco de Dados', 'Modelo Relacional, 3FN e SQL básico', '2026-10-10 19:00:00', '2026-10-10 21:00:00', 1),
('Prova 2 - SQL Avançado', 'Joins, subconsultas, índices e transações', '2026-10-28 19:00:00', '2026-10-28 21:00:00', 1),
('Avaliação 1 - Estruturas de Dados', 'Listas, pilhas, filas e árvores binárias', '2026-11-05 14:00:00', '2026-11-05 16:00:00', 2),
('Avaliação 1 - Engenharia de Software', 'Requisitos, UML e metodologias ágeis', '2026-11-12 08:00:00', '2026-11-12 10:00:00', 3),
('Simulado - Redes de Computadores', 'Modelo OSI, TCP/IP e protocolos de aplicação', '2026-11-20 19:00:00', '2026-11-20 21:00:00', 4);

-- 4. QUESTÕES
INSERT INTO Questao (enunciado, tipo) VALUES
-- Avaliação 1
('Qual comando SQL é usado para consultar registros em uma tabela?', 'MULTIPLA_ESCOLHA'),
('O que caracteriza uma tabela na Primeira Forma Normal (1FN)?', 'MULTIPLA_ESCOLHA'),
('Qual restrição garante que não haja valores duplicados nem nulos em uma coluna?', 'MULTIPLA_ESCOLHA'),
('O que significa a sigla DDL em SQL?', 'MULTIPLA_ESCOLHA'),
('Qual cláusula SQL filtra grupos criados pelo GROUP BY?', 'MULTIPLA_ESCOLHA'),
('Explique a diferença entre chave primária e chave estrangeira.', 'DISSERTATIVA'),

-- Avaliação 2
('Qual tipo de JOIN retorna todas as linhas da tabela à esquerda?', 'MULTIPLA_ESCOLHA'),
('Qual propriedade ACID garante que uma transação seja executada por completo ou desfeita?', 'MULTIPLA_ESCOLHA'),
('Para que serve um índice (INDEX) em um banco relacional?', 'MULTIPLA_ESCOLHA'),
('Qual comando desfaz as alterações de uma transação não confirmada?', 'MULTIPLA_ESCOLHA'),
('Qual função de agregação conta o número de registros?', 'MULTIPLA_ESCOLHA'),
('Explique quando usar ON DELETE CASCADE e qual cuidado deve ser tomado.', 'DISSERTATIVA'),

-- Avaliação 3
('Qual estrutura de dados segue o princípio LIFO?', 'MULTIPLA_ESCOLHA'),
('Qual estrutura de dados segue o princípio FIFO?', 'MULTIPLA_ESCOLHA'),
('Qual a complexidade de busca em uma árvore binária de busca balanceada?', 'MULTIPLA_ESCOLHA'),
('Qual algoritmo de ordenação utiliza a estratégia dividir para conquistar?', 'MULTIPLA_ESCOLHA'),
('Em uma lista encadeada simples, cada nó armazena:', 'MULTIPLA_ESCOLHA'),
('Compare o uso de vetor (array) e lista encadeada em termos de inserção e acesso.', 'DISSERTATIVA'),

-- Avaliação 4
('Qual diagrama UML representa a estrutura estática de classes e relacionamentos?', 'MULTIPLA_ESCOLHA'),
('No Scrum, quem é responsável por priorizar o Product Backlog?', 'MULTIPLA_ESCOLHA'),
('O que são requisitos não funcionais?', 'MULTIPLA_ESCOLHA'),
('Qual prática ágil envolve dois programadores trabalhando no mesmo código?', 'MULTIPLA_ESCOLHA'),
('Qual teste verifica a menor unidade isolada de um software?', 'MULTIPLA_ESCOLHA'),
('Descreva a diferença entre validação e verificação de software.', 'DISSERTATIVA'),

-- Avaliação 5
('Qual protocolo da camada de transporte garante entrega confiável e ordenada?', 'MULTIPLA_ESCOLHA'),
('Qual porta padrão é utilizada pelo protocolo HTTPS?', 'MULTIPLA_ESCOLHA'),
('Qual camada do modelo OSI é responsável pelo roteamento de pacotes?', 'MULTIPLA_ESCOLHA'),
('Qual protocolo traduz nomes de domínio em endereços IP?', 'MULTIPLA_ESCOLHA'),
('Qual protocolo é usado para atribuição automática de endereços IP na rede?', 'MULTIPLA_ESCOLHA'),
('Explique a diferença entre TCP e UDP dando um exemplo de uso para cada.', 'DISSERTATIVA');

-- 5. ALTERNATIVAS (75 registros: 3 por questão de múltipla escolha)
INSERT INTO alternativa (descricao, correta, id_questao) VALUES

('SELECT', TRUE, 1), ('INSERT', FALSE, 1), ('UPDATE', FALSE, 1),
('Ausência de chave estrangeira', FALSE, 2), ('Atributos atômicos e sem grupos repetitivos', TRUE, 2), ('Apenas duas colunas por tabela', FALSE, 2),
('PRIMARY KEY', TRUE, 3), ('CHECK', FALSE, 3), ('DEFAULT', FALSE, 3),
('Data Delete Language', FALSE, 4), ('Direct Data Link', FALSE, 4), ('Data Definition Language', TRUE, 4),
('WHERE', FALSE, 5), ('HAVING', TRUE, 5), ('ORDER BY', FALSE, 5),

('LEFT JOIN', TRUE, 7), ('INNER JOIN', FALSE, 7), ('RIGHT JOIN', FALSE, 7),
('Isolamento', FALSE, 8), ('Atomicidade', TRUE, 8), ('Durabilidade', FALSE, 8),
('Acelerar consultas de busca', TRUE, 9), ('Remover colunas duplicadas', FALSE, 9), ('Criptografar senhas', FALSE, 9),
('COMMIT', FALSE, 10), ('SAVEPOINT', FALSE, 10), ('ROLLBACK', TRUE, 10),
('SUM()', FALSE, 11), ('COUNT()', TRUE, 11), ('AVG()', FALSE, 11),

('Pilha (Stack)', TRUE, 13), ('Fila (Queue)', FALSE, 13), ('Grafo', FALSE, 13),
('Pilha (Stack)', FALSE, 14), ('Fila (Queue)', TRUE, 14), ('Árvore Binária', FALSE, 14),
('O(n^2)', FALSE, 15), ('O(n)', FALSE, 15), ('O(log n)', TRUE, 15),
('Merge Sort', TRUE, 16), ('Bubble Sort', FALSE, 16), ('Selection Sort', FALSE, 16),
('Apenas o dado', FALSE, 17), ('O dado e a referência para o próximo nó', TRUE, 17), ('Apenas o índice do vetor', FALSE, 17),

('Diagrama de Classes', TRUE, 19), ('Diagrama de Sequência', FALSE, 19), ('Diagrama de Atividades', FALSE, 19),
('Scrum Master', FALSE, 20), ('Product Owner', TRUE, 20), ('Desenvolvedor Sênior', FALSE, 20),
('Telas do sistema', FALSE, 21), ('Apenas regras de negócio', FALSE, 21), ('Restrições de qualidade como desempenho e segurança', TRUE, 21),
('Pair Programming', TRUE, 22), ('Code Freeze', FALSE, 22), ('Waterfall', FALSE, 22),
('Teste de Aceitação', FALSE, 23), ('Teste Unitário', TRUE, 23), ('Teste de Carga', FALSE, 23),

('TCP', TRUE, 25), ('UDP', FALSE, 25), ('ICMP', FALSE, 25),
('80', FALSE, 26), ('443', TRUE, 26), ('22', FALSE, 26),
('Física', FALSE, 27), ('Enlace', FALSE, 27), ('Rede', TRUE, 27),
('DNS', TRUE, 28), ('FTP', FALSE, 28), ('SMTP', FALSE, 28),
('ARP', FALSE, 29), ('DHCP', TRUE, 29), ('SNMP', FALSE, 29);

-- 6. AVALIACAO_QUESTAO
INSERT INTO avaliacao_questao (id_avaliacao, id_questao, valor, ordem_questoes) VALUES
-- Prova 1
(1, 1, 1.5, 1), (1, 2, 1.5, 2), (1, 3, 1.5, 3), (1, 4, 1.5, 4), (1, 5, 1.5, 5), (1, 6, 2.5, 6),
-- Prova 2
(2, 7, 1.5, 1), (2, 8, 1.5, 2), (2, 9, 1.5, 3), (2, 10, 1.5, 4), (2, 11, 1.5, 5), (2, 12, 2.5, 6),
-- Prova 3
(3, 13, 1.5, 1), (3, 14, 1.5, 2), (3, 15, 1.5, 3), (3, 16, 1.5, 4), (3, 17, 1.5, 5), (3, 18, 2.5, 6),
-- Prova 4
(4, 19, 1.5, 1), (4, 20, 1.5, 2), (4, 21, 1.5, 3), (4, 22, 1.5, 4), (4, 23, 1.5, 5), (4, 24, 2.5, 6),
-- Prova 5
(5, 25, 1.5, 1), (5, 26, 1.5, 2), (5, 27, 1.5, 3), (5, 28, 1.5, 4), (5, 29, 1.5, 5), (5, 30, 2.5, 6);

-- 7. REALIZA
INSERT INTO realiza (id_aluno, id_avaliacao, status_avaliacao) VALUES
-- Prova 1
(5, 1, 'CONCLUIDA'), (6, 1, 'CONCLUIDA'), (7, 1, 'CONCLUIDA'), (8, 1, 'CONCLUIDA'),
(9, 1, 'CONCLUIDA'), (10, 1, 'CONCLUIDA'), (11, 1, 'CONCLUIDA'), (12, 1, 'EM_ANDAMENTO'),
(13, 1, 'PENDENTE'), (14, 1, 'PENDENTE'),
-- Prova 2
(5, 2, 'CONCLUIDA'), (6, 2, 'CONCLUIDA'), (7, 2, 'CONCLUIDA'), (8, 2, 'CONCLUIDA'),
(9, 2, 'CONCLUIDA'), (15, 2, 'EM_ANDAMENTO'), (16, 2, 'PENDENTE'),
-- Prova 3
(10, 3, 'CONCLUIDA'), (11, 3, 'CONCLUIDA'), (12, 3, 'CONCLUIDA'), (13, 3, 'CONCLUIDA'),
(14, 3, 'CONCLUIDA'), (17, 3, 'EM_ANDAMENTO'), (18, 3, 'PENDENTE'),
-- Prova 4
(15, 4, 'CONCLUIDA'), (16, 4, 'CONCLUIDA'), (17, 4, 'CONCLUIDA'), (18, 4, 'CONCLUIDA'),
(19, 4, 'PENDENTE'), (20, 4, 'PENDENTE'),
-- Prova 5
(19, 5, 'CONCLUIDA'), (20, 5, 'CONCLUIDA'), (21, 5, 'CONCLUIDA'), (22, 5, 'EM_ANDAMENTO'),
(23, 5, 'PENDENTE'), (24, 5, 'PENDENTE');

-- 8. RESPOSTAS
INSERT INTO resposta (id_aluno, id_avaliacao, id_questao, id_alternativa, texto_resposta, nota) VALUES
-- ---------- PROVA 1 (Banco de Dados) ----------
-- Aluno 5 (Renan)
(5, 1, 1, 1, NULL, 1.5), (5, 1, 2, 5, NULL, 1.5), (5, 1, 3, 7, NULL, 1.5),
(5, 1, 4, 12, NULL, 1.5), (5, 1, 5, 14, NULL, 1.5),
(5, 1, 6, NULL, 'PK identifica unicamente cada tupla; FK referencia a PK de outra tabela.', 2.5),

-- Aluno 6 (Aluisio)
(6, 1, 1, 1, NULL, 1.5), (6, 1, 2, 5, NULL, 1.5), (6, 1, 3, 7, NULL, 1.5),
(6, 1, 4, 12, NULL, 1.5), (6, 1, 5, 13, NULL, 0.0),
(6, 1, 6, NULL, 'Chave primária é única e não nula; estrangeira liga duas tabelas.', 2.5),

-- Aluno 7 (Ana)
(7, 1, 1, 1, NULL, 1.5), (7, 1, 2, 4, NULL, 0.0), (7, 1, 3, 7, NULL, 1.5),
(7, 1, 4, 12, NULL, 1.5), (7, 1, 5, 13, NULL, 0.0),
(7, 1, 6, NULL, 'PK é o identificador principal e FK aponta para outra tabela.', 2.0),

-- Aluno 8 (Bruno)
(8, 1, 1, 1, NULL, 1.5), (8, 1, 2, 6, NULL, 0.0), (8, 1, 3, 8, NULL, 0.0),
(8, 1, 4, 12, NULL, 1.5), (8, 1, 5, 15, NULL, 0.0),
(8, 1, 6, NULL, 'Ambas servem para ordenar os dados.', 1.0),

-- Aluno 9 (Camila)
(9, 1, 1, 1, NULL, 1.5), (9, 1, 2, 5, NULL, 1.5), (9, 1, 3, 7, NULL, 1.5),
(9, 1, 4, 12, NULL, 1.5), (9, 1, 5, 14, NULL, 1.5),
(9, 1, 6, NULL, 'PK garante unicidade na tabela e FK mantém integridade referencial.', NULL),

-- Aluno 10 (Diego)
(10, 1, 1, 1, NULL, 1.5), (10, 1, 2, 5, NULL, 1.5), (10, 1, 3, 9, NULL, 0.0),
(10, 1, 4, 10, NULL, 0.0), (10, 1, 5, 13, NULL, 0.0),
(10, 1, 6, NULL, 'A PK identifica o registro e a FK faz o relacionamento entre entidades.', 2.5),

-- Aluno 11 (Eduarda)
(11, 1, 1, 1, NULL, 1.5), (11, 1, 2, 5, NULL, 1.5), (11, 1, 3, 7, NULL, 1.5),
(11, 1, 4, 12, NULL, 1.5), (11, 1, 5, 14, NULL, 1.5),
(11, 1, 6, NULL, 'PK é o identificador único; FK conecta tabelas.', 1.5),

-- ---------- PROVA 2 (SQL Avançado) ----------
-- Aluno 5 (Renan)
(5, 2, 7, 16, NULL, 1.5), (5, 2, 8, 20, NULL, 1.5), (5, 2, 9, 22, NULL, 1.5),
(5, 2, 10, 25, NULL, 0.0), (5, 2, 11, 28, NULL, 0.0),
(5, 2, 12, NULL, 'Usado para apagar registros filhos automaticamente; cuidado com exclusões em massa.', 2.5),

-- Aluno 6 (Aluisio)
(6, 2, 7, 17, NULL, 0.0), (6, 2, 8, 20, NULL, 1.5), (6, 2, 9, 22, NULL, 1.5),
(6, 2, 10, 25, NULL, 0.0), (6, 2, 11, 28, NULL, 0.0),
(6, 2, 12, NULL, 'Deleta tudo em cascata quando o pai é removido.', 2.0),

-- Aluno 7 (Ana)
(7, 2, 7, 18, NULL, 0.0), (7, 2, 8, 19, NULL, 0.0), (7, 2, 9, 22, NULL, 1.5),
(7, 2, 10, 26, NULL, 0.0), (7, 2, 11, 29, NULL, 1.5),
(7, 2, 12, NULL, 'Não lembro exatamente o uso.', 0.0),

-- Aluno 8 (Bruno)
(8, 2, 7, 16, NULL, 1.5), (8, 2, 8, 21, NULL, 0.0), (8, 2, 9, 23, NULL, 0.0),
(8, 2, 10, 27, NULL, 1.5), (8, 2, 11, 30, NULL, 0.0),
(8, 2, 12, NULL, 'Remove dependências automaticamente.', 1.5),

-- Aluno 9 (Camila)
(9, 2, 7, 16, NULL, 1.5), (9, 2, 8, 20, NULL, 1.5), (9, 2, 9, 24, NULL, 0.0),
(9, 2, 10, 27, NULL, 1.5), (9, 2, 11, 29, NULL, 1.5),
(9, 2, 12, NULL, 'Evita erro de FK, mas pode apagar dados importantes sem querer.', NULL),

-- ---------- PROVA 3 (Estruturas de Dados) ----------
-- Aluno 10 (Diego)
(10, 3, 13, 31, NULL, 1.5), (10, 3, 14, 35, NULL, 1.5), (10, 3, 15, 39, NULL, 1.5),
(10, 3, 16, 40, NULL, 1.5), (10, 3, 17, 43, NULL, 0.0),
(10, 3, 18, NULL, 'Vetores têm acesso O(1); listas encadeadas facilitam inserção no início.', 2.5),

-- Aluno 11 (Eduarda)
(11, 3, 13, 31, NULL, 1.5), (11, 3, 14, 35, NULL, 1.5), (11, 3, 15, 39, NULL, 1.5),
(11, 3, 16, 40, NULL, 1.5), (11, 3, 17, 44, NULL, 1.5),
(11, 3, 18, NULL, 'Array tem tamanho fixo e acesso direto; lista é dinâmica e usa ponteiros.', 2.5),

-- Aluno 12 (Felipe)
(12, 3, 13, 31, NULL, 1.5), (12, 3, 14, 35, NULL, 1.5), (12, 3, 15, 38, NULL, 0.0),
(12, 3, 16, 41, NULL, 0.0), (12, 3, 17, 44, NULL, 1.5),
(12, 3, 18, NULL, 'Lista encadeada é mais flexível que array.', 1.0),

-- Aluno 13 (Gabriela)
(13, 3, 13, 31, NULL, 1.5), (13, 3, 14, 34, NULL, 0.0), (13, 3, 15, 39, NULL, 1.5),
(13, 3, 16, 40, NULL, 1.5), (13, 3, 17, 44, NULL, 1.5),
(13, 3, 18, NULL, 'Array é bom para leitura por índice; lista para inserções frequentes.', 1.0),

-- Aluno 14 (Henrique)
(14, 3, 13, 32, NULL, 0.0), (14, 3, 14, 35, NULL, 1.5), (14, 3, 15, 37, NULL, 0.0),
(14, 3, 16, 42, NULL, 0.0), (14, 3, 17, 44, NULL, 1.5),
(14, 3, 18, NULL, 'Ambos armazenam coleções de dados.', 0.5),

-- ---------- PROVA 4 (Engenharia de Software) ----------
-- Aluno 15 (Isabela)
(15, 4, 19, 46, NULL, 1.5), (15, 4, 20, 50, NULL, 1.5), (15, 4, 21, 54, NULL, 1.5),
(15, 4, 22, 55, NULL, 1.5), (15, 4, 23, 59, NULL, 1.5),
(15, 4, 24, NULL, 'Verificação checa se construímos certo; validação se construímos o produto certo.', 2.0),

-- Aluno 16 (João Pedro)
(16, 4, 19, 46, NULL, 1.5), (16, 4, 20, 50, NULL, 1.5), (16, 4, 21, 54, NULL, 1.5),
(16, 4, 22, 56, NULL, 0.0), (16, 4, 23, 59, NULL, 1.5),
(16, 4, 24, NULL, 'Validação foca no cliente e verificação nos requisitos técnicos.', 2.5),

-- Aluno 17 (Larissa)
(17, 4, 19, 46, NULL, 1.5), (17, 4, 20, 50, NULL, 1.5), (17, 4, 21, 54, NULL, 1.5),
(17, 4, 22, 55, NULL, 1.5), (17, 4, 23, 59, NULL, 1.5),
(17, 4, 24, NULL, 'Verificação garante conformidade com especificações; validação atende às necessidades reais.', 2.5),

-- Aluno 18 (Lucas)
(18, 4, 19, 46, NULL, 1.5), (18, 4, 20, 49, NULL, 0.0), (18, 4, 21, 54, NULL, 1.5),
(18, 4, 22, 55, NULL, 1.5), (18, 4, 23, 59, NULL, 1.5),
(18, 4, 24, NULL, 'Uma testa o código e a outra testa com o usuário.', 1.5),

-- ---------- PROVA 5 (Redes de Computadores) ----------
-- Aluno 19 (Matheus)
(19, 5, 25, 61, NULL, 1.5), (19, 5, 26, 65, NULL, 1.5), (19, 5, 27, 69, NULL, 1.5),
(19, 5, 28, 70, NULL, 1.5), (19, 5, 29, 73, NULL, 0.0),
(19, 5, 30, NULL, 'TCP é confiável (ex: HTTP); UDP é mais rápido sem garantia de entrega (ex: streaming).', 2.0),

-- Aluno 20 (Natália)
(20, 5, 25, 61, NULL, 1.5), (20, 5, 26, 64, NULL, 0.0), (20, 5, 27, 69, NULL, 1.5),
(20, 5, 28, 70, NULL, 1.5), (20, 5, 29, 75, NULL, 0.0),
(20, 5, 30, NULL, 'TCP garante entrega; UDP prioriza velocidade em chamadas de vídeo.', 2.0),

-- Aluno 21 (Otávio)
(21, 5, 25, 62, NULL, 0.0), (21, 5, 26, 65, NULL, 1.5), (21, 5, 27, 68, NULL, 0.0),
(21, 5, 28, 70, NULL, 1.5), (21, 5, 29, 74, NULL, 1.5),
(21, 5, 30, NULL, 'TCP e UDP são protocolos de rede.', NULL);

