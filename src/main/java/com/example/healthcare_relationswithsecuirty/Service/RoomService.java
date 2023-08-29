package com.example.healthcare_relationswithsecuirty.Service;

import com.example.healthcare_relationswithsecuirty.Api.ApiException;
import com.example.healthcare_relationswithsecuirty.Model.Patient;
import com.example.healthcare_relationswithsecuirty.Model.Room;
import com.example.healthcare_relationswithsecuirty.Model.User;
import com.example.healthcare_relationswithsecuirty.Repository.AuthRepository;
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
    private final AuthRepository authRepository;

    public List<Room> getAllRoom(Integer user_id){
        User user = authRepository.findUserById(user_id);

        if (!(user.getRole().equals("ADMIN")))
            throw new ApiException("Sorry, only admin can see this page");
        return roomRepository.findAll();
    }

    public void addRoom(Integer user_id,Room room){
        User user = authRepository.findUserById(user_id);

        if (!(user.getRole().equals("ADMIN")))
            throw new ApiException("Sorry, only admin can see this page");

        roomRepository.save(room);
    }

    public void updateRoom(Integer user_id,Integer id, Room room){
        User user = authRepository.findUserById(user_id);
        if (!(user.getRole().equals("ADMIN")))
            throw new ApiException("Sorry, only admin can see this page");

        Room oldRoom = roomRepository.findRoomById(id);

        if (oldRoom == null)
            throw new ApiException("Sorry, room id is wrong");

        oldRoom.setRoomtype(room.getRoomtype());

        roomRepository.save(oldRoom);
    }

    public void deleteRoom(Integer user_id,Integer id){
        User user = authRepository.findUserById(user_id);
        if (!(user.getRole().equals("ADMIN")))
            throw new ApiException("Sorry, only admin can see this page");

        Room deleteRoom = roomRepository.findRoomById(id);

        if (deleteRoom == null)
            throw new ApiException("Sorry, room id is wrong");

        roomRepository.delete(deleteRoom);
    }

    public List<Room> getBasedOnRoomType(Integer user_id,String roomtype){
        User user = authRepository.findUserById(user_id);
        if (!(user.getRole().equals("ADMIN")))
            throw new ApiException("Sorry, only admin can see this page");

        List<Room> roomList = roomRepository.basedOnRoomType(roomtype);

        if (roomList.isEmpty())
            throw new ApiException("Sorry the room type is wrong");

        return roomList;

    }

    public List<Room> orderedRooms(Integer user_id){
        User user = authRepository.findUserById(user_id);
        if (!(user.getRole().equals("ADMIN")))
            throw new ApiException("Sorry, only admin can see this page");

        List<Room> roomList = roomRepository.orderedRoom();

        return roomList;
    }
}
