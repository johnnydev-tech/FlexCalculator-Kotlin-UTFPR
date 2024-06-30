package tech.johnnydev.fuelcalculator

import org.junit.Test

import org.junit.Assert.*


class FuelCalculatorServiceTest {
    @Test
    fun testBestFuelBasedOnConsumptionEthanol() {
        val fuelCalculator = FuelCalculatorService(gasolinePrice = 5.0, ethanolPrice = 3.5)
        val result = fuelCalculator.bestFuelBasedOnConsumption(gasolineConsumption = 10.0, ethanolConsumption = 8.0)
        assertEquals(FuelType.ETHANOL, result)
    }

    @Test
    fun testBestFuelBasedOnConsumptionGasoline() {
        val fuelCalculator = FuelCalculatorService(gasolinePrice = 5.0, ethanolPrice = 4.5)
        val result = fuelCalculator.bestFuelBasedOnConsumption(gasolineConsumption = 10.0, ethanolConsumption = 7.0)
        assertEquals(FuelType.GASOLINE, result)
    }

    @Test
    fun testBestFuelBasedOnPriceEthanol() {
        val fuelCalculator = FuelCalculatorService(gasolinePrice = 5.0, ethanolPrice = 3.0)
        val result = fuelCalculator.bestFuelBasedOnPrice()
        assertEquals(FuelType.ETHANOL, result)
    }

    @Test
    fun testBestFuelBasedOnPriceGasoline() {
        val fuelCalculator = FuelCalculatorService(gasolinePrice = 5.0, ethanolPrice = 4.0)
        val result = fuelCalculator.bestFuelBasedOnPrice()
        assertEquals(FuelType.GASOLINE, result)
    }
}