/**
 * Copyright (C) 2016 Christoph Hennemann
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
package de.androidbytes.recipr.presentation;

import android.app.Application;
import com.fuck_boilerplate.rx_paparazzo.RxPaparazzo;
import de.androidbytes.recipr.domain.model.User;
import de.androidbytes.recipr.presentation.core.di.components.ApplicationComponent;
import de.androidbytes.recipr.presentation.core.di.components.DaggerApplicationComponent;
import de.androidbytes.recipr.presentation.core.di.modules.ApplicationModule;

/**
 * Custom implementation of the {@link Application} class to expose the
 * {@link ApplicationComponent} for dependency injection.
 */
public class ReciprApplication extends Application {

    private ApplicationComponent component;
    private User loggedInUser;

    @Override
    public void onCreate() {
        super.onCreate();
        RxPaparazzo.register(this);
        initializeInjector();
    }

    private void initializeInjector() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.component;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }
    
    public User getLoggedInUser() {
        return loggedInUser;
    }
}
