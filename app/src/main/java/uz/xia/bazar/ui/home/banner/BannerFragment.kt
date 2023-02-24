package uz.xia.bazar.ui.home.banner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.xia.bazar.databinding.FragmentBannerBinding

private const val TAG = "BannerFragment"
class BannerFragment : Fragment() {
    private var _binding: FragmentBannerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resId = arguments?.getInt("KEY_IMAGE_ID") ?: 0
        Log.d(TAG,"onViewCreated $resId")
        binding.ivBanner.setImageResource(resId)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun getInstaince(resId: Int): BannerFragment {
            val bundle = Bundle()
            bundle.putInt("KEY_IMAGE_ID",resId)
            val fragment = BannerFragment()
            fragment.arguments=bundle
            return fragment
        }
    }
}