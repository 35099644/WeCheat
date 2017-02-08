# WeCheat

## 1. Explain
* Sport Cheater(WeChat/QQ/AliPay/Weibo/...).
* Using Xposed Hook Technology.

## 2. Development Dependency

* Xposed Bridge v82 or later.
* Xposed Framework v82 or later.
* Android 5.x or above.

## 3. Source Files

* WeCheat.java
<br>Xposed Entry.

* PreferencesUtils.java
<br>Preferences Read/Write Utilities.

* SettingsActivity.java
<br>Settings UI.

## 4. Development Settings

* Desktop icon are hidden by default<br>
file: app\src\main\AndroidManifest.xml<br>

>&emsp;de.robv.android.xposed.category.MODULE_SETTINGS

<br>

* Shrink Resources<br>
file: app\build.gradle<br>

>&emsp;shrinkResources true

<br>

* Minify Enabled<br>
file: app\build.gradle<br>

>&emsp;minifyEnabled true

<br>

* Signing Configs<br>
file: app\build.gradle<br>

>&emsp;debug {<br>&emsp;
&emsp;    storeFile file("your.jks")<br>&emsp;
&emsp;    storePassword "your_password"<br>&emsp;
&emsp;    keyAlias "your_key_alias"<br>&emsp;
&emsp;    keyPassword "your_key_password"<br>
&emsp;}<br>
&emsp;release {<br>&emsp;
&emsp;    storeFile file("your.jks")<br>&emsp;
&emsp;    storePassword "your_password"<br>&emsp;
&emsp;    keyAlias "your_key_alias"<br>&emsp;
&emsp;    keyPassword "your_key_password"<br>
&emsp;}<br><br>
&emsp;signingConfig signingConfigs.release


<br>

* Keep Class<br>
file: app\proguard-rules.pro<br>

>&emsp;-keep class tk.qcute.wecheat.* { *; }