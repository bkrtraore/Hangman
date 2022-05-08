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

        String[] mots = getIntent().getStringArrayExtra("mots");
        String[] definitions = getIntent().getStringArrayExtra("definitions");


        // Mots et definitions ayant la meme taille
        for (int i = 0; i < mots.length; i++) {
            wordArrayList.add(new Word(mots[i], definitions[i]));
        }

        RecyclerView recyclerView =findViewById(R.id.recyclerViewId);

        WordRecyclerViewAdapter wordAdapter = new WordRecyclerViewAdapter(R.layout.word_item, this, wordArrayList);
        RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(wordAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

    }



}
