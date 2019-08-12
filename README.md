# StatusBarTheme
Implementing immersive status bar through theme

# 使用主题Theme实现Android沉浸式状态栏
很早的时候，通过主题设置activity沉浸式，发现坑很多，就开始使用各种StatusBarUtils，放弃了主题修改沉浸式这种方式，不知道大家有没有同感。

其实各种StatusBarUtils的做法其实也是通过判断系统版本然后设置主题属性，是一种比直接在设置主题滞后的一种方式。这里讲一种通用的主题加一点好理解的代码实现沉浸式主题，方便更好的理解沉浸式，而且很简单。

**先来看下效果：**

| 系统版本 | 4.4 | 9.0|
|:------------|:------------|:------------|
| 纯色 | <img src="https://github.com/Sting926/StatusBarTheme/blob/master/img/4.4-pure-color.png" width="75%"/> | <img src="https://github.com/Sting926/StatusBarTheme/blob/master/img/9.0-pure-color.png" width="75%"/> |
|渐变色| <img src="https://github.com/Sting926/StatusBarTheme/blob/master/img/4.4-gradient-color.png" width="75%"/> | <img src="https://github.com/Sting926/StatusBarTheme/blob/master/img/9.0-gradient-color.png" width="75%"/>|
| 图片| <img src="https://github.com/Sting926/StatusBarTheme/blob/master/img/4.4-picture.png" width="75%"/> | <img src="https://github.com/Sting926/StatusBarTheme/blob/master/img/9.0-pic.png" width="75%"/> |
|Fragment| <img src="https://github.com/Sting926/StatusBarTheme/blob/master/img/4.4-fragment.png" width="75%"/> | <img src="https://github.com/Sting926/StatusBarTheme/blob/master/img/9.0-fragment.png" width="75%"/> |

## 沉浸式主题属性
接下来，先来熟悉一下有那些沉浸式主题的属性设置：
- `<item name="android:windowTranslucentStatus">true</item>` 这个属性可以使状态栏变透明，同时内容区域可以浸入状态栏下面，从android 4.4增加的新属性，开启了沉浸式主题的大门，所以沉浸式的最小sdk版本是19，但是在android 5.x即sdk 21版本以后该属性并不能保证状态栏完全透明（小米MUI系统是透明的，单独处理过），状态栏为半透明状态，在android 6.0版本上设置该值同时会导致设置状态栏明暗的模式失效，但是半透明的状态栏保证信号电源等图标可以看清...。
- `<item name="android:windowTranslucentNavigation">true</item>`  设置底部导航栏变透明，同时内容区域可以浸入导航栏下面，在android 5.x以上版本设置导航栏透明则状态栏才能完全透明，如果内容区域不希望被导航栏遮挡还需要适配底部导航栏的高度，同时该设置会导致设置导航栏颜色失效，如果只是想实现状态栏沉浸式，该属性可以忽略。。。
- `<item name="android:windowDrawsSystemBarBackgrounds">true</item>` 设置可以修改statusBar背景色，5.x新增属性。AppCompat主题继承了Material.x主题，Material主题包含了该属性所以可以不设置，保险起见还是显示的加上这个属性。
- `<item name="android:statusBarColor">@android:color/transparent</item>` 设置状态栏背景色，5.x新增属性。这个时候`windowTranslucentStatus`必须为false才有效果，通常设置为透明，这样一来虽然状态栏透明了，但是显示内容却不能浸入状态栏背面了，这时需要一段辅助代码助攻实现沉浸式，后面会提到。
- `<item name="android:fitsSystemWindows">true</item> ` 会自动让你的布局paddingTop一个状态栏的高度，在NoActionBar的时候很有用，结合`windowTranslucentStatus = true` 同时设置自定义TitleBar或者ToolBar和根布局的背景色为相同的颜色来达到纯色的沉浸式效果。

用到的Theme属性已经介绍完了，现在看如何通过这些属性创造沉浸式主题：
首先创建BaseTheme `AppTheme` 和 `AppTheme.NoActionBar`，` AppTheme.NoActionBar.TransparentStatusBar`和`AppTheme.NoActionBar.TransparentStatusBar.ImmerseStatusBar`是两个沉浸式主题，继承自`AppTheme.NoActionBar`。
**styles.xml**
```xml
<resources>

    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>

    <style name="AppTheme.NoActionBar">
        <item name="windowActionBar">false</item>
        <item name="windowNoTitle">true</item>
    </style>

    <style name="AppTheme.NoActionBar.TransparentStatusBar"/>

    <style name="AppTheme.NoActionBar.TransparentStatusBar.ImmerseStatusBar"/>

</resources>
```
在sdk版本>=19时，第一种沉浸式主题通过`fitsSystemWindows = true`和`windowTranslucentStatus = true`实现，第二种沉浸式通过`windowTranslucentStatus = true` + `paddingTop=状态栏高度`辅助代码实现
**styles.xml v19**
```xml
<resources>

    <style name="AppTheme.NoActionBar.TransparentStatusBar">
        <item name="android:fitsSystemWindows">true</item>

        <!--华为等手机android版本21及以上设置该值状态栏为半透明状态，并且android 6.0版本设置状态栏明暗的模式会失效，半透明的状态栏保证信号电源等图标可以看清... -->
        <item name="android:windowTranslucentStatus">true</item>

        <!--华为等手机设置导航栏透明则状态栏才能完全透明，如果内容区域不希望被导航栏遮挡还需要适配底部导航栏的高度，同时该设置会导致设置导航栏颜色失效 -->
        <!--<item name="android:windowTranslucentNavigation">true</item>-->
    </style>

    <style name="AppTheme.NoActionBar.TransparentStatusBar.ImmerseStatusBar">
        <item name="android:fitsSystemWindows">false</item>
    </style>
</resources>
```
辅助代码一：设置根布局的paddingTop=状态栏高度
```java

    /**
     * 获取状态栏高度
     *
     * @param context 目标Context
     */
    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
    
    /**
     * 增加View的paddingTop,增加的值为状态栏高度 (智能判断，并设置高度)
     *
     * @param context 目标Context
     * @param view    需要增高的View
     */
    public static void setPaddingTop(Context context, @NonNull View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp != null && lp.height > 0 && view.getPaddingTop() == 0) {
                lp.height += getStatusBarHeight(context);
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(context),
                        view.getPaddingRight(), view.getPaddingBottom());
            }
        }
    }
```
在sdk版本>=21时，第一种沉浸式主题没有定义但是会找v19的主题，此时状态栏是半透明的。第二种沉浸式通过`windowTranslucentStatus = false` + `statusBarColor` + `paddingTop=状态栏高度` + 设置系统UI显示标志位辅助代码实现。
**styles.xml v21**
```xml
<resources>

    <style name="AppTheme.NoActionBar.TransparentStatusBar.ImmerseStatusBar">
        <item name="android:fitsSystemWindows">false</item>
        <item name="android:windowTranslucentStatus">false</item>
        <item name="android:windowDrawsSystemBarBackgrounds">true</item>
        <!--Android 5.x开始需要把颜色设置透明，否则状态栏是半透明颜色-->
        <item name="android:statusBarColor">@android:color/transparent</item>
    </style>
</resources>
```
辅助代码二：判断系统版本>=21，设置系统UI的显示标志位。
```java
    /**
     * 设置沉浸式的SystemUiVisibility
     *
     * @param activity 目标Activity
     */
    public static void setImmerseStatusBarSystemUiVisibility(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int systemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            activity.getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
        }
    }
```
以上这些就足够完成沉浸式的功能，通过主题外加一点代码即可实现，既简单又好理解。

第一种适合纯色状态栏的主题，缺点是5.x版本状态栏是半透明并且切换状态栏的明暗模式可能会失效，但是实现简单不需要再写代码，支付宝的二级页面就是这种主题。

第二种支持渐变色和图片的状态栏，缺点是每个activity需要额外的代码辅助功能实现。

下面是完整的辅助代码，包括sdk>=23时设置状态栏明暗模式：
```java
public class StatusBarUtil {
    
    /**
     * 设置沉浸式的SystemUiVisibility
     *
     * @param activity 目标Activity
     */
    public static void setImmerseStatusBarSystemUiVisibility(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int systemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            activity.getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
        }
    }

    /**
     * 增加View的paddingTop,增加的值为状态栏高度 (智能判断，并设置高度)
     *
     * @param context 目标Context
     * @param view    需要增高的View
     */
    public static void setPaddingTop(Context context, @NonNull View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp != null && lp.height > 0 && view.getPaddingTop() == 0) {
                lp.height += getStatusBarHeight(context);
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(context),
                        view.getPaddingRight(), view.getPaddingBottom());
            }
        }
    }
    
    /**
     * 设置状态栏darkMode,字体颜色及icon变黑(目前支持MIUI6以上,Flyme4以上,Android M以上)
     *
     * @param activity 目标activity
     */
    public static void setDarkMode(@NonNull Activity activity) {
        darkMode(activity.getWindow(), true);
    }

    /**
     * 设置状态栏darkMode,字体颜色及icon变亮(目前支持MIUI6以上,Flyme4以上,Android M以上)
     *
     * @param activity 目标activity
     */
    public static void setLightMode(@NonNull Activity activity) {
        darkMode(activity.getWindow(), false);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static void darkMode(Window window, boolean dark) {
        if (isFlyme4()) {
            setModeForFlyme4(window, dark);
        } else if (isMIUI6()) {
            setModeForMIUI6(window, dark);
        }
        darkModeForM(window, dark);
    }
    
    /**
     * android 6.0设置字体颜色
     *
     * @param window 目标window
     * @param dark   亮色 or 暗色
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private static void darkModeForM(Window window, boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
            if (dark) {
                systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                systemUiVisibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            window.getDecorView().setSystemUiVisibility(systemUiVisibility);
        }
    }

    /**
     * 设置MIUI6+的状态栏的darkMode,darkMode时候字体颜色及icon
     * http://dev.xiaomi.com/doc/p=4769/
     *
     * @param window 目标window
     * @param dark   亮色 or 暗色
     */
    private static void setModeForMIUI6(Window window, boolean dark) {
        Class<? extends Window> clazz = window.getClass();
        try {
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(window, dark ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            Log.e("StatusBar", "darkIcon: failed");
        }

    }

    /**
     * 设置Flyme4+的状态栏的darkMode,darkMode时候字体颜色及icon
     * http://open-wiki.flyme.cn/index.php?title=Flyme%E7%B3%BB%E7%BB%9FAPI
     *
     * @param window 目标window
     * @param dark   亮色 or 暗色
     */
    private static void setModeForFlyme4(Window window, boolean dark) {
        try {
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Exception e) {
            Log.e("StatusBar", "darkIcon: failed");
        }
    }
    
    /**
     * 判断是否Flyme4以上
     */
    private static boolean isFlyme4() {
        return Build.FINGERPRINT.contains("Flyme_OS_4") || Build.VERSION.INCREMENTAL.contains("Flyme_OS_4")
                || Pattern.compile("Flyme OS [4|5]", Pattern.CASE_INSENSITIVE).matcher(Build.DISPLAY).find();
    }

    /**
     * 判断是否MIUI6以上
     */
    private static boolean isMIUI6() {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method mtd = clz.getMethod("get", String.class);
            String val = (String) mtd.invoke(null, "ro.miui.ui.version.name");
            val = val.replaceAll("[vV]", "");
            int version = Integer.parseInt(val);
            return version >= 6;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context 目标Context
     */
    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
}
```

具体效果可以查看demo：

[Demo地址](https://github.com/Sting926/StatusBarTheme)

参考：
[StatusBarUtil](https://github.com/Ye-Miao/StatusBarUtil)
