package com.project.easy_travel.ViewModel

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.style.UpdateAppearance
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.project.easy_travel.AdditionalInformationActivity
import com.project.easy_travel.Model.User
import com.project.easy_travel.R
import com.project.easy_travel.remote.UserViewModel


class RegisterActivity : AppCompatActivity() {
    lateinit var userViewModel: UserViewModel

    var user_name: String = ""
    var user_surname: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        registerScreen1()
/*
        findViewById<Button>(R.id.registerButton).setOnClickListener {

            var name = findViewById<TextView>(R.id.name_edttxt).text.toString()
            var surname = findViewById<TextView>(R.id.surname_edttxt).text.toString()
            var email = findViewById<TextView>(R.id.email).text.toString()
            var password = findViewById<TextView>(R.id.password).text.toString()
            var password2 = findViewById<TextView>(R.id.password2).text.toString()

            Log.d("TAG", "onCreate: $password $password2")
            if (password == password2) {
                Log.d("TAG", "onCreate: $email $password")
                register(name, surname, email, password)
            }
        }

 */

    }

    private fun registerScreen1()
    {
        setContentView(R.layout.activity_register_1)
        var field_name = findViewById<TextView>(R.id.name_edttxt)
        var field_surname = findViewById<TextView>(R.id.surname_edttxt)

        field_name.text = user_name
        field_surname.text = user_surname

        var next_button = findViewById<Button>(R.id.next_btn_reg)

        fun updateButtonState() {
            val text1 = field_name.text.toString()
            val text2 = field_surname.text.toString()

            val text1_not_contains_number = text1.matches(".*\\d.*".toRegex())
            val text1_not_contains_symbol = text1.matches(".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*".toRegex())
            val text2_not_contains_number = text2.matches(".*\\d.*".toRegex())
            val text2_not_contains_symbol = text2.matches(".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*".toRegex())

            if (text1.isNotBlank() && text2.isNotBlank() && !text1_not_contains_number && !text1_not_contains_symbol && !text2_not_contains_number && !text2_not_contains_symbol) {
                next_button.alpha = 1.0f
            } else {
                next_button.alpha = 0.5f
            }
        }

        updateButtonState()

        field_name.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {;}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {;}

            override fun afterTextChanged(p0: Editable?) {
                updateButtonState()
            }
        })

        field_surname.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {;}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {;}

            override fun afterTextChanged(p0: Editable?) {
                updateButtonState()
            }
        })


        next_button.setOnClickListener {
            // if we have spaces in name or surname in the end or in the beginning, remove them
            field_name.text = field_name.text.toString().trim()
            field_surname.text = field_surname.text.toString().trim()

            //make first letter of name and surname uppercase
            field_name.text = field_name.text.toString().capitalize()
            field_surname.text = field_surname.text.toString().capitalize()


            val text1_not_contains_number = field_name.text.toString().matches(".*\\d.*".toRegex())
            val text1_not_contains_symbol = field_name.text.toString().matches(".*[!@#$%.,/^&*()_+=|<>?{}\\[\\]~-].*".toRegex())
            val text2_not_contains_number = field_surname.text.toString().matches(".*\\d.*".toRegex())
            val text2_not_contains_symbol = field_surname.text.toString().matches(".*[!@#$%.,/^&*()_+=|<>?{}\\[\\]~-].*".toRegex())
            val text2_not_contains_space = field_surname.text.toString().matches(".*\\s.*".toRegex())

            // Validate data
            if (field_name.text.toString().isEmpty()) {
                field_name.error = "Wprowadź imię"
                field_name.requestFocus()
                return@setOnClickListener
            }
            if (text1_not_contains_number) {
                field_name.error = "Imię nie może zawierać cyfr"
                field_name.requestFocus()
                return@setOnClickListener
            }
            if (text1_not_contains_symbol) {
                field_name.error = "Imię nie może zawierać znaków specjalnych"
                field_name.requestFocus()
                return@setOnClickListener
            }
            if (field_surname.text.toString().isEmpty()) {
                field_surname.error = "Wprowadź nazwisko"
                field_surname.requestFocus()
                return@setOnClickListener
            }
            if (text2_not_contains_number) {
                field_surname.error = "Nazwisko nie może zawierać cyfr"
                field_surname.requestFocus()
                return@setOnClickListener
            }
            if (text2_not_contains_symbol) {
                field_surname.error = "Nazwisko nie może zawierać znaków specjalnych"
                field_surname.requestFocus()
                return@setOnClickListener
            }
            if (text2_not_contains_space) {
                field_surname.error = "Nazwisko nie może zawierać spacji"
                field_surname.requestFocus()
                return@setOnClickListener
            }
            user_name = field_name.text.toString()
            user_surname = field_surname.text.toString()

            registerScreen2()

        }

    }

    private fun registerScreen2()
    {
        setContentView(R.layout.activity_register_2)

        // List of strings
        val register_email : List<String> = listOf()

        var email_txt = findViewById<TextView>(R.id.email)
        var password_txt = findViewById<TextView>(R.id.password)
        var password2_txt = findViewById<TextView>(R.id.password2)

        val register_button = findViewById<Button>(R.id.finn_btn_reg)

        fun updateButtonState() {
            val text1 = email_txt.text.toString()
            val text2 = password_txt.text.toString()
            val text3 = password2_txt.text.toString()

            // if text1 have register_email
            if (text1.isNotBlank() && text2.isNotBlank() && text3.isNotBlank() && text2 == text3 && text1.contains("@") && text2.length >= 8 && !register_email.contains(text1)) {
                register_button.alpha = 1.0f
            } else {
                register_button.alpha = 0.5f
            }
        }

        email_txt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {;}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {;}

            override fun afterTextChanged(p0: Editable?) {
                updateButtonState()
            }
        })

        password_txt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {;}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {;}

            override fun afterTextChanged(p0: Editable?) {
                updateButtonState()
            }
        })

        password2_txt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {;}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {;}

            override fun afterTextChanged(p0: Editable?) {
                updateButtonState()
            }
        })



        register_button.setOnClickListener{
            email_txt.text = email_txt.text.toString().trim()

            // Validate data
            if (email_txt.text.toString().isEmpty()) {
                email_txt.error = "Wprowadź email"
                email_txt.requestFocus()
                return@setOnClickListener
            }
            if (email_txt.text.toString().contains("@") == false) {
                email_txt.error = "Wprowadź poprawny email"
                email_txt.requestFocus()
                return@setOnClickListener
            }
            if (register_email.contains(email_txt.text.toString())) {
                email_txt.error = "Email jest już zarejestrowany"
                email_txt.requestFocus()
                return@setOnClickListener
            }
            if (password_txt.text.toString().isEmpty()) {
                password_txt.error = "Wprowadź hasło"
                password_txt.requestFocus()
                return@setOnClickListener
            }
            if (password_txt.text.toString().length < 8) {
                password_txt.error = "Hasło musi mieć co najmniej 8 znaków"
                password_txt.requestFocus()
                return@setOnClickListener
            }
            if (password2_txt.text.toString().isEmpty()) {
                password2_txt.error = "Wprowadź hasło"
                password2_txt.requestFocus()
                return@setOnClickListener
            }
            if (password_txt.text.toString() != password2_txt.text.toString()) {
                password2_txt.error = "Hasła nie są takie same"
                password2_txt.requestFocus()
                return@setOnClickListener
            }

            register(user_name, user_surname, email_txt.text.toString(), password_txt.text.toString(), email_txt, register_button, register_email)
        }

        findViewById<Button>(R.id.prev_btn_reg).setOnClickListener{
            registerScreen1()
        }
    }

    fun register(name: String, surname: String, email: String, password: String, email_txt: TextView, register_button: Button, register_email: List<String>) {
        var auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {

                val newUser = User(name=name, surname = surname, email = email)
                userViewModel.save(newUser, replaceDotsWithEmail(email))

                this.finish()
            } else {
                email_txt.error = "Email jest już zarejestrowany"
                register_email.plus(email_txt.text.toString())
                register_button.alpha = 0.5f
                email_txt.requestFocus()
            }
        }
    }

    fun replaceDotsWithEmail(email: String): String {
        return email.replace(".", "_")
    }



}