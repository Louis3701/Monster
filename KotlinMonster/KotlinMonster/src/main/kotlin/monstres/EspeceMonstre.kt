package org.example.monstres

/**
 * La classe [EspeceMonstre] représente une espèce de monstre (par exemple : Pikachu, Bulbizarre, etc.).
 * Elle définit les caractéristiques générales de l’espèce, et non celles d’un individu précis.
 *
 * Chaque propriété correspond à une donnée issue du cahier des charges (base de stats, modificateurs, etc.).
 *
 * @property id Identifiant unique de l’espèce de monstre.
 * @property nom Nom de l’espèce (exemple : "Pikachu").
 * @property description Brève description de l’espèce.
 *
 * @property basePV Points de vie de base de l’espèce.
 * @property baseAttaque Valeur d’attaque de base.
 * @property baseDefense Valeur de défense de base.
 * @property baseVitesse Vitesse de base.
 *
 * @property modificateurPV Facteur multiplicatif appliqué aux PV lors des calculs.
 * @property modificateurAttaque Facteur multiplicatif appliqué à l’attaque.
 * @property modificateurDefense Facteur multiplicatif appliqué à la défense.
 * @property modificateurVitesse Facteur multiplicatif appliqué à la vitesse.
 *
 * @property modPV Modificateur additionnel appliqué aux PV (exemple : bonus d’évolution).
 * @property modAtt Modificateur additionnel appliqué à l’attaque.
 * @property modDef Modificateur additionnel appliqué à la défense.
 * @property modVit Modificateur additionnel appliqué à la vitesse.
 *
 * @property evolution Nom de l’espèce vers laquelle ce monstre peut évoluer (chaîne vide si pas d’évolution).
 * @property particularites Texte libre décrivant les particularités de l’espèce (exemple : "résistant à l’électricité").
 * @property caracteres Caractères spéciaux liés à l’espèce (exemple : tempérament, rareté).
 */
class EspeceMonstre(
    val id: Int,
    val nom: String,
    val description: String,
    val basePV: Int,
    val baseAttaque: Int,
    val baseDefense: Int,
    val baseVitesse: Int,
    val modificateurPV: Double,
    val modificateurAttaque: Double,
    val modificateurDefense: Double,
    val modificateurVitesse: Double,
    val modPV: Double,
    val modAtt: Double,
    val modDef: Double,
    val modVit: Double,
    val evolution: String = "",
    val particularites: String = "",
    val caracteres: String = ""
) {

    /**
     * Affiche l’art ASCII de l’espèce de monstre.
     *
     * @param deface true pour afficher la face avant, false pour la face arrière.
     * @return Une chaîne contenant le dessin ASCII du monstre.
     */
    fun afficheArt(deface: Boolean = true): String {
        val nomFichier = if (deface) "${nom.lowercase()}_front.txt" else "${nom.lowercase()}_back.txt"
        val art = java.io.File("src/main/resources/art/$nomFichier").readText()
        return art.replace("/", "⧸").replace("\u000B", "\u001B")
    }
}
