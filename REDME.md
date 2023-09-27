parameters:
java_path: "C:\\Program Files\\Java\\jdk1.6.0_25\\bin\\java.exe"
user_roles:
"ROLE_ADMIN":           "Admin"
"ROLE_RADEEMA_DR":       "Directeur Radeem"
"ROLE_RECLAMATION_RES": "Responsable"
"ROLE_OKSA_DR":         "Directeur exploitation"
"ROLE_OKSA_CHEF":       "Supérviseur"
"ROLE_OKSA_CHEF_EA":     "Supérviseur Eau Assainissement"
"ROLE_OKSA_OR":         "Ordonnanceur "
"ROLE_OKSA_CHEF_EQP":   "Chef d’équipe"
"ROLE_OKSA_EQP_MEMBER": "Membre d’équipe"
mobile_roles:
- ROLE_ADMIN
- ROLE_RADEEMA_DR
- ROLE_OKSA_DR
- ROLE_OKSA_CHEF_EQP
- ROLE_OKSA_EQP_MEMBER
material_flow_types:
1: "Entrée"
2: "Sortie"
reclamation_sources:
5: "Téléphonique"
6: "Lettre"
7: "Fax"
8: "Email"
9: "SMS"
10: "Autre"
1: "Centre d’appel"
2: "Bon écrit"
3: "Service d’exploitation"
4: "Agence"
11: "Centre Relation Client"

reclamation_priorities:
1: "Elevée"
2: "Normale"
3: "Faible"

reclamation_status:
1: "En attente"
5: "Planifier"
6: "Pris en charge"
2: "En cours"
8: "Empêchée"
9: "Rendue"
7: "Clôturée Provisoirement"
4: "Clôturée"
3: "Annulée"


reporting_day_filters:
"sector":   "Localité"
"source":   "Source réclamation"
"duration": "Durée d’intervention"
reporting_month_filters:
"sector":            "Localité"
"source":            "Source réclamation"
"stock_consomation": "Consommation du stock"
reporting_year_filters:
"sector":            "Localité"
"source":            "Source réclamation"
"stock_consomation": "Consommation du stock"
months:
"01": "Janvier"
"02": "Février"
"03": "Mars"
"04": "Avril"
"05": "Mai"
"06": "Juin"
"07": "Juillet"
"08": "Août"
"09": "Septembre"
"10": "Octobre"
"11": "Novembre"
"12": "Décembre"
material_unites:
"m" : "Mètre"
"kg": "KG"
"l" : "Litre"
"u" : "Unité"
is_active:
0: "Non"
1: "Oui"
source:
"1": "1"
"2": "2"
"3": "3"
"4": "4"
"5": "5"
"6": "6"
"7": "7"
"8": "8"
"9": "9"
"10": "10"

reclamation_motifs:
"Surtension": "Surtension"
"Manque_Electricite": "Manque Electricité"
"Eclairage_Publique": "Eclairage Publique"
"Probleme_de_Poteau": "Problème de Poteau"
"Problème_de_Ligne": "Problème de Ligne"
"Manque_Phase": "Manque Phase"
"Client_Non_Avisé_de_la_Coupure": "Client Non Avisé de la Coupure"
"Probleme_de_Compteur": "Problème de Compteur"
"Retard_Execution_Branchement/Pose_compteur": "Retard Exécution Branchement/Pose compteur"
"Court_Circuit": "Court-Circuit"
"Remise_en_Etat_du_Chantier": "Remise en Etat du Chantier"
"Chute_de_Tension": "Chute de Tension"
"Retard_Etude_Devis": "Retard Etude Devis"
"power_cut":       "Coupure d’électricité"
"grilled_counter": "Compteur grillé"
"ground_cable":    "Câble à la terre"
"mass":            "Masse"
"surge":           "Surtension"
"public_danger":   "Danger public"
"third_damages":   "Dégats causés par tiers"
"interv_request":  "Demande d’intervention"
"voltage_drop":    "Chute de tension"
"fire":            "Incendie"
"sparks":          "Etincelles"
"other":           "Autre"
reclamation_motifsEA:
"Fuite_eau":       "Fuite d'eau"
"QualitE_eau":       "Qualité d'eau"
"Probleme_de_Pression":       "Problème de Pression"
"Probleme_Compteur":   "Problème Compteur"
"Manque_deau":   "Manque d'eau"
"Probleme_Regard":   "Problème Regard"
"Mise_a_la_cote_des_ouvrages":   "Mise à la côte des ouvrages"
"Deplacement_des_Ouvrages":   "Déplacement des Ouvrages"
"Retard_Etude_Devis":   "Retard Etude Devis"
"Retard_sur_Travaux":   "Retard sur Travaux"
"Probleme_de_Refection":   "Problème de Réfection"
"other":        "Autre"
reclamation_motifsA:
"Debordement_Eaux_Usees":       "Débordement Eaux Usées"
"Vidange_Fosse_septique":       "Vidange Fosse septique"
"Divers":       "Divers"
"Eaux_pluviales":       "Eaux pluviales"
"Manque_tampon":       "Manque tampon"
"Debouchage":       "Débouchage"
"Affaissement":       "Affaissement"
"Manque_Réseau_Assainissement":       "Manque Réseau Assainissement"
"Refection": "Réfection"
"Problème_de_Branchement_Reseau": "Problème de Branchement Réseau"
"Mise_a_niveau_Regard_de_Visite": "Mise à niveau Regard de Visite"
"other":        "Autre"

field_investigations:
"power_cut":       "Coupure d’électricité"
"grilled_counter": "Compteur grillé"
"other":           "Autre"

branches:
1 : "Électricité"
2 : "Eau potable"
3 : "Assainissement"
4 : "Eau et Assainissement"
0 : "Tous"

categories_reclamation:
1 : "Réclamation Client"
2 : "Réclamation RADEEM"

type_client:
1: "Normal"
2: "Ministère"
3: "Commune"
4: "Autorité"
5: "Industrie"
6: "Hotel"

    doker :
         docker rmi -f $(docker images -q) # delete all running container
            network :
                    docker network  create spring-net

                spring project :
                    docker build -t tirgani_smart_city:1.0 .
                    docker run  --name spring-container --network spring-net -p 8080:8080 tirgani_smart_city:1.0
                mysql :
                    docker run -d -p 3307:3306 --net spring-net --name mysqldb  -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=smartCities mysql:latest
                    docker network connect spring-net mysqldb 