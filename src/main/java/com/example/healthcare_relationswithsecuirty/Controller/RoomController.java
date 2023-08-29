package com.example.healthcare_relationswithsecuirty.Controller;

import com.example.healthcare_relationswithsecuirty.Api.ApiResponse;
import com.example.healthcare_relationswithsecuirty.Model.Bill;
import com.example.healthcare_relationswithsecuirty.Model.Room;
import com.example.healthcare_relationswithsecuirty.Service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/get")
    public ResponseEntity getAllRooms(){
        return ResponseEntity.status(200).body(roomService.getAllRoom());
    }

    @PostMapping("/add")
    public ResponseEntity addNewRoom(@RequestBody @Valid Room room){
        roomService.addRoom(room);
        return ResponseEntity.status(200).body(new ApiResponse("the room added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateRoom(@PathVariable Integer id, @RequestBody @Valid Room room){
        roomService.updateRoom(id,room);
        return ResponseEntity.status(200).body(new ApiResponse("updated room info successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteRoom(@PathVariable Integer id){
        roomService.deleteRoom(id);
        return ResponseEntity.status(200).body(new ApiResponse("The room deleted successfully"));
    }

    @GetMapping("getroomtype/{roomtype}")
    public ResponseEntity getBasedOnRoomsType(@PathVariable String roomtype){
        List<Room> roomList = roomService.getBasedOnRoomType(roomtype);
        return ResponseEntity.status(200).body(roomList);
    }

    @GetMapping("orderRoom")
    public ResponseEntity orderedRoomAsec(){
        return ResponseEntity.status(200).body(roomService.orderedRooms());
    }

}
