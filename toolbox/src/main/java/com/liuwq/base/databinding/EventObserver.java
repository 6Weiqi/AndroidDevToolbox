package com.liuwq.base.databinding;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.liuwq.base.model.Event;

/**
 * An {@link Observer} for {@link Event}s, simplifying the pattern of checking if the {@link
 * Event}'s content has already been handled.
 * <p>
 * <p>{@link #onEventUnhandledContent(T)} is *only* called if the {@link Event}'s contents has not
 * been handled.
 *
 * @param <T>
 */
public abstract class EventObserver<T> implements Observer<Event<T>> {

    @Override
    public void onChanged(@Nullable Event<T> event) {
        if (event == null) {
            return;
        }
        T content = event.getContentIfNotHandled();
        if (content != null) {
            onEventUnhandledContent(content);
        }
    }

    abstract void onEventUnhandledContent(@NonNull T content);
}
