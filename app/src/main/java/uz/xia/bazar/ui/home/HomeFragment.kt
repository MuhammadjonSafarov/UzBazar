package uz.xia.bazar.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import uz.xia.bazar.R
import uz.xia.bazar.common.Status
import uz.xia.bazar.databinding.FragmentHomeBinding
import uz.xia.bazar.ui.home.adapter.CategoryAdapter
import uz.xia.bazar.ui.home.adapter.CategoryItemDecoration
import uz.xia.bazar.ui.home.adapter.RestaurantAdapter
import uz.xia.bazar.ui.home.banner.BannerMarketAdapter
import uz.xia.bazar.utils.lazyFast
import uz.xia.bazar.utils.toDp

private const val TAG = "HomeFragment"

class HomeFragment : Fragment(), Runnable, CompoundButton.OnCheckedChangeListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //private val foodPagerAdapter by lazyFast { BannerFoodAdapter(childFragmentManager) }
    private val marketPagerAdapter by lazyFast { BannerMarketAdapter(childFragmentManager) }

    private val mViewModel: IHomeViewModel by viewModels<HomeViewModel>()
    private val mAdapter by lazyFast { CategoryAdapter() }
    private val mAdapterRestaurant by lazyFast { RestaurantAdapter() }
    private val itemDecorator by lazyFast { CategoryItemDecoration(32.toDp(), 12.toDp()) }
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        mViewModel.loadCategories()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
        setUpObservable()
        Log.d(TAG, "onViewCreated")
        binding.bannerPager1.adapter = marketPagerAdapter
        //  binding.bannerPager2.adapter = marketPagerAdapter
        handler.postDelayed(this, 6_000L)

        /*categories*/
        binding.content.categoriesRv.adapter = mAdapter
        binding.content.categoriesRv.addItemDecoration(itemDecorator)

        /*restaurant*/
        binding.content.restaurantRv.adapter=mAdapterRestaurant

        binding.content.buttonBazaar.setOnCheckedChangeListener(this)
        binding.content.buttonFood.setOnCheckedChangeListener(this)


    }

    private fun setUpObservable() {

        mViewModel.liveData.observe(viewLifecycleOwner) {
            mAdapter.submitList(it)
        }

        mViewModel.liveRestaurantData.observe(viewLifecycleOwner) {
            mAdapterRestaurant.submitList(it)
        }
        mViewModel.liveStatus.observe(viewLifecycleOwner) {
            when (it) {
                Status.LOADING -> binding.content.shimmerViewContainer.visibility = View.VISIBLE
                Status.SUCCESS -> {
                    binding.content.shimmerViewContainer.visibility = View.GONE
                    binding.content.categoriesRv.visibility = View.VISIBLE
                }
                is Status.ERROR -> binding.content.shimmerViewContainer.visibility = View.GONE
            }
        }
        mViewModel.liveRestaurantStatus.observe(viewLifecycleOwner) {
            when (it) {
                Status.LOADING -> {}/*binding.content.shimmerViewContainer.visibility = View.VISIBLE*/
                Status.SUCCESS -> {
                   /* binding.content.shimmerViewContainer.visibility = View.GONE
                    binding.content.categoriesRv.visibility = View.VISIBLE*/
                }
                is Status.ERROR ->{
                    Toast.makeText(requireContext(),it.text,Toast.LENGTH_LONG).show()
                } /*binding.content.shimmerViewContainer.visibility = View.GONE*/
            }
        }
    }

    private fun setUpViews() {

    }

    override fun onResume() {
        super.onResume()
        binding.content.shimmerViewContainer.startShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
        binding.content.shimmerViewContainer.stopShimmerAnimation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        handler.removeCallbacks(this)
    }

    override fun run() {
        val currentItemPosition = binding.bannerPager1.currentItem
        binding.bannerPager1.setCurrentItem((currentItemPosition + 1) % 3, true)
        if (currentItemPosition == 2)
            handler.postDelayed(this, 6_000L)
        else handler.postDelayed(this, 4_000L)
    }

    override fun onCheckedChanged(button: CompoundButton?, isChecked: Boolean) {
        if (button?.id == R.id.buttonFood && isChecked) {
            mViewModel.loadRestaurants()
        }
        Log.d(TAG,"isChecked : $isChecked")
    }

}