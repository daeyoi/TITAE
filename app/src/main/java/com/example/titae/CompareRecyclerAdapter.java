package com.example.titae;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CompareRecyclerAdapter extends RecyclerView.Adapter<CompareRecyclerAdapter.ViewHolder>{
    private ArrayList<SearchRecyclerItem> mData = null ;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    CompareRecyclerAdapter(ArrayList<SearchRecyclerItem> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public CompareRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycler_item_compare, parent, false) ;
        CompareRecyclerAdapter.ViewHolder vh = new CompareRecyclerAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(CompareRecyclerAdapter.ViewHolder holder, int position) {

        SearchRecyclerItem item = mData.get(position) ;

        holder.bankName.setText(item.getBankName()) ;
        holder.productName.setText(item.getProductName()) ;
        holder.rate.setText(""+item.getRate()+"%") ;
        //계산기 돌려서 출력하게 코딩 필요
//        holder.amount.setText(item.get());
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bankName ;
        TextView productName ;
        TextView rate ;
        TextView amount;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            bankName = itemView.findViewById(R.id.bank_name_compare) ;
            productName = itemView.findViewById(R.id.product_name_compare) ;
            rate = itemView.findViewById(R.id.interest_rate_compare) ;
            amount = itemView.findViewById(R.id.received_amount_compare);

            //클릭 이벤트 처리
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    //현재 아이템의 위치를 받아 옴
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        //데이터 리스트로부터 아이템 데이터 참조
                        SearchRecyclerItem item = mData.get(pos);
                    }
                }
            });
        }
    }
}
