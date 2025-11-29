INSERT INTO TBROLE (nm_role) VALUES ('ROLE_USER') ON CONFLICT (nm_role) DO NOTHING;
INSERT INTO TBROLE (nm_role) VALUES ('ROLE_ADMIN') ON CONFLICT (nm_role) DO NOTHING;
INSERT INTO TBROLE (nm_role) VALUES ('ROLE_AGENTE') ON CONFLICT (nm_role) DO NOTHING;

--==================================================================================================================================================

INSERT INTO TBPLANO (nm_plano, vl_plano, limite_usuarios, hr_resposta_plano, hr_resolucao_plano)
SELECT 'BRONZE', 99.90, 30, 8, 12 WHERE NOT EXISTS (SELECT 1 FROM TBPLANO WHERE nm_plano = 'BRONZE');

INSERT INTO TBPLANO (nm_plano, vl_plano, limite_usuarios, hr_resposta_plano, hr_resolucao_plano)
SELECT 'PRATA', 199.90, 60, 6, 10 WHERE NOT EXISTS (SELECT 1 FROM TBPLANO WHERE nm_plano = 'PRATA');

INSERT INTO TBPLANO (nm_plano, vl_plano, limite_usuarios, hr_resposta_plano, hr_resolucao_plano)
SELECT 'OURO', 499.90, null, 2, 6 WHERE NOT EXISTS (SELECT 1 FROM TBPLANO WHERE nm_plano = 'OURO');

--==================================================================================================================================================

INSERT INTO TBCATEGORIA (NMCATEGORIA, HRRESPOSTA, HRRESOLUCAO, FLATIVO)
SELECT 'Incidente', 24, 48, TRUE WHERE NOT EXISTS (SELECT 1 FROM TBCATEGORIA WHERE NMCATEGORIA = 'Incidente');

INSERT INTO TBCATEGORIA (NMCATEGORIA, HRRESPOSTA, HRRESOLUCAO, FLATIVO)
SELECT 'Requisicao', 18, 36, TRUE WHERE NOT EXISTS (SELECT 1 FROM TBCATEGORIA WHERE NMCATEGORIA = 'Requisicao');

INSERT INTO TBCATEGORIA (NMCATEGORIA, HRRESPOSTA, HRRESOLUCAO, FLATIVO)
SELECT 'Problema', 12, 24, TRUE WHERE NOT EXISTS (SELECT 1 FROM TBCATEGORIA WHERE NMCATEGORIA = 'Problema');

--==================================================================================================================================================

INSERT INTO TBSLA (qt_horas_resposta, qt_horas_resolucao, fl_ativo, cd_plano, cd_categoria)
SELECT
    p.hr_resposta_plano + c.hrresposta,
    p.hr_resolucao_plano + c.hrresolucao,
    TRUE,
    p.cd_plano,
    c.cdcategoria
FROM TBPLANO p
JOIN TBCATEGORIA c ON 1 = 1
WHERE p.nm_plano = 'BRONZE'
  AND c.nmcategoria = 'Incidente'
  AND NOT EXISTS (
      SELECT 1 FROM TBSLA WHERE cd_plano = p.cd_plano AND cdcategoria = c.cdcategoria
  );

INSERT INTO TBSLA (qt_horas_resposta, qt_horas_resolucao, fl_ativo, cd_plano, cd_categoria)
SELECT
    p.hr_resposta_plano + c.hrresposta,
    p.hr_resolucao_plano + c.hrresolucao,
    TRUE,
    p.cd_plano,
    c.cdcategoria
FROM TBPLANO p
JOIN TBCATEGORIA c ON 1 = 1
WHERE p.nm_plano = 'BRONZE'
  AND c.nmcategoria = 'Requisicao'
  AND NOT EXISTS (
      SELECT 1 FROM TBSLA WHERE cd_plano = p.cd_plano AND cd_categoria = c.cdcategoria
  );

INSERT INTO TBSLA (qt_horas_resposta, qt_horas_resolucao, fl_ativo, cd_plano, cd_categoria)
SELECT
    p.hr_resposta_plano + c.hrresposta,
    p.hr_resolucao_plano + c.hrresolucao,
    TRUE,
    p.cd_plano,
    c.cdcategoria
FROM TBPLANO p
JOIN TBCATEGORIA c ON 1 = 1
WHERE p.nm_plano = 'BRONZE'
  AND c.nmcategoria = 'Problema'
  AND NOT EXISTS (
      SELECT 1 FROM TBSLA WHERE cd_plano = p.cd_plano AND cd_categoria = c.cdcategoria
  );

INSERT INTO TBSLA (qt_horas_resposta, qt_horas_resolucao, fl_ativo, cd_plano, cd_categoria)
SELECT
    p.hr_resposta_plano + c.hrresposta,
    p.hr_resolucao_plano + c.hrresolucao,
    TRUE,
    p.cd_plano,
    c.cdcategoria
FROM TBPLANO p
JOIN TBCATEGORIA c ON 1 = 1
WHERE p.nm_plano = 'PRATA'
  AND c.nmcategoria = 'Incidente'
  AND NOT EXISTS (
      SELECT 1 FROM TBSLA WHERE cd_plano = p.cd_plano AND cd_categoria = c.cdcategoria
  );

INSERT INTO TBSLA (qt_horas_resposta, qt_horas_resolucao, fl_ativo, cd_plano, cd_categoria)
SELECT
    p.hr_resposta_plano + c.hrresposta,
    p.hr_resolucao_plano + c.hrresolucao,
    TRUE,
    p.cd_plano,
    c.cdcategoria
FROM TBPLANO p
JOIN TBCATEGORIA c ON 1 = 1
WHERE p.nm_plano = 'PRATA'
  AND c.nmcategoria = 'Requisicao'
  AND NOT EXISTS (
      SELECT 1 FROM TBSLA WHERE cd_plano = p.cd_plano AND cd_categoria = c.cdcategoria
  );

INSERT INTO TBSLA (qt_horas_resposta, qt_horas_resolucao, fl_ativo, cd_plano, cd_categoria)
SELECT
    p.hr_resposta_plano + c.hrresposta,
    p.hr_resolucao_plano + c.hrresolucao,
    TRUE,
    p.cd_plano,
    c.cdcategoria
FROM TBPLANO p
JOIN TBCATEGORIA c ON 1 = 1
WHERE p.nm_plano = 'PRATA'
  AND c.nmcategoria = 'Problema'
  AND NOT EXISTS (
      SELECT 1 FROM TBSLA WHERE cd_plano = p.cd_plano AND cd_categoria = c.cdcategoria
  );

INSERT INTO TBSLA (qt_horas_resposta, qt_horas_resolucao, fl_ativo, cd_plano, cd_categoria)
SELECT
    p.hr_resposta_plano + c.hrresposta,
    p.hr_resolucao_plano + c.hrresolucao,
    TRUE,
    p.cd_plano,
    c.cdcategoria
FROM TBPLANO p
JOIN TBCATEGORIA c ON 1 = 1
WHERE p.nm_plano = 'OURO'
  AND c.nmcategoria = 'Incidente'
  AND NOT EXISTS (
      SELECT 1 FROM TBSLA WHERE cd_plano = p.cd_plano AND cd_categoria = c.cdcategoria
  );

INSERT INTO TBSLA (qt_horas_resposta, qt_horas_resolucao, fl_ativo, cd_plano, cd_categoria)
SELECT
    p.hr_resposta_plano + c.hrresposta,
    p.hr_resolucao_plano + c.hrresolucao,
    TRUE,
    p.cd_plano,
    c.cdcategoria
FROM TBPLANO p
JOIN TBCATEGORIA c ON 1 = 1
WHERE p.nm_plano = 'OURO'
  AND c.nmcategoria = 'Requisicao'
  AND NOT EXISTS (
      SELECT 1 FROM TBSLA WHERE cd_plano = p.cd_plano AND cd_categoria = c.cdcategoria
  );

INSERT INTO TBSLA (qt_horas_resposta, qt_horas_resolucao, fl_ativo, cd_plano, cd_categoria)
SELECT
    p.hr_resposta_plano + c.hrresposta,
    p.hr_resolucao_plano + c.hrresolucao,
    TRUE,
    p.cd_plano,
    c.cdcategoria
FROM TBPLANO p
JOIN TBCATEGORIA c ON 1 = 1
WHERE p.nm_plano = 'OURO'
  AND c.nmcategoria = 'Problema'
  AND NOT EXISTS (
      SELECT 1 FROM TBSLA WHERE cd_plano = p.cd_plano AND cd_categoria = c.cdcategoria
  );

--==================================================================================================================================================

INSERT INTO TBUSUARIO (nm_usuario, ds_senha, ds_email, nu_funcionario, plano_cd_plano, dt_criacao, dt_ultimo_acesso, fl_ativo)
VALUES ('Usuario Comum', '$2a$10$eL1SvZLbqfaacrZzJLSNTu3pdmuxFD6St/P5RPr0oZfuG28l2RbIW', 'user@empresa.com', 1001, 1, NOW(), NOW(), TRUE)
    ON CONFLICT (ds_email) DO NOTHING;

INSERT INTO TBUSUARIO (nm_usuario, ds_senha, ds_email,nu_funcionario,  plano_cd_plano, dt_criacao, dt_ultimo_acesso, fl_ativo)
VALUES ('Administrador', '$2a$10$eL1SvZLbqfaacrZzJLSNTu3pdmuxFD6St/P5RPr0oZfuG28l2RbIW', 'admin@empresa.com',1 , 2, NOW(), NOW(), TRUE)
    ON CONFLICT (ds_email) DO NOTHING;

INSERT INTO TBUSUARIOROLES (usuario_id, role_id) SELECT 1, 1 WHERE NOT EXISTS (SELECT 1 FROM TBUSUARIOROLES WHERE usuario_id = 1 AND role_id = 1);

INSERT INTO TBUSUARIOROLES (usuario_id, role_id) SELECT 2, 2 WHERE NOT EXISTS (SELECT 1 FROM TBUSUARIOROLES WHERE usuario_id = 2 AND role_id = 2);


