package com.example.administrator.app;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

		Button hmdBt; // button of Blacklist
		Button txlBt; // button of contactlist
		Button bljBt; // button of being Intercepted

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_home);
				//initial Button
				initView();

		}

		private void initView() {

			hmdBt= (Button) findViewById(R.id.home_hmd_bt);
			//set drawable size
			Drawable drawable=getResources().getDrawable(R.drawable.blacklist);
			drawable.setBounds(0,0,100,100);
			hmdBt.setCompoundDrawables(drawable,null,null,null);

			txlBt= (Button) findViewById(R.id.home_txl_bt);
			//set drawable size
			Drawable drawable1=getResources().getDrawable(R.drawable.contacts);
			drawable1.setBounds(0,0,100,100);
			txlBt.setCompoundDrawables(drawable1,null,null,null);

			bljBt= (Button) findViewById(R.id.home_blj_bt);
			//set drawable size
			Drawable drawable2=getResources().getDrawable(R.drawable.intercepted);
			drawable2.setBounds(0,0,100,100);
			bljBt.setCompoundDrawables(drawable2,null,null,null);
				//Bind monitor event
				hmdBt.setOnClickListener(this);
				txlBt.setOnClickListener(this);
				bljBt.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
				Intent intent;
				switch ( v.getId()){
						case R.id.home_hmd_bt:
								//Entry blacklist activity
								intent=new Intent(HomeActivity.this,BlackListActivity.class);
								startActivity(intent);
								break;
						case R.id.home_txl_bt:
								//Entry contact activity
								intent=new Intent(HomeActivity.this,ContactsActivity.class);
								startActivity(intent);
								break;
						case R.id.home_blj_bt:
								//Entry intercepted activity
								intent=new Intent(HomeActivity.this,InterceptedActivity.class);
								startActivity(intent);
								break;
				}
		}
}
