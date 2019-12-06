package io.cloudtamer.adventofcode.lib.day6

fun orbitalTransfersBetweenYouAndSanta(orbits: List<PlanetInOrbit>): Int {
    val orbitMap = orbits.associateBy { it.planetName }

    val you = orbitMap["YOU"] ?: throw IllegalStateException("You're not in the universe!")
    val yourPlanet = orbitMap[you.orbiting] ?: throw IllegalStateException("Your planet doesn't exist!")
    val santa = orbitMap["SAN"] ?: throw IllegalStateException("Santa's not in the universe!")
    val santasPlanet = orbitMap[santa.orbiting] ?: throw IllegalStateException("Santa's not around a planet!")

    val yourPlanetsOrbitPath = getOrbitPath(orbitMap, yourPlanet).toMutableList()
    println("Your orbit path: $yourPlanetsOrbitPath")
    val santasPlanetOrbitPath = getOrbitPath(orbitMap, santasPlanet).toMutableList()
    println("Santa's orbit path: $santasPlanetOrbitPath")

    while (yourPlanetsOrbitPath[0] == santasPlanetOrbitPath[0]) {
        yourPlanetsOrbitPath.removeAt(0)
        santasPlanetOrbitPath.removeAt(0)
    }

    return yourPlanetsOrbitPath.size + santasPlanetOrbitPath.size
}

fun getOrbitPath(orbitMap: Map<String, PlanetInOrbit>, origin: PlanetInOrbit): List<String> {
    val path = mutableListOf(origin.planetName)
    var nextPlanet = orbitMap[origin.orbiting]

    while (nextPlanet != null) {
        path.add(0, nextPlanet.planetName)
        nextPlanet = orbitMap[nextPlanet.orbiting]
    }

    return path
}