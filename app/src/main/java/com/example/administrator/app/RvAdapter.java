package com.example.administrator.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class RvAdapter extends RecyclerView.Adapter <RvAdapter.MyHolder>{

		Context mContext;
		List<ContactsBean> mContactsBeen;

		public RvAdapter(Context context,List<ContactsBean> mInfoBeanList) {
				mContext = context;
				mContactsBeen=mInfoBeanList;
		}

		@Override
		public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
						View view=LayoutInflater.from(mContext).inflate(R.layout.contacts_rv_item,parent,false);
						return new MyHolder(view);
		}



		@Override
		public void onBindViewHolder(MyHolder holder, int position) {
				holder.numberTv.setText("Phone Number："+mContactsBeen.get(position).getNumber());
				holder.contentTv.setText(mContactsBeen.get(position).getContrnt());
		}

		@Override
		public int getItemCount() {
				return mContactsBeen.size();
		}


		static class MyHolder extends ViewHolder{
				TextView numberTv;
				TextView contentTv;
				public MyHolder(View itemView) {
						super(itemView);
						numberTv= (TextView) itemView.findViewById(R.id.contacts_number);
						contentTv= (TextView) itemView.findViewById(R.id.contacts_content);
				}
		}
}
