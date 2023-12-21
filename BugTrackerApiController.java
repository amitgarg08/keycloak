package com.bugtracker.bugtrackerapi;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.bugtrackerapi.service.BugStatistics;
import com.bugtracker.bugtrackerapi.service.BugTrackerConfiguration;
import com.bugtracker.bugtrackerapi.service.BugTrackerService;

import jakarta.validation.constraints.NotNull;

@RestController
public class BugTrackerApiController {

    private final BugTrackerService bugService;

    public BugTrackerApiController(BugTrackerService bugService) {
        this.bugService = bugService;
    }

    @GetMapping("/bugtrackerapi/statistics")
    public BugStatistics getBugStatistics() {
    	SecurityContext ctxt = SecurityContextHolder.getContext();
		/*
		 * OAuth2AuthenticationToken token = (OAuth2AuthenticationToken)
		 * ctxt.getAuthentication();
		 */
    	JwtAuthenticationToken token=(JwtAuthenticationToken)ctxt.getAuthentication();
    	Jwt principal = (Jwt)token.getPrincipal();
    	String memberId= principal.getClaim("given_name");
    	
        return bugService.getBugStatistics();
    }

    @GetMapping("/bugtrackerapi/configuration")
    public BugTrackerConfiguration getConfiguration() {
        return bugService.getConfiguration();
    }

    @PostMapping("/bugtrackerapi/administration/project/{project}")
    public void addProject(@PathVariable @NotNull String project) {
        bugService.addProject(project);
    }

    @DeleteMapping ("/bugtrackerapi/administration/project/{project}")
    public void removeProject(@PathVariable @NotNull String project) {
        bugService.removeProject(project);
    }


}
