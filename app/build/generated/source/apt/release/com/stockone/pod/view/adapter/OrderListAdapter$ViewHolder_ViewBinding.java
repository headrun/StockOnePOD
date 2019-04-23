// Generated code from Butter Knife. Do not modify!
package com.stockone.pod.view.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.stockone.pod.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderListAdapter$ViewHolder_ViewBinding implements Unbinder {
  private OrderListAdapter.ViewHolder target;

  @UiThread
  public OrderListAdapter$ViewHolder_ViewBinding(OrderListAdapter.ViewHolder target, View source) {
    this.target = target;

    target.loan_proposal_id = Utils.findRequiredViewAsType(source, R.id.loan_proposal_id, "field 'loan_proposal_id'", TextView.class);
    target.customer_name = Utils.findRequiredViewAsType(source, R.id.customer_name, "field 'customer_name'", TextView.class);
    target.pod_status = Utils.findRequiredViewAsType(source, R.id.pod_status, "field 'pod_status'", TextView.class);
    target.sl_no = Utils.findRequiredViewAsType(source, R.id.sl_no, "field 'sl_no'", TextView.class);
    target.list_serial_no = Utils.findRequiredViewAsType(source, R.id.list_serial_no, "field 'list_serial_no'", TextView.class);
    target.img_not_uploaded = Utils.findRequiredViewAsType(source, R.id.img_not_uploaded, "field 'img_not_uploaded'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderListAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.loan_proposal_id = null;
    target.customer_name = null;
    target.pod_status = null;
    target.sl_no = null;
    target.list_serial_no = null;
    target.img_not_uploaded = null;
  }
}
