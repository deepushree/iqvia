package com.webcontroller;

import com.jpaspecservice.SchedulerService;
import com.jpaspecwebmodel.QuartzInformation;
import com.jpaspecwebmodel.QuartzJobDetail;
import com.jpaspecwebmodel.QuartzResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static javax.servlet.http.HttpServletResponse.*;

@Api(tags = "Scheduler API")
@Controller
@CrossOrigin
@RequestMapping(value = "/scheduler/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SchedulerController {
    private SchedulerService schedulerService;

    public SchedulerController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @ApiOperation(value = "Retrieves general information about the Quartz scheduler")
    @ApiResponses(value = { @ApiResponse(code = SC_OK, message = "ok"), @ApiResponse(code = SC_BAD_REQUEST, message = "An unexpected error occurred") })
    @GetMapping(value = "information")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<QuartzInformation> getSchedulerInformation() {
        try {
            return new ResponseEntity<>(schedulerService.getSchedulerInformation(), HttpStatus.OK);
        } catch (SchedulerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Retrieves job key information from the Quartz scheduler")
    @ApiResponses(value = { @ApiResponse(code = SC_OK, message = "ok"), @ApiResponse(code = SC_BAD_REQUEST, message = "An unexpected error occurred") })
    @GetMapping(value = "jobKeys")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<JobKey>> getJobKeys() {
        try {
            return new ResponseEntity<>(schedulerService.getJobKeys(), HttpStatus.OK);
        } catch (SchedulerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "For a given name and group, returns job details from the Quartz scheduler")
    @ApiResponses(value = { @ApiResponse(code = SC_OK, message = "ok"), @ApiResponse(code = SC_BAD_REQUEST, message = "An unexpected error occurred") })
    @GetMapping(value = "jobDetail")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<QuartzJobDetail> getJobDetail(@RequestParam String name, @RequestParam String group) {
        try {
            return new ResponseEntity<>(schedulerService.getJobDetail(name, group), HttpStatus.OK);
        } catch (SchedulerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "For a given name and group, deletes the job/trigger(s) from the Quartz scheduler")
    @ApiResponses(value = { @ApiResponse(code = SC_ACCEPTED, message = "accepted"), @ApiResponse(code = SC_BAD_REQUEST, message = "An unexpected error occurred") })
    @DeleteMapping(value = "deleteJob")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<QuartzResponse> deleteJob(@RequestParam String name, @RequestParam String group) {
        try {
            return new ResponseEntity<>(schedulerService.deleteJobDetail(name, group), HttpStatus.ACCEPTED);
        } catch (SchedulerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
