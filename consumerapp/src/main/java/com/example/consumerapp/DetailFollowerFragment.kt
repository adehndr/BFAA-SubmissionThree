package com.example.consumerapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.consumerapp.dataclass.UserItems


class DetailFollowerFragment : Fragment() {
    companion object {
        private const val ARG_USERNAME = "username"
        fun newInstance(username: String): DetailFollowerFragment {
            val fragment = DetailFollowerFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }

    }

    private lateinit var adapter: UserAdapter
    private lateinit var rvFollowers: RecyclerView
    private lateinit var mainViewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBarFollowers)
        progressBar.visibility = View.VISIBLE
        val userName = arguments?.getString(ARG_USERNAME)
        rvFollowers = view.findViewById(R.id.rv_followers_users)
        rvFollowers.setHasFixedSize(true)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        rvFollowers.layoutManager = LinearLayoutManager(activity)
        rvFollowers.adapter = adapter
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItems) {
                val intent = Intent(activity, UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.EXTRA_PERSON, data)
                startActivity(intent)
            }
        })

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        if (userName != null) {
            progressBar.visibility = View.GONE
            mainViewModel.setUsersFollower(userName)
        }
        activity?.let {
            mainViewModel.getUsersFollower().observe(it, { userItems ->
                if (userItems != null) {
                    adapter.setData(userItems)
                }
            })
        }
    }
}