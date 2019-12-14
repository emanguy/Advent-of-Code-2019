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
}

fun generateTransforms(rawInputs: List<String>): Map<Symbol, SymbolTransform> {
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

fun getOresRequiredToMakeFuel(availableTransforms: Map<Symbol, SymbolTransform>): Int {
    var currentStage: Stage =  mapOf("FUEL" to 1)
    val wastebin = mutableMapOf<Symbol, Coefficient>()

    while (currentStage.keys != setOf("ORE")) {
        val currentComponents = currentStage.entries.map { Component(it.value, it.key) }
        val nextStage: MutableStage = mutableMapOf()

        for (component in currentComponents) {
            // Ore cannot be decomposed
            if (component.symbol == "ORE") {
                nextStage["ORE"] = component.coefficient + nextStage.getOrDefault("ORE", 0)
                continue
            }
            // If we have enough of this component in the wastebin, we can safely cancel it out
            if (component.coefficient <= wastebin.getOrDefault(component.symbol, 0)) {
                val amountInWastebin = wastebin[component.symbol] ?: error("Component coefficient less than 0: $component")
                wastebin[component.symbol] = amountInWastebin - component.coefficient
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

    return currentStage["ORE"] ?: error("Somehow there was no ore at the end.")
}

fun nextHighestMultipleOf(factor: Int, from: Int) = from + (factor - (from % factor))