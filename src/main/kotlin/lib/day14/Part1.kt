package io.cloudtamer.adventofcode.lib.day14

typealias Symbol = String
typealias Coefficient = Int
typealias Stage = Map<Symbol, Coefficient>
typealias MutableStage = MutableMap<Symbol, Coefficient>

data class Component(
    val coefficient: Coefficient,
    val symbol: Symbol
)
data class SymbolTransform(val inputs: List<Component>, val output: Component) {
    fun decomposeFrom(component: Component): List<Component> {
        require(component.coefficient % output.coefficient == 0)
        val numOutputsNeeded = component.coefficient / output.coefficient
        return inputs.map { Component(it.coefficient * numOutputsNeeded, it.symbol)}
    }

    override fun toString(): String {
        val inputString = inputs.map { "${it.coefficient} ${it.symbol}" }.joinToString(", ")
        val outputString = "${output.coefficient} ${output.symbol}"

        return "$inputString => $outputString"
    }
}

fun parseTransforms(rawInputs: List<String>): Map<Symbol, SymbolTransform> {
    val resultingTransformMap = mutableMapOf<Symbol, SymbolTransform>()
    fun stringToComponent(inputStr: String): Component {
        val (coefficientStr, symbol) = inputStr.split(" ")
        return Component(coefficientStr.toInt(), symbol)
    }

    for (input in rawInputs) {
        val (inputString, outputString) = input.split(" => ")
        val outputComponent = stringToComponent(outputString)
        val inputComponentStrs = inputString.split(", ")
        val inputComponents = inputComponentStrs.map(::stringToComponent)
        val transform = SymbolTransform(inputComponents, outputComponent)

        resultingTransformMap += outputComponent.symbol to transform
    }

    return resultingTransformMap
}

fun getOresRequiredToMakeFuelWithWaste(
    availableTransforms: Map<Symbol, SymbolTransform>,
    previousWaste: Map<Symbol, Coefficient> = emptyMap()
): Int {
    var currentStage: Stage =  mapOf("FUEL" to 1)
    val wastebin = previousWaste.toMutableMap()

    while (currentStage.keys != setOf("ORE")) {
        val currentComponents = currentStage.entries.map { Component(it.value, it.key) }
        val nextStage: MutableStage = mutableMapOf()

        for (component in currentComponents) {
            // Ore cannot be decomposed
            if (component.symbol == "ORE") {
                nextStage["ORE"] = component.coefficient + nextStage.getOrDefault("ORE", 0)
                continue
            }

            // Find the function that transforms simpler components into this component
            val componentTransform = availableTransforms[component.symbol] ?: error("No transform for ${component.symbol}!!")
            // Find the actual amount of this component necessary
            val amountRequired = if (component.coefficient % componentTransform.output.coefficient == 0) {
                component.coefficient
            } else {
                val trueCoefficient = nextHighestMultipleOf(factor = componentTransform.output.coefficient, from = component.coefficient)
                // Record wasted resource
                wastebin[component.symbol] = (trueCoefficient - component.coefficient) + wastebin.getOrDefault(component.symbol, 0)
                trueCoefficient
            }
            // Decompose the actual amount necessary
            val componentToProduce = Component(amountRequired, component.symbol)
            val decomposedComponent = componentTransform.decomposeFrom(componentToProduce)

            for (newComponent in decomposedComponent) {
                nextStage[newComponent.symbol] = newComponent.coefficient + nextStage.getOrDefault(newComponent.symbol, 0)
            }
        }

        currentStage = nextStage
    }

    val oreExpended = currentStage["ORE"] ?: error("Somehow there was no ore at the end.")
    return oreExpended - calculateWastedOre(availableTransforms, wastebin)
}

fun calculateWastedOre(availableTransforms: Map<Symbol, SymbolTransform>, wastebin: Map<Symbol, Coefficient>): Int {
    var wastebinState = wastebin

    while(true) {
        val newWastebin = mutableMapOf<Symbol, Coefficient>()
        var performedReduction = false

        for ((symbol, coefficient) in wastebinState) {
            if (symbol == "ORE") {
                newWastebin["ORE"] = coefficient
                continue
            }
            val transform = availableTransforms[symbol] ?: error("Transform for symbol $symbol didn't exist!")

            // If we wasted enough to perform the transform in reverse, we could have not expended it in the first place. Decompose.
            if (coefficient >= transform.output.coefficient) {
                val amountNotExpended = coefficient % transform.output.coefficient
                val amountExpended = coefficient - amountNotExpended
                val decomposedComponent = transform.decomposeFrom(Component(amountExpended, symbol))

                for ((producedCoefficient, producedSymbol) in decomposedComponent) {
                    newWastebin[producedSymbol] = newWastebin.getOrDefault(producedSymbol, 0) + producedCoefficient
                }
                if (amountNotExpended > 0) {
                    newWastebin[symbol] = newWastebin.getOrDefault(symbol, 0) + amountNotExpended
                }
                performedReduction = true
            } else {
                // Otherwise, just add in the symbol to what's already there. Could be used later.
                newWastebin[symbol] = newWastebin.getOrDefault(symbol, 0) + coefficient
            }
        }

        // Swap the wastebin state. If we didn't decompose anything we're done.
        wastebinState = newWastebin
        if (!performedReduction) break
    }

    return wastebinState["ORE"] ?: 0
}

fun nextHighestMultipleOf(factor: Int, from: Int) = from + (factor - (from % factor))