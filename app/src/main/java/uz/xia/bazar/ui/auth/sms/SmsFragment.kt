package uz.xia.bazar.ui.auth.sms

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import uz.xia.bazar.common.Status
import uz.xia.bazar.databinding.FragmentSmsBinding
import uz.xia.bazar.ui.auth.login.ILoginListener
import uz.xia.bazar.ui.dialog.ProgressDialog
import uz.xia.bazar.utils.lazyFast

private const val TAG = "SmsFragment"
class SmsFragment :Fragment(){
    private var _binding: FragmentSmsBinding? = null
    private val binding get() = _binding!!
    private var mListener: ILoginListener? = null
    private var phoneSms:String=""
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ILoginListener) {
            mListener = context
        }
    }
    private val maskedListener=object: MaskedTextChangedListener.ValueListener{
        override fun onTextChanged(
            maskFilled: Boolean,
            extractedValue: String,
            formattedValue: String
        ) {
            binding.buttonSms.isEnabled=maskFilled
            Log.d(TAG,"extractedValue:$extractedValue  formattedValue:$formattedValue")
        }

    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }
    private val mViewModel by viewModels<SmsViewModel>()
    private val progressBar by lazyFast { ProgressDialog() }
    companion object {
        fun newInstance() = SmsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val affineFormats: MutableList<String> = ArrayList()
        affineFormats.add("[000]-[000]")

        val listener: MaskedTextChangedListener = MaskedTextChangedListener.installOn(
            binding.tvCode,
            "[000]-[000]",
            affineFormats, AffinityCalculationStrategy.PREFIX, maskedListener
        )
        binding.tvCode.hint = listener.placeholder()
        setUpObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSmsBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun setUpObserver() {
        mViewModel.liveStatus.observe(viewLifecycleOwner, stateObserver)
    }
    private val stateObserver = Observer<Status> {
        when (it) {
            Status.LOADING -> progressBar.show(childFragmentManager,"progress_dialog")
            Status.SUCCESS -> {
                progressBar.dismiss()
                mListener?.onToSmsConform(phoneSms)
            }
            is Status.ERROR -> {
                progressBar.dismiss()
            }
        }
    }
}