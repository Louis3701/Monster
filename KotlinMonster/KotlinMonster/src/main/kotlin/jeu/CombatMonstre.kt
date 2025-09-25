package org.example.jeu

import org.example.monstres.IndividuMonstre
import kotlin.math.max
import kotlin.random.Random

/**
 * Représente un combat entre un monstre du joueur et un monstre sauvage.
 *
 * @property monstreJoueur Monstre appartenant au joueur (lié à un Entraineur).
 * @property monstreSauvage Monstre sauvage affronté.
 * @property round Compteur de rounds, commence à 1.
 */
class CombatMonstre(
    var monstreJoueur: IndividuMonstre,
    val monstreSauvage: IndividuMonstre
) {
    var round: Int = 1
    private var captureReussie: Boolean = false

    /**
     * Joue un round automatiquement en respectant le diagramme fourni.
     * - Détermine qui est le plus rapide (égalité -> joueur commence).
     * - Affiche l'écran de combat.
     * - Fait jouer l'acteur le plus rapide via actionJoueur()/actionAdversaire().
     * - Si l'action du joueur retourne false, on s'arrête (ex: capture).
     * - Sinon et si la partie n'est pas finie, l'adversaire joue.
     */
    fun jouer() {
        val joueurPlusRapide = monstreJoueur.vitesse >= monstreSauvage.vitesse
        afficherEcranCombat()
        if (joueurPlusRapide) {
            val continuer = actionJoueur()
            if (!continuer) return
            if (!gameOver() && !joueurGagne()) {
                actionAdversaire()
            }
        } else {
            actionAdversaire()
            if (!gameOver()) {
                val continuer = actionJoueur()
                if (!continuer) return
            }
        }
        round++
    }

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

    /**
     * Indique si le joueur a gagné le combat.
     * Deux façons de gagner:
     *  1) Capturer le monstre sauvage.
     *  2) Réduire les PV du monstre sauvage à 0.
     * Le monstre du joueur gagne de l’EXP uniquement dans le cas (2): +20% de l’EXP du sauvage.
     */
    fun joueurGagne(): Boolean {
        if (monstreSauvage.pv <= 0) {
            println("[${monstreJoueur.nom}] a gagné !")
            val gainExp = monstreSauvage.exp * 0.20
            if (gainExp > 0.0) {
                monstreJoueur.gagnerExp(gainExp)
                println("[${monstreJoueur.nom}] gagne ${"%.0f".format(gainExp)} exp")
            }
            return true
        }
        if (captureReussie) {
            println("[${monstreSauvage.nom}] a été capturé !")
            return true
        }
        return false
    }

    /**
     * Pour l’instant, méthode simple:
     * Si les PV du monstre sauvage sont > 0 alors il attaque le monstreJoueur.
     */
    fun actionAdversaire() {
        if (monstreSauvage.pv > 0 && monstreJoueur.pv > 0) {
            val degats = Random.nextInt(3, 11) // 3..10
            monstreJoueur.pv = max(0, monstreJoueur.pv - degats)
            println("=> ${monstreSauvage.nom} attaque et inflige $degats dégâts.")
            println("PV ${monstreJoueur.nom}: ${monstreJoueur.pv}")
        }
    }

    /**
     * Boucle de combat jouable dans le terminal.
     * Actions: 1) Attaquer  2) Capturer  3) Fuir
     */
    fun combattreDansLeTerminal(chanceCaptureBase: Double = 30.0) {
        println("=== Combat lancé: ${monstreJoueur.nom} VS ${monstreSauvage.nom} ===")
        afficherEcranCombat()

        while (true) {
            if (gameOver()) {
                println("=== GAME OVER (le joueur n’a plus de monstre apte au combat) ===")
                return
            }
            if (joueurGagne()) {
                println("=== FIN DU COMBAT: Victoire du joueur ===")
                return
            }

            println()
            println("— Round $round —")
            println("Choisissez une action:")
            println("  1) Attaquer")
            println("  2) Capturer")
            println("  3) Fuir")
            print("> ")
            val saisie = readLine()?.trim()

            when (saisie) {
                "1" -> {
                    actionAttaque(monstreJoueur, monstreSauvage)
                    if (!joueurGagne()) actionAdversaire()
                }
                "2" -> {
                    if (tenterCapture(chanceCaptureBase)) {
                        captureReussie = true
                        // pas d'action adverse si capture réussie
                    } else {
                        println("La capture a échoué !")
                        actionAdversaire()
                    }
                }
                "3" -> {
                    println("Vous prenez la fuite...")
                    println("=== FIN DU COMBAT: Fuite ===")
                    return
                }
                else -> {
                    println("Saisie invalide. Entrez 1, 2 ou 3.")
                    continue
                }
            }

            round++
            afficherEcranCombat()
        }
    }

    /**
     * Effectue l'action du joueur selon le diagramme fourni.
     * Retourne true si le combat doit continuer, false sinon.
     */
    fun actionJoueur(): Boolean {
        // Vérifier l'état de fin de jeu (aucun monstre apte côté joueur)
        if (gameOver()) {
            return false
        }

        // Afficher menu d'actions
        println("Choisissez une action: 1) Attaquer  2) Utiliser un objet  3) Changer de monstre")
        print("> ")
        val choixAction = readLine()?.trim()

        when (choixAction) {
            "1" -> {
                // Attaquer
                actionAttaque(monstreJoueur, monstreSauvage)
                return true
            }
            "2" -> {
                // Utiliser un objet
                val entraineur = monstreJoueur.entraineur
                if (entraineur == null) {
                    println("Aucun entraîneur associé: pas de sac d'objets.")
                    return true
                }

                if (entraineur.sacAItems.isEmpty()) {
                    println("Le sac est vide.")
                    return true
                }

                println("Objets dans le sac:")
                entraineur.sacAItems.forEachIndexed { index, item ->
                    println("  ${'$'}index) ${'$'}{item.nom}")
                }
                print("Choisissez l'index de l'objet > ")
                val indexChoix = readLine()?.toIntOrNull()
                if (indexChoix == null || indexChoix !in entraineur.sacAItems.indices) {
                    println("Choix invalide.")
                    return true
                }
                val objetChoisi = entraineur.sacAItems[indexChoix]

                if (objetChoisi is org.example.item.Utilisable) {
                    val effet = objetChoisi.utiliser(monstreSauvage)
                    // Si c'est une MonsterKube et capture réussie -> fin du combat
                    if (effet && objetChoisi is org.example.item.MonsterKube) {
                        // transfert de propriété et ajout à l'équipe
                        val entraineurJoueur = monstreJoueur.entraineur
                        if (entraineurJoueur != null) {
                            monstreSauvage.entraineur = entraineurJoueur
                            if (!entraineurJoueur.equipeMonstre.contains(monstreSauvage)) {
                                entraineurJoueur.equipeMonstre.add(monstreSauvage)
                            }
                            println("${'$'}{monstreSauvage.nom} rejoint l’équipe de ${'$'}{entraineurJoueur.nom} !")
                        }
                        captureReussie = true
                        return false
                    }
                    // Sinon, on continue le combat
                    return true
                } else {
                    println("Objet non utilisable")
                    return true
                }
            }
            "3" -> {
                // Changer de monstre
                val entraineur = monstreJoueur.entraineur
                if (entraineur == null) {
                    println("Aucun entraîneur associé: impossible de changer de monstre.")
                    return true
                }

                val vivants = entraineur.equipeMonstre
                if (vivants.isEmpty()) {
                    println("Aucun monstre dans l'équipe.")
                    return true
                }

                println("Équipe de monstres (PV > 0):")
                vivants.forEachIndexed { index, m ->
                    println("  ${'$'}index) ${'$'}{m.nom} - PV ${'$'}{m.pv}")
                }
                print("Choisissez l'index du monstre > ")
                val indexChoix = readLine()?.toIntOrNull()
                if (indexChoix == null || indexChoix !in vivants.indices) {
                    println("Choix invalide.")
                    return true
                }
                val choixMonstre = vivants[indexChoix]
                if (choixMonstre.pv <= 0) {
                    println("Impossible ! Ce monstre est KO")
                    return true
                }
                println("${'$'}{choixMonstre.nom} remplace ${'$'}{monstreJoueur.nom}")
                monstreJoueur = choixMonstre
                return true
            }
            else -> {
                println("Saisie invalide. Entrez 1, 2 ou 3.")
                return true
            }
        }
    }

    // ---------------------
    // Helpers privés
    // ---------------------
    private fun actionAttaque(attaquant: IndividuMonstre, defenseur: IndividuMonstre) {
        val degats = Random.nextInt(5, 13) // 5..12
        defenseur.pv = max(0, defenseur.pv - degats)
        println("${attaquant.nom} attaque et inflige $degats dégâts à ${defenseur.nom}.")
    }

    private fun tenterCapture(chanceCaptureBase: Double): Boolean {
        val jet = Random.nextDouble(0.0, 100.0)
        val seuil = chanceCaptureBase.coerceIn(5.0, 95.0)
        val reussite = jet < seuil
        println("Tentative de capture... (jet=${"%.1f".format(jet)} / seuil=${"%.1f".format(seuil)})")
        if (reussite) {
            val entraineurJoueur = monstreJoueur.entraineur
            if (entraineurJoueur != null) {
                // Transfert de propriété et ajout à l’équipe
                monstreSauvage.entraineur = entraineurJoueur
                if (!entraineurJoueur.equipeMonstre.contains(monstreSauvage)) {
                    entraineurJoueur.equipeMonstre.add(monstreSauvage)
                }
                println("${monstreSauvage.nom} rejoint l’équipe de ${entraineurJoueur.nom} !")
            }
        }
        return reussite
    }

    private fun afficherEtat() {
        println("Etat: ${monstreJoueur.nom} [PV=${monstreJoueur.pv}]  |  ${monstreSauvage.nom} [PV=${monstreSauvage.pv}]")
    }

    private fun afficherEcranCombat() {
        println("======= Début Round : $round =======")
        // Infos du monstre sauvage (face)
        println("Niveau : ${monstreSauvage.niveau}")
        println("PV : ${monstreSauvage.pv} / ${monstreSauvage.pvMax}")
        // ASCII Art face à face (dos du joueur à gauche, face du sauvage à droite)
        afficherArtFaceAFace()
        // Infos du monstre du joueur (dos)
        println("Niveau : ${monstreJoueur.niveau}")
        println("PV : ${monstreJoueur.pv} / ${monstreJoueur.pvMax}")
        println("===============================")
    }

    private fun afficherArtFaceAFace() {
        val gauche = runCatching { monstreJoueur.espece.afficheArt(deface = false) }.getOrDefault("")
        val droite = runCatching { monstreSauvage.espece.afficheArt(deface = true) }.getOrDefault("")
        if (gauche.isEmpty() && droite.isEmpty()) return

        println()
        println("${monstreJoueur.nom} (dos)".padEnd(40) + "${monstreSauvage.nom} (face)")
        val gL = gauche.lines()
        val dL = droite.lines()
        val gW = gL.maxOfOrNull { it.length } ?: 0
        val maxL = maxOf(gL.size, dL.size)
        for (i in 0 until maxL) {
            val l = gL.getOrNull(i) ?: ""
            val r = dL.getOrNull(i) ?: ""
            println(l.padEnd(gW + 4) + r)
        }
        println()
    }
}

















