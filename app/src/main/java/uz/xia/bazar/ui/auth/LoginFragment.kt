package uz.xia.bazar.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import uz.xia.bazar.R
import uz.xia.bazar.databinding.FragmentLoginBinding
import uz.xia.bazar.ui.main.SignInForm

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
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