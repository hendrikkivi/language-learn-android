package com.gmail.kivihendrik.languagelearn;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.MyViewHolder> implements Filterable {
    private List<Word> wordsList;
    private List<Word> filteredWordsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvWord, tvLanguage;

        public MyViewHolder(View view) {
            super(view);
            tvWord = (TextView) view.findViewById(R.id.tvWord);
            tvLanguage = (TextView) view.findViewById(R.id.tvLanguage);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    removeItem(position, view);
                    return true;
                }
            });
        }
    }

    public WordsAdapter(List<Word> wordsList) {
        this.wordsList = wordsList;
        this.filteredWordsList = wordsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Word word = filteredWordsList.get(position);
        holder.tvWord.setText(word.getForeignWord() + " - " + word.getTranslation());
        holder.tvLanguage.setText(word.getWordLanguage() + " - " + word.getTranslationLanguage());
    }

    @Override
    public int getItemCount() {
        return filteredWordsList.size();
    }

    public void removeItem(int position, View view) {
        Word word = filteredWordsList.get(position);
        filteredWordsList.remove(word);
        notifyDataSetChanged();

        DatabaseHandler db = new DatabaseHandler(view.getContext());
        db.deleteWord(word);
        db.close();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filteredWordsList = wordsList;
                } else {
                    List<Word> filteredList = new ArrayList<>();
                    for (Word row : wordsList) {
                        if (row.getForeignWord().toLowerCase().contains(charString.toLowerCase()) || row.getTranslation().toLowerCase().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }
                    filteredWordsList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredWordsList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredWordsList = (ArrayList<Word>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
