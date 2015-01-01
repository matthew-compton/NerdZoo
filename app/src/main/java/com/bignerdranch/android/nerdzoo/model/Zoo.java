package com.bignerdranch.android.nerdzoo.model;

import com.bignerdranch.android.nerdzoo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Zoo {

    private List<Animal> mAnimalList;

    @Inject
    public Zoo() {
        setupInitialList();
    }

    private void setupInitialList() {
        mAnimalList = new ArrayList<Animal>();
    }

    public Animal findAnimalById(UUID id) {
        for (Animal animal : mAnimalList) {
            if (id.equals(animal.getId())) {
                return animal;
            }
        }
        return null;
    }

    public int findPositionById(UUID id) {
        for (int i = 0; i < mAnimalList.size(); i++) {
            Animal animal = mAnimalList.get(i);
            if (id.equals(animal.getId())) {
                return i;
            }
        }
        return 0;
    }

    public int size() {
        return mAnimalList.size();
    }

    public Animal get(int position) {
        return mAnimalList.get(position);
    }

    public void add() {
        mAnimalList.add(generateRandomAnimal());
    }

    public void remove(int position) {
        mAnimalList.remove(position);
    }

    public void clear() {
        mAnimalList.clear();
    }

    private Animal generateRandomAnimal() {
        Random r = new Random();
        int random = r.nextInt(6) + 1;
        switch (random) {
            case 1:
                return new Animal(UUID.randomUUID(), R.string.animal_lion, R.string.animal_lion_description, R.drawable.animal_lion);
            case 2:
                return new Animal(UUID.randomUUID(), R.string.animal_tiger, R.string.animal_tiger_description, R.drawable.animal_tiger);
            case 3:
                return new Animal(UUID.randomUUID(), R.string.animal_cheetah, R.string.animal_cheetah_description, R.drawable.animal_cheetah);
            case 4:
                return new Animal(UUID.randomUUID(), R.string.animal_leopard, R.string.animal_leopard_description, R.drawable.animal_leopard);
            case 5:
                return new Animal(UUID.randomUUID(), R.string.animal_lynx, R.string.animal_lynx_description, R.drawable.animal_lynx);
            case 6:
                return new Animal(UUID.randomUUID(), R.string.animal_cat, R.string.animal_cat_description, R.drawable.animal_cat);
            default:
                return null;
        }
    }

}