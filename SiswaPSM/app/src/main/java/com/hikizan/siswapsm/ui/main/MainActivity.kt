package com.hikizan.siswapsm.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hikizan.siswapsm.R
import com.hikizan.siswapsm.adapter.StudentAdapter
import com.hikizan.siswapsm.databinding.ActivityMainBinding
import com.hikizan.siswapsm.helper.ViewModelFactory
import com.hikizan.siswapsm.ui.insert.StudentAddUpdateActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StudentAdapter

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.main_title)

        binding.fabAdd.setOnClickListener { view ->
            if (view.id == R.id.fab_add) {
                val intent = Intent(this@MainActivity, StudentAddUpdateActivity::class.java)
                startActivity(intent)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel = obtainViewModel(this)
        viewModel.getAllStudents().observe(this) { list ->
            if (list != null) {
                adapter.setListStudents(list)
            }
        }

        adapter = StudentAdapter()

        binding.rvStudents.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvStudents.setHasFixedSize(true)
        binding.rvStudents.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }
}