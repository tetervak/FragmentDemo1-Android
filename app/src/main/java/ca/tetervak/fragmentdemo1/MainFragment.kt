package ca.tetervak.fragmentdemo1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
import ca.tetervak.fragmentdemo1.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    companion object {
        const val REPLY_REQUEST_KEY = "replyRequest"
        const val REPLY_MESSAGE_KEY = "replyMessage"

        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.sendButton.setOnClickListener { showOutput() }

        // set up the listener for the reply result
        setFragmentResultListener(REPLY_REQUEST_KEY) { _, bundle ->
            val replyMessage = bundle.getString(OutputFragment.REPLY_TEXT_KEY)
            displayReplyMessage(replyMessage)
        }

        // recover the saved reply message after rotation
        if (savedInstanceState?.containsKey(REPLY_MESSAGE_KEY) == true) {
            val replyMessage = savedInstanceState.getString(REPLY_MESSAGE_KEY)
            displayReplyMessage(replyMessage)
        }

        return binding.root
    }

    private fun showOutput() {
        // get urgent flag value
        val urgent = binding.urgentCheckBox.isChecked

        // get the selected message text
        val message = when (binding.messageGroup.checkedRadioButtonId) {
            R.id.purr_button -> getString(R.string.cat_purr)
            R.id.mew_button -> getString(R.string.cat_mew)
            R.id.hiss_button -> getString(R.string.cat_hiss)
            else -> getString(R.string.undefined)
        }

        // switch the fragment
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(null)
            replace(
                R.id.fragment_container,
                OutputFragment.newInstance(message, urgent, REPLY_REQUEST_KEY)
            )
        }
    }

    private fun displayReplyMessage(replyMessage: String?) {
        binding.receivedReplyLabel.visibility = View.VISIBLE
        binding.receivedReplyValue.visibility = View.VISIBLE
        binding.receivedReplyValue.text = replyMessage
    }

    // save the reply message value on rotation
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(REPLY_MESSAGE_KEY, binding.receivedReplyValue.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}