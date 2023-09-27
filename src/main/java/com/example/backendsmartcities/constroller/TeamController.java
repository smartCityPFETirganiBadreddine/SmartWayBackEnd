package com.example.backendsmartcities.constroller;

import com.example.backendsmartcities.dto.TeamDto;
import com.example.backendsmartcities.exception.DuplicateEntityException;
import com.example.backendsmartcities.exception.ErrorResponse;
import com.example.backendsmartcities.exception.TeamServiceException;
import com.example.backendsmartcities.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
/**
 * Author: Badreddine TIRGANI
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<?> createTeam(@RequestBody TeamDto teamDto) {
        try {
            TeamDto createdTeam = teamService.save(teamDto);
            return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
        } catch (DuplicateEntityException e) {
            TeamDto errorTeamDto = new TeamDto();
            errorTeamDto.setTeamName(e.getMessage());
            return new ResponseEntity<>(errorTeamDto, HttpStatus.BAD_REQUEST);
        } catch (TeamServiceException e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable Long id, @RequestBody TeamDto teamDto) {
        try {
            TeamDto updatedTeam = teamService.update(id, teamDto);
            return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        List<TeamDto> teamDtos = teamService.getAll();
        if (teamDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(teamDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable Long id) {
        Optional<TeamDto> teamDto = teamService.findById(id);
        return teamDto.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/branch/{id}")
    public ResponseEntity<List<TeamDto>> getByBranch(@PathVariable Long id) {
        List<TeamDto> teamDtoList = teamService.findByBranch(id);
        if (!teamDtoList.isEmpty()) {
            return new ResponseEntity<>(teamDtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable Long id) {
        try {
            teamService.softDelete(id);
            return new ResponseEntity<>("Team with id " + id + " has been deleted.", HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Team must not be in EN_COURS status to be deleted.", HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Team with id " + id + " not found.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the team.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
