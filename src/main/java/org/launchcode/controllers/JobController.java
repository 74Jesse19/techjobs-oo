package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String index(Model model, @PathVariable int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        model.addAttribute("someJob", jobData.findById(id));
        //Job someJob = jobData.findById(id);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors, String name){
        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()){
            return "job/add";
        } else {

            Job newJob = new Job();

            name = jobForm.getName();
            Employer employers = jobData.getEmployers().findById(jobForm.getEmployerId());
            Location locations = jobData.getLocations().findById(jobForm.getLocationsId());
            CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetenciesId());
            PositionType positionType = jobData.getPositionTypes().findById(jobForm.getPositionTypesId());

            newJob.setName(name);
            newJob.setEmployer(employers);
            newJob.setLocation(locations);
            newJob.setCoreCompetency(coreCompetency);
            newJob.setPositionType(positionType);


            model.addAttribute("name", jobForm.getName());
            model.addAttribute("employers", jobData.getEmployers().findById(jobForm.getEmployerId()));
            model.addAttribute("locations", jobData.getLocations().findById(jobForm.getLocationsId()));
            model.addAttribute("coreCompetencies", jobData.getCoreCompetencies().findById(jobForm.getCoreCompetenciesId()));
            model.addAttribute("positionTypes", jobData.getPositionTypes().findById(jobForm.getPositionTypesId()));
            model.addAttribute("Job", newJob);



            jobData.add(newJob);



            return "job-detail";
        }
    }
}
