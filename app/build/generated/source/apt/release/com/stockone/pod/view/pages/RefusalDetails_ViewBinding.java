// Generated code from Butter Knife. Do not modify!
package com.stockone.pod.view.pages;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.stockone.pod.R;
import com.weiwangcn.betterspinner.library.BetterSpinner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RefusalDetails_ViewBinding implements Unbinder {
  private RefusalDetails target;

  private View view2131296308;

  @UiThread
  public RefusalDetails_ViewBinding(RefusalDetails target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RefusalDetails_ViewBinding(final RefusalDetails target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_refusal_details, "field 'toolbar'", Toolbar.class);
    target.img_refusal_details = Utils.findRequiredViewAsType(source, R.id.img_refusal_details, "field 'img_refusal_details'", ImageView.class);
    target.loan = Utils.findRequiredViewAsType(source, R.id.loan_id, "field 'loan'", EditText.class);
    target.order_customer = Utils.findRequiredViewAsType(source, R.id.order_customer, "field 'order_customer'", EditText.class);
    target.et_reason = Utils.findRequiredViewAsType(source, R.id.et_reason, "field 'et_reason'", EditText.class);
    target.spinner_reason = Utils.findRequiredViewAsType(source, R.id.spinner_reason, "field 'spinner_reason'", BetterSpinner.class);
    view = Utils.findRequiredView(source, R.id.btn_refusal_submit, "field 'btn_refusal_submit' and method 'onRefusalSubmit'");
    target.btn_refusal_submit = Utils.castView(view, R.id.btn_refusal_submit, "field 'btn_refusal_submit'", Button.class);
    view2131296308 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRefusalSubmit();
      }
    });
    target.reason_text = Utils.findRequiredViewAsType(source, R.id.reason_text, "field 'reason_text'", TextView.class);
    target.txt_reason = Utils.findRequiredViewAsType(source, R.id.txt_reason, "field 'txt_reason'", TextView.class);
    target.btn_refusal_submitted = Utils.findRequiredViewAsType(source, R.id.btn_refusal_submitted, "field 'btn_refusal_submitted'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RefusalDetails target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.img_refusal_details = null;
    target.loan = null;
    target.order_customer = null;
    target.et_reason = null;
    target.spinner_reason = null;
    target.btn_refusal_submit = null;
    target.reason_text = null;
    target.txt_reason = null;
    target.btn_refusal_submitted = null;

    view2131296308.setOnClickListener(null);
    view2131296308 = null;
  }
}
