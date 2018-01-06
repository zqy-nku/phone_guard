package com.example.administrator.app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.internal.telephony.ITelephony;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhoneHandleService extends Service {
		int i=0;
		WindowManager windowManager;
		View warningView;
		final String TAG="PhoneHandleService";
		MyDataBaseHelper  dbHelper;
		public PhoneHandleService() {
		}

		@Override
		public void onCreate() {
				super.onCreate();
				Log.v("AAA","onCreate");
				dbHelper=new MyDataBaseHelper(this,"BlackList.db3",1);
		}

		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
				String incomingNumber=intent.getStringExtra("number");
				//check whether the phone number is in the blacklist
				if (queryBlackList(incomingNumber)){
						//reject call
						rejectCall();
						//add phone number and time into database
						SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						Date date=new Date(System.currentTimeMillis());
						String str=format.format(date);
						dbHelper.getWritableDatabase().execSQL("insert into record values(?,?)",new String[]{incomingNumber,str});
						return START_NOT_STICKY;
				}
				//check whether the phone number is in the contact list
				if (queryContacts(incomingNumber)){
						return START_NOT_STICKY;
				}
				//pop up graphic warning message
				showWarningView(incomingNumber);
				return START_NOT_STICKY;
		}

		private void showWarningView(final String number) {
				Button addToBlackListBt;
				Button ackBt;
				TextView tv;
				ImageView imageView;
				windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
				//initialize the warningView of graphic warning message
				warningView = LayoutInflater.from(this).inflate(R.layout.warning_view,null);
				imageView= (ImageView) warningView.findViewById(R.id.warning_bg_iv);
				SimpleDateFormat format=new SimpleDateFormat("ss");
				Date date=new Date(System.currentTimeMillis());
				String str=format.format(date);
				//load widget image
				Picasso.with(this).load(ImageUrls.imageUrls[Integer.parseInt(str)%ImageUrls.imageUrls.length]).error(getResources().getDrawable(R.drawable.logo)).into(imageView);
				//setOnClickListener
				addToBlackListBt= (Button) warningView.findViewById(R.id.add_to_list_bt);
				ackBt= (Button) warningView.findViewById(R.id.ack_bt);
				tv= (TextView) warningView.findViewById(R.id.warning_msg_tv);
				tv.setText("Phone Number of Incoming Call："+number+"\n"+tv.getText().toString());
				addToBlackListBt.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
								dbHelper.getWritableDatabase().execSQL("insert into phone values(?)",new String[]{number});
								rejectCall();
								onDestroy();
						}
				});
				ackBt.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							onDestroy();
						}
				});
				WindowManager.LayoutParams params = new WindowManager.LayoutParams(
						WindowManager.LayoutParams.WRAP_CONTENT,
						WindowManager.LayoutParams.WRAP_CONTENT,
						WindowManager.LayoutParams.TYPE_PHONE,
						WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
						PixelFormat.TRANSLUCENT);

				params.gravity = Gravity.CENTER;
				params.x = 0;
				params.y = 100;
				//Add params into windowManager to display params
				windowManager.addView(warningView, params);
				setViewTouchListener(params);
		}

		@Override
		public void onDestroy() {
				super.onDestroy();
				dbHelper.close();
				windowManager.removeViewImmediate(warningView);
		}

		@Override
		public IBinder onBind(Intent intent) {
				// TODO: Return the communication channel to the service.
				return null;
		}

		//when the incoming number is in the contact list
		boolean queryContacts(String incomingNumber){
				//query the database
				Cursor cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
				while (cursor.moveToNext()){
						String number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						if (number.equals(incomingNumber)){
								return true;
						}
				}
				return false;
		}

		//when the incoming number is in the blacklist
		boolean queryBlackList(String incomingNumber){
				//query the database
				Cursor cursor=dbHelper.getWritableDatabase().rawQuery("select * from phone",new String[]{});
				while (cursor.moveToNext()){
						String number=cursor.getString(0);
						if (incomingNumber.equals(number)){
								return true;
						}
				}
				return false;
		}

		//reject the call by using Reflection
		public void rejectCall() {
				try {
					// acquire ServiceManager and the method in the ServiceManager
						Method method = Class.forName("android.os.ServiceManager")
								.getMethod("getService", String.class);
					//Using Reflection to invoke method
						IBinder binder = (IBinder) method.invoke(null, new Object[]{Context.TELEPHONY_SERVICE});
						ITelephony telephony = ITelephony.Stub.asInterface(binder);
						telephony.endCall();
				} catch (NoSuchMethodException e) {
						Log.v(TAG, e.toString());
				} catch (ClassNotFoundException e) {
						Log.v(TAG ,e.toString());
				} catch (Exception e) {
						Log.v(TAG,e.toString());
				}
		}

		//respond to touch events: to drag the image: params
		private void setViewTouchListener(final WindowManager.LayoutParams params) {
				warningView.setOnTouchListener(new View.OnTouchListener() {
						private int initialX;
						private int initialY;
						private float initialTouchX;
						private float initialTouchY;

						@Override
						public boolean onTouch(View v, MotionEvent event) {
								switch (event.getAction()) {
										case MotionEvent.ACTION_DOWN:
												initialX = params.x;
												initialY = params.y;
												initialTouchX = event.getRawX();
												initialTouchY = event.getRawY();
												return true;
										case MotionEvent.ACTION_UP:
												return true;
										case MotionEvent.ACTION_MOVE:
												params.x = initialX
														+ (int) (event.getRawX() - initialTouchX);
												params.y = initialY
														+ (int) (event.getRawY() - initialTouchY);
												windowManager.updateViewLayout(warningView, params);
												return true;
								}
								return false;
						}
				});
		}
}
