// Generated code from Butter Knife. Do not modify!
package com.stockone.pod.view.pages;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.airbnb.lottie.LottieAnimationView;
import com.stockone.pod.R;
import com.wang.avi.AVLoadingIndicatorView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Splash_ViewBinding implements Unbinder {
  private Splash target;

  @UiThread
  public Splash_ViewBinding(Splash target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Splash_ViewBinding(Splash target, View source) {
    this.target = target;

    target.bg_splash = Utils.findRequiredViewAsType(source, R.id.bg_splash, "field 'bg_splash'", ImageView.class);
    target.lottie = Utils.findRequiredViewAsType(source, R.id.lottie_splash, "field 'lottie'", LottieAnimationView.class);
    target.avi = Utils.findRequiredViewAsType(source, R.id.avi_splash, "field 'avi'", AVLoadingIndicatorView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Splash target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.bg_splash = null;
    target.lottie = null;
    target.avi = null;
  }
}
