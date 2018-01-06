package com.example.administrator.app;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

		RecyclerView mRecyclerView;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_contacts);
				setTitle("Contact List");
				mRecyclerView= (RecyclerView) findViewById(R.id.contacts_rv);
				Cursor cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
				List<ContactsBean> beanList=new ArrayList<>();
				while (cursor.moveToNext()){
						String number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
						ContactsBean contactsBean=new ContactsBean();
						contactsBean.setNumber(number);
						contactsBean.setContrnt(name);
						beanList.add(contactsBean);
				}
				RvAdapter adapter=new RvAdapter(this,beanList);
				LinearLayoutManager manager=new LinearLayoutManager(this);
				manager.setOrientation(LinearLayoutManager.VERTICAL);
				mRecyclerView.setLayoutManager(manager);
				mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
				mRecyclerView.setAdapter(adapter);
		}
}
