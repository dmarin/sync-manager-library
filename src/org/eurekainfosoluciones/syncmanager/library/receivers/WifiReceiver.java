package org.eurekainfosoluciones.syncmanager.library.receivers;

import org.eurekainfosoluciones.syncmanager.library.R;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
/**
 * Receiver to manage the WIFI STATES changes
 * 
 * 
 * @author DavidMarin
 *
 */
public class WifiReceiver extends BroadcastReceiver{
	private final String SYNC_SETTINGS_ACTION = "android.settings.SYNC_SETTINGS";
	/**
	 * onReceive we have to check if the app is enabled or disabled
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		//The user will have the option to enable/disable this app
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		if (sp.getBoolean(context.getResources().getString(R.string.checkbox_enableApp), true)){
			goAhead(context, intent);
		} 
	}

	/**
	 * If the app is enabled ==> enable or disable the sync manager.
	 * @param context
	 * @param intent
	 */
	private void goAhead(Context context, Intent intent) {
		final String action = intent.getAction();
		if (action.equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)) {
			//This is the real one!! but it seems that in some devices is not fully working
			if (intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false)) {
				enableSync(context);
			}
		}
		if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
			NetworkInfo mNetInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
			if (NetworkInfo.State.DISCONNECTED.equals(mNetInfo.getState())){
				createAndSendNotification(context);
				//This check is not required at least in Android 4... but you never know
				if (ContentResolver.getMasterSyncAutomatically())
					ContentResolver.setMasterSyncAutomatically(false);
			} else if (NetworkInfo.State.CONNECTED.equals((mNetInfo.getState()))){
				enableSync(context);
			}
		}
	}
	
	/**
	 * First we will try to remove the notification and then we will disable the MasterSync
	 * @param context
	 */
	private void enableSync(Context context) {
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(
				context.getResources().getString(R.string.notification_tag),
				context.getResources().getInteger(R.integer.notification_id));
		if (!ContentResolver.getMasterSyncAutomatically()) {
			ContentResolver.setMasterSyncAutomatically(true);
		}
	}

	/**
	 * In this method we will create and send the notification
	 * @param context
	 */
	private void createAndSendNotification(Context context) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(
						context.getResources().getString(
								R.string.reenable_sync_notification_title))
				.setContentText(
						context.getResources().getString(
								R.string.reenable_sync_notification_content))
				.setContentIntent(
						PendingIntent.getActivity(context, 0, new Intent(
								SYNC_SETTINGS_ACTION), 0)).setAutoCancel(true);

		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(
				context.getResources().getString(R.string.notification_tag),
				context.getResources().getInteger(R.integer.notification_id),
				mBuilder.build());
	}

}
