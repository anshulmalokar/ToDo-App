package com.anshul.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), INotesRvAdapter {
    lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager=LinearLayoutManager(this)
        val adapter=NotesRVAdapter(this,this)
        recyclerView.adapter=adapter

        viewModel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).
        get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this,
            Observer {list->
                list?.let {
                    adapter.updateList(it)
                }
            })
    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNode(note)
        Toast.makeText(this,"Deleted ${note.text}",Toast.LENGTH_LONG).show()
    }

    fun submitData(view: View) {
        val noteText=input.text.toString()
        if(noteText.isNotEmpty()){
            viewModel.insert(Note(noteText))
            Toast.makeText(this,"Added $noteText",Toast.LENGTH_LONG).show()
        }
    }
}