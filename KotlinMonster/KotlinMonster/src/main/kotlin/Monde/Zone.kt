package org.example.monde

import org.example.monstres.EspeceMonstre

class Zone(
    val id: Int,
    val nom: String,
    val expZone: MutableList<EspeceMonstre>,
    var especesMonstres: MutableList<EspeceMonstre> = mutableListOf(),
    var zoneSuivante: Zone? = null,
    var zonePrecedente: Zone? = null
) {
    // Méthode TODO à compléter plus tard
    fun genererMonstre(): Monstre {
        // à implémenter
        TODO()
    }

    fun rencontreMonstre(): Monstre {
        // à implémenter
        TODO()
    }
}

class Monstre {
}