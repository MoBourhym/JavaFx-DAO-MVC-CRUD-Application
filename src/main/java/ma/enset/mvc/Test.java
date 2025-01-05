package ma.enset.mvc;

import ma.enset.mvc.dao.PlayerDaoImpl;
import ma.enset.mvc.dao.TeamDaoImpl;
import ma.enset.mvc.dao.entities.Team;
import ma.enset.mvc.service.CPlayerTeamService;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        PlayerDaoImpl playerDao = new PlayerDaoImpl();
        TeamDaoImpl teamDao = new TeamDaoImpl();
        CPlayerTeamService service = new CPlayerTeamService(playerDao, teamDao);

        // Retrieve all teams
        List<Team> teams = service.findAllTeams();

        // Print team information
        teams.forEach(team -> {
            System.out.println("Team ID: " + team.getId());
            System.out.println("Team Name: " + team.getName());
            System.out.println("Team City: " + team.getCity());
            System.out.println("-----------------------------");
        });
    }
}
