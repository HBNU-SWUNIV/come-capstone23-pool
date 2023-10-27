# 📂 Package Description
## 👭 MnN
테이블의 다대다 매핑을 위한 패키지 입니다. <br>
멤버 테이블과 채팅 테이블, 멤버 테이블과 게시물 테이블은 각각 M : N(다대다) 매핑이 되어야하고 이를 위한 중간 테이블을 생성하였습니다.<br> 이 패키지에서는 이를 관리하는 기능을 수행합니다.
<br>
 - #### mac(Member And Chat) : 멤버테이블과 채팅 테이블 사이의 다대다 매핑을 위해 생성된 중간 테이블(MAC)에 SpringDataJpa를 사용하여 값을 삽입,조회,수정,삭제 등의 기능을 수행
 - #### map(Member And Post) : 멤버테이블과 게시물 테이블 사이의 다대다 매핑을 위해 생성된 중간 테이블(MAP)에 SpringDataJpa를 사용하여 값을 삽입,조회,수정,삭제 등의 기능을 수행  
 - #### service : 다대다 매핑 관련 비즈니스 로직을 수행
 - #### Crew.java : Post에 포함된 Member로써 map 패키지에서 사용되는 DTO
 - #### MemberAndChat.java : mac테이블과의 연결을 위한 DTO
 - #### MemberAndPost.java  : map테이블과의 연결을 위한 DTO
   
## 📫 app
카풀 게시물에 신청(application)을 하면 신청자들의 정보 리스트를 저장 및 관리하고 이를 게시물 관리자에게 보여주기 위한 기능을 수행합니다.<br>
 - #### repository : 카풀신청자의 정보와 게시물과 연결하여 관리하는 역할을 수행
 - #### Application.java : 카풀 신청자 정보를 관리에 사용되는 DTO
   
## 🚗 car
사용자의 자동차 정보를 관리합니다. Member테이블과 매핑되어있고 차량사진의 정보를 저장하는 carImage 테이블과 1:1 매핑이 되어있습니다.
 - #### Car.java : 자동차 정보에 대한 테이블 관리에 사용되는 DTO
 - #### CarService.java :CAR 테이블에서 자동차 정보를 삽입, 수정, 조회기능에 대한 인터페이스
 - #### CarServiceImple.java : CarService.java의 구현 클래스
 - #### SpringDataJpaCarRepository.java : Spring Data JPA를 상속받아 CAR 테이블에 CRUD 기능을 실행
   
## 🚗 carImage
사용자의 차량사진 정보를 관리합니다. 차량사진이 서버에 어느 위치에 저장되어있는지, 어떤 사용자의 차량사진인지 등을 DB를 사용하여 관리합니다.
 - #### CarFileHandler.java : 차량 사진 데이터를 가공하여 서버에 특정 위치에 저장
 - #### CarImage.java : 차량 사진 관리에 사용되는 DTO
 - #### CarServiceImple.java : 차량 사진 정보에 관한 비즈니스로직 실행
 - #### SpringDataJpaCarRepository.java : Spring Data JPA를 상속받아 CARIMAGE 테이블에 CRUD 기능을 실행
   
## ✉ chat
채팅 기능을 구현하는 클래스들이 포함된 패키지입니다. 채팅 방을 생성하여 DB에 저장하고 웹소켓을 사용하여 채팅방에 포함된 사용자끼리 실시간 채팅을 할 수 있도록 했으며 동시에 채팅기록을 몽고DB에 저장합니다.
 - #### repository : 몽고DB로부터 채팅 기록을 가져와 리스트 생성
 - #### ChatMessgage.java : DB에서 채팅에 대한 정보를 관리할때 사용하는 DTO 
 - #### ChatRoom.java : 채팅방 내부에서 소켓통신으로 전송되는 메시지의 웹소켓 세션과 내용등을 분석하고 관리  
 - #### ChatService.java : 웹소켓 세션을 통해 메시지 전송기능을 수행하며 채팅방을 생성하고 DB를 통해 채팅방에 대한 정보를 관리하는 기능을 수행
 - #### SpringDataJpaChatRepository.java : Spring Data JPA를 상속받아 채팅기능에 관련된 CRUD 기능을 실행
   
## 🖼 file
앱에서 사용될 전체적인 사진파일들을 관리하는 패키지 입니다. DB를통해 파일의 저장위치 및 파일 이름등을 저장하고 관리합니다.
 - #### FileHandler.java : 사진 데이터를 가공하여 서버에 특정 위치에 저장
 - #### Board.java : 사진 관리에 사용되는 DTO
 - #### BoardRepository.java : Spring Data JPA를 상속받아 BOARD 테이블에 CRUD 기능을 실행
 - #### BoardService.java : 사진 정보에 관한 비즈니스로직 실행
   
## login
로그인 기능을 수행하는 패키지입니다. 

## 🧑 member
사용자 정보를 관리하는 클래스입니다. 
 - #### repositroy : Spring Data JPA를 상속받아 DB의 MEMEBER 테이블에 사용자 정보를 CRUD 수행 
 - #### service : 사용자 정보에 관한 비즈니스 로직 수행
 - #### Member.java : 사용자 정보 관리에 사용되는 DTO
 - #### MemberUpdateDto.java : 사용자 정보 업데이트에 사용되는 DTO

## 📝 post
카풀 게시물 정보를 관리하는 클래스입니다.
 - #### repositroy : Spring Data JPA를 상속받아 DB의 POST 테이블에 카풀 게시물 정보를 CRUD 수행 
 - #### service : 카풀 게시물 정보에 관한 비즈니스 로직 수행
 - #### Post.java : 카풀 게시물 정보 관리에 사용되는 DTO
 - #### PostSearchCond.java : 카풀 게시물 검색 조건과 관련된 DTO
 - #### PostUpdateDto.java : 카풀 게시물 정보 업데이트에 사용되는 DTO

