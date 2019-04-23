// Generated code from Butter Knife. Do not modify!
package com.stockone.pod.view.pages;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.stockone.pod.R;
import com.wang.avi.AVLoadingIndicatorView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderList_ViewBinding implements Unbinder {
  private OrderList target;

  @UiThread
  public OrderList_ViewBinding(OrderList target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OrderList_ViewBinding(OrderList target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.rv_order_list, "field 'recyclerView'", RecyclerView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_order, "field 'toolbar'", Toolbar.class);
    target.img_order = Utils.findRequiredViewAsType(source, R.id.img_order, "field 'img_order'", ImageView.class);
    target.avi = Utils.findRequiredViewAsType(source, R.id.avi_order_list, "field 'avi'", AVLoadingIndicatorView.class);
    target.avi_bottom = Utils.findRequiredViewAsType(source, R.id.avi_bottom, "field 'avi_bottom'", AVLoadingIndicatorView.class);
    target.shimmer_view = Utils.findRequiredViewAsType(source, R.id.shimmer_view, "field 'shimmer_view'", ShimmerFrameLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderList target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.toolbar = null;
    target.img_order = null;
    target.avi = null;
    target.avi_bottom = null;
    target.shimmer_view = null;
  }
}
