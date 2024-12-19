package de.mlex.spacefckrs.data

import java.util.UUID

interface USO {
    val id: UUID
}

data class Alien(
    val type: Int = 0,
    var life: Int = 0,
    override val id: UUID = UUID.randomUUID()
) : USO

data class DestroyedOne(
    override val id: UUID = UUID.randomUUID()
) : USO

data class JustSpace(
    override val id: UUID = UUID.randomUUID()
) : USO

data class JustScrap(
    override val id: UUID = UUID.randomUUID()
) : USO


