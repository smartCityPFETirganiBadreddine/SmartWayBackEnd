package com.example.backendsmartcities.service;

import com.example.backendsmartcities.dto.TeamDto;
import com.example.backendsmartcities.dto.UserDto;
import com.example.backendsmartcities.entity.Branch;
import com.example.backendsmartcities.entity.Team;
import com.example.backendsmartcities.entity.User;
import com.example.backendsmartcities.exception.DuplicateEntityException;
import com.example.backendsmartcities.exception.TeamServiceException;
import com.example.backendsmartcities.repository.BranchRepository;
import com.example.backendsmartcities.repository.TeamRepository;
import com.example.backendsmartcities.repository.UserRepository;
import com.example.backendsmartcities.security.services.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Author: Badreddine TIRGANI
 */
@Service
@Slf4j
public class TeamService implements BaseService<TeamDto> {

    @Autowired
    TeamRepository teamRepository;
    private static final int MAX_TEAMS_PER_SUPERVISOR = 300;
    private static final int MAX_TEAMS_PER_CHEFEQUIPE = 200;
    @Autowired
    UserRepository userRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Transactional(rollbackFor = { TeamServiceException.class, DuplicateEntityException.class, EntityNotFoundException.class })
    public TeamDto save(TeamDto teamDto) throws TeamServiceException {
        validateTeamDto(teamDto);

        Optional<Team> teamOptional = teamRepository.findByTeamName(teamDto.getTeamName());
        if (teamOptional.isPresent()) {
            throw new DuplicateEntityException("Team already exists");
        }

        List<User> members = new ArrayList<>();
        for (UserDto memberDto : teamDto.getMembers()) {
            User member = userRepository.findById(memberDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Member not found"));
            validateUserNotAssignedToTeam(member);
            members.add(member);
        }


        User superviseur = userRepository.findById(teamDto.getSuperviseur().getId())
                .orElseThrow(() -> new EntityNotFoundException("Superviseur not found"));
        members.add(superviseur);

        List<Team> supervisorTeams = teamRepository.findBySuperviseur(superviseur);
        if (supervisorTeams.size() >= MAX_TEAMS_PER_SUPERVISOR) {
            throw new TeamServiceException("Superviseur is already assigned to the maximum number of teams");
        }

        User chefEquipe = userRepository.findById(teamDto.getChefEquipe().getId())
                .orElseThrow(() -> new EntityNotFoundException("Chef equipe not found"));
        members.add(chefEquipe);

        List<Team> chefEquipeTeams = teamRepository.findByChefEquipe(chefEquipe);
        if (chefEquipeTeams.size() >= MAX_TEAMS_PER_CHEFEQUIPE) {
            throw new TeamServiceException("Chef equipe is already assigned to the maximum number of teams");
        }

        Team team = modelMapper.map(teamDto, Team.class);
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        User owner = userRepository.findByUserName(username);
        team.setEditeur(owner);
        team.setMembers(members);
        team.setSuperviseur(superviseur);
        log.debug("Team: {}", team);
        Team createdTeam = teamRepository.save(team);
        log.debug("Created team: {}", createdTeam);

        List<User> updatedMembers = new ArrayList<>();
        for (User member : members) {
            User updatedMember = userRepository.findById(member.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Member not found"));
            validateUserNotAssignedToTeam(member);
            updatedMember.setTeam(createdTeam);
            updatedMembers.add(updatedMember);
        }
        createdTeam.setMembers(updatedMembers);

        superviseur.setTeam(createdTeam);
        userRepository.saveAll(updatedMembers);
        userRepository.save(superviseur);

        return modelMapper.map(createdTeam, TeamDto.class);
    }
    private void validateTeamDto(TeamDto teamDto) throws TeamServiceException {
        if (teamDto == null) {
            throw new TeamServiceException("TeamDto is null");
        }
        if (teamDto.getTeamName() == null) {
            throw new TeamServiceException("Team name is null");
        }
        if (teamDto.getSuperviseur() == null) {
            throw new TeamServiceException("Superviseur is null");
        }
        if (teamDto.getChefEquipe() == null) {
            throw new TeamServiceException("Chef de departement is null");
        }
    }

    private void validateUserNotAssignedToTeam(User user) throws DuplicateEntityException {
       /* if (user.getTeam() != null) {
            throw new DuplicateEntityException("User " + user.getUserName() + " is already assigned to a team");
        }*/
    }

    @Override
    public TeamDto update(long id, TeamDto teamDto) throws NotFoundException {
        Optional<Team> teamOptional = teamRepository.findById(id);
        if (teamOptional.isPresent()) {
            Team existingTeam = teamOptional.get();
            Team updatedTeam = modelMapper.map(teamDto, Team.class);
            updatedTeam.setId(existingTeam.getId());
            updatedTeam.setLastLocationDate(new Date());
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = principal.getUsername();
            User owner = userRepository.findByUserName(username);
            updatedTeam.setEditeur(owner);


            // Remove team assignment for members who are not in the updated team
            for (User member : existingTeam.getMembers()) {
                if (!updatedTeam.getMembers().contains(member)) {
                    member.setTeam(null);
                }
            }

            List<User> updatedMembers = new ArrayList<>();
            for (User member : updatedTeam.getMembers()) {
                User updatedMember = userRepository.findById(member.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Member not found"));
                updatedMember.setTeam(updatedTeam);
                updatedMembers.add(updatedMember);
            }
            updatedTeam.setMembers(updatedMembers);
            Team savedTeam = teamRepository.save(updatedTeam);
            return modelMapper.map(savedTeam, TeamDto.class);
        }
        throw new NotFoundException("Team not found with id " + id);
    }

    @Override
    @Transactional
    public void softDelete(Long id) throws NotFoundException {
        Optional<Team> teamOptional = teamRepository.findById(id);
        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();

            // Remove team assignment for all members of the team
        /*    for (User member : team.getMembers()) {
                member.setTeam(null);
                userRepository.save(member);
            }
*/
            // Remove the team from all users who are currently assigned to the team
            List<User> usersWithTeam = userRepository.findByTeam(team);
            for (User user : usersWithTeam) {
                user.setTeam(null);
                userRepository.save(user);
            }

            // Remove the team from the database
            //team.setDeleteAt(new Date());
            team.setActive(false);
            team.setDeleted(true);
            teamRepository.save(team);
        } else {
            throw new NotFoundException("Team not found with id " + id);
        }
    }

    @Override
    public Optional<TeamDto> findById(Long id) {
        Optional<Team> reclamationOptional = teamRepository.findById(id);
        return reclamationOptional.map(team -> modelMapper.map(team, TeamDto.class));
    }
    public List<TeamDto> findByBranch(Long id) {
        Branch branch = branchRepository.getBranchById(id);
        List<Team> teams = teamRepository.getByBranch(branch);
        return teams.stream()
                .map(team -> modelMapper.map(team, TeamDto.class))
                .collect(Collectors.toList());
    }


    /*@Override
    public List<TeamDto> getAll() {

        List<Team> teams = teamRepository.findByDeletedFalse();
        return teams.stream()
                .map(team -> modelMapper.map(team, TeamDto.class))
                .collect(Collectors.toList());
    }*/
    @Override
    public List<TeamDto> getAll() {
        List<Team> teams = teamRepository.findByDeletedFalse();
        return teams.stream()
                .map(team -> {
                    TeamDto teamDto = modelMapper.map(team, TeamDto.class);

                    User editeur = userRepository.findById(team.getEditeur().getId()).orElse(null);
                    if (editeur != null) {
                        teamDto.setFullNameEditeur(editeur.getLastName()+" "+editeur.getFirstName());
                    }
                    return teamDto;
                })
                .collect(Collectors.toList());
    }

    public void softDeletes(Long id) {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isPresent()) {
            Team existingTeam = team.get();

            //existingTeam.setDeleteAt(new Date());
            existingTeam.setActive(false);
            existingTeam.setDeleted(true);
                teamRepository.save(existingTeam);

        } else {
            throw new EntityNotFoundException("Team with id " + id + " not found.");
        }
    }
}
