package com.example.titae;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CompareRecyclerAdapter extends RecyclerView.Adapter<CompareRecyclerAdapter.ViewHolder>{
    private ArrayList<SearchRecyclerItem> mData = null ;
    private Context mContext;
    private OnItemClick mCallback;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    CompareRecyclerAdapter(Context mContext, ArrayList<SearchRecyclerItem> list, OnItemClick listener) {
        mData = list ;
        mContext = mContext;
        this.mCallback = listener;
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
    //button클릭 처리 없을 시
    @Override
    public void onBindViewHolder(CompareRecyclerAdapter.ViewHolder holder, int position) {

        int amount_data = 0;
        int period = 0;

        SearchRecyclerItem item = mData.get(position) ;

        holder.bankName.setText(item.getBankName()) ;
        holder.productName.setText(item.getProductName()) ;
        holder.rate.setText(""+item.getRate()+"%") ;
        period = mCallback.getPeriod();
        amount_data = mCallback.getAmount();
        //실수령액 표시
        holder.ret_amount.setText(String.valueOf(amount_data * item.getRate() / 100 * period)  + (period * amount_data));
    }


//     onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
//     버튼 클릭 시
    @Override
    public void onBindViewHolder(CompareRecyclerAdapter.ViewHolder holder, int position,  @NonNull List<Object> payloads) {

        SearchRecyclerItem item = mData.get(position) ;

        int amount_data = 0;
        int period = 0;

        holder.bankName.setText(item.getBankName()) ;
        holder.productName.setText(item.getProductName()) ;
        holder.rate.setText(""+item.getRate()+"%") ;
        //계산기 돌려서 출력하게 코딩 필요
        //payload
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        }else {
            for (Object payload : payloads) {
                if (payload instanceof String) {
                    String type = (String) payload;
//                    if (TextUtils.equals(type, "calc_amount") && holder instanceof TextHolder) { //payload 값 출력
                        if (TextUtils.equals(type, "calc_amount")) { //payload 값 출력
                            period = mCallback.getPeriod();
                            amount_data = mCallback.getAmount();

                            holder.ret_amount.setText(String.valueOf((amount_data * item.getRate() / 100 * period)  + (period * amount_data)));
                        }
                }
            }
        }
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bankName;
        TextView productName ;
        TextView rate ;
        TextView ret_amount;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            bankName = itemView.findViewById(R.id.bank_name_compare) ;
            productName = itemView.findViewById(R.id.product_name_compare) ;
            rate = itemView.findViewById(R.id.interest_rate_compare) ;
            ret_amount = itemView.findViewById(R.id.received_amount_result_compare);

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
