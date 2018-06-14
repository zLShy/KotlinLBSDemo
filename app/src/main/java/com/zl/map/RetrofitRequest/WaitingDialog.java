/*
 * Copyright (c) 2015-2016 Cheng Du YuePao Science And Technology Co.,Ltd. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * YuePao Science And Technology Co.,Ltd.("Confidential Information").
 * You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement
 * you entered into with YuePao.
 */

package com.zl.map.RetrofitRequest;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.zl.map.R;

/**
 * Created by liuyuguo on 2016/5/18.
 */
public class WaitingDialog extends Dialog {
    public WaitingDialog(Context context) {
        super(context);
    }

    public WaitingDialog(Context context, int theme) {
        super(context, theme);
    }

    protected WaitingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    public static class Builder {
        private Context context;
        private String msg;
        private boolean isCancelable = true;

        public Builder(Context context) {
            this.context = context;
        }

        public void setMsg(String msg){
            this.msg = msg;
        }

        public void setCancelable(boolean isCancelable){
            this.isCancelable = isCancelable;
        }

        public WaitingDialog create(){
            final WaitingDialog dialog = new WaitingDialog(context, R.style.WaitingDialog);
            dialog.setContentView(R.layout.waiting_dialog);
            // set the dialog title
            dialog.setCancelable(isCancelable);
            dialog.setCanceledOnTouchOutside(isCancelable);

            TextView msgView = (TextView) dialog.findViewById(R.id.waitingmsg);
            msgView.setText(msg);
            
            return dialog;
        }
    }
}
