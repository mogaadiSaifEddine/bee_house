package com.beehopuse.servlet;

import com.beehopuse.model.Farm;
import com.beehopuse.model.User;
import com.beehopuse.service.FarmService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/farms/*")
public class FarmServlet extends BaseServlet {

    @EJB
    private FarmService farmService;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String action = pathInfo != null ? pathInfo.substring(1) : "list";

        switch (action) {
            case "list":
                listFarms(request, response);
                break;
            case "add":
                if (request.getMethod().equals("POST")) {
                    addFarm(request, response);
                } else {
                    showAddForm(request, response);
                }
                break;
            case "edit":
                if (request.getMethod().equals("POST")) {
                    updateFarm(request, response);
                } else {
                    showEditForm(request, response);
                }
                break;
            case "delete":
                deleteFarm(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void listFarms(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User currentUser = (User) getUserSession(request);
        List<Farm> farms = farmService.getFarmsByOwner(currentUser);
        request.setAttribute("farms", farms);
        forward("farm/list", request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        forward("farm/add", request, response);
    }

    private void addFarm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        User owner = (User) getUserSession(request);

        Farm farm = new Farm();
        farm.setName(name);
        farm.setDescription(description);
        farm.setOwner(owner);

        farmService.createFarm(farm);
        redirect("/farms", response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long farmId = Long.parseLong(request.getParameter("id"));
        Farm farm = farmService.getFarmById(farmId);
        request.setAttribute("farm", farm);
        forward("farm/edit", request, response);
    }

    private void updateFarm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long farmId = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        Farm farm = farmService.getFarmById(farmId);
        farm.setName(name);
        farm.setDescription(description);

        farmService.updateFarm(farm);
        redirect("/farms", response);
    }

    private void deleteFarm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long farmId = Long.parseLong(request.getParameter("id"));
        farmService.deleteFarm(farmId);
        redirect("/farms", response);
    }
}