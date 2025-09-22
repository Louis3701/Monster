package org.example

import Entraineur
import org.example.monde.Zone
import org.example.monstres.EspeceMonstre
import org.example.monstres.IndividuMonstre


// Création de l'entraîneur
var joueur = Entraineur(1,"Sacha",100)

// Création d'une espèce de monstre (exemple : Pikachu)
val pikachu = EspeceMonstre(
    id = 25,
    nom = "Pikachu",
    description = "Un petit Pokémon de type Électrik, mascotte célèbre.",
    basePV = 35,
    baseAttaque = 55,
    baseDefense = 40,
    baseVitesse = 90,
    modificateurPV = 1.0,
    modificateurAttaque = 1.0,
    modificateurDefense = 1.0,
    modificateurVitesse = 1.0,
    modPV = 0.0,
    modAtt = 0.0,
    modDef = 0.0,
    modVit = 0.0,
    evolution = "Raichu",
    particularites = "Très affectueux, adore le ketchup",
    caracteres = "Rapide, loyal"
)

// Création de deux zones (exemple)
val route1 = Zone(1, "Route 1", mutableListOf(pikachu))
val route2 = Zone(2, "Route 2", mutableListOf(pikachu))

fun main() {
    // On relie les zones entre elles
    route1.zoneSuivante = route2
    route2.zonePrecedente = route1

    println("===== TEST EspeceMonstre =====")
    println("Nom : ${pikachu.nom}")
    println("PV de base : ${pikachu.basePV}")
    println("Vitesse : ${pikachu.baseVitesse}")
    println("Évolution : ${pikachu.evolution}")

    // === TEST IndividuMonstre ===
    val pikachuIndividu = IndividuMonstre(
        id = 1,
        nom = "Mon Pikachu",
        espece = pikachu,
        entraineur = joueur
    )

    // Afficher les infos du monstre
    pikachuIndividu.afficherInfos()

    // Simuler un level up
    pikachuIndividu.levelUp()
    println("Après un level up :")
    pikachuIndividu.afficherInfos()
}

