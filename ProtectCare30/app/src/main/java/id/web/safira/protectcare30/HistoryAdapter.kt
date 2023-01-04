package id.web.safira.protectcare30

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat.setBackgroundTintList
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import kotlinx.android.synthetic.main.card_history.view.*

class HistoryAdapter (val context: Context, val historyAdpt: ArrayList<History>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    val username = Global.getUsername(context)

    class HistoryViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.card_history, parent, false)

        return HistoryViewHolder(v)
    }

    override fun getItemCount() = historyAdpt.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyList = historyAdpt[position]
        with(holder.view) {
            txtLocationName.text = historyList.namelocation
            txtCheckIn.text = historyList.checkintime
            if(historyList.checkouttime == "null"){
                txtCheckOut.text = "Not yet checked out"
            }else{
                txtCheckOut.text = historyList.checkouttime
            }

            //Color Theme
            R.color.design_default_color_surface
            if(historyList.doses_vaccine == 1) {
                cardView.setCardBackgroundColor(Color.YELLOW)
            }else if(historyList.doses_vaccine >= 2){
                cardView.setCardBackgroundColor(Color.GREEN)
            }else if(historyList.doses_vaccine == 0){
                cardView.setCardBackgroundColor(Color.RED)
            }
        }

    }
}