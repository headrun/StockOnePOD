// Generated code from Butter Knife. Do not modify!
package com.stockone.pod.view.pages;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.stockone.pod.R;
import com.wang.avi.AVLoadingIndicatorView;
import com.weiwangcn.betterspinner.library.BetterSpinner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderDetails_ViewBinding implements Unbinder {
  private OrderDetails target;

  private View view2131296379;

  private View view2131296383;

  private View view2131296306;

  private View view2131296458;

  @UiThread
  public OrderDetails_ViewBinding(OrderDetails target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OrderDetails_ViewBinding(final OrderDetails target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_order_details, "field 'toolbar'", Toolbar.class);
    target.img_order_details = Utils.findRequiredViewAsType(source, R.id.img_order_details, "field 'img_order_details'", ImageView.class);
    target.img_id_card = Utils.findRequiredViewAsType(source, R.id.img_id_card, "field 'img_id_card'", ImageView.class);
    target.img_ackldgmnt_slip = Utils.findRequiredViewAsType(source, R.id.img_ackldgmnt_slip, "field 'img_ackldgmnt_slip'", ImageView.class);
    target.loan = Utils.findRequiredViewAsType(source, R.id.loan_id, "field 'loan'", EditText.class);
    target.order_customer = Utils.findRequiredViewAsType(source, R.id.order_customer, "field 'order_customer'", EditText.class);
    target.model = Utils.findRequiredViewAsType(source, R.id.model_desc, "field 'model'", EditText.class);
    target.order_district = Utils.findRequiredViewAsType(source, R.id.order_district, "field 'order_district'", EditText.class);
    target.mob_number = Utils.findRequiredViewAsType(source, R.id.mob_number, "field 'mob_number'", EditText.class);
    target.serial = Utils.findRequiredViewAsType(source, R.id.serial_no, "field 'serial'", EditText.class);
    view = Utils.findRequiredView(source, R.id.img_ackldgmnt_click, "field 'img_ackldgmnt_click' and method 'onAcknowledgmentCliked'");
    target.img_ackldgmnt_click = Utils.castView(view, R.id.img_ackldgmnt_click, "field 'img_ackldgmnt_click'", ImageView.class);
    view2131296379 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onAcknowledgmentCliked();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_id_click, "field 'img_id_click' and method 'onIDClicked'");
    target.img_id_click = Utils.castView(view, R.id.img_id_click, "field 'img_id_click'", ImageView.class);
    view2131296383 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onIDClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_order_submit, "field 'btn_order_submit' and method 'onOrderSubmission'");
    target.btn_order_submit = Utils.castView(view, R.id.btn_order_submit, "field 'btn_order_submit'", Button.class);
    view2131296306 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onOrderSubmission();
      }
    });
    target.avi = Utils.findRequiredViewAsType(source, R.id.avi_order, "field 'avi'", AVLoadingIndicatorView.class);
    target.btn_order_submitted = Utils.findRequiredViewAsType(source, R.id.btn_order_submitted, "field 'btn_order_submitted'", Button.class);
    target.spinner1 = Utils.findRequiredViewAsType(source, R.id.spinner1, "field 'spinner1'", BetterSpinner.class);
    target.rl_proof = Utils.findRequiredViewAsType(source, R.id.rl_proof, "field 'rl_proof'", RelativeLayout.class);
    target.txt_id_proof_no = Utils.findRequiredViewAsType(source, R.id.txt_id_proof_no, "field 'txt_id_proof_no'", TextView.class);
    target.et_id_proof_no = Utils.findRequiredViewAsType(source, R.id.et_id_proof_no, "field 'et_id_proof_no'", EditText.class);
    target.id_type_text = Utils.findRequiredViewAsType(source, R.id.id_type_text, "field 'id_type_text'", TextView.class);
    target.alter_number = Utils.findRequiredViewAsType(source, R.id.alter_number, "field 'alter_number'", EditText.class);
    view = Utils.findRequiredView(source, R.id.scan_qr, "field 'scan_qr' and method 'onQrScannedClicked'");
    target.scan_qr = Utils.castView(view, R.id.scan_qr, "field 'scan_qr'", CardView.class);
    view2131296458 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onQrScannedClicked();
      }
    });

    Context context = source.getContext();
    target.bg = ContextCompat.getDrawable(context, R.drawable.bg);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderDetails target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.img_order_details = null;
    target.img_id_card = null;
    target.img_ackldgmnt_slip = null;
    target.loan = null;
    target.order_customer = null;
    target.model = null;
    target.order_district = null;
    target.mob_number = null;
    target.serial = null;
    target.img_ackldgmnt_click = null;
    target.img_id_click = null;
    target.btn_order_submit = null;
    target.avi = null;
    target.btn_order_submitted = null;
    target.spinner1 = null;
    target.rl_proof = null;
    target.txt_id_proof_no = null;
    target.et_id_proof_no = null;
    target.id_type_text = null;
    target.alter_number = null;
    target.scan_qr = null;

    view2131296379.setOnClickListener(null);
    view2131296379 = null;
    view2131296383.setOnClickListener(null);
    view2131296383 = null;
    view2131296306.setOnClickListener(null);
    view2131296306 = null;
    view2131296458.setOnClickListener(null);
    view2131296458 = null;
  }
}
