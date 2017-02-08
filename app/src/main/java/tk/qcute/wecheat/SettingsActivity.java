package tk.qcute.wecheat;


import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;




public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if(savedInstanceState==null) {
            getFragmentManager().beginTransaction().replace(R.id.settings_container, new SettingsFragment()).commit();
        }
    }
    /**
     * A placeholder fragment containing a settings view.
     */
    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getPreferenceManager().setSharedPreferencesMode(MODE_WORLD_READABLE);
            addPreferencesFromResource(R.xml.pref_setting);

            /*
            //module active check
            if(!isModuleActive())
                new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("模块尚未启用，是否重启手机？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            reBoot();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();
                        }
                    }).show();
             */



            final Preference rate=findPreference("rate");
            String value = getPreferenceManager().getSharedPreferences().getString("rate","1000");
            rate.setSummary(value);
            rate.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    rate.setSummary(String.valueOf(newValue));
                    return true;
                }
            });
        }
    }
    /*
    //启用检查
    private static boolean isModuleActive(){return false;}

    //执行命令
    private static boolean reBoot(){
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("reboot");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = bufferedReader.readLine();
            if(line!=null){
                if(line.compareTo("Permission denied")==0){
                    bufferedReader.close();
                    return  false;
                }
            }
            process.waitFor();
        } catch (Exception e) {e.printStackTrace();}
        return  true;
    }
    */
}
