package org.example

import Entraineur
import org.example.monde.Zone
import org.example.monstres.EspeceMonstre
import org.example.monstres.IndividuMonstre
import org.example.item.Badge
import org.example.item.MonsterKube
import org.example.jeu.CombatMonstre

// ====================================================
// 1) Création de l’entraîneur
// ====================================================
val joueur = Entraineur(1, "Sacha", 100)

// ====================================================
// 2) Création d'une espèce de monstre
// ====================================================
val springleaf = EspeceMonstre(
    id = 1,
    nom = "Springleaf",
    description = "Petit monstre espiègle rond comme une graine, adore le soleil.",
    basePV = 60,
    baseAttaque = 9,
    baseDefense = 11,
    baseVitesse = 10,
    modificateurPV = 6.5,
    modificateurAttaque = 9.5,
    modificateurDefense = 8.0,
    modificateurVitesse = 7.0,
    modPV = 14.0,
    modAtt = 8.0,
    modDef = 7.0,
    modVit = 8.0,
    evolution = "???", // à remplir si besoin
    particularites = "Sa feuille sur la tête indique son humeur.",
    caracteres = "Curieux, amical, timide"
)
val flamkip = EspeceMonstre(
    id = 4,
    nom = "Flamkip",
    description = "Petit animal entouré de flammes, déteste le froid.",
    basePV = 50,
    baseAttaque = 12,
    baseDefense = 8,
    baseVitesse = 13,
    modificateurPV = 10.0,
    modificateurAttaque = 5.5,
    modificateurDefense = 9.5,
    modificateurVitesse = 12.0,
    modPV = 22.0,
    modAtt = 16.0,
    modDef = 7.0,
    modVit = 10.0,
    evolution = "???",
    particularites = "Sa flamme change d’intensité selon son énergie.",
    caracteres = "Impulsif, joueur, loyal"
)

// ====================================================
// 3) Création de zones
// ====================================================
val route1 = Zone(1, "Forêt Mystérieuse", mutableListOf(springleaf))
val route2 = Zone(2, "Plaine Ensoleillée", mutableListOf(springleaf))

// ====================================================
// 4) Fonction main
// ====================================================

// Objet MonsterKube disponible globalement (avant main)
val kubeBasique = org.example.item.MonsterKube(
    id = 100,
    nom = "MonsterKube",
    description = "Une kube standard pour capturer des monstres.",
    chanceCapture = 30.0
)

fun main() {
    // Crée un monstre de démonstration et affiche l’art + les détails côte à côte
    val monstreDemo = IndividuMonstre(
        id = 1,
        nom = "springleaf",
        expInit = 1500.0,
        espece = springleaf,
        entraineur = joueur
    )

    // Affiche dans le terminal comme dans l’exemple
    monstreDemo.afficheDetail(coteACote = true)

    // Prépare un combat de démonstration et affiche si c'est "Game Over"
    // 1) Ajouter le monstre du joueur dans son équipe
    joueur.equipeMonstre.add(monstreDemo)

    // 2) Créer un monstre sauvage
    val monstreSauvage = IndividuMonstre(
        id = 2,
        nom = "Flamkip sauvage",
        expInit = 0.0,
        espece = flamkip,
        entraineur = null
    )

    // 3) Créer le combat
    val combat = CombatMonstre(monstreJoueur = monstreDemo, monstreSauvage = monstreSauvage)

    // Lancer un vrai combat interactif dans le terminal
    combat.combattreDansLeTerminal(chanceCaptureBase = kubeBasique.chanceCapture)

    // Test temporaire: création d'un badge (hérite de Item)
    val badgePierre = Badge(
        id = 1,
        nom = "Badge Roche",
        description = "Badge gagné lorsque le joueur atteint l'arène de pierre",
        champion = joueur
    )
    println("Badge créé -> id=${badgePierre.id}, nom=${badgePierre.nom}, champion=${badgePierre.champion.nom}")
}











