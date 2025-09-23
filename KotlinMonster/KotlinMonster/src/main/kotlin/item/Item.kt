package org.example.item

/**
 * Représente un objet simple du jeu.
 *
 * @property id Identifiant unique de l'objet.
 * @property nom Nom de l'objet.
 * @property description Description courte de l'objet.
 */
open class Item(
    val id: Int,
    val nom: String,
    val description: String
)
