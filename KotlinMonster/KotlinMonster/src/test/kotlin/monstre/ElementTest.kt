package monstre

import org.example.eau
import org.example.feu
import org.example.insecte
import org.example.normal
import org.example.plante
import org.example.roche
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ElementTest {

    @BeforeTest
    fun valorisation() {
        // 🔥 Feu
        feu.forces.addAll(listOf(plante, insecte))
        feu.faiblesses.addAll(listOf(eau, roche, feu))

        // 🌱 Plante
        plante.forces.addAll(listOf(eau, roche))
        plante.faiblesses.addAll(listOf(feu, insecte))

        // 💧 Eau
        eau.forces.addAll(listOf(feu, roche))
        eau.faiblesses.addAll(listOf(plante))

        // 🐞 Insecte
        insecte.forces.addAll(listOf(plante))
        insecte.faiblesses.addAll(listOf(feu, roche))

        // 🪨 Roche
        roche.forces.addAll(listOf(feu, insecte))
        roche.faiblesses.addAll(listOf(eau, plante))

        // ⚪ Normal
        normal.faiblesses.add(roche)
    }

    @Test
    fun efficaciteContre() {
        // 🔥 Feu contre Normal -> attendu 1.0
        assertEquals(1.0, feu.efficaciteContre(normal))

        // 🔥 Feu contre Plante -> attendu 2.0
        assertEquals(2.0, feu.efficaciteContre(plante))

        // 🔥 Feu contre Feu -> attendu 0.5
        assertEquals(0.5, feu.efficaciteContre(feu))

        // 🔥 Feu contre Eau -> attendu 0.5
        assertEquals(0.5, feu.efficaciteContre(eau))

        // 🐞 Insecte contre Plante -> attendu 2.0
        assertEquals(2.0, insecte.efficaciteContre(plante))
    }
}


