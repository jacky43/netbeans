
## Description
HeatMyfloor est une application concu en java pour visualiser et modéliser le système de chauffage du sol d'une pièce. Elle va nous permettre de visualiser une pièce, 
y ajouter des meubles, les selectionner pour les déplacer, modifier leur dimensions, les déplcaer et le supprimer au final


## Prérequis de l'environement exécution windows|macs
	- **java 21** ou supérieur
	- **Maven** pour la compilation
	
## compilation

# Aller dans le repertoire du projet
cd HeatMyfloor

# Éxécuter le fichier .jar dans le repertoire
java -jar target/HeatMyfloor-v1.jar

## Guide d'utilisation

### Visualisation de la pièce
	- Au démarrage une pièce rectagulaire par defaut s'affiche dans la zone de dessin centrale
	- La pièce est représentée par un polygone beige avec contour noir
	- Les dimensiosns s'adaptent automatiquement à la taille de la fenêtre
	
###	Création et gestion des meubles

#### Ajouter un meuble sans drain
1-cliquer sur le bouton ajouter meuble sans drain 
2-un meuble est représentée par un rectangle gris foncé avec contour noir

#### Sélectionner un meuble
	- cliquer sur le meuble dans qui est dans la zone de dessin 
	- Les coordonnées du meubles sont affiché à gauche sur les box position x et y
	
#### Modifier les dimension du meuble
1 - Sélectionner le meuble en cliquant dessus
2 - Utiliser le panneau d'édition à gauche 
	- Modifier la valeur dans le champ "Longueur"
	- Modifier la valeur dans le champ "Largeur"
3 - cliquer sur le bouton modifier meuble pour apliquer les changements

#### Déplacer un meuble
1 - Sélectionner le meuble en cliquant dessus
2 - Utiliser le panneau d'édition à gauche 
	- Modifier la valeur dans le champ "Position X"
	- Modifier la valeur dans le champ "Position Y"
3 - cliquer sur le bouton modifier meuble pour apliquer les changements

#### supprimer un meuble
1 - Sélectionner le meuble en cliquant dessus
2 - cliquer sur le boutton supprimer meuble
