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
        val lc = nom.lowercase()
        val fileName = if (deface) "front.txt" else "back.txt"
        val flatName = if (deface) "${lc}_front.txt" else "${lc}_back.txt"

        // Ordre d’essai: (classpath) art/<lc>_front.txt, art/<lc>/front.txt, puis (fichier) équivalents
        val classpathCandidates = listOf(
            "/art/$flatName",
            "/art/$lc/$fileName",
            "art/$flatName",
            "art/$lc/$fileName"
        )

        // 1) Tentative via le classpath (packagé dans resources)
        for (path in classpathCandidates) {
            val stream = this::class.java.getResourceAsStream(path)
                ?: Thread.currentThread().contextClassLoader.getResourceAsStream(path.removePrefix("/"))
            val text = stream?.bufferedReader(Charsets.UTF_8)?.use { it.readText() }
            if (text != null) return text
        }

        // 2) Repli: chemins sur disque (utile en exécution depuis l’IDE)
        val fileCandidates = listOf(
            "src/main/resources/art/$flatName",
            "src/main/resources/art/$lc/$fileName"
        )
        for (p in fileCandidates) {
            val text = runCatching { java.io.File(p).readText(Charsets.UTF_8) }.getOrNull()
            if (text != null) return text
        }

        return "(art introuvable: $flatName ou $lc/$fileName)"
    }
    
    }

