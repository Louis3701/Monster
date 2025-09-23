package org.example.item

import org.example.monstres.IndividuMonstre
import kotlin.random.Random

/**
 * Un MonsterKube est un sous-type d'Item utilisé pour capturer des monstres.
 *
 * chanceCapture est la probabilité de base en pourcentage (ex: 30.0 = 30%).
 * La probabilité effective est ajustée selon les PV restants de la cible.
 */
class MonsterKube(
    id: Int,
    nom: String,
    description: String,
    var chanceCapture: Double
) : Item(id, nom, description), Utilisable {

    /**
     * Version "complexe" d'utilisation:
     * - ratioVie = pv / pvMax (entre 0.0 et 1.0)
     * - chanceEffective = chanceCapture * (1.5 - ratioVie)
     * - Toujours au moins 5% de chance, et plafonné à 95% pour éviter les certitudes.
     */
    override fun utiliser(cible: IndividuMonstre): Boolean {
        println("Vous lancez $nom sur ${cible.nom} !")

        val ratioVie = if (cible.pvMax <= 0) 1.0 else cible.pv.toDouble() / cible.pvMax.toDouble()
        var chanceEffective = chanceCapture * (1.5 - ratioVie)
        chanceEffective = chanceEffective.coerceAtLeast(5.0).coerceAtMost(95.0)

        val tirage = Random.nextDouble(0.0, 100.0)
        println("PV: ${cible.pv}/${cible.pvMax} | Chance: ${"%.1f".format(chanceEffective)}% | Tirage: ${"%.1f".format(tirage)}%")

        val capture = tirage < chanceEffective
        if (capture) {
            println("Bravo ! ${cible.nom} est capturé !")
        } else {
            println("${cible.nom} s'échappe...")
        }
        return capture
    }
}
