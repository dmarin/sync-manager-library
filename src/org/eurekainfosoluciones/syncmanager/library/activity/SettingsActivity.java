package org.eurekainfosoluciones.syncmanager.library.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import org.eurekainfosoluciones.syncmanager.library.R;


/**
 * We want to do an app compatible with Android 2.3 so we will use prefereceActivity instead of
 * PreferenceFragment
 * @author DavidMarin
 *
 */
public class SettingsActivity extends PreferenceActivity {
	/**
	 * Yes I know, addPreferencesFromResource has been deprecated but I do not like the alternative
	 * (extends PrefrenceFragment)
	 */
	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref_general);
	}
}
