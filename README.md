# 한밭대학교 컴퓨터공학과 Pool팀
**환경보호와 공유경제 실천을 위한 카풀 서비스 앱(Carpool service app for environmental protection and sharing economy)**
![panel](https://github.com/HBNU-SWUNIV/come-capstone23-pool/assets/127067775/1ae06c33-c5c6-4056-9471-ef4c7a1dffa9)

## <u>🏃‍♂️Teamate
**팀 구성**  
- 개발&기획
	- 20187094 강홍규
- 디자인&기획     
	- 20151941 손이지

## </u> 🧐Project Background
- ### 필요성
  - '카풀 서비스 앱'은 게시글과 채팅을 통해 쉽게 카풀 이용자를 모집하여 이용하는 앱이다. 전 세계적으로 환경 보호에 대한 관심을 더욱 높아짐에 따라 차량 이용 감소를 위해 힘쓰고 있지만 대중교통의 불편함과 시간 절약을 이유로 승용차 이용 인구는 오히려 증가하였다. 따라서 승용차의 장점은 가져가되 차량 이용을 줄여 교통체증과 온실가스 배출을 감소시켜 환경 보호에 도움이 되고자 제시한 방법이 카풀 서비스 앱이다. 과반수의 사람들이 카풀 도입에 긍정적이며 관심이 있지만 이용이 어렵다는 인식이 있어 이를 개선하기 위해 쉽게 이용할 수 있는 서비스가 필요하다.

- ### 기존 해결책의 문제점
  - 기존 출시된 서비스는 직장인 같은 특정 사용층에 초점을 맞추거나 일회성 이용으로 택시를 대체하는 것에 집중되어 있었고 이용자의 선택 폭이 좁아 불편함을 주었다. 하지만 이 앱은 정기적으로 이용하는 공유 차량에 집중하여 승용차의 대체에 초점을 두었다.
  
## 💻System Design

<h3> Tech Stack </h3>
<h4> Platforms & Languages </h4>

<div align="left">
	<img src="https://img.shields.io/badge/Java-007396?style=flat&logo=Conda-Forge&logoColor=white" />
	<img src="https://img.shields.io/badge/jQuery-0769AD?style=flat&logo=jQuery&logoColor=white" />
	<img src="https://img.shields.io/badge/Spring-6DB33F?style=flat&logo=Spring&logoColor=white" />
	<img src="https://img.shields.io/badge/Android-3DDC84?style=flat&logo=android&logoColor=white" />
	<img src="https://img.shields.io/badge/Gradle-02303A?style=flat&logo=gradle&logoColor=white" />
	<br>
	<img src="https://img.shields.io/badge/mongodb-47A248?style=flat&logo=mongodb&logoColor=white" />
	<img src="https://img.shields.io/badge/h2DB-4053D6?style=flat&logo=amazondynamodb&logoColor=black" />
	
</div>
<br>

<h4> Tools </h4>
<div align=left>
	<img src="https://img.shields.io/badge/Intellij%20IDE-000000?style=flat&logo=intellijidea&logoColor=white" />
	<img src="https://img.shields.io/badge/Tomcat-F8DC75?style=flat&logo=ApacheTomcat&logoColor=white" />
	<img src="https://img.shields.io/badge/AWS-232F3E?style=flat&logo=AmazonAWS&logoColor=white" />
	<img src="https://img.shields.io/badge/Androidstudio-3DDC84?style=flat&logo=androidstudio&logoColor=white"/>
	<br>
	<img src="https://img.shields.io/badge/Figma-F24E1E?style=flat&logo=figma&logoColor=white" />	
	<img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=white" />
</div>
<br><br>

### System Requirements
   
![need](https://github.com/HBNU-SWUNIV/come-capstone23-pool/assets/77769783/463556d1-1c64-4b2e-bfca-9411fcdcc8ae)
    
## 📚Case Study
  - ### 기존 서비스 분석
    - 카카오 T 카풀
      - 카카오의 서비스로 높은 접근성
      - 차량 선택의 제한 (연식, 차종 등 좁은 선택 폭)
      - 위치 제한 없어 먼 거리에 있는 사용자 인식
    - 네카
      - 경로 선택 시 자동 추천 기능
      - 대표 경로 수정 불가
    - 풀카
      - 직장인 전용. 같은 직장 재직자끼리 이용 가능
      - 직장 이메일 없으면 등록 불가
      - 회사 주변으로만 위치 지정 가능
    - 풀럭
      - 무료 서비스
      - 1회성 카풀만 지원
  - ### 기존 서비스 보완
    - 정기적<br>
      &nbsp;&nbsp;1회성으로 매번 예약해야 하는 불편함<br>&nbsp;&nbsp;-> 한 번의 등록으로 별도의 예약 없이 정기적 이용 가능
    - 자유로움<br>
      &nbsp;&nbsp;제한된 옵션, 위치 설정<br>&nbsp;&nbsp;-> 자유로운 위치 설정, 다양한 옵션 제공으로 원하는 운전자, 탑승자 선택
    - 융화<br>
      &nbsp;&nbsp;개인의 자가용 유상 제공시 운수사업법 위반, 택시 등 기존 시장과의 갈등<br>&nbsp;&nbsp;-> 출ㆍ퇴근시간대(오전 7-9시, 오후 6-8시(주말 및 공휴일 제외))에 제한적 이용
    - 새로운 개념<br>
      &nbsp;&nbsp;차량 소지자가 카풀 모임에 참여하는 새로운 방법<br>&nbsp;&nbsp;-> 교대 운행모드를 통해 가격을 지불하는 기존 방식과는 달리 이용자끼리 각각 본인의차량으로 교대로 운전하여 무료로 카풀을 이용하는 교대 운행 모드 추가

  
## 📲Result
   ### 시스템 구성도
![project](https://github.com/HBNU-SWUNIV/come-capstone23-pool/assets/77769783/da36abb1-c58a-4f56-a4e8-b33c3a01292f)
   ### 주요기능
   - #### 로그인 기능
      - 세션을 활용한 자동 로그인기능 <br><br>
      	<img src="https://github.com/HBNU-SWUNIV/come-capstone23-pool/assets/77769783/8ac89075-1000-4259-8a3e-7ece76e0843a" width="200" height="400"/>
     
   - #### 카풀 게시물 리스트
      - 게시물 검색 기능(출발지, 도착지, 운행요일, 세부 옵션 등으로 검색)
      - 회원 가입시 등록한 사용자의 거주지와 인접한 출발지를 가진 카풀 게시물만 보는 기능
      - 게시물 요약 정보 확인 (운전자, 별점, 출발지, 도착지, 출발시간)<br><br>
	<img src="https://github.com/HBNU-SWUNIV/come-capstone23-pool/assets/77769783/4309219b-9fa3-40d8-9279-bad55ca3fb1e" width="200" height="400"/>
  	<img src="https://github.com/HBNU-SWUNIV/come-capstone23-pool/assets/77769783/0f55ae73-bdf7-4bf1-988f-2dbec0b43aa6" width="200" height="400"/>   

   - #### 카풀 게시물 등록
      - 마이 페이지에서 차량을 등록한 사용자만 게시물 등록 가능
      - 네이버 지도 API를 사용하여 출발지와 목적지를 세밀하게 등록 가능
      - 카풀 운행 요일 및 시간 등록
      - 카풀 운행 거리에 따른 카풀 요금 추천
      - 카풀 탑승 세부 조건 등록 (성별, 흡연여부, 반려동물 탑승 가능 여부, 아이동반 탑승 가능여부, 화물 소지 가능 여부)
      - 최대 탑승 가능 인원 설정 
      - 추가 전달사항 등록 <br><br>
        <img src="https://github.com/HBNU-SWUNIV/come-capstone23-pool/assets/77769783/7a460606-3b36-482f-8690-b4dc18174198" width="200" height="450"/>
	        <img src="https://github.com/HBNU-SWUNIV/come-capstone23-pool/assets/77769783/5d41e566-e468-4675-9703-77c9a44a35d5" width="200" height="400"/>

  - #### 카풀 게시물 확인
      - 카풀 세부 정보 확인(출발지, 운행 요일 및 시간, 세부옵션, 카풀 평점 등)
      - 카풀 게시물 등록자 정보 확인(사용자 정보 페이지로 이동)
      - 출발지 목적지 터치 시 지도에 위치 표시
      - 카풀 신청 기능 <br><br>
             <img src="https://github.com/HBNU-SWUNIV/come-capstone23-pool/assets/77769783/538c3449-4e66-4c22-84fc-d920d6ec30d0" width="200" height="450"/>


  - #### 등록된 카풀 리스트
      - 현재 자신이 소속된 카풀 리스트 확인
      - 카풀 게시물 등록자인 경우 게시물 관리자가 됨
      - 카풀 관리자인 경우 해당 카풀 신청자 리스트를 확인 가능
      - 카풀 관리자인 경우 해당 카풀에 공지사항 등록 가능
      - 카풀 관리자인 경우 해당 카풀에 새로운 유저 등록 및 기존 유저 탑승 일정 수정 및 삭제 가능
      - 카풀 운행 요일이 표로 나오며 해당 요일에 탑승자 명단과 운전자가 표시
      - 카풀 탑승객인 경우 1회 카풀 평점 등록 가능 <br><br>
        <img src="https://github.com/HBNU-SWUNIV/come-capstone23-pool/assets/77769783/7e4f5a57-d259-41ac-a57b-8fe345db178a" width="200" height="450"/>
  
  - #### 채팅 기능
      - 카풀 관리자는 카풀 신청자 리스트에서 신청자의 평점과 세부 정보확인 가능
      - 카풀 관리자는 신청자를 카풀에 등록시키기 전 채팅 가능
      - 채팅 버튼 누를 시 채팅 목록 페이지에 채팅방 생성
      - 소켓 통신으로 실시간 채팅 <br><br>
        
        <img src="https://github.com/HBNU-SWUNIV/come-capstone23-pool/assets/77769783/f4a76cb7-3b73-47ea-baba-853331d1c15a" width="200" height="400"/>

  - #### 마이페이지
    - 본인의 평점 확인 기능
    - 프로필 사진 등록 기능
    - 카풀 탑승 일정 시간표로 호가인 가능
    - 차량 사진과 정보 등록 기능
    - 가상 머니 충전, 송금 출금 기능
    - 회원 정보 수정 및 회원 탈퇴 기능 <br><br>
        <img src="https://github.com/HBNU-SWUNIV/come-capstone23-pool/assets/77769783/f693c03b-b280-40ad-9081-65ad3f7dd08e" width="200" height="400"/>

   
  ## 🎓Conclusion

  글로벌 카풀 시장의 규모가 148억 달러(2015)에서 701억 달러(2021)로 급증한 것에 비해 우리나라의 카풀 규모는 작은 편이지만 처음 서비스가 시작된 해에 6억 원(2011)에 불과하던 것과 비교하면 1800억 원(2017)으로 300배 성장했다. 전 세계적으로 공유 교통이 새로운 교통수단으로 자리 잡아가고 있고 우리나라 또한 택시대란을 해소하기 위해 국토교통부가 여객자동차운수사업법 개정 가능성을 논의하고 있다. 따라서 서비스를 공급하여 시장의 수요를 만들고 공유 교통을 더욱 대중화하는 것을 기대할 수 있다.

