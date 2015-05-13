GLPOO_ESIEA_1415_Eternity_Brainville
=================================

Projet GLPOO ESIEA 2014-2015 Eternity groupe Brainville

Equipe
------ 
- Brainville Arthur (Ybalrid)
- Adam Romain       (lioncelest)
- Chauvel Sébastien (Sinma)
- Devaux Nicolas    (nisemono951)
- Gattolin Nicolas  (ztenma)
- Raullet Sébastien (granackPathos)
- Ye Sylvie         (Shibiye)

Installation
------------
- Installer maven et eclipse comme pour le TD
- dans la copie locale, faire "mvn clone install eclipse:eclipse"
- Importer le projet dans eclipse
- dans eclipse, faire *Project > Properties > Java Build Path > Sources*, retirer ce qu'eclipse a créé automatiquement, rajouter le dossier /src du projet uniquement.

Ce dépot est configuré automatiquement avec maven, tourne sur java 8 et effectue des test unitaires avec jUnit 4.12, OpenCSV est aussi inclu en dépendances. 

Il contient aussi des fichiers destiné à être utiliser avec le plugin UML ObjectAid pour eclipse disponible sur ce dépot : http://www.objectaid.net/update (à installer avec help->Install new software...)