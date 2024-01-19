Dernières modifications :

18/01/2024 : 
Correction de bugs : 
  - Correction d'un problème causant le premier appui sur le bouton lang d'être ignoré quand la langue de départ est Fr dans MainActivity
  - Dépassement du texte dans Information_activity
Ajout :
  - Activité WIFI
  - Retrait des animations de transition entre activités (sauf retour en arrière)

18/01/2024 : 
Correction de bugs : 
  - Réecriture de l'incrémentation des octets dans VLSMCalculator => erreurs de calcul lorsque l'on incrémente un octet + d'une fois
  - Réecriture du if qui change le language lors du click sur le bouton qui change le langage dans MainActivity => marche une seule fois
Ajout :
  - Ajout d'une vérification de dépassement du bloc d'@ pour les requêtes contenant 1 SR dans VLSMCalculator
  - Ajout d'une vérification de dépassement du bloc d'@ pour le reste dans VLSMCalculator
  - Ajout du support de la langue anglaise dans VLSMCalculator + VLSM

17/01/2024 :
Correction de bug :
  - Bouton de changement de langue fonctionnel (+bug : 1 click max)
Ajout :
  - Activité Information + contenu
  - Activité VLSM + contenu
  - Activité IP + contenu
