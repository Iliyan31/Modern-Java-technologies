package bg.sofia.uni.fmi.mjt.escaperoom;

import bg.sofia.uni.fmi.mjt.escaperoom.exception.PlatformCapacityExceededException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomNotFoundException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.TeamNotFoundException;
import bg.sofia.uni.fmi.mjt.escaperoom.room.EscapeRoom;
import bg.sofia.uni.fmi.mjt.escaperoom.team.Team;

public class EscapeRoomPlatformValidator {
    void validateRoomName(String roomName) {
        if (roomName == null || roomName.isEmpty() || roomName.isBlank()) {
            throw new IllegalArgumentException("The room's name must not be null, or empty, neither blank!");
        }
    }

    void validateTeamName(String teamName) {
        if (teamName == null || teamName.isEmpty() || teamName.isBlank()) {
            throw new IllegalArgumentException("The team's name must not be null, or empty, neither blank!");
        }
    }

    void validateEscapeTime(int escapeTime) {
        if (escapeTime <= 0) {
            throw new IllegalArgumentException("The escape time must not be below 0!");
        }
    }

    void validateExistsRoom(String roomName, EscapeRoom[] escapeRooms) throws RoomAlreadyExistsException {
        if(containsRoomWithSuchName(roomName, escapeRooms)) {
            throw new RoomAlreadyExistsException("The current room already exists!");
        }
    }

    void validateDoesNotExistRoom(String roomName, EscapeRoom[] escapeRooms) throws RoomNotFoundException {
        if ( ! containsRoomWithSuchName(roomName, escapeRooms)) {
            throw new RoomNotFoundException("There was not found room with such name!");
        }
    }

    void validatePlatformCapacity(int numberOfCurrentEscapeRooms, int maxCapacity) {
        if (numberOfCurrentEscapeRooms + 1 > maxCapacity) {
            throw new PlatformCapacityExceededException("The platform has already reached its capacity!");
        }
    }

    void validateContainsSuchTeam(String teamName, Team[] teams) throws TeamNotFoundException {
        if (!containsTeamWithSuchName(teamName, teams)) {
            throw new TeamNotFoundException("There was not found team with such name!");
        }
    }

    void validateExceedsTimeLimitOfRoom(String roomName, int escapeTime, EscapeRoom[] escapeRooms) {
        if (!checkExceedsTimeLimit(roomName, escapeTime, escapeRooms)) {
            throw new IllegalArgumentException("Time to escape exceeded!");
        }
    }

    void validateEscapeRoom(EscapeRoom room) {
        if (room == null) {
            throw new IllegalArgumentException("Room must not be null value!");
        }
    }

    private boolean containsRoomWithSuchName(String roomName, EscapeRoom[] escapeRooms) {
        for (EscapeRoom escapeRoom : escapeRooms) {
            if (escapeRoom == null) {
                continue;
            }

            if (escapeRoom.getName().equals(roomName)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkExceedsTimeLimit(String roomName, int escapeTime, EscapeRoom[] escapeRooms) {
        for (EscapeRoom escapeRoom : escapeRooms) {
            if (escapeRoom == null) {
                continue;
            }

            if (escapeRoom.getName().equals(roomName)) {
                if (escapeTime <= escapeRoom.getMaxTimeToEscape()) {
                    return true;
                }

                break;
            }
        }

        return false;
    }

    private boolean containsTeamWithSuchName(String teamName, Team[] teams) {
        for (Team team : teams) {
            if (team == null) {
                continue;
            }

            if (team.getName().equals(teamName)) {
                return true;
            }
        }

        return false;
    }

}
