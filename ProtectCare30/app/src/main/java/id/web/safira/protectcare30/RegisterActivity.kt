package id.web.safira.protectcare30

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        buttonCancel.setOnClickListener {
            finish()
        }
        var pwdCheck = false;
        btnRegis.setOnClickListener {

            val username = EditTextUsername.text.toString()
            val password = EditTextPwd.text.toString()
            val repeatPwd = EditTextRepeatPwd.text.toString()
            val name = EditTextName.text.toString()
            if(username.isNotEmpty() && password.isNotEmpty() && repeatPwd.isNotEmpty()
                && name.isNotEmpty())
                if (password == repeatPwd){
                    pwdCheck = true
                }
                else{
                    AlertDialog.Builder(this).apply {
                        setMessage("Password didn't match")
                        setPositiveButton("Ok" ,null)
                        create().show()
                    }
                }
                if(pwdCheck == true){
                    val queue = Volley.newRequestQueue(this)
                    val url = "https://ubaya.fun/native/160419158/ProtectCare30/register.php"
                    val stringRequest = object : StringRequest(
                        Method.POST,url,Response.Listener {
                            Log.e("UserRegister" , it)
                            val obj = JSONObject(it)
                            if(obj.getString("result")=="ok"){
                                AlertDialog.Builder(this).apply {
                                    setMessage("Success Register")
                                    setPositiveButton("OK") { _, _->
                                        this@RegisterActivity.finish()
                                    }
                                    create().show()
                                }
                            }
                            else{
                                AlertDialog.Builder(this).apply {
                                    setTitle("Failed Register")
                                    setMessage("Username is already taken")
                                    setPositiveButton("OK",null)
                                    create().show()
                                }
                            }
                        },
                        Response.ErrorListener {
                            Log.e("UserRegister" , it.toString())
                        }
                    ){
                        override fun getParams(): MutableMap<String, String> {
                            val params = HashMap<String,String>()
                            params["username"] = username
                            params["password"] = password
                            params["full_name"] = name
                            return params
                        }
                    }
                    queue.add(stringRequest)
                }
            else{
                Toast.makeText(this,"Make sure all column are filled",Toast.LENGTH_SHORT).show()
            }
        }
    }
}