package uz.xia.bazar.ui.profile.addresses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import uz.xia.bazar.R
import uz.xia.bazar.databinding.FragmentAddressesBinding
import uz.xia.bazar.ui.profile.addresses.adapter.AddressAdapter
import uz.xia.bazar.utils.lazyFast

class AddressesFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentAddressesBinding? = null
    private val binding get() = _binding!!
    private val navController by lazyFast {
        Navigation.findNavController(
            requireActivity(), R.id.nav_host_fragment_main
        )
    }
    private val viewModel by viewModels<AddressViewModel>()
    private val addressAdapter by lazyFast { AddressAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddressesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvAddresses.adapter=addressAdapter
        binding.addHomeLocation.setOnClickListener(this)
        binding.toolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }
      /*  viewModel.loadAddress().observe(viewLifecycleOwner){
            addressAdapter.submitList(it)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        navController.navigate(R.id.nav_add_address_map)
    }
}