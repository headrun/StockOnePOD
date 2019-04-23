// Generated code from Butter Knife. Do not modify!
package com.stockone.pod.utils;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.stockone.pod.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SwipeRefreshRecyclerView_ViewBinding implements Unbinder {
  private SwipeRefreshRecyclerView target;

  @UiThread
  public SwipeRefreshRecyclerView_ViewBinding(SwipeRefreshRecyclerView target) {
    this(target, target);
  }

  @UiThread
  public SwipeRefreshRecyclerView_ViewBinding(SwipeRefreshRecyclerView target, View source) {
    this.target = target;

    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recycler, "field 'recyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SwipeRefreshRecyclerView target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.swipeRefreshLayout = null;
    target.recyclerView = null;
  }
}
