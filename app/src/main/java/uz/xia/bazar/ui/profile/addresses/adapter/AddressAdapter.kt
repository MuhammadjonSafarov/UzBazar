package uz.xia.bazar.ui.profile.addresses.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.xia.bazar.R
import uz.xia.bazar.data.local.entity.AddressType
import uz.xia.bazar.data.local.entity.UserAddress
import uz.xia.bazar.databinding.ItemAddressBinding

class AddressAdapter(
) : ListAdapter<UserAddress, AddressAdapter.AddressVH>(ItemDiffer){

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
        private val icon=binding.ivHome
        fun onBind(userAddress: UserAddress){
            when(userAddress.type){
                AddressType.HOME-> {
                    icon.setImageResource(R.drawable.icon_filled_home_addresses)
                  // Todo home icon vs home title
                }
                AddressType.WORK-> {
                    icon.setImageResource(R.drawable.icon_filled_job)
                    // Todo work icon vs work title
                }
                else -> {
                    icon.setImageResource(R.drawable.icon_filled_adresses_location)
                    // Todo other icon vs other title
                }

            }
            tvName.text=userAddress.name
        }
    }
}
private val ItemDiffer=object :ItemCallback<UserAddress>(){
    override fun areItemsTheSame(oldItem: UserAddress, newItem: UserAddress): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: UserAddress, newItem: UserAddress): Boolean {
        return oldItem==newItem
    }

}
