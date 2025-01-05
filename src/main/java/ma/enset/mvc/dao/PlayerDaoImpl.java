package ma.enset.mvc.dao;

import ma.enset.mvc.dao.entities.Player;
import ma.enset.mvc.dao.entities.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoImpl implements PlayerDao {

    @Override
    public void save(Player player) {
        String query = "INSERT INTO players (name, position, number, team_id) VALUES (?, ?, ?, ?)";
        try {

            Connection connection = SignletonConnexionDB.getConnection();
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, player.getName());
            pstm.setString(2, player.getPosition());
            pstm.setInt(3, player.getNumber());
            pstm.setObject(4, player.getTeam().getId());
            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Player player) {
        String query = "UPDATE players SET name = ?, position = ?, number = ?, team_id = ? WHERE player_id = ?";
        try  {
            Connection connection = SignletonConnexionDB.getConnection();
            PreparedStatement pstm = connection.prepareStatement(query);

            pstm.setString(1, player.getName());
            pstm.setString(2, player.getPosition());
            pstm.setInt(3, player.getNumber());
            pstm.setObject(4, player.getTeam());
            pstm.setLong(5, player.getId());
            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        String query = "DELETE FROM players WHERE player_id = ?";
        try  {
            Connection connection = SignletonConnexionDB.getConnection();
            PreparedStatement pstm = connection.prepareStatement(query);

            pstm.setLong(1, id);
            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Player findById(Long id) {
        Player player = null;  // Initialize player outside the try-catch block
        String query = "SELECT * FROM players WHERE player_id = ?";
        try  {
            Connection connection = SignletonConnexionDB.getConnection();
            PreparedStatement pstm = connection.prepareStatement(query);

            pstm.setLong(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                player = new Player(); // Create the player only if a record is found
                player.setId(rs.getLong("player_id"));
                player.setName(rs.getString("name"));
                player.setPosition(rs.getString("position"));
                player.setNumber(rs.getInt("number"));
                player.setId(rs.getLong("team_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player; // Return the player (null if not found)
    }

    @Override
    public List<Player> findAll() {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM players ";
        try  {
            Connection connection = SignletonConnexionDB.getConnection();
            PreparedStatement pstm = connection.prepareStatement(query);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Player player = new Player();
                player.setId(rs.getLong("player_id"));
                player.setName(rs.getString("name"));
                player.setPosition(rs.getString("position"));
                player.setNumber(rs.getInt("number"));
                player.setTeam(findTeamById(rs.getLong("team_id")));
                players.add(player);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    public Team findTeamById(long teamId) {
        String query = "SELECT * FROM teams WHERE team_id = ?";
        Team team = null;
        try  {
            Connection connection = SignletonConnexionDB.getConnection();
            PreparedStatement pstm = connection.prepareStatement(query);

            pstm.setLong(1, teamId);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    team = new Team();
                    team.setId(rs.getLong("team_id"));
                    team.setName(rs.getString("name"));
                    team.setCity(rs.getString("city"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return team;
    }


}
