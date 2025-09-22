package org.example

import Entraineur
import org.example.monstres.EspeceMonstre
import org.example.monde.Zone

// Création du joueur (un entraîneur)
var joueur = Entraineur(1, "Sacha", 100)

// === Déclaration des espèces de monstres ===
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

// === Déclaration des zones (avant main) ===
// Ici on place directement id, nom et liste mutable d’espèces
val route1 = Zone(
    id = 1,
    nom = "Route 1",
    expZone = 50,
    especesMonstres = mutableListOf(pikachu) // pour l’instant on met Pikachu dans cette zone
)

val route2 = Zone(
    id = 2,
    nom = "Route 2",
    expZone = 100,
    especesMonstres = mutableListOf(pikachu) // exemple : même espèce pour tester
)

fun main() {
    // === Relier les zones ===
    // On relie route1 → route2
    route1.zoneSuivante = route2
    // Et route2 ← route1
    route2.zonePrecedente = route1

    println("===== TEST Zone =====")
    println("Zone 1 : ${route1.nom}, Exp : ${route1.expZone}, Suivante : ${route1.zoneSuivante?.nom}")
    println("Zone 2 : ${route2.nom}, Exp : ${route2.expZone}, Précédente : ${route2.zonePrecedente?.nom}")
}

