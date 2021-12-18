package com.example.payoneertask.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.payoneertask.R;
import com.payoneer.checkout.model.ApplicableNetwork;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    private List<ApplicableNetwork> networkList;
    private Context context;

    public PaymentAdapter(Context applicationContext, List<ApplicableNetwork> networkArrayList) {
        this.context =applicationContext;
        this.networkList = networkArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.card_payment, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ApplicableNetwork network = networkList.get(position);

        viewHolder.nameView.setText(network.getLabel());
        Picasso.get()
                .load(network.getLinks().get("logo").toString())
                .into(viewHolder.logoView);

    }
    @Override
    public int getItemCount() {
        return networkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        ImageView logoView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.paymentName);
            logoView = itemView.findViewById(R.id.paymentLogo);

            // on item click
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    ApplicableNetwork clickedNetwork = networkList.get(getAdapterPosition());
                        Toast.makeText(v.getContext(), clickedNetwork.getLabel() , Toast.LENGTH_SHORT).show();
                    }
            });
        }
    }

    public void clear() {
        networkList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<ApplicableNetwork> networks) {
        networkList.addAll(0,networks);
        notifyItemInserted(0);
        //mRecycler.smoothScrollToPosition(0);
        notifyDataSetChanged();
    }
}
