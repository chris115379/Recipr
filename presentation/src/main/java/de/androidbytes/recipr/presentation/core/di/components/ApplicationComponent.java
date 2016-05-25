/**
 * Copyright (C) 2016 Christoph Hennemann
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.androidbytes.recipr.presentation.core.di.components;

import android.content.ContentResolver;
import android.content.Context;
import com.google.android.gms.analytics.Tracker;
import dagger.Component;
import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import de.androidbytes.recipr.domain.repository.*;
import de.androidbytes.recipr.presentation.core.di.modules.ApplicationModule;
import de.androidbytes.recipr.presentation.core.navigation.Navigator;
import de.androidbytes.recipr.presentation.core.util.GoogleAccountManager;

import javax.inject.Singleton;

/**
 * Dagger Component used to expose application wide dependencies.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context context();
    ExecutionThread executionThread();
    PostExecutionThread postExecutionThread();

    ContentResolver contentResolver();

    Navigator navigator();
    GoogleAccountManager googleAccountManager();

    RecipeOverviewRepository recipeOverviewRepository();
    RecipeDetailRepository recipeDetailRepository();
    CategoryRepository categoryRepository();
    SaveRecipeRepository saveRecipeRepository();
    UserRepository userRepository();

    Tracker tracker();
}
