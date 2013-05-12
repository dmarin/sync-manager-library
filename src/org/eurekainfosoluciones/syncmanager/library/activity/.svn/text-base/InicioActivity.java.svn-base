package org.eurekainfosoluciones.syncmanager.library.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.eurekainfosoluciones.syncmanager.library.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class InicioActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        go();
    }

	public void go() {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
			}
		}, 1500);
	}
}
