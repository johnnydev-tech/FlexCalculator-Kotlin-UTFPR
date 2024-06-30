package tech.johnnydev.fuelcalculator

import java.io.Serializable

data class CardData(
    val name: String,
    val gasoline: Double,
    val ethanol: Double
) : Serializable