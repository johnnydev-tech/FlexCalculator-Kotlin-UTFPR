package tech.johnnydev.fuelcalculator


enum class FuelType {
    GASOLINE,
    ETHANOL
}

class FuelCalculatorService(
    private val gasolinePrice: Double,
    private val ethanolPrice: Double
) {

    fun bestFuelBasedOnConsumption(
        gasolineConsumption: Double,
        ethanolConsumption: Double
    ): FuelType {
        val costPerKmGasoline = gasolinePrice / gasolineConsumption
        val costPerKmEthanol = ethanolPrice / ethanolConsumption

        return if (costPerKmEthanol < costPerKmGasoline) {
            FuelType.ETHANOL
        } else {
            FuelType.GASOLINE
        }
    }


    fun bestFuelBasedOnPrice(): FuelType {
        val efficiencyThreshold = 0.7

        return if (ethanolPrice / gasolinePrice <= efficiencyThreshold) {
            FuelType.ETHANOL
        } else {
            FuelType.GASOLINE
        }
    }
}