package com.example.healthcare_relationswithsecuirty;

import com.example.healthcare_relationswithsecuirty.Model.Room;
import com.example.healthcare_relationswithsecuirty.Repository.RoomRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoomRepositoryTest {

    @Autowired
    RoomRepository roomRepository;


    Room room1;
    Room room2;
    Room room3;
    Room room4;
    Room room5;
    Room checkRoom;

    List<Room> rooms;

    @BeforeEach
    void setUp() {
        room1 = new Room(1,"CCC",null);
        room2 = new Room(2,"BBB",null);
        room3 = new Room(3,"AAA",null);
        room4 = new Room(4,"AAA",null);
        room5 = new Room(5,"AAA",null);

    }

    @Test
    public void findRoomByIdTest(){
        roomRepository.save(room1);
        checkRoom = roomRepository.findRoomById(room1.getId());

        Assertions.assertThat(checkRoom).isEqualTo(room1);
    }

    @Test
    public void getRoomByRoomTypeTest(){
        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);
        rooms = roomRepository.orderedRoom();
        Assertions.assertThat(rooms.get(0).getRoomtype()).isEqualTo(room3.getRoomtype());
    }

    @Test
    public void findRoomBasedOnRoomTypeTest(){
        roomRepository.save(room1);
        roomRepository.save(room2);
        // test the rooms
        roomRepository.save(room3);
        roomRepository.save(room4);
        roomRepository.save(room5);

        rooms = roomRepository.basedOnRoomType("AAA");

        Assertions.assertThat(rooms.size()).isEqualTo(3);

    }
}
