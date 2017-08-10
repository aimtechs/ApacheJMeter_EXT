
# [Apache JMeter](http://jmeter.apache.org/)

## 들어가며

메시지 기반 어플리케이션을 풀테스트 하거나, 데모를 하기 위해 메시지를 생성해 주는 도구가 필요합니다. 단순 메시지 발생부터 시나리오 기반 메시지 생성까지 각양각색 입니다.

TIBRV 기반 어플리케이션일 경우 TibON(R&D팀 나재무 수석 작)을 이용할 수 있습니다. WEB 기반 어플리케이션은 [POSTMAN](https://www.getpostman.com/ ) or [Selenium](http://www.seleniumhq.org/)과 같은 도구를 이용하겠지요?

이 밖에 용도는 조금 다를 수 있겠지만, [Apache JMeter](http://jmeter.apache.org/) 같은 도구를 이용할 수 있습니다. 아래는 JMeter란 무엇인가를 설명해 놓은 글입니다.

> Apache JMeter ™ 애플리케이션은 테스트 기능 동작을로드하고 성능을 측정하도록 설계된 100 % 순수 Java 애플리케이션 인 오픈 소스 소프트웨어입니다. 원래 웹 응용 프로그램을 테스트하기 위해 설계되었지만 다른 테스트 기능으로 확장되었습니다.

결론부터 말씀드리자면, JMeter 기반 Tibrv 메시지 테스트가 가능하도록 기능을 만들었습니다.

## 설치가이드

java 8 버젼 이상

- 1. [DownLoad](http://jmeter.apache.org/download_jmeter.cgi)
- 2. ApacheJMeter_tibrv-X.X.X.jar 파일을 `JMETER_HOME/lib/ext`에 복사
- 2. `JMETER_HOME/bin/jmeter.bat` 실행
- 3. `JMETER_HOME/bin` 하위 디렉토리(examples, report-template, templates) 
     에 있는 jmx 확장자 파일을 로드하여 테스트 해 본다.



## ApacheJMeter_tibrv 테스트 

첨부의 `Tib TEST.jmx` 파일을 로드한다.

시작 버튼을 클릭 한다.

### 수정 항목

`CSV Data Set Config` 의 fileName 파일 위치를 변경한다.

### tibrvlisten

```bash
tibrvlisten -service 5420 -network 192.168.101.50 -daemon tcp:7500 a.b.c
tibrvlisten -service 5420 -network 192.168.101.50 -daemon tcp:7500 a.b.c.d.e.f.g
```


## 개발가이드

```bash

mvn install:install-file -Dfile=D:\e-4.6-neon-jmeter-ws\ApacheJMeter_tibrv\ext\tibrvj-8.4.0.jar -DgroupId=com.tibco -DartifactId=tibrvj -Dversion=8.4.0 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=D:\e-4.6-neon-jmeter-ws\ApacheJMeter_tibrv\ext\tibrvj-8.3.0.jar -DgroupId=com.tibco -DartifactId=tibrvj -Dversion=8.3.0 -Dpackaging=jar -DgeneratePom=true
```

```bash
tibrvlisten -service 5420 -network 192.168.101.50 -daemon tcp:7500 a.b.c
```