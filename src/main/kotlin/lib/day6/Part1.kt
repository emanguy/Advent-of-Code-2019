package io.cloudtamer.adventofcode.lib.day6

data class PlanetInOrbit(val orbiting: String, val planetName: String)

fun getTotalOrbitCount(planetsInOrbit: List<PlanetInOrbit>): Int {
    val orbitMap = planetsInOrbit.associateBy { it.planetName }

    return orbitMap.keys.fold(0) { totalOrbits, planetName -> totalOrbits + getTotalIndirectAndDirectOrbits(planetName, orbitMap) }
}

fun getTotalIndirectAndDirectOrbits(planet: String, hierarchy: Map<String, PlanetInOrbit>): Int {
    val nextOrbit = hierarchy[planet]
    return if (nextOrbit == null) 0 else 1 + getTotalIndirectAndDirectOrbits(nextOrbit.orbiting, hierarchy)
}

