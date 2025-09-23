package org.example.item

import Entraineur

/**
 * Un badge est un type d'Item particulier, possédé après une victoire d'arène.
 *
 * Héritage:
 * - id, nom, description proviennent de Item et sont passés au constructeur parent.
 * Spécifique:
 * - champion: l'entraîneur qui remet ce badge.
 */
class Badge(
    id: Int,
    nom: String,
    description: String,
    var champion: Entraineur
) : Item(id, nom, description)
