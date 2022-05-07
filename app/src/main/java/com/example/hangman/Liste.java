package com.example.hangman;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Liste extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.liste);

        ArrayList<Word> wordArrayList= new ArrayList<>();

        Word one = new Word("Marcel","def1");
        Word two = new Word("Christine","de2");
        Word three = new Word("Janvier","def3");


        wordArrayList.add(one);
        wordArrayList.add(two);
        wordArrayList.add(three);

        RecyclerView recyclerView =findViewById(R.id.recyclerViewId);

        WordRecyclerViewAdapter wordAdapter = new WordRecyclerViewAdapter(R.layout.word_item, this, wordArrayList);
        RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(wordAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        /*
        // Get drawable object
        Drawable mDivider = ContextCompat.getDrawable(this, R.drawable.divider);
        // Create a DividerItemDecoration whose orientation is Horizontal
        DividerItemDecoration hItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL);
        // Set the drawable on it
        hItemDecoration.setDrawable(mDivider);
        */
    }



}
