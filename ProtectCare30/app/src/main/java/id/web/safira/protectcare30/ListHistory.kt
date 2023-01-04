package id.web.safira.protectcare30

data class ListHistory(
    val username:String,
    val location:String,
    val checkIn:String,
    val checkOut:String,
    val doses_vaccine:Int
    )
