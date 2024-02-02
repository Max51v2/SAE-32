L'APK est téléchargeable à cette adresse : https://drive.google.com/file/d/1EcGfwRuWNuqU0WbFpVJuHrXVj5jE-qQN/view?usp=sharing
=> note : google flag le fichier comme contenant un virus car c'est un apk
=> Scan VirusTotal : https://www.virustotal.com/gui/file/e8dc54116761adf47e1ac00e4f38cb9be5470f858816f8feea6831e850f1ec5c/detection

Dernières modifications :

30/01/2024 : 
Ajout :
  - Activité WIFI <= Page Warning qui demande d'ajouter les droits de localisation
  - Activité WIFI <= Requête pour avoir les droits de localisation
  - Activité WIFI <= informations complémentaires
  - Activité WIFI <= boucle infinie avec pauses

22/01/2024 : 
Correction de bugs : 
  - Correction d'un problème causant le crash de l'application lorsque l'on rentre soit rien ou un espace dans un champ dans IP et VLSM
Ajout :
  - Activité WIFI <= donne toutes les info sur une interface

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
