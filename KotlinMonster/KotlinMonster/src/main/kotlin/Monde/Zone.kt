package org.example.monde

import Entraineur
import org.example.jeu.CombatMonstre
import org.example.monstres.EspeceMonstre
import org.example.monstres.IndividuMonstre
import kotlin.random.Random

class Zone(
    val id: Int,
    val nom: String,
    // Liste des espèces qui peuvent apparaître dans cette zone
    var especesMonstres: MutableList<EspeceMonstre>,
    // Expérience « moyenne » des monstres générés dans la zone
    var expBaseZone: Double = 200.0,
    var zoneSuivante: Zone? = null,
    var zonePrecedente: Zone? = null
) {
    /**
     * Génère un monstre sauvage appartenant à une des espèces de la zone.
     * Son expérience initiale est définie comme expBaseZone ±20%.
     */
    fun genererMonstre(): IndividuMonstre {
        require(especesMonstres.isNotEmpty()) { "Aucune espèce définie pour la zone $nom" }
        val espece = especesMonstres.random()
        val facteur = Random.nextDouble(0.8, 1.2)
        val expInit = expBaseZone * facteur
        return IndividuMonstre(
            id = Random.nextInt(1_000_000),
            nom = espece.nom.lowercase(),
            expInit = expInit,
            espece = espece,
            entraineur = null
        )
    }

    /**
     * Démarre un combat entre un monstre sauvage généré et le premier monstre du joueur
     * ayant des PV > 0. Ne fait rien si aucun monstre valide n'est disponible.
     */
    fun rencontreMonstre(joueur: Entraineur) {
        val monstreSauvage = genererMonstre()
        val premierPokemon = joueur.equipeMonstre.firstOrNull { it.pv > 0 }
        if (premierPokemon == null) {
            println("Aucun monstre apte au combat dans l'équipe de ${joueur.nom}.")
            return
        }
        val combat = CombatMonstre(monstreJoueur = premierPokemon, monstreSauvage = monstreSauvage)
        combat.lancerCombat()
    }
}