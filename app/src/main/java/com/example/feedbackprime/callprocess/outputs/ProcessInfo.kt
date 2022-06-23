package com.example.feedbackprime.callprocess.outputs

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedbackprime.callprocess.api.models.FinalResultFromAPI
import com.example.feedbackprime.databinding.FragmentProcessInfoBinding


private const val TAG = "ProcessInfo"

class ProcessInfo : Fragment() {
    private lateinit var binding: FragmentProcessInfoBinding
    private val args: ProcessInfoArgs by navArgs()
    private lateinit var viewModel: ProcessInfoViewModel
    private lateinit var progressDialog: ProgressDialog
    private var adapter: RecyclerView.Adapter<CustomAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProcessInfoBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[ProcessInfoViewModel::class.java]
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        progressDialog = ProgressDialog(requireContext())

        viewModel.countOfJobStatus.observe(requireActivity(), Observer { count ->
            if (count == 2)
                showProgressDialog("Analysing the conversation")
        })
        getAccessToken()

        return binding.root
    }

    private fun showProgressDialog(message: String) {
        progressDialog.setMessage(message)
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    private fun getAccessToken() {
        showProgressDialog("Sending to server")
        viewModel.getAccessToken()
        viewModel.gotAccessToken.observe(requireActivity(), Observer { accessToken ->
            Log.i(TAG, accessToken.toString())
            getConversationId(accessToken.accessToken, args.url, args.name)
        })
    }

    private fun getConversationId(accessToken: String, url: String, name: String) {
        viewModel.getConversationId(accessToken, url, name)
        viewModel.gotConversationId.observe(requireActivity(), Observer { conversationId ->
            Log.i(TAG, conversationId.toString())
            getJobStatusFromAPI(conversationId.jobId, accessToken, conversationId.conversationId)
        })
    }

    private fun getJobStatusFromAPI(jobId: String, accessToken: String, conversationId: String) {
        viewModel.getJobStatus(jobId, accessToken, conversationId)
        viewModel.jobCompleted.observe(requireActivity(), Observer { statusCompleted ->
            Log.i(TAG, "Job Done")
            if (statusCompleted == true)
                getFinalResultFromAPI(conversationId, accessToken)
        })
    }

    private fun getFinalResultFromAPI(conversationId: String, accessToken: String) {
        showProgressDialog("Almost there")
        viewModel.getFinalResultFromAPI(conversationId, accessToken)
        viewModel.finalResult.observe(requireActivity(), Observer { finalResult ->
            Log.i(TAG, finalResult.toString())
            if (finalResult.messages.isNotEmpty())
                showResultToUser(finalResult)
        })
    }

    private fun showResultToUser(finalResult: FinalResultFromAPI?) {
        if (progressDialog.isShowing)
            progressDialog.dismiss()
        if (finalResult != null) {
            adapter = CustomAdapter(finalResult.messages)
            binding.recyclerview.adapter = adapter
        }
    }
}