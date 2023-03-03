package uz.xia.bazar.ui.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import io.reactivex.disposables.CompositeDisposable
import uz.xia.bazar.databinding.FragmentLoginBinding


private const val TAG = "LoginFragment"
const val NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
const val CHANNEL_ID = "CHANNEL_ID"

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val cd = CompositeDisposable()
    private var mListener: ILoginListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ILoginListener) {
            mListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    companion object {
        fun newInstance() = LoginFragment()
    }

    private val maskedListener=object: MaskedTextChangedListener.ValueListener{
        override fun onTextChanged(
            maskFilled: Boolean,
            extractedValue: String,
            formattedValue: String
        ) {
            binding.button.isEnabled=maskFilled
            Log.d(TAG,"extractedValue:$extractedValue  formattedValue:$formattedValue")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val affineFormats: MutableList<String> = ArrayList()
        affineFormats.add("([00])[000]-[00]-[00]")

        val listener: MaskedTextChangedListener = MaskedTextChangedListener.installOn(
            binding.phone,
            "([00])[000]-[00]-[00]",
            affineFormats, AffinityCalculationStrategy.PREFIX, maskedListener
        )
        binding.phone.hint = listener.placeholder()
        binding.button.setOnClickListener {
            mListener?.onToSmsConform()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (!cd.isDisposed) {
            cd.dispose()
        }
    }
}

interface ILoginListener {
    fun onToLogin()
    fun onToSmsConform()
}