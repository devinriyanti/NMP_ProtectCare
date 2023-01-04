package id.web.safira.protectcare30

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_check_in.*
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CheckInFragment : Fragment() {
    var location:ArrayList<ListLocation> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/native/160419158/ProtectCare30/get_location.php"

        val stringRequest = object : StringRequest(
            Method.POST,url,Response.Listener {
                Log.e("get_location",it)
                val obj = JSONObject(it)
                if(obj.getString("result")=="ok"){
                    val data = obj.getJSONArray("data")
                    for (i in 0 until data.length()){
                        var playObj= data.getJSONObject(i)
                        with(playObj){
                            var list = ListLocation(
                                getInt("id"),
                                getString("name"),
                                getString("unique_code")
                            )
                            location.add(list)
                        }
                        Log.d("list",location.toString())

                        val list = arrayListOf<String>()
                        list.add("Select a place...")

                        for(i in location){
                            list.add(i.name)
                        }
                        val adapter = activity?.let {
                            it1 -> ArrayAdapter(it1,android.R.layout.simple_list_item_1,list)
                        }
                        spinnerLocation.adapter = adapter
                        checkIn()
                    }
                }
            },
            Response.ErrorListener {
                Log.e("get_location",it.toString())
            }
        )
        {

        }
        q.add(stringRequest)

    }

    private fun checkIn() {
        buttonCheckIn.setOnClickListener {
            var username = Global.getUsername(requireActivity())
            val q2 = Volley.newRequestQueue(activity)
            val url2 = "https://ubaya.fun/native/160419158/ProtectCare30/checkIn.php"

            val stringRequest2=object :StringRequest(
                Method.POST,url2,Response.Listener {
                    Log.e("checkIn",it)
                    val obj2 = JSONObject(it)
                    if(obj2.getString("result") == "ok"){
                        Toast.makeText(activity,"Successful added",Toast.LENGTH_SHORT).show()

                        val intent = Intent(activity,MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(activity,"Code is unregistered",Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener {
                    Log.e("checkIn",it.toString())
                }
            ){
                @RequiresApi(Build.VERSION_CODES.O)

                override fun getParams(): MutableMap<String, String> {

                    val params = HashMap<String,String>()
                    val calendar =  Calendar.getInstance()
                    var format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    var checkinDate = format.format(calendar.time)

                    val valueSpinner= spinnerLocation.selectedItem.toString()

                    params["username"] = username.toString()
                    params["checkInDate"] = checkinDate.toString()
                    params["location"] = valueSpinner
                    params["unique_code"] = textUniqueCode.text.toString()
                    return params
                }
            }
            q2.add(stringRequest2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_in, container, false)
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            CheckInFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}