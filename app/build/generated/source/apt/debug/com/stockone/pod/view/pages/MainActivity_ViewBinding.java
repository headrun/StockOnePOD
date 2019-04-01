// Generated code from Butter Knife. Do not modify!
package com.stockone.pod.view.pages;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
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

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131296473;

  private View view2131296521;

  private View view2131296408;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    target.img_home = Utils.findRequiredViewAsType(source, R.id.img_home, "field 'img_home'", ImageView.class);
    target.manifest_no = Utils.findRequiredViewAsType(source, R.id.manifest_no, "field 'manifest_no'", EditText.class);
    target.avi = Utils.findRequiredViewAsType(source, R.id.avi_main, "field 'avi'", AVLoadingIndicatorView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_home, "field 'toolbar'", Toolbar.class);
    view = Utils.findRequiredView(source, R.id.search_manifest, "field 'search_manifest' and method 'onSearch'");
    target.search_manifest = Utils.castView(view, R.id.search_manifest, "field 'search_manifest'", CardView.class);
    view2131296473 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSearch();
      }
    });
    target.image_title = Utils.findRequiredViewAsType(source, R.id.image_title, "field 'image_title'", TextView.class);
    target.search_text = Utils.findRequiredViewAsType(source, R.id.search_text, "field 'search_text'", TextView.class);
    view = Utils.findRequiredView(source, R.id.text_update, "field 'text_update' and method 'onUpdateClicked'");
    target.text_update = Utils.castView(view, R.id.text_update, "field 'text_update'", TextView.class);
    view2131296521 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onUpdateClicked();
      }
    });
    target.update_rl = Utils.findRequiredViewAsType(source, R.id.update_rl, "field 'update_rl'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.logout, "method 'onLogout'");
    view2131296408 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onLogout();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.img_home = null;
    target.manifest_no = null;
    target.avi = null;
    target.toolbar = null;
    target.search_manifest = null;
    target.image_title = null;
    target.search_text = null;
    target.text_update = null;
    target.update_rl = null;

    view2131296473.setOnClickListener(null);
    view2131296473 = null;
    view2131296521.setOnClickListener(null);
    view2131296521 = null;
    view2131296408.setOnClickListener(null);
    view2131296408 = null;
  }
}
