package uz.xia.bazar.ui.profile.addresses.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.xia.bazar.R
import uz.xia.bazar.data.local.entity.Address
import uz.xia.bazar.databinding.ItemAddressBinding
import uz.xia.bazar.databinding.ItemNearbyCityBinding
import uz.xia.bazar.ui.profile.addresses.add.model.NearEmpty
import uz.xia.bazar.ui.profile.addresses.add.model.NearLoading
import uz.xia.bazar.ui.profile.addresses.add.model.NearbyPlace

class AddressAdapter(
) : ListAdapter<Address, AddressAdapter.AddressVH>(ItemDiffer){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressVH {
        val layout=LayoutInflater.from(parent.context)
        val binding=ItemAddressBinding.inflate(layout,parent,false)
        return AddressVH(binding)
    }

    override fun onBindViewHolder(holder: AddressVH, position: Int) {
        holder.onBind(getItem(position))
    }

    class AddressVH(binding: ItemAddressBinding):RecyclerView.ViewHolder(binding.root){
        private val tvName=binding.tvName
        fun onBind(address: Address){
            tvName.text=address.name
        }
    }
}
private val ItemDiffer=object :ItemCallback<Address>(){
    override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem==newItem
    }

}
