package com.example.hangman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordRecyclerViewAdapter extends RecyclerView.Adapter<WordRecyclerViewAdapter.WordViewHolder> {

    private int cellLayoutResourceId;
    private Context context;
    private List<Word> wordList;
    private LayoutInflater inflater;


    public WordRecyclerViewAdapter(int cellLayoutResourceId, Context context, List<Word> wordList) {
        this.cellLayoutResourceId = cellLayoutResourceId;
        this.context = context;
        this.wordList = wordList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(cellLayoutResourceId,null,false);
        WordRecyclerViewAdapter.WordViewHolder wHolder=new WordRecyclerViewAdapter.WordViewHolder(view);

        return wHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        Word word = wordList.get(position);
        holder.mot.setText(word.mot);
        holder.definition.setText(word.definition);
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {
        public TextView mot;
        public TextView definition;
        public View associatedView;

        public WordViewHolder(@NonNull View view) {
            super(view);

            associatedView = view;
            mot = (TextView) view.findViewById(R.id.word_mot);
            definition = (TextView) view.findViewById(R.id.word_definition);
        }

        public TextView getMot() {
            return mot;
        }

        public TextView getDefinition() {
            return definition;
        }

    }
}


