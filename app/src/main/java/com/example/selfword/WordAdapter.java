package com.example.selfword;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter <WordAdapter.ViewHolder> {

    private List<WordEntity> wordList;
    private Activity context;
    private WordDatabase wordDatabase;

    public WordAdapter(Activity context, List<WordEntity> wordList) {
        this.context = context;
        this.wordList = wordList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_words, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WordEntity wordEntity = wordList.get(position);

        wordDatabase = WordDatabase.getInstance(context);

        holder.textView_word.setText(wordEntity.getThe_word());
        holder.textView_mean.setText(wordEntity.getMean_of_word());
        holder.btn_edit_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WordEntity wordEntity = wordList.get(holder.getAdapterPosition());

                int sID = wordEntity.getID();
                String str_the_word = wordEntity.getThe_word();
                String str_mean_of_word = wordEntity.getMean_of_word();

                Dialog dialog = new Dialog(context);

                dialog.setContentView(R.layout.dialog_update);

                int width = WindowManager.LayoutParams.MATCH_PARENT;

                int height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setLayout(width, height);

                dialog.show();

                EditText editText_dialog_word = dialog.findViewById(R.id.edit_dialog_word);
                EditText editText_dialog_mean = dialog.findViewById(R.id.edit_dialog_mean);
                Button btUpdate = dialog.findViewById(R.id.btn_dialog_update);

                editText_dialog_word.setText(str_the_word);
                editText_dialog_mean.setText(str_mean_of_word);
                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        String uWord = editText_dialog_word.getText().toString();
                        String uMean = editText_dialog_mean.getText().toString();
                        wordDatabase.wordDao().update(sID, uWord, uMean);

                        wordList.clear();
                        wordList.addAll(wordDatabase.wordDao().getAll());
                        notifyDataSetChanged();
                    }
                });

            }
        });
        holder.btn_delete_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WordEntity wordEntity = wordList.get(holder.getAdapterPosition());
                wordDatabase.wordDao().delete(wordEntity);

                int position = holder.getAdapterPosition();
                wordList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, wordList.size());

            }
        });
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_word, textView_mean;
        ImageView btn_edit_word, btn_delete_word;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_word = itemView.findViewById(R.id.text_view_word);
            textView_mean = itemView.findViewById(R.id.text_view_mean);
            btn_edit_word = itemView.findViewById(R.id.bt_edit);
            btn_delete_word = itemView.findViewById(R.id.bt_delete);
        }
    }
}
