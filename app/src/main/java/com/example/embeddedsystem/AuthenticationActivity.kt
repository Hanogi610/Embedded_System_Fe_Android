package com.example.embeddedsystem

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.embeddedsystem.model.House
import com.example.embeddedsystem.service.HouseApi
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthenticationActivity : AppCompatActivity() {
    lateinit var emailEditText : TextInputEditText
    lateinit var passwordEditText : TextInputEditText
    lateinit var loginButton : Button
    lateinit var responsedHouse : Response<House>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        loginButton.setOnClickListener(View.OnClickListener{
            val house = House(
                username = emailEditText.text.toString(),
                password = passwordEditText.text.toString()
            )
            lifecycleScope.launch(Dispatchers.IO) {
                responsedHouse = HouseApi.retrofitService.login(house)
                if(responsedHouse.isSuccessful) {
                    val intent = Intent(this@AuthenticationActivity, MainActivity::class.java)
                    intent.putExtras(Bundle().apply {
                        responsedHouse.body()?.id?.let { it1 -> putInt("houseId", it1) }
                    })
                    startActivity(intent)
                }else {
                    Toast.makeText(this@AuthenticationActivity, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}