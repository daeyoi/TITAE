package com.example.titae;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {
    private ArrayList<SearchRecyclerItem> mData = null;
    private Context mContext;
    // 생성자에서 데이터 리스트 객체를 전달받음.
    SearchRecyclerAdapter(Context mContext, ArrayList<SearchRecyclerItem> list) {
        mContext = mContext;
        mData = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public SearchRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recycler_item_search, parent, false);
        SearchRecyclerAdapter.ViewHolder vh = new SearchRecyclerAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SearchRecyclerAdapter.ViewHolder holder, int position) {

        SearchRecyclerItem item = mData.get(position);

        holder.bankName.setText(item.getBankName());
        holder.productName.setText(item.getProductName());
        holder.rate.setText("" + item.getRate() + "%");
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bankName;
        TextView productName;
        TextView rate;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            bankName = itemView.findViewById(R.id.bank_name);
            productName = itemView.findViewById(R.id.product_name);
            rate = itemView.findViewById(R.id.interest_rate);

            //클릭 이벤트 처리
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    //현재 아이템의 위치를 받아 옴
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        //데이터 리스트로부터 아이템 데이터 참조
                        SearchRecyclerItem item = mData.get(pos);
                        Toast.makeText(context, pos + "번쨰 아이템 클릭 / button click (to ResultAct)" + item.getBankName(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(view.getContext(), ResultActivity.class);

                        //클릭위치 아이템 삽임
                        intent.putExtra("SEARCH_ITEM", item);

                        view.getContext().startActivity(intent);
//                        notifyItemChanged(pos);
                    }
                }
            });
        }
    }
}
