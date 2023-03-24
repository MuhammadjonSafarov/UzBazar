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
import uz.xia.bazar.ui.home.adapter.RestaurantVerticalAdapter
import uz.xia.bazar.ui.home.banner.BannerFoodAdapter
import uz.xia.bazar.ui.home.banner.BannerMarketAdapter
import uz.xia.bazar.utils.lazyFast
import uz.xia.bazar.utils.toDp

private const val TAG = "HomeFragment"

class HomeFragment : Fragment(), Runnable, CompoundButton.OnCheckedChangeListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val foodPagerAdapter by lazyFast { BannerFoodAdapter(childFragmentManager) }
    private val marketPagerAdapter by lazyFast { BannerMarketAdapter(childFragmentManager) }

    private val mViewModel: IHomeViewModel by viewModels<HomeViewModel>()
    private val mAdapter by lazyFast { CategoryAdapter() }
    private val mAdapterRestaurant by lazyFast { RestaurantAdapter() }
    private val mAdapterRestaurantV by lazyFast { RestaurantVerticalAdapter() }
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
        mViewModel.loadRestaurants()
        mViewModel.loadVerticalRestaurants()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpObservable()
        Log.d(TAG, "onViewCreated")


    }

    private fun setUpViews() {
        binding.bannerPager1.adapter = marketPagerAdapter
        binding.bannerPager2.adapter = foodPagerAdapter
        handler.postDelayed(this, 6_000L)

        /*categories*/
        binding.content.categoriesRv.adapter = mAdapter
        binding.content.categoriesRv.addItemDecoration(itemDecorator)

        /*restaurant*/
        binding.content.restaurantRv.adapter = mAdapterRestaurant
        binding.content.restaurantRvVertical.adapter = mAdapterRestaurantV

        binding.content.buttonBazaar.setOnCheckedChangeListener(this)
        binding.content.buttonFood.setOnCheckedChangeListener(this)
    }

    private fun setUpObservable() {
        mViewModel.liveCatData.observe(viewLifecycleOwner) {
            mAdapter.submitList(it)
        }

        mViewModel.liveRestaurantData.observe(viewLifecycleOwner) {
            mAdapterRestaurant.submitList(it)
        }

        mViewModel.liveRestaurantVData.observe(viewLifecycleOwner) {
            mAdapterRestaurantV.submitList(it)
        }

        mViewModel.liveCatStatus.observe(viewLifecycleOwner) {
            when (it) {
                Status.LOADING -> binding.content.shimmerViewContainer.visibility = View.VISIBLE
                Status.SUCCESS -> {
                    binding.content.categoriesRv.visibility = View.VISIBLE
                    binding.content.shimmerViewContainer.stopShimmerAnimation()
                    binding.content.shimmerViewContainer.visibility = View.GONE
                }
                is Status.ERROR -> binding.content.shimmerViewContainer.visibility = View.GONE
            }
        }
        mViewModel.liveRestaurantStatus.observe(viewLifecycleOwner) {
            when (it) {
                Status.LOADING -> {} /*binding.content.shimmerViewContainer.visibility = View.VISIBLE*/
                Status.SUCCESS -> {
                    /* binding.content.shimmerViewContainer.visibility = View.GONE
                     binding.content.categoriesRv.visibility = View.VISIBLE*/
                }
                is Status.ERROR -> {
                   // Toast.makeText(requireContext(), it.text, Toast.LENGTH_LONG).show()
                } /*binding.content.shimmerViewContainer.visibility = View.GONE*/
            }
        }
    }

    override fun onCheckedChanged(button: CompoundButton?, isChecked: Boolean) {
        if (button?.id == R.id.buttonFood && isChecked) {
            binding.content.restaurantRv.visibility = View.VISIBLE
            binding.content.restaurantRvVertical.visibility=View.VISIBLE
            binding.content.tvRestaurant.visibility=View.VISIBLE

            binding.bannerPager1.visibility=View.GONE
            binding.bannerPager2.visibility=View.VISIBLE
            binding.toolbar.setNavigationIcon(R.drawable.icon_outline_work)
            binding.tvAddress.text=getText(R.string.work)
        } else if (button?.id == R.id.buttonFood && !isChecked) {
            binding.toolbar.setNavigationIcon(R.drawable.icon_outline_home)
            binding.tvAddress.text=getText(R.string.home)
            binding.bannerPager1.visibility=View.VISIBLE
            binding.bannerPager2.visibility=View.GONE

            binding.content.restaurantRv.visibility = View.GONE
            binding.content.restaurantRvVertical.visibility=View.GONE
            binding.content.tvRestaurant.visibility=View.GONE
        }


        if (button?.id == R.id.buttonBazaar && isChecked) {
            binding.content.categoriesRv.visibility=View.VISIBLE
            if (binding.content.shimmerViewContainer.isAnimationStarted)
            binding.content.shimmerViewContainer.visibility=View.VISIBLE
        }else if (button?.id == R.id.buttonBazaar && !isChecked){
            binding.content.categoriesRv.visibility=View.GONE
            binding.content.shimmerViewContainer.visibility=View.GONE
        }
        Log.d(TAG, "isChecked : $isChecked")
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
        if (currentItemPosition == 2) handler.postDelayed(this, 6_000L)
        else handler.postDelayed(this, 4_000L)
    }
}