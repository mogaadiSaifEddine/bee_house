package com.beehopuse.servlet;

import com.beehopuse.model.Site;
import com.beehopuse.model.Farm;
import com.beehopuse.service.SiteService;
import com.mysql.cj.result.SqlDateValueFactory;
import com.beehopuse.service.FarmService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/sites/*")
public class SiteServlet extends BaseServlet {

    @EJB
    private SiteService siteService;

    @EJB
    private FarmService farmService;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set Google Maps API key
        request.setAttribute("googleMapsApiKey", getGoogleMapsApiKey());

        String pathInfo = request.getPathInfo();
        String action = pathInfo != null ? pathInfo.substring(1) : "list";

        switch (action) {
            case "list":
                try {
                    listSites(request, response);
                } catch (NumberFormatException | ServletException | IOException | SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case "add":
                if (request.getMethod().equals("POST")) {
                    try {
                        addSite(request, response);
                    } catch (ServletException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        showAddForm(request, response);
                    } catch (NumberFormatException | ServletException | IOException | SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            case "edit":
                if (request.getMethod().equals("POST")) {
                    updateSite(request, response);
                } else {
                    showEditForm(request, response);
                }
                break;
            case "delete":
                deleteSite(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void listSites(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NumberFormatException, SQLException {
        String farmId = request.getParameter("farmId");
        List<Site> sites;

        if (farmId != null && !farmId.isEmpty()) {
            Farm farm = farmService.getFarmById(Long.parseLong(farmId));
            sites = siteService.getSitesByFarm(farm);
            request.setAttribute("farm", farm);
        } else {
            sites = siteService.getAllSites();
        }

        request.setAttribute("sites", sites);
        forward("site/list", request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NumberFormatException, SQLException {
        String farmId = request.getParameter("farmId");
        if (farmId != null && !farmId.isEmpty()) {
            Farm farm = farmService.getFarmById(Long.parseLong(farmId));
            request.setAttribute("farm", farm);
        }
        forward("site/add", request, response);
    }

    private void addSite(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Double latitude = Double.parseDouble(request.getParameter("latitude"));
        Double longitude = Double.parseDouble(request.getParameter("longitude"));
        Double altitude = Double.parseDouble(request.getParameter("altitude"));
        Long farmId = Long.parseLong(request.getParameter("farmId"));

        Farm farm = farmService.getFarmById(farmId);

        Site site = new Site();
        site.setName(name);
        site.setDescription(description);
        site.setLatitude(latitude);
        site.setLongitude(longitude);
        site.setAltitude(altitude);
        site.setFarm(farm);

        siteService.createSite(site);
        redirect("/sites?farmId=" + farmId, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long siteId = Long.parseLong(request.getParameter("id"));
        Site site = siteService.getSiteById(siteId);
        request.setAttribute("site", site);
        forward("site/edit", request, response);
    }

    private void updateSite(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long siteId = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Double latitude = Double.parseDouble(request.getParameter("latitude"));
        Double longitude = Double.parseDouble(request.getParameter("longitude"));
        Double altitude = Double.parseDouble(request.getParameter("altitude"));

        Site site = siteService.getSiteById(siteId);
        site.setName(name);
        site.setDescription(description);
        site.setLatitude(latitude);
        site.setLongitude(longitude);
        site.setAltitude(altitude);

        siteService.updateSite(site);
        redirect("/sites?farmId=" + site.getFarm().getId(), response);
    }

    private void deleteSite(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long siteId = Long.parseLong(request.getParameter("id"));
        Site site = siteService.getSiteById(siteId);
        Long farmId = site.getFarm().getId();

        siteService.deleteSite(siteId);
        redirect("/sites?farmId=" + farmId, response);
    }

    private String getGoogleMapsApiKey() {
        // In a real application, this should be loaded from a configuration file or
        // environment variable
        return System.getProperty("google.maps.api.key", "YOUR_API_KEY");
    }
}