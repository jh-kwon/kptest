##
# 환경 정보
1. Spring Boot+ Java 8 기반으로 작성
2. 저장소는 MongoDB 사용
    : 단, 테스트 목적이므로 사용이 간단한 in-memory DB(Embedded MongoDB)를 사용
    1) 이에 따라 어플리케이션 프로세스를 종료할 때 데이터도 모두 사라지게 된다
     : 즉, 재기동 할때마다 데이터 초기화 -> 유저 시퀀스 시작값은 초기화 과정에 따라 임의로 조절 가능
    2) 프로세스 기동 시 DB도 함께 기동하기 때문에 속도가 조금 느림
3. 클라이언트 역할의 front end는 thymeleaf 프레임워크 기반에 jquery + bootstrap으로 작성

##
# 특이 사항
1. 발급 ID(seq)는 증가하는 일련의 숫자이지만 중간에 건너뛰어질 수 있음

##
# 프로세스 실행 방법
1. 이 프로그램은 일반적인 Java 프로그램과 같이 main()함수 호출만으로 실행(배포도 executable JAR형태로 이루어짐)되므로
Java가 설치되어 있다면 그 외에 특별한 환경 설정이 필요 없음

ex> java -jar kptest-0.0.1-SNAPSHOT.jar
위의 명령어 만으로 실행 가능

to be contined...