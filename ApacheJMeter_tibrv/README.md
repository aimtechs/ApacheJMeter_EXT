
# TIBRV Tester on [Apache JMeter](http://jmeter.apache.org/)

## 들어가며

메시지 기반 어플리케이션 풀테스트시 혹은 데모 준비시, 메시지 생성 도구가 필요합니다. 단순 메시지 생성 부터 시나리오 기반 메시지 생성까지 각양각색 입니다.

TIBRV 기반 어플리케이션일 경우 TibOn을 이용할 수 있습니다. WEB 기반 어플리케이션은 [POSTMAN](https://www.getpostman.com/ ) or [Selenium](http://www.seleniumhq.org/)과 같은 도구를 이용하겠지요?

이 밖에 용도는 조금 다를 수 있겠지만, [Apache JMeter](http://jmeter.apache.org/) 같은 도구를 이용할 수 있습니다. 아래는 JMeter란 무엇인가를 설명해 놓은 글입니다.

> Apache JMeter ™ 애플리케이션은 테스트 기능 동작을 로드하고 성능을 측정하도록 설계된 100 % 순수 Java 애플리케이션 오픈 소스 소프트웨어입니다. 원래 웹 응용 프로그램을 테스트하기 위해 설계되었지만 다른 테스트 기능으로 확장되었습니다.

이 프로젝트는 JMeter 확장 Tibrv 메시징 sampler 프로젝트입니다.

## 설치가이드

**(필수) java 8 이상**

- [Apache JMeter DownLoad](http://jmeter.apache.org/download_jmeter.cgi)
- [ApacheJMeter_tibrv-X.X.X.jar Download](https://github.com/aimtechs/ApacheJMeter_EXT/tree/master/ApacheJMeter_tibrv/deploy) (설치된 TIBRV 버전에 맞는 파일을 다운로드)
- `JMETER_HOME/lib/ext`에 `ApacheJMeter_tibrv-X.X.X.jar` 복사 

## 실행 파일

- WINDOWS : `JMETER_HOME/bin/jmeterw.cmd`
- OSX, LINUX : `JMETER_HOME/bin/jmeter.sh`

## JMeter 샘플 이용 학습 테스트

`JMETER_HOME/bin` 하위 디렉토리(examples, report-template, templates)에 있는 jmx 확장자 파일을 로드하여 학습 테스트 수행

## ApacheJMeter_tibrv 샘플 테스트 가이드

- [test.csv download](https://github.com/aimtechs/ApacheJMeter_EXT/blob/master/ApacheJMeter_tibrv/src/test/resources/test.csv)
- [Tib_TEST.jmx download](https://github.com/aimtechs/ApacheJMeter_EXT/blob/master/ApacheJMeter_tibrv/src/test/resources/Tib_TEST.jmx)
- JMeter에서 다운로드 받은 `Tib_TEST.jmx` 파일 로드
- `CSV Data Set Config` **fileName** 파일(다운로드 받은 `test.csv` 파일) 위치 변경
- 테스트 확인을 위한 `tibrvlisten` 실행
    ```bash
    tibrvlisten -service 5420 -daemon tcp:7500 a.b.c

    tibrvlisten -service 5420 -daemon tcp:7500 a.b.c.d.e.f.g
    ```
- Jmeter 테스트 실행


## ApacheJMeter_tibrv 개발 가이드

- JMeter Main class : `org.apache.jmeter.NewDriver`
- 실행 VM ARGS : -Djmeter.home=${JMETER_SRC_HOME_PATH}
- tibrv install (maven 의존성 해결)

    ```bash
    mvn install:install-file -Dfile=D:\e-4.6-neon-jmeter-ws\ApacheJMeter_EXT\ApacheJMeter_tibrv\ext\tibrvj-8.4.0.jar -DgroupId=com.tibco -DartifactId=tibrvj -Dversion=8.4.0 -Dpackaging=jar -DgeneratePom=true

    mvn install:install-file -Dfile=D:\e-4.6-neon-jmeter-ws\ApacheJMeter_EXT\ApacheJMeter_tibrv\ext\tibrvj-8.3.0.jar -DgroupId=com.tibco -DartifactId=tibrvj -Dversion=8.3.0 -Dpackaging=jar -DgeneratePom=true
    ```
