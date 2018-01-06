package com.example.administrator.app;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneReceiver extends BroadcastReceiver {

		Context mContext;

		TelephonyManager tm;

		final  String TAG="AAA";

		@Override
		public void onReceive(Context context, Intent intent){
				//phone status has been changed
				mContext=context;
				System.out.println("action"+intent.getAction());
				if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
						//If it is outgoing call
						Log.v(TAG,"Outgoing call");
				}else{
						//we searched some Android documents，and found that there are no special actions for receiving incoming calls
					   // so... non-outgoing calls are incoming calss
						Log.v(TAG,"Register listeners");
						tm= (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);
						tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
				}
		}
		PhoneStateListener listener=new PhoneStateListener(){
				@Override
				public void onCallStateChanged(int state, String incomingNumber) {
						Log.v("AAA","phone status has been changed");
						// TODO Auto-generated method stub
						//state current state incomingNumber, it seems that there is no API for outgoing
						super.onCallStateChanged(state, incomingNumber);
						switch(state) {
								case TelephonyManager.CALL_STATE_IDLE:
										Log.v(TAG, "Reject");
										break;
								case TelephonyManager.CALL_STATE_OFFHOOK:
										Log.v(TAG,  "Answer");
										break;
								case TelephonyManager.CALL_STATE_RINGING:
										Log.v(TAG,  "Receive the call");
										System.out.println(incomingNumber);
										Intent intent=new Intent(mContext,PhoneHandleService.class);
										intent.putExtra("number",incomingNumber);
										mContext.startService(intent);
										break;
						}
						//To avoid creating multiple listeners, we cancel the listener after processing
						tm.listen(listener,LISTEN_NONE);
				}
		};

}

