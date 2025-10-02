package org.example

import Entraineur
import monstre.Element
import org.example.monde.Zone
import org.example.monstres.EspeceMonstre
import org.example.monstres.IndividuMonstre
import org.example.item.Badge
import org.example.jeu.CombatMonstre

// ====================================================
// 0) Déclaration des Éléments (globaux)
// ====================================================
val feu = Element(1, "Feu")
val plante = Element(2, "Plante")
val eau = Element(3, "Eau")
val insecte = Element(4, "Insecte")
val roche = Element(5, "Roche")
val normal = Element(6, "Normal")

// ====================================================
// 1) Création de l’entraîneur
// ====================================================
val joueur = Entraineur(1, "Sacha", 100)

// ====================================================
// 2) Création d'espèces de monstres
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
// 4) Objet global
// ====================================================
val kubeBasique = org.example.item.MonsterKube(
    id = 100,
    nom = "MonsterKube",
    description = "Une kube standard pour capturer des monstres.",
    chanceCapture = 30.0
)

// ====================================================
// 5) Fonction main
// ====================================================
fun main() {
    // --- Définition des relations entre Éléments ---
    feu.forces.addAll(listOf(plante, insecte))
    feu.faiblesses.addAll(listOf(eau, roche, feu))

    plante.forces.addAll(listOf(eau, roche))
    plante.faiblesses.addAll(listOf(feu, insecte))

    eau.forces.addAll(listOf(feu, roche))
    eau.faiblesses.addAll(listOf(plante))

    insecte.forces.addAll(listOf(plante))
    insecte.faiblesses.addAll(listOf(feu, roche))

    roche.forces.addAll(listOf(feu, insecte))
    roche.faiblesses.addAll(listOf(eau, plante))

    normal.faiblesses.add(roche)

    // --- Associer des éléments aux espèces ---
    springleaf.elements.add(plante) // Springleaf est de type 🌱
    flamkip.elements.add(feu)       // Flamkip est de type 🔥

    // --- Démonstration ---
    val monstreDemo = IndividuMonstre(
        id = 1,
        nom = "Springleaf",
        expInit = 1500.0,
        espece = springleaf,
        entraineur = joueur
    )

    monstreDemo.afficheDetail(coteACote = true)

    joueur.equipeMonstre.add(monstreDemo)

    val monstreSauvage = IndividuMonstre(
        id = 2,
        nom = "Flamkip sauvage",
        expInit = 0.0,
        espece = flamkip,
        entraineur = null
    )

    val combat = CombatMonstre(
        monstreJoueur = monstreDemo,
        monstreSauvage = monstreSauvage
    )

    combat.combattreDansLeTerminal(chanceCaptureBase = kubeBasique.chanceCapture)

    val badgePierre = Badge(
        id = 1,
        nom = "Badge Roche",
        description = "Badge gagné lorsque le joueur atteint l'arène de pierre",
        champion = joueur
    )

    println("Badge créé -> id=${badgePierre.id}, nom=${badgePierre.nom}, champion=${badgePierre.champion.nom}")

    // --- Test efficacité éléments ---
    println("🔥 contre 🌱 : ${feu.efficaciteContre(plante)}")  // 2.0
    println("🔥 contre 💧 : ${feu.efficaciteContre(eau)}")     // 0.5
    println("💧 contre 🪨 : ${eau.efficaciteContre(roche)}")   // 2.0
}
