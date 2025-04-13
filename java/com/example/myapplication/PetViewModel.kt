package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PetViewModel(application: Application): AndroidViewModel(application) {
    private val repo: PetDBRepository
    var allData: LiveData<List<PetData>>
    val allDog: LiveData<List<PetData>>
    val allCat: LiveData<List<PetData>>
    val allBird: LiveData<List<PetData>>
    val allFish: LiveData<List<PetData>>

    init {
        val dao = PetDB.getMyDB(application).petDao()
        repo = PetDBRepository(dao)
        allData = repo.allPets
        allDog = repo.allDogs
        allCat = repo.allCats
        allBird = repo.allBirds
        allFish = repo.allFishes
    }

    fun addPet(pet: PetData) = viewModelScope.launch(Dispatchers.IO){
        repo.insertRecord(pet)
    }

    fun removePet(pet: PetData) = viewModelScope.launch(Dispatchers.IO){
        repo.deleteRecord(pet)
    }
}