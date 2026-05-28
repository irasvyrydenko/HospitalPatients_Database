package org.example.app.patient;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping
    public String listPatients(@RequestParam(required = false) String surgeryType, Model model) {
        List<Patient> patients = new ArrayList<>();
        if (surgeryType != null && !surgeryType.isBlank()) {
            patients = patientRepository.findBySurgeryType(surgeryType);
        } else {
            patientRepository.findAll().forEach(patients::add);
        }
        patients.sort(Comparator.comparing(Patient::patientId));
        model.addAttribute("patients", patients);
        return "patient/list";
    }

    @GetMapping("/{id}/summary")
    public String viewSummary(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        var summary = patientRepository.getPatientMedicalSummary(id).orElse(null);
        if (summary == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Summary not found.");
            return "redirect:/patient";
        }
        model.addAttribute("summary", summary);
        return "patient/summary-view";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        if (!model.containsAttribute("patientForm")) {
            model.addAttribute("patientForm", PatientForm.empty());
        }
        populateFormPage(model, "Register Patient", "/patient/create", "Register");
        return "patient/form";
    }

    @PostMapping("/create")
    public String createPatient(@Valid @ModelAttribute("patientForm") PatientForm form,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            populateFormPage(model, "Register Patient", "/patient/create", "Register");
            return "patient/form";
        }

        patientRepository.save(Patient.of(
                form.firstName(),
                form.lastName(),
                form.gender(),
                form.dateOfBirth(), // Convert LocalDate -> LocalDateTime
                form.bloodType()
        ));

        redirectAttributes.addFlashAttribute("successMessage", "Patient registered successfully.");
        return "redirect:/patient";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        var patient = patientRepository.findById(id).orElse(null);
        if (patient == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Patient not found.");
            return "redirect:/patient";
        }

        if (!model.containsAttribute("patientForm")) {
            model.addAttribute("patientForm", PatientForm.from(patient));
        }

        populateFormPage(model, "Edit Patient", "/patient/" + id + "/edit", "Save changes");
        return "patient/form";
    }

    @PostMapping("/{id}/edit")
    public String updatePatient(@PathVariable Long id,
                                @Valid @ModelAttribute("patientForm") PatientForm form,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        var patient = patientRepository.findById(id).orElse(null);
        if (patient == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Patient not found.");
            return "redirect:/patient";
        }

        if (bindingResult.hasErrors()) {
            populateFormPage(model, "Edit Patient", "/patient/" + id + "/edit", "Save changes");
            return "patient/form";
        }

        patientRepository.save(patient.withDetails(
                form.firstName(),
                form.lastName(),
                form.gender(),
                form.dateOfBirth(), // Convert LocalDate -> LocalDateTime
                form.bloodType()
        ));

        redirectAttributes.addFlashAttribute("successMessage", "Patient updated.");
        return "redirect:/patient";
    }

    @PostMapping("/{id}/delete")
    public String deletePatient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (!patientRepository.existsById(id)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Patient not found.");
            return "redirect:/patient";
        }
        patientRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Patient deleted.");
        return "redirect:/patient";
    }

    private void populateFormPage(Model model, String pageTitle, String formAction, String submitLabel) {
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("formAction", formAction);
        model.addAttribute("submitLabel", submitLabel);
    }

    // --- ONLY ONE RECORD DEFINITION HERE ---
    public record PatientForm(
            String firstName,
            String lastName,
            java.time.LocalDate dateOfBirth,
            String gender,
            String bloodType
    ) {
        public static PatientForm empty() {
            return new PatientForm("", "", null, "", "");
        }

        public static PatientForm from(Patient p) {
            return new PatientForm(
                    p.firstName(),
                    p.lastName(),
                    p.dateOfBirth(), // Convert LocalDateTime -> LocalDate
                    p.gender(),
                    p.bloodType()
            );
        }
    }
}