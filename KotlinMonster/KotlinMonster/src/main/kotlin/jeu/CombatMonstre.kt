package org.example.jeu

import org.example.monstres.IndividuMonstre

/**
 * Représente un combat entre un monstre du joueur et un monstre sauvage.
 *
 * @property monstreJoueur Monstre appartenant au joueur (lié à un Entraineur).
 * @property monstreSauvage Monstre sauvage affronté.
 * @property round Compteur de rounds, commence à 1.
 */
class CombatMonstre(
    val monstreJoueur: IndividuMonstre,
    val monstreSauvage: IndividuMonstre
) {
    var round: Int = 1

    /**
     * Détermine si le joueur a perdu.
     *
     * Règle de défaite:
     *  - Aucun monstre de l'équipe du joueur n'a des PV > 0.
     * Si l'équipe n'est pas disponible, on considère seulement le monstreJoueur.
     */
    fun gameOver(): Boolean {
        val equipe = monstreJoueur.entraineur?.equipeMonstre
        return if (equipe.isNullOrEmpty()) {
            monstreJoueur.pv <= 0
        } else {
            // true si aucun vivant
            equipe.none { it.pv > 0 }
        }
    }
}
