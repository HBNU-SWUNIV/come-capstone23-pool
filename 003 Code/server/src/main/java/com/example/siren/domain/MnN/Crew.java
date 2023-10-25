package com.example.siren.domain.MnN;

import com.example.siren.domain.post.Post;
import lombok.Data;

@Data
public class Crew {
   private Long memberId;
   private String memberName;
   private String dow;
   private String times;
   private String driver;


   public Crew(Long memberId, String memberName, String dow, String times,String driver) {
      this.memberId = memberId;
      this.memberName = memberName;
      this.dow = dow;
      this.times = times;
      this.driver = driver;
   }
}
