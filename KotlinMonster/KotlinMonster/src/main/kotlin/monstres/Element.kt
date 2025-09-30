package monstre

class Element(
    val id: Int,
    val nom: String
) {
    val forces: MutableSet<Element> = mutableSetOf()
    val faiblesses: MutableSet<Element> = mutableSetOf()
    val immunises: MutableSet<Element> = mutableSetOf()

    fun ajouterForce(element: Element) {
        forces.add(element) // ajout auto sans doublon
    }

    fun ajouterFaiblesse(element: Element) {
        faiblesses.add(element)
    }

    fun ajouterImmunite(element: Element) {
        immunises.add(element)

    }
}
