package uz.xia.bazar.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import uz.xia.bazar.R
import uz.xia.bazar.databinding.FragmentLoginBinding
import uz.xia.bazar.ui.main.SignInForm
import uz.xia.bazar.utils.Validation
import uz.xia.bazar.utils.errorCheckingTextChanges
import uz.xia.bazar.utils.hideKeyboard
import uz.xia.bazar.utils.throttleFirstShort

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


    private val emailChanges
        get() = binding.etLogin.errorCheckingTextChanges(
            binding.inputLayoutLogin, R.string.invalidet_login
        ) {
            Validation.isValidEmail(it)
        }
    private val passwordChanges
        get() = binding.etPassword.errorCheckingTextChanges(
            binding.inputLayoutPassword, R.string.invalidet_password
        ) {
            Validation.isValidPassword(it)
        }

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext())
            .load("http://127.0.0.1:8082/api/preview/Aldxlx")
            .into(binding.image)
        binding.textView.setOnClickListener {
        }

        cd.add(Observable.combineLatest(
            emailChanges, passwordChanges
        ) { t1, t2 ->
            SignInForm(t1, t2)
        }.switchMap { form ->
            val isValid = form.isValid()
            binding.button.isEnabled = isValid
            binding.button.clicks().throttleFirstShort().map { form }
        }.subscribe { form ->
            hideKeyboard()
            signIn(form)
            Toast.makeText(requireContext(), "nimadur", Toast.LENGTH_LONG).show()
        })

        /* binding.etLogin.addTextChangedListener(object : TextWatcher {
             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

             }

             override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 if (text != null) {
                     binding.button.isEnabled = Patterns.EMAIL_ADDRESS.matcher(text).matches()
                 }
             }

             override fun afterTextChanged(p0: Editable?) {

             }

         })*/
    }


    private fun signIn(form: SignInForm?) {
        /*    FirebaseAuth.getInstance().signInWithEmailAndPassword(form!!.email, form.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        mListener?.onClickLogin()
                    } else {
                        Log.d(TAG, "singIn Error ${it.exception}")
                    }
                }*/
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
    fun onClickLogin()
}