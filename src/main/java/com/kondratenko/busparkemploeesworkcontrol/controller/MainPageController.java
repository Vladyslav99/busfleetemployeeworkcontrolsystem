package com.kondratenko.busparkemploeesworkcontrol.controller;

import com.kondratenko.busparkemploeesworkcontrol.dto.FindTripForm;
import com.kondratenko.busparkemploeesworkcontrol.entity.Route;
import com.kondratenko.busparkemploeesworkcontrol.entity.RouteCheckDocument;
import com.kondratenko.busparkemploeesworkcontrol.entity.Schedule;
import com.kondratenko.busparkemploeesworkcontrol.service.RouteCheckDocumentService;
import com.kondratenko.busparkemploeesworkcontrol.service.RouteService;
import com.kondratenko.busparkemploeesworkcontrol.service.ScheduleService;
import com.kondratenko.busparkemploeesworkcontrol.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(ControllerConstants.Mapping.BUS_PARK_EMPLOYEES_WORK_CONTROL)
public class MainPageController {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Autowired
    private RouteCheckDocumentService routeCheckDocumentService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private RouteService routeService;

    @GetMapping({ControllerConstants.Mapping.MAIN})
    public String showMainPage(@ModelAttribute FindTripForm findTripForm) {
        return ControllerConstants.Pages.MAIN;
    }

    @PostMapping("/findtrip")
    public String showFoundTripFragment(@Valid @ModelAttribute FindTripForm findTripForm, BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return ControllerConstants.Pages.MAIN;
        }

        if (!DateTimeUtils.isDateTimeAfterNow(LocalDateTime.parse(findTripForm.getDepartureDateTime(), DATE_TIME_FORMATTER))) {
            bindingResult.rejectValue("departureDateTime", "page.main.find-trip.error.past-date");
        }

        Optional<Route> routeOptional = routeService.findByName(findTripForm.getDeparturePoint(), findTripForm.getArrivalPoint());

        if (!routeOptional.isPresent()) {
            bindingResult.rejectValue("tripsNotFound", "page.main.find-trip.error.nothing-found");
        }

        List<Schedule> schedules = scheduleService.findByArrivalDateTimeAfterAndAndArrivalDateTimeBeforeAndRoute(
                LocalDateTime.parse(findTripForm.getDepartureDateTime(), DATE_TIME_FORMATTER), routeOptional.get());

        if (schedules.isEmpty()) {
            bindingResult.rejectValue("tripsNotFound", "page.main.find-trip.error.nothing-found");
        }

        List<RouteCheckDocument> routeCheckDocuments = routeCheckDocumentService.findAllByScheduleIn(schedules);

        if (routeCheckDocuments.isEmpty()) {
            bindingResult.rejectValue("tripsNotFound", "page.main.find-trip.error.nothing-found");
        }

        if (bindingResult.hasErrors()) {
            return ControllerConstants.Pages.MAIN;
        }

        redirectAttributes.addFlashAttribute("foundTripList", routeCheckDocuments);

        return ControllerConstants.REDIRECT + ControllerConstants.Mapping.BUS_PARK_EMPLOYEES_WORK_CONTROL +
                ControllerConstants.Mapping.MAIN;
    }

}
