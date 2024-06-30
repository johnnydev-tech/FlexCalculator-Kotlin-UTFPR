package tech.johnnydev.fuelcalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    private lateinit var gasolinePriceInput: EditText
    private lateinit var ethanolPriceInput: EditText
    private lateinit var gasolineConsumptionInput: EditText
    private lateinit var ethanolConsumptionInput: EditText
    private lateinit var switchMode: Switch
    private lateinit var calculateButton: Button
    private lateinit var resultText: TextView
    private lateinit var clearButton: Button
    private lateinit var carListButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        gasolinePriceInput = findViewById(R.id.gasoline_price)
        ethanolPriceInput = findViewById(R.id.ethanol_price)
        gasolineConsumptionInput = findViewById(R.id.gasoline_consumption)
        ethanolConsumptionInput = findViewById(R.id.ethanol_consumption)
        switchMode = findViewById(R.id.switch_mode)
        calculateButton = findViewById(R.id.calculate_button)
        resultText = findViewById(R.id.result_text)
        clearButton = findViewById(R.id.clear_button)
        carListButton = findViewById(R.id.car_button)


        resultText.text = ""


        carListButton.setOnClickListener {
            val intent = Intent(this, CarListActivity::class.java)

            getResult.launch(intent)
        }

        switchMode.setOnClickListener {
            handleSwitchMode()
        }


        calculateButton.setOnClickListener {
            calculateButtonOnClick()
        }

        clearButton.setOnClickListener {
            clearData()
        }


    }

    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                if (it.data != null) {

                    val selectedCar = it.data?.getSerializableExtra("selectedCar") as? CardData
                    selectedCar?.let {
                        gasolineConsumptionInput.setText(it.gasoline.toString())
                        ethanolConsumptionInput.setText(it.ethanol.toString())
                        switchMode.isEnabled = true
                        Toast.makeText(this, "Carro selecionado: ${it.name}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }


    private fun clearData() {
        gasolinePriceInput.text.clear()
        ethanolPriceInput.text.clear()
        gasolineConsumptionInput.text.clear()
        ethanolConsumptionInput.text.clear()
        resultText.text = ""
    }

    private fun calculateButtonOnClick() {
        val gasolinePrice = gasolinePriceInput.text.toString().toDoubleOrNull()
        val ethanolPrice = ethanolPriceInput.text.toString().toDoubleOrNull()

        if (gasolinePrice == null || ethanolPrice == null) {
            resultText.text = getString(R.string.please_enter_valid_prices)
            return
        }

        val fuelCalculator = FuelCalculatorService(gasolinePrice, ethanolPrice)

        val result = if (switchMode.isChecked) {
            val gasolineConsumption = gasolineConsumptionInput.text.toString().toDoubleOrNull()
            val ethanolConsumption = ethanolConsumptionInput.text.toString().toDoubleOrNull()

            if (gasolineConsumption == null || ethanolConsumption == null) {
                resultText.text = getString(R.string.please_enter_valid_consumptions)
                return
            }

            fuelCalculator.bestFuelBasedOnConsumption(gasolineConsumption, ethanolConsumption)
        } else {
            fuelCalculator.bestFuelBasedOnPrice()
        }

        resultText.text = when (result) {
            FuelType.GASOLINE -> getString(R.string.result_label, getString(R.string.gasoline))
            FuelType.ETHANOL -> getString(R.string.result_label, getString(R.string.ethanol))
        }
    }

    private fun handleSwitchMode() {
        if (switchMode.isChecked) {
            gasolineConsumptionInput.visibility = EditText.VISIBLE
            ethanolConsumptionInput.visibility = EditText.VISIBLE
        } else {
            gasolineConsumptionInput.visibility = EditText.GONE
            ethanolConsumptionInput.visibility = EditText.GONE
        }
    }
}