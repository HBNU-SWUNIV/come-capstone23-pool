package com.example.siren.domain.MnN;

import com.example.siren.domain.post.Post;
import lombok.Data;

@Data
public class Crew {
   private Long memberId;
   private String memberName;
   private String dow;
   private String times;


   public Crew(Long memberId, String memberName, String dow, String times) {
      this.memberId = memberId;
      this.memberName = memberName;
      this.dow = dow;
      this.times = times;
   }
}
