package com.example.demo3;

import com.example.demo3.persistence.MemberRepository;
import com.example.demo3.persistence.ProfileRepository;
import com.example.demo3.vo.Memner;
import com.example.demo3.vo.Profile;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class ProfileTests {

    @Autowired
    MemberRepository memberRepo;

    @Autowired
    ProfileRepository profileRepo;


    // 더미회원
    @Test
    public void testInsertMembers(){

        IntStream.range(1,101).forEach(i -> {
            Memner memner = new Memner();
            memner.setUid("user"+i);
            memner.setUpw("pw"+i);
            memner.setUname("사용자"+i);

            memberRepo.save(memner);
        });
    }
    @Test
    public void testInsertProfile(){

        Memner memner = new Memner();
        memner.setUid("user1");

        for (int i = 1; i < 5; i++){
            Profile profile1 = new Profile();
            profile1.setFname("face"+i+".jsp");

            if(i == 1){
                profile1.setCurrent(true);
            }

            profile1.setMemner(memner);

            profileRepo.save(profile1);
        }

    }
    @Test
    public void testFetchJoin1(){
        List<Object[]> result = memberRepo.getMemnerByWithProfileCount("user1");

        result.forEach(arr-> System.out.println(Arrays.toString(arr)));
    }
    @Test
    public void testFetchJoin2(){
        List<Object[]> result = memberRepo.gerMemnerByWithProfile("user1");

        result.forEach(arr-> System.out.println(Arrays.toString(arr)));
    }
}
