package com.example.playersreviewsapplication.landing.show

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playersreviewsapplication.R
import com.example.playersreviewsapplication.data.response.Player
import com.example.playersreviewsapplication.databinding.ActivityShowAllPlayersBinding
import com.example.playersreviewsapplication.landing.details.PlayerDetailsActivity
import com.example.playersreviewsapplication.utils.Resources

class ShowAllPlayersActivity : AppCompatActivity(), OnPlayerClickListener {
    private lateinit var binding: ActivityShowAllPlayersBinding
    private lateinit var adapter: PlayerAdapter
    private lateinit var mViewModel: ShowAllPlayersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_all_players)
        binding.lifecycleOwner = this
        mViewModel = ViewModelProvider(this)[ShowAllPlayersViewModel::class.java]
        binding.showAllPlayersViewModel = mViewModel
        binding.playersRecyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launchWhenCreated {
            mViewModel.showPlayersFlow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { commands ->
                    when (commands) {
                        is Resources.CustomCommands.LoadPlayers -> {
                            binding.playersRecyclerView.visibility = GONE
                            binding.showPlayersReload.visibility = GONE
                            binding.showPlayersLoading.visibility = VISIBLE
                        }
                        is Resources.CustomCommands.ShowPlayers -> {
                            adapter =
                                PlayerAdapter(mViewModel.playersList, this@ShowAllPlayersActivity)
                            binding.playersRecyclerView.adapter = adapter
                            adapter.notifyDataSetChanged()
                            binding.playersRecyclerView.visibility = VISIBLE
                            binding.showPlayersReload.visibility = GONE
                            binding.showPlayersLoading.visibility = GONE
                        }
                        is Resources.CustomCommands.ShowReloadBTN -> {
                            binding.playersRecyclerView.visibility = GONE
                            binding.showPlayersReload.visibility = VISIBLE
                            binding.showPlayersLoading.visibility = GONE
                        }
                        is Resources.CustomCommands.OpenDetailsActivity -> {
                            val bundle = Bundle()
                            bundle.putString("playerName", commands.player.name)
                            bundle.putString("playerBrief", commands.player.brief)
                            bundle.putString("PlayerImage", commands.player.image)
                            val intent =
                                Intent(
                                    this@ShowAllPlayersActivity,
                                    PlayerDetailsActivity::class.java
                                )
                            intent.putExtra("player", bundle)
                            startActivity(intent)
                        }
                        else -> {

                        }
                    }
                }

        }

    }

    override fun onPlayerPressed(player: Player) {
        mViewModel.onPlayerPressed(player)
    }
}