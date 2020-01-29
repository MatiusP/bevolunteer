package org.dzedzich.volunteers.tasks.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by alexscrobot on 16.05.17.
 */
@Scope
@Retention(RetentionPolicy.CLASS)
public @interface TaskScope {
}
