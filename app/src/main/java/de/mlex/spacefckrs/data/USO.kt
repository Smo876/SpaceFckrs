package de.mlex.spacefckrs.data

//TODO: without these dates
interface USO {
    val id: Int
}

data class Alien(
    val type: Int = 0,
    var life: Int = 0,
    override val id: Int = 0
) : USO

data class JustSpace(
    override val id: Int = 0
) : USO

data class JustScrap(
    override val id: Int = 0
) : USO


