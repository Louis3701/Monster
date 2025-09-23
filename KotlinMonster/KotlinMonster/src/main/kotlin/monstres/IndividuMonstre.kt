package org.example.monstres

import Entraineur
import kotlin.random.Random

class IndividuMonstre(
    val id: Int,
    var nom: String,
    // expInit = expérience initiale (Double)
    var expInit: Double,
    val espece: EspeceMonstre,
    var entraineur: Entraineur? = null
) {
    // Potentiel aléatoire (0.5 .. 2.0) si tu n'en fournis pas
    val potentiel: Double = Random.nextDouble(0.5, 2.0)

    // EXP courante (on initialise depuis expInit)
    var exp: Double = expInit
        private set

    // Niveau (déterminé par l'exp)
    var niveau: Int = 1
        private set

    // Stats de base (avec petite variation aléatoire)
    var attaque: Int = espece.baseAttaque + Random.nextInt(-2, 3)
    var defense: Int = espece.baseDefense + Random.nextInt(-2, 3)
    var vitesse: Int = espece.baseVitesse + Random.nextInt(-2, 3)
    var attaqueSpe: Int = espece.baseAttaque + Random.nextInt(-2, 3)
    var defenseSpe: Int = espece.baseDefense + Random.nextInt(-2, 3)

    // PV max et actuels (pv est borné dans son setter)
    var pvMax: Int = (espece.basePV * potentiel).toInt() + 5
    var pv: Int = pvMax
        set(value) {
            field = when {
                value < 0 -> 0
                value > pvMax -> pvMax
                else -> value
            }
        }

    init {
        // Calculer le niveau initial en fonction de expInit (si expInit est élevé)
        while (exp >= palierExp(niveau + 1)) {
            niveau++
            // si on veut appliquer des gains de stats pour niveaux initiaux
            // on peut appeler levelUpProgressif() ou appliquer un petit boost ici :
            // mais pour l'instant on n'applique que l'incrément de niveau.
        }
    }

    // calcule l'expérience (totale) nécessaire pour atteindre un niveau donné
    fun palierExp(niveau: Int): Int {
        // formule fournie : 100 * (niveau - 1)^2
        return (100 * Math.pow(((niveau - 1).toDouble()), 2.0)).toInt()
    }

    // levelUp : incrémente le niveau et augmente les stats
    fun levelUp() {
        niveau++

        // Augmentation des statistiques selon modificateurs et potentiel + hasard
        attaque += (espece.modificateurAttaque * potentiel).toInt() + Random.nextInt(-2, 3)
        defense += (espece.modificateurDefense * potentiel).toInt() + Random.nextInt(-2, 3)
        vitesse += (espece.modificateurVitesse * potentiel).toInt() + Random.nextInt(-2, 3)
        attaqueSpe += (espece.modificateurAttaque * potentiel).toInt() + Random.nextInt(-2, 3)
        defenseSpe += (espece.modificateurDefense * potentiel).toInt() + Random.nextInt(-2, 3)

        // Mise à jour des PV
        pvMax += (espece.modificateurPV * potentiel).toInt() + Random.nextInt(5, 11)
        pv = pvMax
    }

    // ajouter de l'exp et gérer les level up successifs
    fun gagnerExp(qte: Double) {
        if (qte <= 0) return
        exp += qte
        // tant que l'exp dépasse le palier suivant, level up
        while (exp >= palierExp(niveau + 1)) {
            levelUp()
        }
    }

    // Méthode d'affichage intégrée (tu peux appeler monstre.afficheDetail())
    fun afficheDetail(coteACote: Boolean = true) {
        // Récupération de l'art ASCII en protégeant en cas d'erreur fichier absent
        val art: String = try {
            espece.afficheArt(deface = true)
        } catch (e: Exception) {
            // si afficheArt lève une exception (fichier manquant), retourne une chaîne vide
            ""
        }

        val artLines = if (art.isEmpty()) listOf<String>() else art.lines()

        val details = buildList {
            add("===============")
            add("Nom : $nom    Niveau : $niveau")
            add("Exp : ${"%.0f".format(exp)}")
            add("PV : $pv / $pvMax")
            add("")
            add("Atk : $attaque   Def : $defense   Vit : $vitesse")
            add("AtkSpe : $attaqueSpe   DefSpe : $defenseSpe")
            add("Espèce : ${espece.nom}")
            add("===============")
        }

        if (!coteACote || artLines.isEmpty()) {
            // affichage linéaire (d'abord détails puis art)
            details.forEach { println(it) }
            if (artLines.isNotEmpty()) {
                println()
                artLines.forEach { println(it) }
            }
            return
        }

        // affichage côte-à-côte : art | details
        val artWidth = artLines.maxOfOrNull { it.length } ?: 0
        val maxLines = maxOf(artLines.size, details.size)

        for (i in 0 until maxLines) {
            val artLine = artLines.getOrNull(i) ?: ""
            val detailLine = details.getOrNull(i) ?: ""
            println(artLine.padEnd(artWidth + 4) + detailLine)
        }
    }

    // petite utilitaire pour tests
    fun afficherInfosCourtes() {
        println("$nom (Lv $niveau) - PV $pv/$pvMax - Atk $attaque - Def $defense - Vit $vitesse")
    }
}





















