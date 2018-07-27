/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.utilities

import android.content.Context
import codegraft.inject.BootstrapComponent
import codegraft.inject.android.AndroidApplication
import com.google.samples.apps.sunflower.App
import com.google.samples.apps.sunflower.AppComponent
import com.google.samples.apps.sunflower.data.AppDatabase
import com.google.samples.apps.sunflower.data.GardenPlantingDao
import com.google.samples.apps.sunflower.data.GardenPlantingRepository
import com.google.samples.apps.sunflower.data.PlantDao
import com.google.samples.apps.sunflower.data.PlantRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// TODO: temp
val Context.appComponent: AppComponent
    get() {
        val app = applicationContext as App
        return app.bootstrap.component
    }

@BootstrapComponent(
        applicationModules = [DbModule::class],
        flatten = true
)
interface MainComponent {
    val database: AppDatabase
    val plantRepository: PlantRepository
    val gardenPlantingRepository: GardenPlantingRepository
}

@Module
class DbModule {
    @Provides
    @Singleton
    fun provideDatabase(app: AndroidApplication): AppDatabase {
        return AppDatabase.create(app)
    }

    @Provides
    fun providePlantDao(database: AppDatabase): PlantDao {
        return database.plantDao()
    }

    @Provides
    @Singleton
    fun providePlantRepository(plantDao: PlantDao): PlantRepository {
        return PlantRepository.create(plantDao)
    }

    @Provides
    fun provideGardenPlantingDao(database: AppDatabase): GardenPlantingDao {
        return database.gardenPlantingDao()
    }

    @Provides
    @Singleton
    fun provideGardenPlantingRepository(gardenPlantingDao: GardenPlantingDao): GardenPlantingRepository {
        return GardenPlantingRepository.create(gardenPlantingDao)
    }
}
