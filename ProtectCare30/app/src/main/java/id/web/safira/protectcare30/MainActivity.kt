package id.web.safira.protectcare30

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    val fragments : ArrayList<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        status()
    }

    private fun status() {
        var username = Global.getUsername(this)
        var listHistory:ArrayList<ListHistory> = ArrayList()
        val q = Volley.newRequestQueue(this)
        val url = "https://ubaya.fun/native/160419158/ProtectCare30/status.php"

        val stringRequest = object :StringRequest(
            Method.POST,url,Response.Listener {
                Log.e("checkIn",it)
                val obj = JSONObject(it)
                if(obj.getString("result")=="ok"){
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
                        Log.d("status",listHistory.toString())
                        for (i in listHistory){
                            if(i.checkOut == "null"){
                                fragments.add(CheckOutFragment())
                                fragments.add(HistoryFragment())
                                fragments.add(ProfileFragment())
                                addfragment()
                            }else{
                                fragments.add(CheckInFragment())
                                fragments.add(HistoryFragment())
                                fragments.add(ProfileFragment())
                                addfragment()
                            }
                        }
                    }
                }else{
                    fragments.add(CheckInFragment())
                    fragments.add(HistoryFragment())
                    fragments.add(ProfileFragment())
                    addfragment()
                }
            },
            Response.ErrorListener {
                Log.e("checkIn",it.toString())
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String,String>()
                params["username"] = username.toString()
                return params
            }
        }
        q.add(stringRequest)
    }

    private fun addfragment() {
        viewPager.adapter = MyAdapter(this,fragments)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                val menu = arrayOf(R.id.itemHome, R.id.itemHistory,R.id.itemProfile)
                bottomNav.selectedItemId = menu[position]
                bottomNav.selectedItemId = bottomNav.menu.getItem(position).itemId
            }
        })
        bottomNav.setOnNavigationItemSelectedListener {
            viewPager.currentItem = when(it.itemId){
                R.id.itemHome -> 0
                R.id.itemHistory -> 1
                R.id.itemProfile -> 2
                else -> 0
            }
            true
        }
    }


}