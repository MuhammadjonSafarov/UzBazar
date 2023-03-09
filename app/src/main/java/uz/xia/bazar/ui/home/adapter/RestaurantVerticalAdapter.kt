package uz.xia.bazar.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.xia.bazar.common.BASE_URL
import uz.xia.bazar.data.Category
import uz.xia.bazar.data.Restaurant
import uz.xia.bazar.databinding.ItemLayoutCategoryBinding
import uz.xia.bazar.databinding.ItemLayoutRestaurantBinding

class RestaurantVerticalAdapter : ListAdapter<Restaurant, RestaurantVerticalAdapter.RestaurantVerticalVH>(ItemDiffer) {

    class RestaurantVerticalVH(binding: ItemLayoutRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val ivImage = binding.ivImage
        private val tvName = binding.tvName
        private val tvFoodName = binding.tvFoodName
        private val tvDiscount = binding.tvDiscount
        private val tvService = binding.tvServicetime
        fun onBind(model: Restaurant) {
            tvName.text = model.name
            tvFoodName.text = model.foodName
            tvDiscount.text = "${model.discount}% скидка"
            tvService.text="${(model.currentPrice/100)} so\'m"
            Glide.with(ivImage.context).load("${BASE_URL}preview/${model.fileStorage.hashId}")
                .into(ivImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantVerticalVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutRestaurantBinding.inflate(layoutInflater, parent, false)
        return RestaurantVerticalVH(binding)
    }

    override fun onBindViewHolder(holder: RestaurantVerticalVH, position: Int) {
        holder.onBind(getItem(position))
    }
}

private val ItemDiffer = object : ItemCallback<Restaurant>() {
    override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
        return oldItem == newItem
    }

}