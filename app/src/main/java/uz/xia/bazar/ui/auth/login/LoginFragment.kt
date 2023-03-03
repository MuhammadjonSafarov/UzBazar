package uz.xia.bazar.ui.auth.login
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import io.reactivex.disposables.CompositeDisposable
import uz.xia.bazar.common.MASK_PHONE_NUMBER
import uz.xia.bazar.common.Status
import uz.xia.bazar.databinding.FragmentLoginBinding
import uz.xia.bazar.ui.dialog.ProgressDialog
import uz.xia.bazar.utils.lazyFast

private const val TAG = "LoginFragment"
const val NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
const val CHANNEL_ID = "CHANNEL_ID"

class LoginFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val cd = CompositeDisposable()
    private var mListener: ILoginListener? = null
    private var phoneNumber:String=""
    companion object {
        fun newInstance() = LoginFragment()
    }

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

    private val mViewModel by viewModels<LoginViewModel>()
    private val progressBar by lazyFast { ProgressDialog() }

    private val stateObserver = Observer<Status> {
        when (it) {
            Status.LOADING -> progressBar.show(childFragmentManager,"progress_dialog")
            Status.SUCCESS -> {
                progressBar.dismiss()
                mListener?.onToSmsConform(phoneNumber)
            }
            is Status.ERROR -> {
                progressBar.dismiss()
            }
        }
    }


    private val maskedListener = object : MaskedTextChangedListener.ValueListener {
        override fun onTextChanged(
            maskFilled: Boolean,
            extractedValue: String,
            formattedValue: String
        ) {
            binding.button.isEnabled = maskFilled
            phoneNumber=extractedValue
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
        setUpViews()
        setUpObserver()
    }

    private fun setUpObserver() {
        mViewModel.liveStatus.observe(viewLifecycleOwner, stateObserver)
    }

    private fun setUpViews() {
        val affineFormats = mutableListOf<String>()
        affineFormats.add(MASK_PHONE_NUMBER)
        val listener: MaskedTextChangedListener = MaskedTextChangedListener.installOn(
            binding.phoneNumber,
            MASK_PHONE_NUMBER, affineFormats,
            AffinityCalculationStrategy.PREFIX, maskedListener
        )
        binding.phoneNumber.hint = listener.placeholder()
        binding.button.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (!cd.isDisposed) {
            cd.dispose()
        }
    }

    override fun onClick(view: View?) {
        val phoneNumber = binding.phoneNumber.text.toString()
        mViewModel.onLogin(phoneNumber)
    }
}

interface ILoginListener {
    fun onToLogin()
    fun onToSmsConform(phone:String)
}