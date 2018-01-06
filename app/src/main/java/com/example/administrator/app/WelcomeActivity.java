package com.example.administrator.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

		Button mButton;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_welcome);
				mButton= (Button) findViewById(R.id.wel_start_bt);
				mButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
								//Entry into Main page
								Intent intent=new Intent(WelcomeActivity.this,HomeActivity.class);
								startActivity(intent);
								finish();
						}
				});
		}
}
