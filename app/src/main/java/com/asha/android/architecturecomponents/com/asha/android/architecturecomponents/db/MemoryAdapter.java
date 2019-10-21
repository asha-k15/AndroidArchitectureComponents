package com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.db;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.model.Memory;
import com.asha.android.architecturecomponents.R;

import java.util.ArrayList;
import java.util.List;

public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.MemoryHolder> {

    private List<Memory> memories = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public MemoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.memory_item, parent, false);
        return new MemoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoryHolder holder, int position) {
        Memory currentMemory = memories.get(position);
        holder.textViewTitle.setText(currentMemory.getTitle());
        holder.textViewDescription.setText(currentMemory.getMemoryInfo());
        holder.textViewPriority.setText(String.valueOf(currentMemory.getPriority()));
        holder.textDate.setText(currentMemory.getDate_info());
    }

    @Override
    public int getItemCount() {
        return memories.size();
    }

    public Memory getMemoryAt(int position){
        return memories.get(position);
    }

    public void setMemories(List<Memory> memories) {
        this.memories = memories;
        notifyDataSetChanged();
    }

    class MemoryHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private TextView textDate;

        public MemoryHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
            textDate = itemView.findViewById(R.id.text_view_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(memories.get(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Memory memory);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}