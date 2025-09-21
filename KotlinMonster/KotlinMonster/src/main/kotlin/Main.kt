package org.example
import Entraineur
import org.example.monstres.EspeceMonstre

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
var joueur = Entraineur(1,"Sacha",100)

fun main() {
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

    println("===== TEST EspeceMonstre =====")
    println("Nom : ${pikachu.nom}")
    println("PV de base : ${pikachu.basePV}")
    println("Vitesse : ${pikachu.baseVitesse}")
    println("Évolution : ${pikachu.evolution}")

    try {
        val art = pikachu.afficheArt()
        println("Art ASCII de ${pikachu.nom} :\n$art")
    } catch (e: Exception) {
        println("⚠ Aucun fichier ASCII trouvé pour ${pikachu.nom}.")
    }
}


/**
 * Change la couleur du message donné selon le nom de la couleur spécifié.
 * Cette fonction utilise les codes d'échappement ANSI pour appliquer une couleur à la sortie console. Si un nom de couleur
 * non reconnu ou une chaîne vide est fourni, aucune couleur n'est appliquée.
 *
 * @param message Le message auquel la couleur sera appliquée.
 * @param couleur Le nom de la couleur à appliquer (ex: "rouge", "vert", "bleu"). Par défaut c'est une chaîne vide, ce qui n'applique aucune couleur.
 * @return Le message coloré sous forme de chaîne, ou le même message si aucune couleur n'est appliquée.
 */

fun changeCouleur(message: String, couleur:String=""): String {
    val reset = "\u001B[0m"
    val codeCouleur = when (couleur.lowercase()) {
        "rouge" -> "\u001B[31m"
        "vert" -> "\u001B[32m"
        "jaune" -> "\u001B[33m"
        "bleu" -> "\u001B[34m"
        "magenta" -> "\u001B[35m"
        "cyan" -> "\u001B[36m"
        "blanc" -> "\u001B[37m"
        else -> "" // pas de couleur si non reconnu
    }
    return "$codeCouleur$message$reset"
}


