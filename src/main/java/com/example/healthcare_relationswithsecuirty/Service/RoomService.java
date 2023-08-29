package com.example.healthcare_relationswithsecuirty.Service;

import com.example.healthcare_relationswithsecuirty.Api.ApiException;
import com.example.healthcare_relationswithsecuirty.Model.Patient;
import com.example.healthcare_relationswithsecuirty.Model.Room;
import com.example.healthcare_relationswithsecuirty.Repository.PatientRepository;
import com.example.healthcare_relationswithsecuirty.Repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final PatientRepository patientRepository;

    public List<Room> getAllRoom(){
        return roomRepository.findAll();
    }

    public void addRoom(Room room){
        roomRepository.save(room);
    }

    public void updateRoom(Integer id, Room room){
        Room oldRoom = roomRepository.findRoomById(id);

        if (oldRoom == null)
            throw new ApiException("Sorry, room id is wrong");

        oldRoom.setRoomtype(room.getRoomtype());

        roomRepository.save(oldRoom);
    }

    public void deleteRoom(Integer id){
        Room deleteRoom = roomRepository.findRoomById(id);

        if (deleteRoom == null)
            throw new ApiException("Sorry, room id is wrong");

        roomRepository.delete(deleteRoom);
    }

    public List<Room> getBasedOnRoomType(String roomtype){
        List<Room> roomList = roomRepository.basedOnRoomType(roomtype);

        if (roomList.isEmpty())
            throw new ApiException("Sorry the room type is wrong");

        return roomList;

    }

    public List<Room> orderedRooms(){
        List<Room> roomList = roomRepository.orderedRoom();

        return roomList;
    }
}
