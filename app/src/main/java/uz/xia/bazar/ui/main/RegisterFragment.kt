package uz.xia.bazar.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import uz.xia.bazar.R
import uz.xia.bazar.databinding.FragmentRegisterBinding
import uz.xia.bazar.utils.Validation
import uz.xia.bazar.utils.errorCheckingTextChanges
import uz.xia.bazar.utils.hideKeyboard
import uz.xia.bazar.utils.throttleFirstShort

private const val TAG = "RegisterFragment"
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val cd = CompositeDisposable()

    private val emailChanges
        get() = binding.etEmail.errorCheckingTextChanges(
            binding.inputLayoutEmail, R.string.invalidet_login
        ) {
            Validation.isValidEmail(it)
        }
    private val passwordChanges
        get() = binding.etPassword.errorCheckingTextChanges(
            binding.inputLayoutPassword, R.string.invalidet_password
        ) {
            Validation.isValidPassword(it)
        }

    private val passwordRetryChanges
        get() = binding.etRetryPassword.errorCheckingTextChanges(
            binding.inputLayoutRetryPassword, R.string.invalidet_password
        ) {
            Validation.isValidPassword(it)
        }
    companion object {
        fun newInstance() = RegisterFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cd.add(Observable.combineLatest(
            emailChanges,
            passwordChanges,
            passwordRetryChanges
        ) { t1, t2,t3->
            SignUpForm(t1, t2,t3)
        }.switchMap { form ->
            val isValid = form.isValid()
            binding.button.isEnabled = isValid
            binding.button.clicks().throttleFirstShort().map { form }
        }.subscribe { form ->
            hideKeyboard()
            signUpFire(form)
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

    private fun signUpFire(form: SignUpForm) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(form.email,form.password)
            .addOnCompleteListener { result->
                if (result.isSuccessful) {
                    Toast.makeText(requireContext(),"Muvofaqiyatli o'tdi",Toast.LENGTH_LONG).show()
                }else{
                    Log.d(TAG,"Error ${result.exception}")
                }
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