package com.test.rappitest.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.test.rappitest.R;
import com.test.rappitest.app.TestRappiApplication;
import com.test.rappitest.model.CardItem;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private Dialog mDetailsItem;

    public List<CardItem> getmData() {
        return mData;
    }

    public void setmData(List<CardItem> mData) {
        this.mData = mData;
    }

    private List<CardItem> mData;

    public RecyclerViewAdapter(Context mContext, List<CardItem> mListData) {
        this.mContext = mContext;
        this.mData = mListData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new MyViewHolder(inflater.inflate(R.layout.cardview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.mImg_thumbail.setImageResource(R.drawable.ic_launcher_foreground);
        Glide.with(mContext)
                .load(TestRappiApplication.BASE_URL_IMAGE + mData.get(position).getBackdrop_path())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.mImg_thumbail);

        mDetailsItem = new Dialog(mContext);
        mDetailsItem.setContentView(R.layout.details_item);
        mDetailsItem.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView details_item_title = (TextView) mDetailsItem.findViewById(R.id.details_item_title);
                TextView details_item_year = (TextView) mDetailsItem.findViewById(R.id.details_item_year);
                TextView details_item_overView = (TextView) mDetailsItem.findViewById(R.id.details_item_overview);
                ImageView details_item_thumbnail = (ImageView) mDetailsItem.findViewById(R.id.details_item_thumbnail);

                details_item_overView.setMovementMethod(new ScrollingMovementMethod());


                details_item_title.setText(mData.get(holder.getAdapterPosition()).getTitle());
                details_item_year.setText(mData.get(holder.getAdapterPosition()).getRelease_date());
                details_item_overView.setText(mData.get(holder.getAdapterPosition()).getOverview());

                Glide.with(mContext)
                        .load(TestRappiApplication.BASE_URL_IMAGE + mData.get(holder.getAdapterPosition()).getBackdrop_path())
                        .apply(RequestOptions.centerCropTransform())
                        .into(details_item_thumbnail);

                mDetailsItem.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<CardItem> filteredList(String strFilter, List<CardItem> listItems) {
        List<CardItem> filteredValues = new ArrayList<CardItem>(listItems);

        for (CardItem value : listItems) {
            if (!value.getTitle().toLowerCase().contains(strFilter.toLowerCase()) || value.getOriginal_title().contains(strFilter)) {
                filteredValues.remove(value);
            }
        }
        return filteredValues;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImg_thumbail;
        private CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            mImg_thumbail = (ImageView) itemView.findViewById(R.id.card_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);

        }
    }
}
