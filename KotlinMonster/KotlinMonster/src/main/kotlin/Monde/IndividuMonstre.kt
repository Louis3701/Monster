package org.example.monstres

import Entraineur
import kotlin.random.Random

class IndividuMonstre(
    val id: Int,
    val nom: String,
    val espece: EspeceMonstre,
    var entraineur: Entraineur? = null,
    val potentiel: Double = Random.nextDouble(0.5, 2.0) // nombre aléatoire entre 0.5 et 2
) {
    // Niveau par défaut
    var niveau: Int = 1

    // Statistiques initiales avec une petite variation aléatoire
    var attaque: Int = espece.baseAttaque + Random.nextInt(-2, 3)
    var defense: Int = espece.baseDefense + Random.nextInt(-2, 3)
    var vitesse: Int = espece.baseVitesse + Random.nextInt(-2, 3)
    var attaqueSpe: Int = espece.baseAttaque + Random.nextInt(-2, 3)
    var defenseSpe: Int = espece.baseDefense + Random.nextInt(-2, 3)

    // Points de vie max et courants
    var pvMax: Int = (espece.basePV * potentiel).toInt() + 5

    // Getter / Setter de pv : jamais inférieur à 0 et jamais supérieur à pvMax
    var pv: Int = pvMax
        get() = field
        set(nouveauPv) {
            field = when {
                nouveauPv < 0 -> 0
                nouveauPv > pvMax -> pvMax
                else -> nouveauPv
            }
        }

    // Fonction pour calculer l'XP nécessaire pour atteindre un niveau donné
    fun palierExp(niveau: Int): Int {
        return (100 * Math.pow(((niveau - 1).toDouble()), 2.0)).toInt()
    }

    // Fonction level up (gain de niveau + augmentation des stats)
    fun levelUp() {
        niveau++

        // Augmentation des statistiques
        attaque += (espece.modificateurAttaque * potentiel).toInt() + Random.nextInt(-2, 3)
        defense += (espece.modificateurDefense * potentiel).toInt() + Random.nextInt(-2, 3)
        vitesse += (espece.modificateurVitesse * potentiel).toInt() + Random.nextInt(-2, 3)
        attaqueSpe += (espece.modificateurAttaque * potentiel).toInt() + Random.nextInt(-2, 3)
        defenseSpe += (espece.modificateurDefense * potentiel).toInt() + Random.nextInt(-2, 3)

        // Mise à jour des PV
        pvMax += (espece.modificateurPV * potentiel).toInt() + Random.nextInt(5, 10)
        pv = pvMax
    }

    // Petite fonction utilitaire pour afficher les infos du monstre
    fun afficherInfos() {
        println("==== ${nom} (Individu de ${espece.nom}) ====")
        println("Niveau : $niveau")
        println("PV : $pv / $pvMax")
        println("Attaque : $attaque | Défense : $defense")
        println("Vitesse : $vitesse")
        println("Att. Spé : $attaqueSpe | Déf. Spé : $defenseSpe")
    }
}
