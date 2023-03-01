package uz.xia.bazar.data

data class Restaurant(
    val closeTime: Int,
    val currentPrice: Double,
    val discount: Int,
    val favorite: Boolean,
    val fileStorage: FileStorage,
    val foodName: String,
    val id: Int,
    val name: String,
    val openTime: Int,
    val restaurantStatus: String,
    val status: String
)