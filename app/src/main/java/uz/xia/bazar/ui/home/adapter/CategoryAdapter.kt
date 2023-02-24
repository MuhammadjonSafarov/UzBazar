package uz.xia.bazar.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.xia.bazar.common.BASE_URL
import uz.xia.bazar.data.Category
import uz.xia.bazar.databinding.ItemLayoutCategoryBinding

class CategoryAdapter : ListAdapter<Category, CategoryAdapter.CategoryVH>(ItemDiffer) {

    class CategoryVH(binding: ItemLayoutCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        private val ivImage = binding.ivImage
        private val tvName = binding.tvTitle
        fun onBind(model: Category) {
            tvName.text = model.nameRu
         /*   Glide.with(ivImage.context).load("${BASE_URL}preview/${model.fileStorage.hashId}")
                .into(ivImage)*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutCategoryBinding.inflate(layoutInflater, parent, false)
        return CategoryVH(binding)
    }

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        holder.onBind(getItem(position))
    }
}

private val ItemDiffer = object : ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

}