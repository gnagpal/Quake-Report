package com.example.android.quakereport;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
    }

    public static class EarthquakePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference minMag = findPreference(getString(R.string.settings_min_magnitude_key));
            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));

            bindPreferenceSummaryToValue(minMag);
            bindPreferenceSummaryToValue(orderBy);
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = sharedPreferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object val) {
            String stringVal = val.toString();

            if(preference instanceof ListPreference){
                ListPreference listPref = (ListPreference) preference;
                int prefndex = listPref.findIndexOfValue(stringVal);
                if(prefndex>=0){
                    CharSequence[] labels = listPref.getEntries();
                    preference.setSummary(labels[prefndex]);
                }
            } else {
                preference.setSummary(stringVal);
            }
            return true;
        }
    }
}
