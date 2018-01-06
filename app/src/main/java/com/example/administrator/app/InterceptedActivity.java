package com.example.administrator.app;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InterceptedActivity extends AppCompatActivity {

		final String TAG="BBB";
		private RecyclerView mRecyclerView;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_intercepted);
				setTitle("Intercepted List");
				mRecyclerView= (RecyclerView) findViewById(R.id.intercepted_rv);
				MyDataBaseHelper dataBaseHelper=new MyDataBaseHelper(this,"BlackList.db3",1);
				Cursor cursor=dataBaseHelper.getReadableDatabase().rawQuery("select * from record",new String[]{});
				List<ContactsBean> beanList=new ArrayList<>();
				while (cursor.moveToNext()){
						ContactsBean bean=new ContactsBean();
						String number=cursor.getString(0);
						String time=cursor.getString(1);
						bean.setNumber(number);
						bean.setContrnt(time);
						beanList.add(bean);
				}
				RvAdapter adapter=new RvAdapter(this,beanList);
				LinearLayoutManager manager=new LinearLayoutManager(this);
				manager.setOrientation(LinearLayoutManager.VERTICAL);
				mRecyclerView.setLayoutManager(manager);
				mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
				mRecyclerView.setAdapter(adapter);
		}
}
