package com.beehopuse.service;

import com.beehopuse.model.Farm;
import com.beehopuse.model.User;
import jakarta.ejb.Remote;

import java.sql.SQLException;
import java.util.List;

@Remote
public interface FarmService {

    public List<Farm> getFarmsByOwner(User owner) throws SQLException;

    public List<Farm> getAllFarms() throws SQLException;

    public Farm getFarmById(Long id) throws SQLException;

    public Farm createFarm(Farm farm) throws SQLException;

    public Farm updateFarm(Farm farm) throws SQLException;

    public void deleteFarm(Long id) throws SQLException;
}