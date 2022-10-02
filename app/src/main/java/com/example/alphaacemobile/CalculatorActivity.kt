package com.example.alphaacemobile
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.alphaacemobile.databinding.ActivityCalculatorBinding
import com.zues.searchable_spinner.SearchableSpinner

class CalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Back
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);

        supportActionBar!!.title = "hi"

        binding.tvShow.setOnClickListener {
            val channelID = "1000"
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val builder = NotificationCompat.Builder(applicationContext, channelID)
                .setSmallIcon(R.drawable.ic_coin)
                .setContentTitle("My Notification")
                .setContentText("Notification Body")

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(channelID, "Custom Notification", NotificationManager.IMPORTANCE_DEFAULT)

                channel.enableLights(true)
                channel.enableVibration(true)
                notificationManager.createNotificationChannel(channel)
                builder.setChannelId(channelID)
            }
            val notification = builder.build()

            notificationManager.notify(1000, notification)
        }


        binding.apply {

            var click = 0
            llCalculatorAdd1.visibility = View.GONE
            llCalculatorAdd2.visibility = View.GONE
            btnCalculatorCalculate.visibility = View.GONE

            ibCalculatorAdd.setOnClickListener {
                click++
                when (click) {
                    0 -> {
                        rlCalculatorEmpty.visibility = View.VISIBLE
                    }
                    1 -> {
                        rlCalculatorEmpty.visibility = View.GONE
                        llCalculatorAdd1.visibility = View.VISIBLE
                        btnCalculatorCalculate.visibility = View.VISIBLE
                    }
                    2 -> {
                        llCalculatorAdd2.visibility = View.VISIBLE
                    }
                    else -> {
                        rlCalculatorEmpty.visibility = View.VISIBLE
                        llCalculatorAdd1.visibility = View.GONE
                        llCalculatorAdd2.visibility = View.GONE
                        btnCalculatorCalculate.visibility = View.GONE
                    }
                }
            }

            etCalculatorEnter1.text.toString()

            spinner.setItems(getListOfItems()) // Any type of list
            spinner.setOnItemSelectListener(object : SearchableSpinner.SearchableItemListener {
                override fun onItemSelected(view: View?, position: Int) {
                    Toast.makeText(this@CalculatorActivity, "${spinner.mSelectedItem}", Toast.LENGTH_SHORT).show()
                }

                override fun onSelectionClear() {
                    Toast.makeText(this@CalculatorActivity, "Clear", Toast.LENGTH_SHORT).show()
                }
            })
            // if you want to select an item programatically
            //spinner.setSelection(xyz) // Any type of list item





            btnCalculatorCalculate.setOnClickListener {

                setupCalculation()

            }
        }
    }

    private fun getListOfItems(): List<Any>? {
        return listOf(
            "ETH",
            "ERC",
            "RAM",
            "PEJ",
            "SHI",
            "HI",
            "HELLO",
            "PRO",
            "LOW",
            "FLO"
        )
    }

    private fun setupCalculation() {
        binding.apply {
            // Calculator Add 1
            var amount1 = etCalculatorEnter1.text.toString().toDouble()
            var result1: Double = 0.00
            if (etCalculatorEnter1.text.isNotEmpty()){
                if (spinner.mSelectedItem.toString() == "ETH"){
                    result1 = amount1 * 10
                }
            } else {
                result1 = amount1 * 0
            }

            // Calculator Add 2
            var amount2 = etCalculatorEnter1.text.toString().toDouble()
            var result2: Double = 0.00
            if (etCalculatorEnter2.text.isNotEmpty()){
                if (spinner2.selectedItem.toString() == "ETH"){
                    result2 = amount2 * 10
                }
            } else {
                result2 = amount2 * 0
            }


            // Result Table
            tvCalculatorTokenAmount.text = (((result1 + result2)/1).toString())
            tvCalculatorNewZealandAmount.text = (((result1 + result2)/1).toString())
            tvCalculatorPhilippinesAmount.text = (((result1 + result2)/1).toString())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}