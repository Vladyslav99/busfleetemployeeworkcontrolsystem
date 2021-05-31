package com.kondratenko.busparkemploeesworkcontrol.controller;

import com.kondratenko.busparkemploeesworkcontrol.dto.CustomUserDTO;
import com.kondratenko.busparkemploeesworkcontrol.dto.MedicalExaminationLogDTO;
import com.kondratenko.busparkemploeesworkcontrol.entity.CustomUser;
import com.kondratenko.busparkemploeesworkcontrol.exception.UserAlreadyExistsException;
import com.kondratenko.busparkemploeesworkcontrol.service.CustomUserDetailsService;
import com.kondratenko.busparkemploeesworkcontrol.service.MedicalExaminationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(ControllerConstants.Mapping.BUS_PARK_EMPLOYEES_WORK_CONTROL)
public class DriverInfoPageController {

    private static final String DRIVER_LIST = "driverList";

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private MedicalExaminationLogService medicalExaminationLogService;

    @GetMapping(ControllerConstants.Mapping.DRIVER_INFO_PERFORMING)
    public String showDriverInfoPerformingPage(@ModelAttribute("driverDTO") CustomUserDTO driverDTO) {
        return ControllerConstants.Pages.DRIVER_INFO_PERFORMING;
    }

    @GetMapping(ControllerConstants.Mapping.SAVE_DRIVER_INFO)
    public String showDriverInfoPerformingPageSave(@ModelAttribute("driverDTO") CustomUserDTO driverDTO) {
        return showDriverInfoPerformingPage(driverDTO);
    }

    @PostMapping(ControllerConstants.Mapping.SAVE_DRIVER_INFO)
    public String saveBusInfo(@Valid @ModelAttribute("driverDTO") CustomUserDTO driverDTO, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return ControllerConstants.Pages.DRIVER_INFO_PERFORMING;
        }

        try {
            customUserDetailsService.save(driverDTO);
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            bindingResult.rejectValue("email", "Driver with entered email already exists");
            return ControllerConstants.Pages.DRIVER_INFO_PERFORMING;
        }

        redirectAttributes.addFlashAttribute(ControllerConstants.Model.INFO_SAVED_ATTRIBUTE_KEY, true);
        return ControllerConstants.REDIRECT + ControllerConstants.Mapping.BUS_PARK_EMPLOYEES_WORK_CONTROL
                + ControllerConstants.Mapping.DRIVER_INFO_PERFORMING;
    }

    @GetMapping(ControllerConstants.Mapping.MEDICAL_EXAMINATION_INFO_PERFORMING)
    public String showMedicalExaminationInfoPage(@ModelAttribute MedicalExaminationLogDTO medicalExaminationLogDTO,
                                                 Model model) {
        loadMedicalExaminationInfoPageModel(model);
        return ControllerConstants.Pages.MEDICAL_EXAMINATION_INFO_PERFORMING;
    }

    @PostMapping(ControllerConstants.Mapping.SAVE_MEDICAL_EXAMINATION_INFO)
    public String saveMedicalExaminationInfo(@Valid @ModelAttribute MedicalExaminationLogDTO medicalExaminationLogDTO,
                                             BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            loadMedicalExaminationInfoPageModel(model);
            return ControllerConstants.Pages.MEDICAL_EXAMINATION_INFO_PERFORMING;
        }

        medicalExaminationLogService.save(medicalExaminationLogDTO);
        redirectAttributes.addFlashAttribute(ControllerConstants.Model.INFO_SAVED_ATTRIBUTE_KEY, true);
        return ControllerConstants.REDIRECT + ControllerConstants.Mapping.BUS_PARK_EMPLOYEES_WORK_CONTROL
                + ControllerConstants.Mapping.MEDICAL_EXAMINATION_INFO_PERFORMING;
    }

    private void loadMedicalExaminationInfoPageModel(Model model) {
        model.addAttribute(DRIVER_LIST, customUserDetailsService.findAllByRole(CustomUser.CustomUserRole.DRIVER));
    }
}
