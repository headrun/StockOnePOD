// Generated code from Butter Knife. Do not modify!
package com.stockone.pod.view.pages;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.stockone.pod.R;
import com.wang.avi.AVLoadingIndicatorView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Login_ViewBinding implements Unbinder {
  private Login target;

  private View view2131296310;

  private View view2131296304;

  private View view2131296546;

  @UiThread
  public Login_ViewBinding(Login target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Login_ViewBinding(final Login target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_sign_in, "field 'btnSignIn' and method 'onSignClicked'");
    target.btnSignIn = Utils.castView(view, R.id.btn_sign_in, "field 'btnSignIn'", CardView.class);
    view2131296310 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSignClicked();
      }
    });
    target.avi = Utils.findRequiredViewAsType(source, R.id.avi_login, "field 'avi'", AVLoadingIndicatorView.class);
    target.otp_parent = Utils.findRequiredViewAsType(source, R.id.otp_rl, "field 'otp_parent'", RelativeLayout.class);
    target.number_parent = Utils.findRequiredViewAsType(source, R.id.phone_rl, "field 'number_parent'", RelativeLayout.class);
    target.phoneNo = Utils.findRequiredViewAsType(source, R.id.edt_phone_no, "field 'phoneNo'", EditText.class);
    target.otpNo = Utils.findRequiredViewAsType(source, R.id.edt_otp, "field 'otpNo'", EditText.class);
    target.avi_otp = Utils.findRequiredViewAsType(source, R.id.avi_otp, "field 'avi_otp'", AVLoadingIndicatorView.class);
    target.img_login = Utils.findRequiredViewAsType(source, R.id.img_login, "field 'img_login'", ImageView.class);
    target.text_phone = Utils.findRequiredViewAsType(source, R.id.text_phone, "field 'text_phone'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_confirm, "method 'onConfirmOTP'");
    view2131296304 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onConfirmOTP();
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_resend_otp, "method 'onResendOTP'");
    view2131296546 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onResendOTP();
      }
    });

    Context context = source.getContext();
    target.bar_color = ContextCompat.getColor(context, R.color.login_text);
  }

  @Override
  @CallSuper
  public void unbind() {
    Login target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnSignIn = null;
    target.avi = null;
    target.otp_parent = null;
    target.number_parent = null;
    target.phoneNo = null;
    target.otpNo = null;
    target.avi_otp = null;
    target.img_login = null;
    target.text_phone = null;

    view2131296310.setOnClickListener(null);
    view2131296310 = null;
    view2131296304.setOnClickListener(null);
    view2131296304 = null;
    view2131296546.setOnClickListener(null);
    view2131296546 = null;
  }
}
