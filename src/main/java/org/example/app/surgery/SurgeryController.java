package org.example.app.surgery;

import org.example.app.patient.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/patient/{patientId}/surgery")
public class SurgeryController {

    private final SurgeryRepository surgeryRepository;
    private final PatientRepository patientRepository;

    public SurgeryController(SurgeryRepository surgeryRepository, PatientRepository patientRepository) {
        this.surgeryRepository = surgeryRepository;
        this.patientRepository = patientRepository;
    }

    @GetMapping
    public String listSurgeries(@PathVariable Long patientId, Model model) {
        var patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) return "redirect:/patient";

        model.addAttribute("patient", patient);
        model.addAttribute("surgeries", surgeryRepository.findByPatientId(patientId));
        return "surgery/list";
    }

    @GetMapping("/create")
    public String createSurgeryPage(@PathVariable Long patientId, Model model) {
        var patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) return "redirect:/patient";

        model.addAttribute("patient", patient);
        model.addAttribute("surgeryForm", new SurgeryForm(null, "", "", null, null));
        return "surgery/form";
    }

    @PostMapping("/create")
    public String createSurgery(@PathVariable Long patientId, @ModelAttribute SurgeryForm form) {
        surgeryRepository.save(new Surgery(
                null, patientId, form.procedureType(), form.operationRoom(), form.startTime(), form.endTime()
        ));
        return "redirect:/patient/" + patientId + "/surgery";
    }

    public record SurgeryForm(Long patientId, String procedureType, String operationRoom, LocalDateTime startTime, LocalDateTime endTime) {}
}