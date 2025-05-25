package com.beehopuse.servlet;

import com.beehopuse.model.Hive;
import com.beehopuse.model.Site;
import com.beehopuse.service.HiveService;
import com.beehopuse.service.SiteService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/hives/*")
public class HiveServlet extends BaseServlet {

    @EJB
    private HiveService hiveService;

    @EJB
    private SiteService siteService;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String action = pathInfo != null ? pathInfo.substring(1) : "list";

        switch (action) {
            case "list":
                listHives(request, response);
                break;
            case "add":
                if (request.getMethod().equals("POST")) {
                    addHive(request, response);
                } else {
                    showAddForm(request, response);
                }
                break;
            case "edit":
                if (request.getMethod().equals("POST")) {
                    updateHive(request, response);
                } else {
                    showEditForm(request, response);
                }
                break;
            case "delete":
                deleteHive(request, response);
                break;
            case "view":
                viewHive(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void listHives(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String siteId = request.getParameter("siteId");
        List<Hive> hives;

        if (siteId != null && !siteId.isEmpty()) {
            Site site = siteService.getSiteById(Long.parseLong(siteId));
            hives = hiveService.getHivesBySite(site);
            request.setAttribute("site", site);
        } else {
            hives = hiveService.getAllHives();
        }

        request.setAttribute("hives", hives);
        forward("hive/list", request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String siteId = request.getParameter("siteId");
        if (siteId != null && !siteId.isEmpty()) {
            Site site = siteService.getSiteById(Long.parseLong(siteId));
            request.setAttribute("site", site);
        }
        forward("hive/add", request, response);
    }

    private void addHive(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String status = request.getParameter("status");
        String orientation = request.getParameter("orientation");
        Double latitude = Double.parseDouble(request.getParameter("latitude"));
        Double longitude = Double.parseDouble(request.getParameter("longitude"));
        Double altitude = Double.parseDouble(request.getParameter("altitude"));
        Integer queenAge = Integer.parseInt(request.getParameter("queenAge"));
        Integer frameCount = Integer.parseInt(request.getParameter("frameCount"));
        Integer extensionCount = Integer.parseInt(request.getParameter("extensionCount"));
        Double honeyQuantity = Double.parseDouble(request.getParameter("honeyQuantity"));
        String healthStatusStr = request.getParameter("healthStatus");
        String productivityStatus = request.getParameter("productivityStatus");
        Integer colonySize = Integer.parseInt(request.getParameter("colonySize"));
        Boolean hasShade = Boolean.parseBoolean(request.getParameter("hasShade"));
        Boolean hasWaterSource = Boolean.parseBoolean(request.getParameter("hasWaterSource"));
        Boolean hasFlowerSource = Boolean.parseBoolean(request.getParameter("hasFlowerSource"));
        Boolean hasPredatorProtection = Boolean.parseBoolean(request.getParameter("hasPredatorProtection"));
        Boolean hasVentilation = Boolean.parseBoolean(request.getParameter("hasVentilation"));
        Boolean hasInsulation = Boolean.parseBoolean(request.getParameter("hasInsulation"));
        Boolean hasMoistureProtection = Boolean.parseBoolean(request.getParameter("hasMoistureProtection"));
        Boolean hasPestControl = Boolean.parseBoolean(request.getParameter("hasPestControl"));
        Boolean hasDisease = Boolean.parseBoolean(request.getParameter("hasDisease"));
        Boolean hasTreatment = Boolean.parseBoolean(request.getParameter("hasTreatment"));
        Boolean hasFeeding = Boolean.parseBoolean(request.getParameter("hasFeeding"));
        Boolean hasSwarmControl = Boolean.parseBoolean(request.getParameter("hasSwarmControl"));
        Boolean needsQueenReplacement = Boolean.parseBoolean(request.getParameter("needsQueenReplacement"));
        Boolean needsFrameReplacement = Boolean.parseBoolean(request.getParameter("needsFrameReplacement"));
        Boolean needsExtensionReplacement = Boolean.parseBoolean(request.getParameter("needsExtensionReplacement"));
        Boolean needsMaintenance = Boolean.parseBoolean(request.getParameter("needsMaintenance"));
        Boolean needsInspection = Boolean.parseBoolean(request.getParameter("needsInspection"));
        Boolean readyForHarvest = Boolean.parseBoolean(request.getParameter("readyForHarvest"));
        Long siteId = Long.parseLong(request.getParameter("siteId"));

        Site site = siteService.getSiteById(siteId);

        Hive hive = new Hive();
        hive.setName(name);
        hive.setType(type);
        hive.setStatus(status);
        hive.setOrientation(orientation);
        hive.setLatitude(latitude);
        hive.setLongitude(longitude);
        hive.setAltitude(altitude);
        hive.setQueenAge(queenAge);
        hive.setFrameCount(frameCount);
        hive.setExtensionCount(extensionCount);
        hive.setHoneyQuantity(honeyQuantity);
        hive.setHealthStatus(Hive.HealthStatus.valueOf(healthStatusStr));
        hive.setProductivityStatus(productivityStatus);
        hive.setColonySize(colonySize);
        hive.setHasShade(hasShade);
        hive.setHasWaterSource(hasWaterSource);
        hive.setHasFlowerSource(hasFlowerSource);
        hive.setHasPredatorProtection(hasPredatorProtection);
        hive.setHasVentilation(hasVentilation);
        hive.setHasInsulation(hasInsulation);
        hive.setHasMoistureProtection(hasMoistureProtection);
        hive.setHasPestControl(hasPestControl);
        hive.setHasDisease(hasDisease);
        hive.setHasTreatment(hasTreatment);
        hive.setHasFeeding(hasFeeding);
        hive.setHasSwarmControl(hasSwarmControl);
        hive.setNeedsQueenReplacement(needsQueenReplacement);
        hive.setNeedsFrameReplacement(needsFrameReplacement);
        hive.setNeedsExtensionReplacement(needsExtensionReplacement);
        hive.setNeedsMaintenance(needsMaintenance);
        hive.setNeedsInspection(needsInspection);
        hive.setReadyForHarvest(readyForHarvest);
        hive.setSite(site);

        hiveService.createHive(hive);
        redirect("/hives?siteId=" + siteId, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long hiveId = Long.parseLong(request.getParameter("id"));
        Hive hive = hiveService.getHiveById(hiveId);
        request.setAttribute("hive", hive);
        forward("hive/edit", request, response);
    }

    private void updateHive(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long hiveId = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String status = request.getParameter("status");
        String orientation = request.getParameter("orientation");
        Double latitude = Double.parseDouble(request.getParameter("latitude"));
        Double longitude = Double.parseDouble(request.getParameter("longitude"));
        Double altitude = Double.parseDouble(request.getParameter("altitude"));
        Integer queenAge = Integer.parseInt(request.getParameter("queenAge"));
        Integer frameCount = Integer.parseInt(request.getParameter("frameCount"));
        Integer extensionCount = Integer.parseInt(request.getParameter("extensionCount"));
        Double honeyQuantity = Double.parseDouble(request.getParameter("honeyQuantity"));
        String healthStatusStr = request.getParameter("healthStatus");
        String productivityStatus = request.getParameter("productivityStatus");
        Integer colonySize = Integer.parseInt(request.getParameter("colonySize"));
        Boolean hasShade = Boolean.parseBoolean(request.getParameter("hasShade"));
        Boolean hasWaterSource = Boolean.parseBoolean(request.getParameter("hasWaterSource"));
        Boolean hasFlowerSource = Boolean.parseBoolean(request.getParameter("hasFlowerSource"));
        Boolean hasPredatorProtection = Boolean.parseBoolean(request.getParameter("hasPredatorProtection"));
        Boolean hasVentilation = Boolean.parseBoolean(request.getParameter("hasVentilation"));
        Boolean hasInsulation = Boolean.parseBoolean(request.getParameter("hasInsulation"));
        Boolean hasMoistureProtection = Boolean.parseBoolean(request.getParameter("hasMoistureProtection"));
        Boolean hasPestControl = Boolean.parseBoolean(request.getParameter("hasPestControl"));
        Boolean hasDisease = Boolean.parseBoolean(request.getParameter("hasDisease"));
        Boolean hasTreatment = Boolean.parseBoolean(request.getParameter("hasTreatment"));
        Boolean hasFeeding = Boolean.parseBoolean(request.getParameter("hasFeeding"));
        Boolean hasSwarmControl = Boolean.parseBoolean(request.getParameter("hasSwarmControl"));
        Boolean needsQueenReplacement = Boolean.parseBoolean(request.getParameter("needsQueenReplacement"));
        Boolean needsFrameReplacement = Boolean.parseBoolean(request.getParameter("needsFrameReplacement"));
        Boolean needsExtensionReplacement = Boolean.parseBoolean(request.getParameter("needsExtensionReplacement"));
        Boolean needsMaintenance = Boolean.parseBoolean(request.getParameter("needsMaintenance"));
        Boolean needsInspection = Boolean.parseBoolean(request.getParameter("needsInspection"));
        Boolean readyForHarvest = Boolean.parseBoolean(request.getParameter("readyForHarvest"));

        Hive hive = hiveService.getHiveById(hiveId);
        hive.setName(name);
        hive.setType(type);
        hive.setStatus(status);
        hive.setOrientation(orientation);
        hive.setLatitude(latitude);
        hive.setLongitude(longitude);
        hive.setAltitude(altitude);
        hive.setQueenAge(queenAge);
        hive.setFrameCount(frameCount);
        hive.setExtensionCount(extensionCount);
        hive.setHoneyQuantity(honeyQuantity);
        hive.setHealthStatus(Hive.HealthStatus.valueOf(healthStatusStr));
        hive.setProductivityStatus(productivityStatus);
        hive.setColonySize(colonySize);
        hive.setHasShade(hasShade);
        hive.setHasWaterSource(hasWaterSource);
        hive.setHasFlowerSource(hasFlowerSource);
        hive.setHasPredatorProtection(hasPredatorProtection);
        hive.setHasVentilation(hasVentilation);
        hive.setHasInsulation(hasInsulation);
        hive.setHasMoistureProtection(hasMoistureProtection);
        hive.setHasPestControl(hasPestControl);
        hive.setHasDisease(hasDisease);
        hive.setHasTreatment(hasTreatment);
        hive.setHasFeeding(hasFeeding);
        hive.setHasSwarmControl(hasSwarmControl);
        hive.setNeedsQueenReplacement(needsQueenReplacement);
        hive.setNeedsFrameReplacement(needsFrameReplacement);
        hive.setNeedsExtensionReplacement(needsExtensionReplacement);
        hive.setNeedsMaintenance(needsMaintenance);
        hive.setNeedsInspection(needsInspection);
        hive.setReadyForHarvest(readyForHarvest);

        hiveService.updateHive(hive);
        redirect("/hives?siteId=" + hive.getSite().getId(), response);
    }

    private void deleteHive(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long hiveId = Long.parseLong(request.getParameter("id"));
        Hive hive = hiveService.getHiveById(hiveId);
        Long siteId = hive.getSite().getId();

        hiveService.deleteHive(hiveId);
        redirect("/hives?siteId=" + siteId, response);
    }

    private void viewHive(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long hiveId = Long.parseLong(request.getParameter("id"));
        Hive hive = hiveService.getHiveById(hiveId);
        request.setAttribute("hive", hive);
        forward("hive/view", request, response);
    }
}