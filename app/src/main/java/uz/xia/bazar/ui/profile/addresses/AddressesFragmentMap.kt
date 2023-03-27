package uz.xia.bazar.ui.profile.addresses

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import uz.xia.bazar.R
import uz.xia.bazar.data.pref.IPreferenceManager
import uz.xia.bazar.data.pref.PreferenceManager
import uz.xia.bazar.databinding.FragmentAddAddressBinding
import uz.xia.bazar.ui.profile.addresses.add.AddAddressViewModel
import uz.xia.bazar.ui.profile.addresses.add.IAddAddressViewModel
import uz.xia.bazar.ui.profile.addresses.add.model.NearbyPlace
import uz.xia.bazar.utils.lazyFast

class AddressesFragmentMap : Fragment(), View.OnClickListener,
    LocationAdapterMap.OnPlaceClickListener {
    private var _binding: FragmentAddAddressBinding? = null
    private val viewModel: IAddAddressViewModel by viewModels<AddAddressViewModel>()
    private val locationAdapter by lazyFast { LocationAdapterMap(this) }
    private val binding get() = _binding!!
    private val navController by lazyFast {
        Navigation.findNavController(
            requireActivity(), R.id.nav_host_fragment_main
        )
    }
    private var addressName: String = ""
    private val preference: IPreferenceManager by lazyFast {
        PreferenceManager(
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(
                requireContext()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpObserver()
    }

    private fun setUpViews() {
        binding.btnAddLocationMap.setOnClickListener(this)
        binding.toolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }
        binding.searchMap.addTextChangedListener {
            if (it != null && it.length > 3) {
                viewModel.searchPlace(it.toString(), "uz")
            } else if (it != null && it.isEmpty()) {
                viewModel.loadNearbyPlaces()
            }
        }
        binding.recyclerAdjustMap.adapter = locationAdapter
        binding.recyclerAdjustMap.setOnClickListener{
            conformAddressDialog()
        }

    }

    private fun setUpObserver() {
        viewModel.livePlaceList.observe(viewLifecycleOwner) {
            if (!binding.recyclerAdjustMap.isVisible) {
                binding.recyclerAdjustMap.visibility = View.VISIBLE
            }
            locationAdapter.submitList(it)
        }
    }
    @SuppressLint("CutPasteId")
    private fun conformAddressDialog() {
        val layout =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_address, null, false)
        val etAddressName = layout.findViewById<AppCompatEditText>(R.id.etAddressName)
        etAddressName.setText(preference.addressName)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Manzilni kiritish")
            .setView(layout)
            .setPositiveButton("Ok", DialogInterface.OnClickListener { d, v ->
                d.dismiss()
                val addressName = etAddressName.text.toString()
                viewModel.saveAddress(addressName)
            })
            .setNegativeButton("Yopish", DialogInterface.OnClickListener { d, v ->
                d.dismiss()
            })
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        navController.navigate(R.id.nav_add_address_map)
    }

    override fun onNearPlaceSearch(place: NearbyPlace) {

    }
}