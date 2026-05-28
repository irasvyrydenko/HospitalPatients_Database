package org.example.app.hospitalStay;

import org.example.app.patient.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/patient")
public class HospitalStayController {

    private final HospitalStayRepository hospitalStayRepository;
    private final PatientRepository patientRepository;

    public HospitalStayController(HospitalStayRepository hospitalStayRepository, PatientRepository patientRepository) {
        this.hospitalStayRepository = hospitalStayRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * Displays the list of stays for a specific patient.
     */
    @GetMapping("/{patientId}/hospitalStay")
    public String listStays(@PathVariable Long patientId, Model model, RedirectAttributes redirectAttributes) {
        var patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Patient not found.");
            return "redirect:/patient";
        }

        model.addAttribute("patient", patient);
        model.addAttribute("hospitalStays", hospitalStayRepository.findByPatientId(patientId));

        return "hospitalStay/list";
    }

    /**
     * Displays the form to create a new stay record.
     */
    @GetMapping("/{patientId}/hospitalStay/create")
    public String createStayPage(@PathVariable Long patientId, Model model) {
        var patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            return "redirect:/patient";
        }

        model.addAttribute("patient", patient);
        model.addAttribute("stayForm", new StayForm(patientId, null, null, null, null, "", ""));
        model.addAttribute("formAction", "/patient/" + patientId + "/hospitalStay/create");
        return "hospitalStay/form";
    }

    /**
     * Processes the creation of a new stay record.
     */
    @PostMapping("/{patientId}/hospitalStay/create")
    public String createStay(@PathVariable Long patientId,
                             @ModelAttribute("stayForm") StayForm form,
                             RedirectAttributes redirectAttributes) {

        hospitalStayRepository.save(new HospitalStay(
                null,
                patientId,
                form.bedNumber(),
                form.admissionDate(),
                form.dischargeDate(),
                form.roomNumber(),
                form.diagnosis(),
                form.anaesthesiaType()
        ));

        redirectAttributes.addFlashAttribute("successMessage", "Stay record added successfully.");
        return "redirect:/patient/" + patientId + "/hospitalStay";
    }

    /**
     * Form record for data transfer from the view.
     */
    public record StayForm(
            Long patientId,
            Integer roomNumber,
            Integer bedNumber,
            LocalDate admissionDate,
            LocalDate dischargeDate,
            String diagnosis,
            String anaesthesiaType
    ) {}
}