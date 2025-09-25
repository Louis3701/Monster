package org.example.jeu

import org.example.jeu.CombatMonstre
import Entraineur
import org.example.monde.Zone
import org.example.monstres.IndividuMonstre
import org.example.monstres.EspeceMonstre

class Partie(
    var id: Int,
    var joueur: Entraineur,
    var zone: Zone
) {
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

            val reponseBrute = readLine()
            val reponse = reponseBrute?.trim() ?: ""
            when (reponse.lowercase()) {
                "" -> println("Entrée vide, veuillez réessayer.")
                "q" -> return
                "m" -> {
                    modifierOrdreEquipe()
                }
                else -> {
                    val numero = reponse.toIntOrNull()
                    if (numero == null) {
                        println("Entrée invalide: tapez un numéro, 'm' ou 'q'.")
                    } else {
                        val equipeSize = joueur.equipeMonstre.size
                        if (numero !in 1..equipeSize) {
                            println("Numéro hors limites. Choisissez entre 1 et $equipeSize.")
                        } else {
                            val monstre = joueur.equipeMonstre[numero - 1]
                            println("=== Détail du monstre #$numero ===")
                            monstre.afficheDetail()
                            // Après affichage du détail, on reste dans la boucle.
                        }
                    }
                }
            }
        }
    }
    /**
     * Permet d'inverser la position de deux monstres dans l'équipe du joueur.
     * Identique au comportement demandé pour Entraineur.modifierOrdreEquipe(),
     * mais exposé au niveau de la Partie pour un accès pratique côté jeu.
     */
    fun modifierOrdreEquipe() {
        val equipe = joueur.equipeMonstre
        if (equipe.size < 2) {
            println("Impossible de modifier l'ordre: il faut au moins 2 monstres dans l'équipe.")
            return
        }
        println("=== Équipe actuelle ===")
        equipe.forEachIndexed { index, monstre ->
            println("${index + 1}. ${monstre.nom} (PV: ${monstre.pv})")
        }
        fun lirePosition(message: String): Int? {
            println(message)
            val input = readLine()?.trim()
            val pos = input?.toIntOrNull()
            if (pos == null) {
                println("Entrée invalide: veuillez saisir un nombre.")
                return null
            }
            if (pos !in 1..equipe.size) {
                println("Position hors limites. Doit être comprise entre 1 et ${equipe.size}.")
                return null
            }
            return pos
        }
        val posA = lirePosition("Entrez la position du monstre à déplacer (1..${equipe.size}) :") ?: return
        val posB = lirePosition("Entrez la nouvelle position (1..${equipe.size}) :") ?: return
        if (posA == posB) {
            println("Les positions sont identiques: aucun changement effectué.")
            return
        }
        val i = posA - 1
        val j = posB - 1
        val tmp = equipe[i]
        equipe[i] = equipe[j]
        equipe[j] = tmp
        println("Ordre modifié: ${posA} <-> ${posB}")
        println("=== Nouvelle équipe ===")
        equipe.forEachIndexed { index, monstre ->
            println("${index + 1}. ${monstre.nom} (PV: ${monstre.pv})")
        }
    }
    // 1) Retourne le premier monstre apte au combat (PV > 0), sinon null.
    fun monstreActif(): IndividuMonstre? =
        joueur.equipeMonstre.firstOrNull { it.pv > 0 }

    // 2) Démarre un combat contre un monstre sauvage donné.
    //    Gère le cas où aucun monstre du joueur n'est disponible.
    fun demarrerCombat(monstreSauvage: IndividuMonstre) {
        val actif = monstreActif()
        if (actif == null) {
            println("Aucun monstre apte au combat.")
            return
        }
        val combat = CombatMonstre(actif, monstreSauvage)
        combat.lancerCombat()
    }

    // 3) Change la zone courante.
    fun changerZone(nouvelleZone: Zone) {
        zone = nouvelleZone
    }

    // 4) Affiche un état simple de la partie (sans hypothèse forte sur Zone).
    fun afficherEtat() {
        println("Partie #$id")
        joueur.afficheDetail()
        println("Zone actuelle: $zone")
    }

    // 5) Propose 3 starters au joueur, en ajoute un à l'équipe et confirme son appartenance.
    fun choixStarter() {
        // Création des espèces de départ
        val springleaf = EspeceMonstre(
            id = 101,
            nom = "Springleaf",
            description = "Un starter Plante équilibré et robuste.",
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
            evolution = "",
            particularites = "",
            caracteres = ""
        )
        val flamkip = EspeceMonstre(
            id = 102,
            nom = "Flamkip",
            description = "Un starter Feu offensif et rapide.",
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
            evolution = "",
            particularites = "",
            caracteres = ""
        )
        val aquamy = EspeceMonstre(
            id = 103,
            nom = "Aquamy",
            description = "Un starter Eau polyvalent.",
            basePV = 65,
            baseAttaque = 8,
            baseDefense = 10,
            baseVitesse = 9,
            modificateurPV = 7.0,
            modificateurAttaque = 7.5,
            modificateurDefense = 7.5,
            modificateurVitesse = 6.5,
            modPV = 16.0,
            modAtt = 9.0,
            modDef = 8.0,
            modVit = 7.0,
            evolution = "",
            particularites = "",
            caracteres = ""
        )

        // Création des individus (appartiennent au joueur)
        val monstre1 = IndividuMonstre(id = 1, nom = "Springleaf", espece = springleaf, entraineur = joueur, expInit = 0.0)
        val monstre2 = IndividuMonstre(id = 2, nom = "Flamkip", espece = flamkip, entraineur = joueur, expInit = 0.0)
        val monstre3 = IndividuMonstre(id = 3, nom = "Aquamy", espece = aquamy, entraineur = joueur, expInit = 0.0)
        val starters = listOf(monstre1, monstre2, monstre3)

        // Boucle de choix
        while (true) {
            println("=== Choix du starter ===")
            println("1/ Springleaf"); monstre1.afficheDetail()
            println("2/ Flamkip");    monstre2.afficheDetail()
            println("3/ Aquamy");     monstre3.afficheDetail()
            println("Choisissez votre starter (1/2/3) :")
            val choix = readLine()?.trim()?.toIntOrNull()
            if (choix != null && choix in 1..3) {
                val starter = starters[choix - 1]
                println("Vous avez choisi ${starter.nom}")
                // Renommer si souhaité
                print("Voulez-vous renommer ${'$'}{starter.nom} ? (o/N) > ")
                val rep = readLine()?.trim()?.lowercase()
                if (rep == "o" || rep == "oui") {
                    print("Nouveau nom > ")
                    val nv = readLine()?.trim()
                    if (!nv.isNullOrEmpty()) starter.nom = nv
                }
                // Ajouter à l'équipe du joueur
                joueur.equipeMonstre.add(starter)
                println("${starter.nom} rejoint l'équipe du joueur !")
                break
            } else {
                println("Choix invalide, veuillez recommencer.")
            }
        }
    }
}