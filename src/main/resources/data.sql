-- 1. ROLES (Baseado em nm_role que é UNIQUE)
INSERT INTO TBROLE (nm_role) VALUES ('ROLE_USER') ON CONFLICT (nm_role) DO NOTHING;
INSERT INTO TBROLE (nm_role) VALUES ('ROLE_ADMIN') ON CONFLICT (nm_role) DO NOTHING;
INSERT INTO TBROLE (nm_role) VALUES ('ROLE_AGENTE') ON CONFLICT (nm_role) DO NOTHING;

-- 2. PLANOS (Assumindo que nm_plano deve ser único. Se não tiver constraint unique no banco, o script abaixo evita duplicatas via SELECT)
INSERT INTO TBPLANO (nm_plano, vl_plano, limite_usuarios, hr_resposta_plano, hr_resolucao_plano)
SELECT 'BRONZE', 99.90, 10, 8, 48 WHERE NOT EXISTS (SELECT 1 FROM TBPLANO WHERE nm_plano = 'BRONZE');

INSERT INTO TBPLANO (nm_plano, vl_plano, limite_usuarios, hr_resposta_plano, hr_resolucao_plano)
SELECT 'PRATA', 199.90, 50, 4, 24 WHERE NOT EXISTS (SELECT 1 FROM TBPLANO WHERE nm_plano = 'PRATA');

INSERT INTO TBPLANO (nm_plano, vl_plano, limite_usuarios, hr_resposta_plano, hr_resolucao_plano)
SELECT 'OURO', 499.90, 999, 1, 8 WHERE NOT EXISTS (SELECT 1 FROM TBPLANO WHERE nm_plano = 'OURO');

-- 3. CATEGORIAS (Evitando duplicatas pelo nome)
INSERT INTO TBCATEGORIA (NMCATEGORIA, HRRESPOSTA, HRRESOLUCAO, FLATIVO)
SELECT 'Incidente', 4, 24, TRUE WHERE NOT EXISTS (SELECT 1 FROM TBCATEGORIA WHERE NMCATEGORIA = 'Incidente');

INSERT INTO TBCATEGORIA (NMCATEGORIA, HRRESPOSTA, HRRESOLUCAO, FLATIVO)
SELECT 'Requisicao', 8, 48, TRUE WHERE NOT EXISTS (SELECT 1 FROM TBCATEGORIA WHERE NMCATEGORIA = 'Requisicao');

INSERT INTO TBCATEGORIA (NMCATEGORIA, HRRESPOSTA, HRRESOLUCAO, FLATIVO)
SELECT 'Problema', 2, 8, TRUE WHERE NOT EXISTS (SELECT 1 FROM TBCATEGORIA WHERE NMCATEGORIA = 'Problema');

-- 4. SLA (Evitando duplicatas pela combinação de plano e categoria)
INSERT INTO TBSLA (qt_horas_resposta, qt_horas_resolucao, fl_ativo, cd_plano, cd_categoria)
SELECT 4, 24, TRUE, 1, 1 WHERE NOT EXISTS (SELECT 1 FROM TBSLA WHERE cd_plano = 1 AND cd_categoria = 1);

INSERT INTO TBSLA (qt_horas_resposta, qt_horas_resolucao, fl_ativo, cd_plano, cd_categoria)
SELECT 8, 48, TRUE, 1, 2 WHERE NOT EXISTS (SELECT 1 FROM TBSLA WHERE cd_plano = 1 AND cd_categoria = 2);

INSERT INTO TBSLA (qt_horas_resposta, qt_horas_resolucao, fl_ativo, cd_plano, cd_categoria)
SELECT 2, 8, TRUE, 2, 3 WHERE NOT EXISTS (SELECT 1 FROM TBSLA WHERE cd_plano = 2 AND cd_categoria = 3);

-- 5. USUARIOS (Baseado em ds_email que é UNIQUE)
INSERT INTO TBUSUARIO (nm_usuario, ds_senha, ds_email, nu_funcionario, plano_cd_plano, dt_criacao, dt_ultimo_acesso, fl_ativo)
VALUES ('Usuario Comum', '$2a$10$eL1SvZLbqfaacrZzJLSNTu3pdmuxFD6St/P5RPr0oZfuG28l2RbIW', 'user@empresa.com', 1001, 1, NOW(), NOW(), TRUE)
    ON CONFLICT (ds_email) DO NOTHING;

INSERT INTO TBUSUARIO (nm_usuario, ds_senha, ds_email, nu_funcionario, plano_cd_plano, dt_criacao, dt_ultimo_acesso, fl_ativo)
VALUES ('Administrador', '$2a$10$W4C7D.NQn48LvEVtNnFQR.tECoDy81h.kaIy/T/oWAl7uvuTADBXG', 'admin@empresa.com', 1002, 2, NOW(), NOW(), TRUE)
    ON CONFLICT (ds_email) DO NOTHING;

INSERT INTO TBUSUARIO (nm_usuario, ds_senha, ds_email, nu_funcionario, plano_cd_plano, dt_criacao, dt_ultimo_acesso, fl_ativo)
VALUES ('Agente Suporte', '$2a$10$h5pfZchmzYH58iPkwUSDKuIRchyEjODeMhscOtYkYLi.aDlOzo/ny', 'agente@empresa.com', 1003, 2, NOW(), NOW(), TRUE)
    ON CONFLICT (ds_email) DO NOTHING;

-- 6. TB_USUARIO_ROLES (Evita duplicar associação de roles)
-- Usuario 1 (Comum) -> Role 1 (User)
INSERT INTO TBUSUARIOROLES (usuario_id, role_id) SELECT 1, 1 WHERE NOT EXISTS (SELECT 1 FROM TBUSUARIOROLES WHERE usuario_id = 1 AND role_id = 1);

-- Usuario 2 (Admin) -> Role 2 (Admin) e Role 1 (User)
INSERT INTO TBUSUARIOROLES (usuario_id, role_id) SELECT 2, 2 WHERE NOT EXISTS (SELECT 1 FROM TBUSUARIOROLES WHERE usuario_id = 2 AND role_id = 2);

-- Usuario 3 (Agente) -> Role 3 (Agente) e Role 1 (User)
INSERT INTO TBUSUARIOROLES (usuario_id, role_id) SELECT 3, 3 WHERE NOT EXISTS (SELECT 1 FROM TBUSUARIOROLES WHERE usuario_id = 3 AND role_id = 3);

-- 7. CHAMADOS (Evita duplicar se já existir um chamado com mesmo título para o mesmo solicitante)
INSERT INTO tb_chamados (DSTITULO, DSDESCRICAO, STATUS, SOLICITANTE, RESPONSAVEL, ANEXO, CATEGORIA, SLA, DTCRIACAO, DTFECHAMENTO, DTVENCIMENTO, FLSLAVIOLADO)
SELECT 'Erro ao acessar o sistema', 'Usuario nao consegue logar no sistema', 'ABERTO', 1, 3, NULL, 1,  1, NOW(), NULL, NOW() + INTERVAL '48 HOURS', FALSE
WHERE NOT EXISTS (SELECT 1 FROM tb_chamados WHERE DSTITULO = 'Erro ao acessar o sistema' AND SOLICITANTE = 1);

INSERT INTO tb_chamados (DSTITULO, DSDESCRICAO, STATUS, SOLICITANTE, RESPONSAVEL, ANEXO, CATEGORIA, SLA, DTCRIACAO, DTFECHAMENTO, DTVENCIMENTO, FLSLAVIOLADO)
SELECT 'Solicitacao de novo acesso', 'Criar usuario para novo colaborador', 'ABERTO', 1, 3, NULL, 2, 2, NOW(), NULL, NOW() + INTERVAL '24 HOURS', FALSE
WHERE NOT EXISTS (SELECT 1 FROM tb_chamados WHERE DSTITULO = 'Solicitacao de novo acesso' AND SOLICITANTE = 1);

-- 8. COMENTARIO (Evita duplicar comentário idêntico no mesmo chamado)
INSERT INTO TBCOMENTARIO (CDCHAMADO, CDUSUARIO, CDANEXO, ds_conteudo, hr_criacao, dt_criacao)
SELECT 1, 3, NULL, 'Estamos analisando o problema e retornaremos em breve.', LOCALTIME, CURRENT_DATE
    WHERE NOT EXISTS (SELECT 1 FROM TBCOMENTARIO WHERE CDCHAMADO = 1 AND CDUSUARIO = 3 AND ds_conteudo = 'Estamos analisando o problema e retornaremos em breve.');