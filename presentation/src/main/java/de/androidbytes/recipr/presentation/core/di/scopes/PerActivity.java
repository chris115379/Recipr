package de.androidbytes.recipr.presentation.core.di.scopes;


import javax.inject.Scope;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}
