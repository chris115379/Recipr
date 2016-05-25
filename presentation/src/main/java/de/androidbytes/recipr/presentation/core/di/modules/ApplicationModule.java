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
package de.androidbytes.recipr.presentation.core.di.modules;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import dagger.Module;
import dagger.Provides;
import de.androidbytes.recipr.data.repository.domain.*;
import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import de.androidbytes.recipr.domain.repository.*;
import de.androidbytes.recipr.presentation.R;
import de.androidbytes.recipr.presentation.ReciprApplication;
import de.androidbytes.recipr.presentation.core.execution.UiThread;
import de.androidbytes.recipr.presentation.core.execution.WorkerThread;

import javax.inject.Singleton;

@Module
public class ApplicationModule {

    private final ReciprApplication reciprApplication;

    public ApplicationModule(ReciprApplication reciprApplication) {
        this.reciprApplication = reciprApplication;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.reciprApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    ExecutionThread provideExecutionThread(WorkerThread workerThread) {
        return workerThread;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UiThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    ContentResolver provideContentResolver(Context context) {
        return context.getContentResolver();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(reciprApplication);
    }

    @Provides
    @Singleton
    RecipeOverviewRepository provideRecipeOverviewRepository(RecipeDomainOverviewRepository recipeDataOverviewRepository) {
        return recipeDataOverviewRepository;
    }

    @Provides
    @Singleton
    RecipeDetailRepository provideRecipeDetailRepository(RecipeDomainDetailRepository recipeDomainDetailRepository) {
        return recipeDomainDetailRepository;
    }

    @Provides
    @Singleton
    CategoryRepository provideCategoryRepository(CategoryDomainRepository categoryDomainRepository) {
        return categoryDomainRepository;
    }

    @Provides
    @Singleton
    SaveRecipeRepository provideSaveRecipeRepository(SaveRecipeDomainRepository saveRecipeRepository) {
        return saveRecipeRepository;
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository(UserDomainRepository userRepository) {
        return userRepository;
    }

    @Provides
    @Singleton
    Tracker provideAnalyticsTracker(Context context) {
        return GoogleAnalytics.getInstance(context).newTracker(R.xml.global_tracker);
    }
}
