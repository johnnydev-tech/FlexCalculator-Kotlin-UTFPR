package tech.johnnydev.fuelcalculator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CarListActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private val carList = listOf(
        CardData("Bravo", 10.5, 7.0),
        CardData("Pulse", 12.0, 9.5),

    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_car_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listView = findViewById(R.id.listView)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, carList.map { it.name })
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedCar = carList[position]
            val intent = Intent().apply {
                putExtra("selectedCar", selectedCar)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}