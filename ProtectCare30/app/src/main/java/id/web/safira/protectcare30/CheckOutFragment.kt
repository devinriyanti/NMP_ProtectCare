package id.web.safira.protectcare30

import android.content.Intent
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
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_check_out.*
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class CheckOutFragment : Fragment() {
    var listHistory:ArrayList<ListHistory> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonCheckOut.setOnClickListener {
            var username = Global.getUsername(requireActivity())
            val q = Volley.newRequestQueue(activity)
            val url = "https://ubaya.fun/native/160419158/ProtectCare30/checkOut.php"

            val stringRequest = object :StringRequest(
                Method.POST,url,Response.Listener {
                    Log.e("checkout", it)
                    val obj = JSONObject(it)
                    if(obj.getString("result")=="ok"){
                        Toast.makeText(activity,"Check Out Success",Toast.LENGTH_SHORT).show()
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(activity, "Check Out Failed", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener {
                    Log.e("checkout",it.toString())
                }
            ){
                @RequiresApi(Build.VERSION_CODES.O)
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    val calendar =  Calendar.getInstance()
                    var format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    var checkoutDate = format.format(calendar.time)

                    params["username"] = username.toString()
                    params["checkOut"] = checkoutDate.toString()
                    return params
                }
            }
            q.add(stringRequest)
        }
        getData()
    }

    private fun getData() {
        var username = Global.getUsername(requireActivity())
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/native/160419158/ProtectCare30/status.php"

        val stringRequest = object :StringRequest(
            Method.POST,url,Response.Listener {
                Log.e("checkout",it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "ok"){
                    val data = obj.getJSONArray("data")
                    for(i in 0 until data.length()){
                        var playObj = data.getJSONObject(i)
                        with(playObj){
                            var list = ListHistory(
                                getString("username"),
                                getString("name"),
                                getString("checkin"),
                                getString("checkout"),
                                getInt("doses_of_vaccine")
                            )
                            listHistory.add(list)
                        }
                        Log.d("history",listHistory.toString())
                        for(i in listHistory){
                            textLocation.text = i.location.toString()
                            textCheckInTime.text = i.checkIn.toString()

                            if(i.doses_vaccine == 0){
                                cardCheckOut.setCardBackgroundColor(Color.RED)
                            }else if(i.doses_vaccine == 1){
                                cardCheckOut.setCardBackgroundColor(Color.YELLOW)
                            }else if(i.doses_vaccine >= 2){
                                cardCheckOut.setCardBackgroundColor(Color.GREEN)
                            }
                        }
                    }
                }
            },
            Response.ErrorListener {
                Log.e("checkout",it.toString())
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username.toString()
                return params
            }
        }
        q.add(stringRequest)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_out, container, false)
    }

    companion object {
        fun newInstance() =
            CheckOutFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}