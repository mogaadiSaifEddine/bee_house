package com.beehopuse.service;

import com.beehopuse.model.Site;
import com.beehopuse.model.Farm;
import java.util.List;

public interface SiteService {
    Site createSite(Site site);
    Site updateSite(Site site);
    void deleteSite(Long id);
    Site getSiteById(Long id);
    List<Site> getSitesByFarm(Farm farm);
    List<Site> getAllSites();
} 