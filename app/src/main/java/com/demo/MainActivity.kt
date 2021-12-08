package com.demo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MainActivity: AppCompatActivity() {

    private lateinit var change: Button
    private lateinit var recycler: RecyclerView
    private val demoAdapter: DemoAdapter = DemoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        change = findViewById(R.id.change)
        change.setOnClickListener {
            val list = ArrayList(demoAdapter.currentList)
            list.add(10, list.removeAt(5))
            demoAdapter.submitList(list)
        }

        recycler = findViewById(R.id.recycler)
        recycler.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 5)
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DemoDecoration(this@MainActivity, 2f, 4f, 2f, 4f))
            adapter = demoAdapter
        }

        val data: MutableList<Model> = ArrayList()
        for (i: Int in 0 .. 39) {
            when ((1..2).random()) {
                1 -> data.add(Model("$i\n\na"))
                2 -> data.add(Model("$i\na\nb"))
            }
        }
        demoAdapter.submitList(data)
    }
}