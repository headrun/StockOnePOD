// Generated code from Butter Knife. Do not modify!
package com.stockone.pod.view.pages;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.stockone.pod.R;
import com.wang.avi.AVLoadingIndicatorView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserDetails_ViewBinding implements Unbinder {
  private UserDetails target;

  private View view2131296305;

  @UiThread
  public UserDetails_ViewBinding(UserDetails target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UserDetails_ViewBinding(final UserDetails target, View source) {
    this.target = target;

    View view;
    target.img_user = Utils.findRequiredViewAsType(source, R.id.img_user, "field 'img_user'", ImageView.class);
    target.avi = Utils.findRequiredViewAsType(source, R.id.avi_user, "field 'avi'", AVLoadingIndicatorView.class);
    view = Utils.findRequiredView(source, R.id.btn_continue, "field 'btn_continue' and method 'onButtonContinue'");
    target.btn_continue = Utils.castView(view, R.id.btn_continue, "field 'btn_continue'", CardView.class);
    view2131296305 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonContinue();
      }
    });
    target.et_first_name = Utils.findRequiredViewAsType(source, R.id.et_first_name, "field 'et_first_name'", EditText.class);
    target.et_last_name = Utils.findRequiredViewAsType(source, R.id.et_last_name, "field 'et_last_name'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UserDetails target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.img_user = null;
    target.avi = null;
    target.btn_continue = null;
    target.et_first_name = null;
    target.et_last_name = null;

    view2131296305.setOnClickListener(null);
    view2131296305 = null;
  }
}
