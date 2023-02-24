package uz.xia.bazar.ui.home.banner

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import uz.xia.bazar.R

class BannerFoodAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val mList = listOf(
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner3
    )
    override fun getCount(): Int = mList.size

    override fun getItem(position: Int): Fragment{
        return BannerFragment.getInstaince(mList[position])
    }
}