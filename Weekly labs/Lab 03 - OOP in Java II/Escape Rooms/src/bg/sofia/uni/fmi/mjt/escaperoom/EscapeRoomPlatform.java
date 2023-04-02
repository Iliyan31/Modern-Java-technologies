package bg.sofia.uni.fmi.mjt.escaperoom;

import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomNotFoundException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.TeamNotFoundException;
import bg.sofia.uni.fmi.mjt.escaperoom.room.EscapeRoom;
import bg.sofia.uni.fmi.mjt.escaperoom.room.Review;
import bg.sofia.uni.fmi.mjt.escaperoom.team.Team;

public class EscapeRoomPlatform extends EscapeRoomPlatformValidator implements EscapeRoomAdminAPI, EscapeRoomPortalAPI {

    private final int maxCapacity;
    private final Team[] teams;
    private final EscapeRoom[] escapeRooms;

    public EscapeRoomPlatform(Team[] teams, int maxCapacity) {
        this.teams = teams;
        this.maxCapacity = maxCapacity;
        this.escapeRooms = new EscapeRoom[maxCapacity];
    }

    @Override
    public void addEscapeRoom(EscapeRoom room) throws RoomAlreadyExistsException {
        validateEscapeRoom(room);
        validatePlatformCapacity(getNumberOfCurrentEscapeRooms(), maxCapacity);
        validateExistsRoom(room.getName(), escapeRooms);

        addRoom(room);
    }

    @Override
    public void removeEscapeRoom(String roomName) throws RoomNotFoundException {
        validateRoomName(roomName);
        validateDoesNotExistRoom(roomName, escapeRooms);

        clearSpecificRoom(roomName);
    }

    @Override
    public EscapeRoom[] getAllEscapeRooms() {
        int sizeOfEscapeRooms = sizeOfNonNullEscapeRooms();

        EscapeRoom[] allEscapeRooms = null;
        allEscapeRooms = new EscapeRoom[sizeOfEscapeRooms];

        int index = 0;
        for (EscapeRoom escapeRoom : this.escapeRooms) {
            if (escapeRoom != null) {
                allEscapeRooms[index++] = escapeRoom;
            }
        }

        return allEscapeRooms;
    }

    @Override
    public void registerAchievement(String roomName, String teamName, int escapeTime) throws RoomNotFoundException, TeamNotFoundException {
        validateRoomName(roomName);
        validateTeamName(teamName);
        validateEscapeTime(escapeTime);
        validateDoesNotExistRoom(roomName, escapeRooms);
        validateExceedsTimeLimitOfRoom(roomName, escapeTime, escapeRooms);
        validateContainsSuchTeam(teamName, teams);

        EscapeRoom exactRoom = null;
        exactRoom = getEscapeRoomByItsName(roomName);


        Team exactTeam = null;
        exactTeam = getTeamByName(teamName);

        int points = sumPointsForTeamForExactRoom(exactRoom, escapeTime);

        if(exactTeam != null) {
            exactTeam.updateRating(points);
        }
    }

    @Override
    public EscapeRoom getEscapeRoomByName(String roomName) throws RoomNotFoundException {
        validateRoomName(roomName);
        validateDoesNotExistRoom(roomName, escapeRooms);

        return getEscapeRoomByItsName(roomName);
    }

    @Override
    public void reviewEscapeRoom(String roomName, Review review) throws RoomNotFoundException {
        validateRoomName(roomName);
        validateDoesNotExistRoom(roomName, escapeRooms);

        addEscapeRoomReview(roomName, review);
    }

    @Override
    public Review[] getReviews(String roomName) throws RoomNotFoundException {
        validateRoomName(roomName);
        validateDoesNotExistRoom(roomName, escapeRooms);

        return getSortedReviews(roomName);
    }

    @Override
    public Team getTopTeamByRating() {
        double maxRating = -1;
        Team topTeam = null;

        for (Team team : this.teams) {
            if (team != null) {
                double currentTeamRating = team.getRating();

                if (maxRating < currentTeamRating) {
                    maxRating = currentTeamRating;
                    topTeam = team;
                }
            }
        }

        return topTeam;
    }

    private void addRoom(EscapeRoom room) {
        for (int i = 0; i < this.escapeRooms.length; i++) {
            if (escapeRooms[i] == null) {
                escapeRooms[i] = room;
                break;
            }
        }
    }

    private int getNumberOfCurrentEscapeRooms() {
        int numberOfEscapeRooms = 0;

        for (EscapeRoom escapeRoom: this.escapeRooms) {
            if (escapeRoom == null) {
                continue;
            }

            numberOfEscapeRooms++;
        }

        return numberOfEscapeRooms;
    }

    private void clearSpecificRoom(String roomName) {
        for (int i = 0; i < this.escapeRooms.length; i++) {
            if (escapeRooms[i] == null) {
                continue;
            }

            if (escapeRooms[i].getName().equals(roomName)) {
                escapeRooms[i] = null;
            }
        }
    }

    private int sizeOfNonNullEscapeRooms() {
        int size = 0;

        for (EscapeRoom escapeRoom : this.escapeRooms) {
            if (escapeRoom != null) {
                size++;
            }
        }

        return size;
    }

    private EscapeRoom getEscapeRoomByItsName(String roomName) {
        for (EscapeRoom escapeRoom : this.escapeRooms) {
            if (escapeRoom == null) {
                continue;
            }

            if (escapeRoom.getName().equals(roomName)) {
                return escapeRoom;
            }
        }

        return escapeRooms[0];
    }

    private Team getTeamByName(String teamName) {
        for (Team team : this.teams) {
            if (team == null) {
                continue;
            }

            if (team.getName().equals(teamName)) {
                return team;
            }
        }

        return teams[0];
    }

    private void addEscapeRoomReview(String roomName, Review review) {
        for (EscapeRoom escapeRoom : this.escapeRooms) {
            if (escapeRoom == null) {
                continue;
            }

            if (escapeRoom.getName().equals(roomName)) {
                escapeRoom.addReview(review);
            }
        }
    }

    private Review[] getSortedReviews(String roomName) {
        for (EscapeRoom escapeRoom : this.escapeRooms) {
            if (escapeRoom == null) {
                continue;
            }

            if (escapeRoom.getName().equals(roomName)) {
                return escapeRoom.getSortedByTimeAddedReviews();
            }
        }
        return new Review[0];
    }

    private int sumPointsForTeamForExactRoom(EscapeRoom exactRoom, int escapeTime) {
        int points = 0;
        int maxTimeToEscapeRoom = 0;

        if (exactRoom == null) {
            return 0;
        }

        maxTimeToEscapeRoom = exactRoom.getMaxTimeToEscape();

        if (escapeTime <= maxTimeToEscapeRoom) {
            points += exactRoom.getDifficulty().getRank();

            if (escapeTime <= maxTimeToEscapeRoom * 0.5) {
                points += 2;
            }
            else if (escapeTime > maxTimeToEscapeRoom * 0.5 && escapeTime <= maxTimeToEscapeRoom * 0.75) {
                points += 1;
            }
        }

        return points;
    }

}

