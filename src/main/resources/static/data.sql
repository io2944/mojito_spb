-- =============================================================================
-- DONNEES D'INITIALISATION POUR MOJITO
-- =============================================================================
-- Les tables sont automatiquement créées par Hibernate à partir des entités JPA

-- Insertion des rôles de base
INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('MANAGER');

-- Insertion d'utilisateurs de test
INSERT INTO users (username, password, email) VALUES ('admin', '$2y$10$PRA.0AuB2I6mP6laNlPOLukvDziASte6TEXgUu5gwSG/CwGC7NuDK', 'admin@mojito.com');
INSERT INTO users (username, password, email) VALUES ('user1', '$2y$10$j9RWApizu6Reyqw5qCSHMu3yjJShGUm99A6OGQPbjw8L9VY.adccW', 'user1@mojito.com');
INSERT INTO users (username, password, email) VALUES ('manager', '$2y$10$lR6aXGJf/CMU5z8ELNM5oONojLQWieCZ0.boVuo9NeZJs.LMIFJf6', 'manager@mojito.com');

-- Association utilisateurs-rôles
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); -- admin -> ADMIN
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2); -- user1 -> USER
INSERT INTO user_roles (user_id, role_id) VALUES (3, 3); -- manager -> MANAGER

-- Insertion de clients de test
INSERT INTO clients (nom, prenom, entreprise, adresse, telephone_fixe, telephone_mobile, email, commentaire, is_active, type_client, statut_client) VALUES ('Dupont', 'Jean', 'Entreprise ABC', '123 Rue de la Paix, Paris', '0123456789', '0678901234', 'jean.dupont@abc.com', 'Client VIP', true, 'PROFESSIONNEL', 'CLIENT');

INSERT INTO clients (nom, prenom, entreprise, adresse, telephone_fixe, telephone_mobile, email, commentaire, is_active, type_client, statut_client) VALUES ('Martin', 'Sophie', 'Jedi SAS', '456 Avenue des Champs, Lyon', '123456789', '0687654321', 'sophie.martin@email.com', 'Cliente particulière', true, 'PARTICULIER', 'CLIENT');

INSERT INTO clients (nom, prenom, entreprise, adresse, telephone_fixe, telephone_mobile, email, commentaire, is_active, type_client, statut_client) VALUES ('Durand', 'Pierre', 'Tech Solutions', '789 Boulevard Tech, Toulouse', '0534567890', '0698765432', 'pierre.durand@techsol.com', 'Client entreprise', true, 'PROFESSIONNEL', 'CLIENT');

-- Insertion de garanties de test
--INSERT INTO garanties (date, commentaire, libelle, client_id) 
--VALUES (CURRENT_DATE, 'Garantie standard 1 an', 'Garantie produit électronique', 1);
--
--INSERT INTO garanties (date, commentaire, libelle, client_id) 
--VALUES (CURRENT_DATE, 'Garantie étendue 2 ans', 'Garantie équipement professionnel', 3);
--
---- Insertion de devis de test
--INSERT INTO devis (client_id, date_creation, poids, tva, type_document, libelle, prix_kilo, commentaire, nom_document, statut)
--VALUES  (1, '2024-01-15', 2.5, 20.00, 'ALIMENTAIRES', 'Transport de produits frais', 500.00, 'Livraison urgente', 'DEV_20240115_1400', 'EN_COURS'),
--        (2, '2024-01-20', 5.0, 20.00, 'PHARMACEUTIQUES', 'Médicaments sensibles', 750.00, 'Température contrôlée', 'DEV_20240120_0930', 'EN_COURS'),
--        (3, '2024-01-25', 1.2, 20.00, 'RAS', 'Documents administratifs', 300.00, 'Transport standard', 'DEV_20240125_1500', 'ARCHIVEE');
--
---- Insertion de commandes de test
--INSERT INTO commandes (client_id, devis_id, date_creation, poids, tva, type_document, libelle, prix_kilo, commentaire, nom_document, orbite, statut)
--VALUES (1, 1, '2024-01-16', 2.5, 20.00, 'ALIMENTAIRES', 'Transport de produits frais', 500.00, 'Commande confirmée depuis devis', 'commande2024-01-16', FALSE, 'EN_COURS'),
--       (2, 2, '2024-01-21', 5.0, 20.00, 'PHARMACEUTIQUES', 'Médicaments sensibles', 750.00, 'Transport prioritaire', 'commande2024-01-21', TRUE, 'EN_COURS');
--
---- Insertion de factures de test
--INSERT INTO factures (client_id, commande_id, date_creation, poids, tva, type_document, libelle, prix_kilo, commentaire, nom_document, statut)
--VALUES (1, 1, '2024-01-17', 2.5, 20.00, 'ALIMENTAIRES', 'Transport de produits frais', 500.00, 'Facture pour commande terminée', 'FAC_20240117_001', 'EN_ATTENTE_DE_REGLEMENT'),
--       (2, 2, '2024-01-22', 5.0, 20.00, 'PHARMACEUTIQUES', 'Médicaments sensibles', 750.00, 'Transport effectué avec succès', 'FAC_20240122_002', 'PAYEE');
--
-- ÉTAPE 1: Documents de base pour les DEVIS (IDs auto-générés)
INSERT INTO documents (date_creation, poids, tva, type_document, libelle, prix_kilo, commentaire, nom_document, client_id) VALUES (CURRENT_DATE, 25.5, 20.0, 'ALIMENTAIRES', 'Devis livraison produits frais', 450.0, 'Transport réfrigéré requis - Devis initial', 'DEV_20250910_001', (SELECT id FROM clients WHERE nom = 'Dupont' AND prenom = 'Jean'));

INSERT INTO documents (date_creation, poids, tva, type_document, libelle, prix_kilo, commentaire, nom_document, client_id) VALUES (CURRENT_DATE, 12.0, 20.0, 'PHARMACEUTIQUES', 'Devis médicaments urgents', 800.0, 'Livraison prioritaire 24h - Estimation', 'DEV_20250910_002', (SELECT id FROM clients WHERE nom = 'Dupont' AND prenom = 'Jean'));

INSERT INTO documents (date_creation, poids, tva, type_document, libelle, prix_kilo, commentaire, nom_document, client_id) VALUES (CURRENT_DATE, 5.2, 20.0, 'SENSIBLES', 'Devis documents confidentiels', 1200.0, 'Transport sécurisé avec signature - Devis', 'DEV_20250910_003', (SELECT id FROM clients WHERE nom = 'Dupont' AND prenom = 'Jean'));

-- ÉTAPE 2: Création des DEVIS (en utilisant les IDs générés automatiquement)
INSERT INTO devis (id, statut) VALUES ((SELECT id FROM documents WHERE nom_document = 'DEV_20250910_001'), 'EN_COURS');

INSERT INTO devis (id, statut) VALUES ((SELECT id FROM documents WHERE nom_document = 'DEV_20250910_002'), 'ARCHIVEE');

INSERT INTO devis (id, statut) VALUES ((SELECT id FROM documents WHERE nom_document = 'DEV_20250910_003'), 'EN_COURS');

-- ÉTAPE 3: Documents de base pour les COMMANDES (IDs auto-générés)
INSERT INTO documents (date_creation, poids, tva, type_document, libelle, prix_kilo, commentaire, nom_document, client_id) VALUES (CURRENT_DATE, 18.3, 20.0, 'ALIMENTAIRES', 'Commande boulangerie artisanale', 420.0, 'Livraison matinale impérative - Commande ferme', 'CMD_20250910_001', (SELECT id FROM clients WHERE nom = 'Dupont' AND prenom = 'Jean'));

INSERT INTO documents (date_creation, poids, tva, type_document, libelle, prix_kilo, commentaire, nom_document, client_id) VALUES (CURRENT_DATE, 35.7, 20.0, 'RAS', 'Commande matériel informatique', 350.0, 'Equipements fragiles - manipulation soigneuse', 'CMD_20250910_002', (SELECT id FROM clients WHERE nom = 'Dupont' AND prenom = 'Jean'));

INSERT INTO documents (date_creation, poids, tva, type_document, libelle, prix_kilo, commentaire, nom_document, client_id) VALUES (CURRENT_DATE, 8.9, 20.0, 'PHARMACEUTIQUES', 'Commande pharmacie urgente', 750.0, 'Chaîne du froid à respecter absolument', 'CMD_20250910_003', (SELECT id FROM clients WHERE nom = 'Dupont' AND prenom = 'Jean'));

-- ÉTAPE 4: Création des COMMANDES (elles utilisent les IDs auto-générés ET peuvent référencer les devis)
INSERT INTO commandes (id, orbite, statut, devis_id) VALUES ((SELECT id FROM documents WHERE nom_document = 'CMD_20250910_001'), false, 'EN_COURS', (SELECT id FROM documents WHERE nom_document = 'DEV_20250910_001'));

INSERT INTO commandes (id, orbite, statut, devis_id) VALUES ((SELECT id FROM documents WHERE nom_document = 'CMD_20250910_002'), true, 'FACTUREE', (SELECT id FROM documents WHERE nom_document = 'DEV_20250910_001'));

INSERT INTO commandes (id, orbite, statut, devis_id) VALUES ((SELECT id FROM documents WHERE nom_document = 'CMD_20250910_003'), false, 'EN_ATTENTE', (SELECT id FROM documents WHERE nom_document = 'DEV_20250910_001'));

-- ÉTAPE 5: Documents et commandes supplémentaires (sans devis associé)
INSERT INTO documents (date_creation, poids, tva, type_document, libelle, prix_kilo, commentaire, nom_document, client_id) VALUES (CURRENT_DATE, 42.1, 20.0, 'ARMES', 'Transport matériel sécurisé', 2000.0, 'Autorisation spéciale requise - escort armé', 'CMD_20250910_004', (SELECT id FROM clients WHERE nom = 'Dupont' AND prenom = 'Jean'));

INSERT INTO commandes (id, orbite, statut, devis_id) VALUES ((SELECT id FROM documents WHERE nom_document = 'CMD_20250910_004'), true, 'ANNULEE', (SELECT id FROM documents WHERE nom_document = 'DEV_20250910_001'));

-- FACTURES
-- 1. Insérer la partie commune dans documents
INSERT INTO documents (date_creation, poids, tva, type_document, libelle, prix_kilo, commentaire, nom_document, client_id) VALUES (CURRENT_DATE, 42.1, 20.0, 'ARMES', 'Facture matériel sécurisé', 2000.0,'Facturation avec escorte armée', 'FAC_20250918_001',(SELECT id FROM clients WHERE nom = 'Dupont' AND prenom = 'Jean'));

-- 2. Insérer la partie spécifique Facture
INSERT INTO factures (id, statut_facture, commande_id) VALUES ((SELECT id FROM documents WHERE nom_document = 'FAC_20250918_001'),'PAYEE',(SELECT id FROM commandes WHERE id = (SELECT id FROM documents WHERE nom_document = 'CMD_20250910_004')));