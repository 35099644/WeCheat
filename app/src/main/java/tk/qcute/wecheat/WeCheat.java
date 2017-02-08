package tk.qcute.wecheat;

/**
 * Created by BALDOOR on 2016/12/22.
 */

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.SparseArray;

import java.lang.reflect.Field;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.getObjectField;


public class WeCheat implements IXposedHookLoadPackage, IXposedHookZygoteInit {

    //wechat
    private static int stepCountWeChat = 1;
    //qq
    private static int stepCountQQ = 1;
    //alipay
    private  static int stepCountAliPay = 1;
    //weibo
    private  static int stepCountWeiBo = 1;

    @Override
    public void initZygote(IXposedHookZygoteInit.StartupParam startupParam) throws Throwable {
        PreferencesUtils.onZygoteLoad();
    }
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam Param) throws Throwable {

        //package filter
        if(!Param.packageName.contains("tencent")
                && !Param.packageName.contains("Alipay")
                && !Param.packageName.contains("weibo"))return;
        //hook method
        // protected void dispatchSensorEvent(int handle, float[] values, int inAccuracy, long timestamp) { ... }
        findAndHookMethod("android.hardware.SystemSensorManager$SensorEventQueue", Param.classLoader,
                "dispatchSensorEvent", int.class, float[].class, int.class, long.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                //switch control
                if (!PreferencesUtils.open()) return;
                //get sensor
                int handle  = (int)param.args[0];
                Sensor sensor = (Sensor) callMethod(getObjectField(getObjectField(param.thisObject, "mManager"), "mHandleToSensor"), "get", handle);
                if(sensor==null)return;
                //check sensor type   --->   step detector / counter
                if(sensor.getType()!=Sensor.TYPE_STEP_DETECTOR && sensor.getType()!=Sensor.TYPE_STEP_COUNTER)return;
                //change rate
                int rate = Integer.parseInt(PreferencesUtils.rate());
                //we chat
                if (PreferencesUtils.wechat() && Param.packageName.equals("com.tencent.mm")){
                    ((float[]) param.args[1])[0] = ((float) (rate * stepCountWeChat++));
                    log(((float[]) param.args[1])[0],"WeChat");
                }
                //qq
                if (PreferencesUtils.qq() && Param.packageName.equals("com.tencent.mobileqq")) {
                    ((float[]) param.args[1])[0] = ((float) (rate * stepCountQQ++));
                    log(((float[]) param.args[1])[0],"QQ");
                }
                //ali pay
                if (PreferencesUtils.alipay() && Param.packageName.equals("com.eg.android.AlipayGphone")) {
                    ((float[]) param.args[1])[0] = ((float) (rate * stepCountAliPay++));
                    log(((float[]) param.args[1])[0],"AliPay");
                }
                //sina weibo
                if (PreferencesUtils.weibo() && Param.packageName.equals("com.sina.weibo")) {
                    ((float[]) param.args[1])[0] = ((float) (rate * stepCountWeiBo++));
                    log(((float[]) param.args[1])[0],"Weibo");
                }

            }});

        /*
        if(loadPackageParam.packageName.equals("tk.qcute.wecheat")) {
            findAndHookMethod("tk.qcute.wecheat.SettingsActivity",loadPackageParam.classLoader, "isModuleActive", XC_MethodReplacement.returnConstant(true));
        }
        */
    }
    private static void log(float step, String name){
        if (PreferencesUtils.log()) {
            String log = "Step("+name+"): " + step;
            XposedBridge.log(log);
        }
    }
}
