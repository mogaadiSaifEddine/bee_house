package com.beehopuse.service;

import com.beehopuse.model.Farm;
import com.beehopuse.model.User;
import java.util.List;

public interface FarmService {
    Farm createFarm(Farm farm);
    Farm updateFarm(Farm farm);
    void deleteFarm(Long id);
    Farm getFarmById(Long id);
    List<Farm> getFarmsByOwner(User owner);
    List<Farm> getAllFarms();
} 