package tk.qcute.wecheat;


import de.robv.android.xposed.XSharedPreferences;

public class PreferencesUtils {

    private static XSharedPreferences instance = null;

    public static void onZygoteLoad(){
        if (instance == null) {
            instance = new XSharedPreferences(PreferencesUtils.class.getPackage().getName());
            instance.makeWorldReadable();
        } else {
            instance.reload();
        }
    }

    private static XSharedPreferences getInstance() {
        if (instance == null) {
            instance = new XSharedPreferences(PreferencesUtils.class.getPackage().getName());
            instance.makeWorldReadable();
        } else {
            instance.reload();
        }
        return instance;
    }


    public static boolean open(){return getInstance().getBoolean("open",false);}

    public static boolean wechat(){return getInstance().getBoolean("wechat",false);}

    public static boolean qq(){return getInstance().getBoolean("qq",false);}

    public static boolean alipay(){return getInstance().getBoolean("alipay",false);}

    public static boolean weibo(){return getInstance().getBoolean("weibo",false);}

    public static String rate(){return getInstance().getString("rate","10");}

    public static boolean log(){return getInstance().getBoolean("log",false);}
}


