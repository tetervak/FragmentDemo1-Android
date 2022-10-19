package ca.tetervak.fragmentdemo1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import ca.tetervak.fragmentdemo1.databinding.FragmentOutputBinding

class OutputFragment : Fragment() {

    companion object {
        const val REPLY_TEXT_KEY = "reply"
        private const val MESSAGE_KEY = "message"
        private const val URGENT_KEY = "urgent"
        private const val REQUEST_KEY = "request"

        fun newInstance(message: String, urgent: Boolean, requestKey: String): OutputFragment {
            val fragment = OutputFragment()
            fragment.arguments = bundleOf(
                URGENT_KEY to urgent,
                MESSAGE_KEY to message,
                REQUEST_KEY to requestKey
            )
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOutputBinding.inflate(inflater, container, false)

        // Get and display the message data
        val args: Bundle = arguments ?: Bundle()
        val urgent = args.getBoolean(URGENT_KEY)
        val message = args.getString(MESSAGE_KEY)
        binding.isUrgentOutput.text =
            getString(if (urgent) R.string.urgent else R.string.not_urgent)
        binding.messageText.text = message

        binding.replyButton.setOnClickListener {
            val userInput = binding.replyTextInput.text.toString().trim()
            if (userInput.isNotEmpty()) {
                val requestKey = args.getString(REQUEST_KEY)!!
                val bundle = bundleOf(REPLY_TEXT_KEY to userInput)
                setFragmentResult(requestKey, bundle)
                parentFragmentManager.popBackStack()
            } else {
                binding.replyTextInput.error = getString(R.string.required_input)
            }
        }

        return binding.root
    }
}