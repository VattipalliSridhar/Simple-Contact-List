package com.simple.simplecontactlist.recycleradapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.simple.simplecontactlist.R;
import com.simple.simplecontactlist.modelclasses.Contact;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.AdapterViewHolder> {

    List<Contact> contactList;
    List<Contact> exampleListFull;
    Context context;

    public RecyclerViewAdapter(Context context, List<Contact> posts) {
        this.contactList = posts;
        this.context = context;
        exampleListFull = new ArrayList<>(contactList);
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_layout, viewGroup, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder adapterViewHolder, int i) {

        adapterViewHolder.first_last_name_txt.setText("" + contactList.get(i).getName());
        adapterViewHolder.contact_email_id.setText("" + contactList.get(i).getEmail());

    }

    @Override
    public int getItemCount() {
        return this.contactList.size();
    }


    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        TextView first_last_name_txt;
        TextView contact_email_id;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            first_last_name_txt = itemView.findViewById(R.id.fst_last_name);
            contact_email_id = itemView.findViewById(R.id.email_id);
        }
    }

    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Contact> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Contact item : exampleListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactList.clear();
            contactList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
