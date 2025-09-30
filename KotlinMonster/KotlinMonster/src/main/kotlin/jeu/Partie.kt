package org.example.jeu

import Entraineur
import org.example.monde.Zone
import org.example.monstres.IndividuMonstre

class Partie(
    var id: Int,
    var joueur: Entraineur,
    var zone: Zone
) {
    val nom: String get() = joueur.nom

    /**
     * Affiche les informations de l'équipe du joueur et propose des actions.
     * - Affiche la liste des monstres avec leurs positions.
     * - Tapez un numéro pour voir le détail d'un monstre.
     * - Tapez 'm' pour modifier l'ordre de l'équipe.
     * - Tapez 'q' pour revenir au menu principal.
     */
    fun examineEquipe() {
        while (true) {
            val equipe = joueur.equipeMonstre
            println("=== Équipe du joueur ===")
            if (equipe.isEmpty()) {
                println("Aucun monstre dans l'équipe.")
                println("Tapez 'q' pour revenir.")
            } else {
                equipe.forEachIndexed { i, m ->
                    println("${i + 1}. ${m.nom} (PV: ${m.pv})")
                }
                println("- Tapez le numéro d'un monstre pour voir son détail")
            }
            println("- Tapez 'm' pour modifier l'ordre des monstres")
            println("- Tapez 'q' pour revenir au menu principal")

            when (val reponse = readLine()?.trim()?.lowercase() ?: "") {
                "" -> println("Entrée vide, veuillez réessayer.")
                "q" -> return
                "m" -> modifierOrdreEquipe()
                else -> {
                    val numero = reponse.toIntOrNull()
                    if (numero == null) {
                        println("Entrée invalide: tapez un numéro, 'm' ou 'q'.")
                    } else {
                        val equipeSize = equipe.size
                        if (numero !in 1..equipeSize) {
                            println("Numéro hors limites. Choisissez entre 1 et $equipeSize.")
                        } else {
                            val monstre = equipe[numero - 1]
                            println("=== Détail du monstre #$numero ===")
                            monstre.afficheDetail()
                        }
                    }
                }
            }
        }
    }

    /** Permet d'inverser la position de deux monstres dans l'équipe du joueur. */
    fun modifierOrdreEquipe() {
        val equipe = joueur.equipeMonstre
        if (equipe.size < 2) {
            println("Impossible de modifier l'ordre: il faut au moins 2 monstres.")
            return
        }
        println("=== Équipe actuelle ===")
        equipe.forEachIndexed { index, monstre ->
            println("${index + 1}. ${monstre.nom} (PV: ${monstre.pv})")
        }

        fun lirePosition(message: String): Int? {
            println(message)
            val pos = readLine()?.trim()?.toIntOrNull()
            return when {
                pos == null -> {
                    println("Entrée invalide: veuillez saisir un nombre.")
                    null
                }
                pos !in 1..equipe.size -> {
                    println("Position hors limites. Doit être comprise entre 1 et ${equipe.size}.")
                    null
                }
                else -> pos
            }
        }

        val posA = lirePosition("Entrez la position du monstre à déplacer :") ?: return
        val posB = lirePosition("Entrez la nouvelle position :") ?: return
        if (posA == posB) {
            println("Les positions sont identiques: aucun changement effectué.")
            return
        }
        equipe[posA - 1] = equipe[posB - 1].also { equipe[posB - 1] = equipe[posA - 1] }

        println("Ordre modifié: $posA <-> $posB")
        println("=== Nouvelle équipe ===")
        equipe.forEachIndexed { index, monstre ->
            println("${index + 1}. ${monstre.nom} (PV: ${monstre.pv})")
        }
    }

    // Retourne le premier monstre apte au combat (PV > 0), sinon null.
    fun monstreActif(): IndividuMonstre? =
        joueur.equipeMonstre.firstOrNull { it.pv > 0 }

    // Démarre un combat contre un monstre sauvage donné.
    fun demarrerCombat(monstreSauvage: IndividuMonstre) {
        val actif = monstreActif()
        if (actif == null) {
            println("Aucun monstre apte au combat.")
            return
        }
        CombatMonstre(actif, monstreSauvage).lancerCombat()
    }

    fun changerZone(nouvelleZone: Zone) {
        zone = nouvelleZone
    }

    fun afficherEtat() {
        println("Partie #$id")
        joueur.afficheDetail()
        println("Zone actuelle: $zone")
    }

    /** Menu principal de la partie */
    fun jouer() {
        while (true) {
            println("\n=== Partie #$id ===")
            println("Zone actuelle: $zone")
            println("Actions possibles :")
            println("1. Rencontrer un monstre sauvage")
            println("2. Examiner l’équipe de monstres")
            println("3. Aller à la zone suivante")
            println("4. Aller à la zone précédente")
            println("q. Quitter la partie")

            when (readLine()?.trim()?.lowercase()) {
                "1" -> zone.rencontreMonstre(this)
                "2" -> examineEquipe()
                "3" -> {
                    if (zone.zoneSuivante != null) {
                        changerZone(zone.zoneSuivante!!)
                        println("Vous avancez vers ${zone.zoneSuivante}")
                    } else {
                        println("Pas de zone suivante.")
                    }
                }
                "4" -> {
                    if (zone.zonePrecedente != null) {
                        changerZone(zone.zonePrecedente!!)
                        println("Vous retournez vers ${zone.zonePrecedente}")
                    } else {
                        println("Pas de zone précédente.")
                    }
                }
                "q" -> {
                    println("Retour au menu principal.")
                    return
                }
                else -> println("Entrée invalide, veuillez réessayer.")
            }
        }
    }

    // choixStarter() reste inchangée
}
