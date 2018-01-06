package com.example.administrator.app;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BlackListActivity extends AppCompatActivity implements View.OnClickListener {

		Button addBt;
		RecyclerView mRecyclerView;
		List<String> mNumbers=new ArrayList<>();
		MyDataBaseHelper dbHelper;
		BlackRvAdapter adapter;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_black_list);
				setTitle("Blacklist");
				addBt= (Button) findViewById(R.id.add_to_black_BT);
				addBt.setOnClickListener(this);
				mRecyclerView= (RecyclerView) findViewById(R.id.black_list_rv);
				dbHelper=new MyDataBaseHelper(this,"BlackList.db3",1);
				//retrieve data from database and display data
				Cursor cursor=dbHelper.getWritableDatabase().rawQuery("select * from phone",new String[]{});
				while (cursor.moveToNext()){
						String number=cursor.getString(0);
						mNumbers.add(number);
				}
				adapter=new BlackRvAdapter(this,mNumbers,dbHelper);
				LinearLayoutManager manager=new LinearLayoutManager(this);
				manager.setOrientation(LinearLayoutManager.VERTICAL);
				mRecyclerView.setLayoutManager(manager);
				mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
				mRecyclerView.setAdapter(adapter);
		}

		@Override
		public void onClick(View v) {
				showAddDialog();
		}


		//pop-up AlertDialog to add phone num into blacklist
		private void showAddDialog() {
				AlertDialog.Builder builder=new AlertDialog.Builder(this);
				View view= LayoutInflater.from(this).inflate(R.layout.add_to_black_dialog,null);
				builder.setView(view);
				builder.setCancelable(false);
				final AlertDialog dialog=builder.create();
				final EditText inputNumberET= (EditText) view.findViewById(R.id.input_et);
				Button cancelBt= (Button) view.findViewById(R.id.cancle_bt);
				Button ackBt= (Button) view.findViewById(R.id.ack_bt);
				cancelBt.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
								dialog.dismiss();
						}
				});
				ackBt.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
								String number=inputNumberET.getText().toString().trim();
								if (number==null||number.isEmpty()){
										Toast.makeText(BlackListActivity.this,"Please enter a valid phone number",Toast.LENGTH_SHORT).show();
										return;
								}
								if (mNumbers.contains(number)){
										Toast.makeText(BlackListActivity.this,"This number has been added",Toast.LENGTH_SHORT).show();
								}else {
										dbHelper.getWritableDatabase().execSQL("insert into phone values(?)",new String[]{number});
										Toast.makeText(BlackListActivity.this,"Added successfully!",Toast.LENGTH_SHORT).show();
										mNumbers.add(number);
										adapter.notifyDataSetChanged();
										dialog.dismiss();
								}
						}
				});
				dialog.show();
		}
}
