package uz.xia.bazar.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.xia.bazar.databinding.FragmentSplashBinding
import uz.xia.bazar.ui.MainActivity


class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private var mListener: ILoginListener? = null


    companion object {
        fun newInstance() = SplashFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ILoginListener) {
            mListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed(Runnable {
        mListener?.onToLogin()
        }, 2_000L)
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

}