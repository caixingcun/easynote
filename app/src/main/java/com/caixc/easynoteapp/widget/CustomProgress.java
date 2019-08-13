package com.caixc.easynoteapp.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.WindowManager;
import com.caixc.easynoteapp.R;
import com.caixc.easynoteapp.util.LogUtils;
import com.ldoublem.loadingviewlib.view.LVFinePoiStar;

/**
 * @author jerry.Guan
 * @date 2016/8/31
 */
public class CustomProgress extends Dialog {
    public CustomProgress(Context context) {
        super(context);
    }

    public CustomProgress(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        LogUtils.debug(hasFocus + " hasFocus");
        LVFinePoiStar star = (LVFinePoiStar) findViewById(R.id.lv_fine_poi_star);
        // 获取ImageView上的动画背景
        star.setCircleColor(Color.RED);
        star.setDrawPath(true);
        star.setViewColor(Color.WHITE);
        star.startAnim();
        // 开始动画
    }



    /**
     * 弹出自定义ProgressDialog
     *
     * @param context
     *            上下文
     * @param message
     *            提示
     * @return
     */
    public static CustomProgress build(Context context, CharSequence message) {
        LogUtils.debug("CustomProgress build");
        CustomProgress dialog = new CustomProgress(context, R.style.Custom_Progress);
        dialog.setTitle("");
        dialog.setContentView(R.layout.progress_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        // 设置居中
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        return dialog;
    }

}
