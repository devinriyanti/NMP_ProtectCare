package id.web.safira.protectcare30

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.util.Log
import androidx.core.content.edit
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (Global.getUsername(this) != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_login)
        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        btnSignIn.setOnClickListener {
            val username = editInputUsername.text.toString()
            val password = editInputPwd.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val queue = Volley.newRequestQueue(this.applicationContext)
                val url = "https://ubaya.fun/native/160419158/ProtectCare30/login.php"
                val stringRequest = object : StringRequest(
                    Method.POST,
                    url,
                    Response.Listener {
                        Log.e("LoginActivity", it)
                        if (it.contains("Success")) {
                            AlertDialog.Builder(this).apply {
                                getSharedPreferences(Global.SHARED_PREFERENCES, Activity.MODE_PRIVATE).edit {
                                    putString(Global.SHARED_PREF_KEY_USERNAME, username)
                                }
                                setMessage("Login Success. Welcome $username!")
                                setPositiveButton("Ok") { _, _ ->
                                    finish()
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                }
                                create().show()
                            }
                        } else {
                            AlertDialog.Builder(this).apply {
                                val message = SpannableString("Login Failed \nWrong Username or Password")
                                message.setSpan(
                                    AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                    0,
                                    message.length,
                                    0
                                )
                                setMessage(message)
                                setPositiveButton("Ok", null)
                                create().show()
                            }
                        }

                    },
                    Response.ErrorListener {
                        Log.e("LoginActivity", it.toString())
                    }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        params["username"] = username
                        params["password"] = password
                        return params
                    }
                }
                queue.add(stringRequest)
            } else {
                AlertDialog.Builder(this).apply {
                    setMessage("Username or password can't be emptied.")
                    setPositiveButton("Ok", null)
                    create().show()
                }
            }
        }
    }
}