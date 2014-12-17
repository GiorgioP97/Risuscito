/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.cammino.risuscito;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class LUtils {
//    private static final int[] STATE_CHECKED = new int[]{android.R.attr.state_checked};
//    private static final int[] STATE_UNCHECKED = new int[]{};
//
//    private static Typeface sMediumTypeface;

    protected Activity mActivity;
//    private Handler mHandler = new Handler();

    private LUtils(Activity activity) {
        mActivity = activity;
    }

    public static LUtils getInstance(Activity activity) {
        return new LUtils(activity);
    }

    private static boolean hasL() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public void startActivityWithTransition(Intent intent, final View clickedView,
                                            final String transitionName) {
        ActivityOptions options = null;
        if (hasL() && clickedView != null && !TextUtils.isEmpty(transitionName)) {
            options = ActivityOptions.makeSceneTransitionAnimation(
                    mActivity, clickedView, transitionName);
            ActivityCompat.startActivity(mActivity, intent, options.toBundle());
        }
        else {
        	mActivity.startActivity(intent);
        	mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.hold_on);
        }
//        	mActivity.startActivity(intent, (options != null) ? options.toBundle() : null);
    }

    public void startActivityWithFadeIn(Intent intent, final View clickedView,
                                            final String transitionName) {
        ActivityOptions options = null;
        if (hasL() && clickedView != null && !TextUtils.isEmpty(transitionName)) {
            options = ActivityOptions.makeSceneTransitionAnimation(
                    mActivity, clickedView, transitionName);
            ActivityCompat.startActivity(mActivity, intent, options.toBundle());
        }
        else {
            mActivity.startActivity(intent);
            mActivity.overridePendingTransition(R.anim.image_fade_in, R.anim.hold_on);
        }
//        	mActivity.startActivity(intent, (options != null) ? options.toBundle() : null);
    }

    public void closeActivityWithTransition() {
    	if (hasL())
            mActivity.overridePendingTransition(0, R.anim.image_fade_out);
        else
    		mActivity.overridePendingTransition(0, R.anim.slide_out_right);
    }

    public void closeActivityWithFadeOut() {
        mActivity.overridePendingTransition(0, R.anim.image_fade_out);
    }

    public void goFullscreen() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else mActivity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

//    public void setMediumTypeface(TextView textView) {
//        if (hasL()) {
//            if (sMediumTypeface == null) {
//                sMediumTypeface = Typeface.create("sans-serif-medium", Typeface.NORMAL);
//            }
//
//            textView.setTypeface(sMediumTypeface);
//        } else {
//            textView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
//        }
//    }
//
//    public int getStatusBarColor() {
//        if (!hasL()) {
//            // On pre-L devices, you can have any status bar color so long as it's black.
//            return Color.BLACK;
//        }
//
//        return mActivity.getWindow().getStatusBarColor();
//    }
//
//    public void setStatusBarColor(int color) {
//        if (!hasL()) {
//            return;
//        }
//
//        mActivity.getWindow().setStatusBarColor(color);
//    }
//
//    public void setOrAnimatePlusCheckIcon(final ImageView imageView, boolean isCheck,
//                                          boolean allowAnimate) {
//        if (!hasL()) {
//            compatSetOrAnimatePlusCheckIcon(imageView, isCheck, allowAnimate);
//            return;
//        }
//
//        Drawable drawable = imageView.getDrawable();
//        if (!(drawable instanceof AnimatedStateListDrawable)) {
//            drawable = mActivity.getResources().getDrawable(R.drawable.add_schedule_fab_icon_anim);
//            imageView.setImageDrawable(drawable);
//        }
//        imageView.setColorFilter(isCheck ?
//                mActivity.getResources().getColor(R.color.theme_accent_1) : Color.WHITE);
//        if (allowAnimate) {
//            imageView.setImageState(isCheck ? STATE_UNCHECKED : STATE_CHECKED, false);
//            drawable.jumpToCurrentState();
//            imageView.setImageState(isCheck ? STATE_CHECKED : STATE_UNCHECKED, false);
//        } else {
//            imageView.setImageState(isCheck ? STATE_CHECKED : STATE_UNCHECKED, false);
//            drawable.jumpToCurrentState();
//        }
//    }
//
//    public void compatSetOrAnimatePlusCheckIcon(final ImageView imageView, boolean isCheck,
//                                                boolean allowAnimate) {
//
//        final int imageResId = isCheck
//                ? R.drawable.add_schedule_button_icon_checked
//                : R.drawable.add_schedule_button_icon_unchecked;
//
//        if (imageView.getTag() != null) {
//            if (imageView.getTag() instanceof Animator) {
//                Animator anim = (Animator) imageView.getTag();
//                anim.end();
//                imageView.setAlpha(1f);
//            }
//        }
//
//        if (allowAnimate && isCheck) {
//            int duration = mActivity.getResources().getInteger(
//                    android.R.integer.config_shortAnimTime);
//
//            Animator outAnimator = ObjectAnimator.ofFloat(imageView, View.ALPHA, 0f);
//            outAnimator.setDuration(duration / 2);
//            outAnimator.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    imageView.setImageResource(imageResId);
//                }
//            });
//
//            AnimatorSet inAnimator = new AnimatorSet();
//            outAnimator.setDuration(duration);
//            inAnimator.playTogether(
//                    ObjectAnimator.ofFloat(imageView, View.ALPHA, 1f),
//                    ObjectAnimator.ofFloat(imageView, View.SCALE_X, 0f, 1f),
//                    ObjectAnimator.ofFloat(imageView, View.SCALE_Y, 0f, 1f)
//            );
//
//            AnimatorSet set = new AnimatorSet();
//            set.playSequentially(outAnimator, inAnimator);
//            set.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    imageView.setTag(null);
//                }
//            });
//            imageView.setTag(set);
//            set.start();
//        } else {
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    imageView.setImageResource(imageResId);
//                }
//            });
//        }
//    }
}
