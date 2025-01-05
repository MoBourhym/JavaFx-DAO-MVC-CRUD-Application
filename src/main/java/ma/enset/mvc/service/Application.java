package ma.enset.mvc.service;

import ma.enset.mvc.dao.PlayerDao;
import ma.enset.mvc.dao.PlayerDaoImpl;
import ma.enset.mvc.dao.TeamDao;
import ma.enset.mvc.dao.TeamDaoImpl;
import ma.enset.mvc.dao.entities.Player;
import ma.enset.mvc.dao.entities.Team;

public class Application {
    public static void main(String[] args) {

        PlayerDaoImpl playerDao = new PlayerDaoImpl();
        TeamDaoImpl teamDao = new TeamDaoImpl();


        CPlayerTeamService service = new CPlayerTeamService(playerDao, teamDao);

        Team team1 = new Team();
        team1.setName("IRT");
        team1.setCity("Tanger");

        Team team2 = new Team();
        team2.setName("Real Madrid");
        team2.setCity("Madrid");


        Team team3 = new Team();
        team3.setName("Manchester United");
        team3.setCity("Manchester");

        service.saveTeam(team1);
//        service.saveTeam(team2);
//        service.saveTeam(team3);
//
//        service.findAllTeams();


//        Player player1 = new Player();
//        player1.setName("Abdelmajid bouselham");
//        player1.setPosition("Java");
//        player1.setNumber(10);
//
//
//        Player player2 = new Player();
//        player2.setName("Mohammed Bourhym");
//        player2.setPosition("Enset");
//        player2.setNumber(11);

//        service.savePlayer(player1);
//        service.savePlayer(player2);



    }





}
