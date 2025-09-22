package org.example.monde
import Entraineur
import org.example.monstres.EspeceMonstre
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

    // Statistiques de combat, calculées avec une petite variation aléatoire
    val attaque: Int = espece.baseAttaque + Random.nextInt(-2, 3)
    val defense: Int = espece.baseDefense + Random.nextInt(-2, 3)
    val vitesse: Int = espece.baseVitesse + Random.nextInt(-2, 3)

    val attaqueSpe: Int = espece.baseAttaque + Random.nextInt(-2, 3) // à ajuster si tu veux un calcul différent
    val defenseSpe: Int = espece.baseDefense + Random.nextInt(-2, 3)

    // Points de vie max et courants
    val pvMax: Int = (espece.basePV * potentiel).toInt() + 5
    var pv: Int = pvMax
}