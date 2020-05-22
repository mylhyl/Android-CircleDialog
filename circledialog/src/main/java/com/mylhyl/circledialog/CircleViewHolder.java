package com.mylhyl.circledialog;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.util.SparseArray;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;

import com.mylhyl.circledialog.internal.Controller;

/**
 * Created by hupei on 2020/5/22.
 */
public class CircleViewHolder {

    private final View dialogView;
    private final SparseArray<View> views;

    public CircleViewHolder(View dialogView) {
        this.dialogView = dialogView;
        this.views = new SparseArray<>();
    }

    public CircleViewHolder setBackgroundColor(@ColorInt int color) {
        dialogView.setBackgroundColor(color);
        return this;
    }

    public CircleViewHolder setBackgroundRes(@DrawableRes int backgroundRes) {
        dialogView.setBackgroundResource(backgroundRes);
        return this;
    }

    public CircleViewHolder setBackgroundDrawable(Drawable background) {
        if (Controller.SDK_JELLY_BEAN) {
            dialogView.setBackground(background);
        } else {
            dialogView.setBackgroundDrawable(background);
        }
        return this;
    }

    public View getDialogView() {
        return dialogView;
    }

    public CircleViewHolder setText(@IdRes int viewId, CharSequence value) {
        TextView view = findViewById(viewId);
        view.setText(value);
        return this;
    }

    public CircleViewHolder setText(@IdRes int viewId, @StringRes int strId) {
        TextView view = findViewById(viewId);
        view.setText(strId);
        return this;
    }

    public CircleViewHolder setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ImageView view = findViewById(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    public CircleViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        View view = findViewById(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public CircleViewHolder setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes) {
        View view = findViewById(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public CircleViewHolder setBackgroundDrawable(@IdRes int viewId, Drawable background) {
        View view = findViewById(viewId);
        if (Controller.SDK_JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
        return this;
    }

    public CircleViewHolder setVisibility(@IdRes int viewId, int visibility) {
        View view = findViewById(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public CircleViewHolder setChecked(@IdRes int viewId, boolean checked) {
        View view = findViewById(viewId);
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(checked);
        }
        return this;
    }

    public CircleViewHolder setEnabled(@IdRes int viewId, boolean enable) {
        View view = findViewById(viewId);
        view.setEnabled(enable);
        return this;
    }

    public CircleViewHolder addTextChangedBeforeListener(@IdRes int viewId, final TextWatcherBefore textWatcherBefore) {
        View view = findViewById(viewId);
        if (view instanceof TextView) {
            ((TextView) view).addTextChangedListener(new android.text.TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    textWatcherBefore.beforeTextChanged(s, start, count, after);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
        return this;
    }

    public CircleViewHolder addTextChangedListener(@IdRes int viewId, final TextWatcher textWatcher) {
        View view = findViewById(viewId);
        if (view instanceof TextView) {
            addTextChangedListener(((TextView) view), textWatcher);
        }
        return this;
    }


    public CircleViewHolder addTextChangedListener(TextView textView, final TextWatcher textWatcher) {
        textView.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textWatcher.onTextChanged(s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return this;
    }

    public CircleViewHolder addTextChangedAfterListener(@IdRes int viewId, final TextWatcherAfter textWatcherAfter) {
        View view = findViewById(viewId);
        if (view instanceof TextView) {
            ((TextView) view).addTextChangedListener(new android.text.TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    textWatcherAfter.afterTextChanged(s);
                }
            });
        }
        return this;
    }


    public <T extends View> T findViewById(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = dialogView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }


    public interface TextWatcherBefore {
        void beforeTextChanged(CharSequence s, int start, int count, int after);
    }

    public interface TextWatcher {
        void onTextChanged(CharSequence s, int start, int before, int count);
    }

    public interface TextWatcherAfter {
        void afterTextChanged(Editable s);
    }
}
