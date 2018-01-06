package com.example.administrator.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class BlackRvAdapter extends RecyclerView.Adapter <BlackRvAdapter.MyHolder>{

		Context mContext;
		List<String> mNumbers;
		MyDataBaseHelper mDataBaseHelper;


		public BlackRvAdapter(Context context, List<String> numbers,MyDataBaseHelper dataBaseHelper) {
				mContext = context;
				mNumbers=numbers;
				mDataBaseHelper=dataBaseHelper;
		}

		@Override
		public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
						View view=LayoutInflater.from(mContext).inflate(R.layout.black_list_rv_item,parent,false);
						return new MyHolder(view);
		}



		@Override
		public void onBindViewHolder(MyHolder holder, final int position) {
				holder.numberTv.setText(mNumbers.get(position));
				holder.deleteBT.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
								mDataBaseHelper.getWritableDatabase().execSQL("delete from phone where number=?",new String[]{mNumbers.get(position)});
								mNumbers.remove(position);
								notifyDataSetChanged();
						}
				});
		}

		@Override
		public int getItemCount() {
				return mNumbers.size();
		}


		static class MyHolder extends ViewHolder{
				TextView numberTv;
				Button deleteBT;
				public MyHolder(View itemView) {
						super(itemView);
						numberTv= (TextView) itemView.findViewById(R.id.contacts_number);
						deleteBT= (Button) itemView.findViewById(R.id.delete_bt);
				}
		}
}
