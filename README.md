# Trusticket

## 암표 방지를 위한 대규모 트래픽을 버틸 수 있는 티켓 예매 플랫폼
### 해커톤 개인 프로젝트
2024년 6월 24일 ~ 2024년 6월 28일까지 진행된 비대면+대면 대학생 해커톤에 참석하여 만든 작품입니다. <br>
평소 도전해보고 싶었던 마이크로서비스와 대기열 큐 구현을 목적으로 프로젝트 주제를 선정하였습니다.  

<br><br>

# 프로젝트 핵심 목적
- 짧은 기간 안에 작업 분배를 잘 하여 완성된 형태의 서비스 구현하기
- 티켓 예매 시 대규모 트래픽에 대한 처리를 분산시키기 위한 대기열 큐 구현
- 클라이언트의 요청을 분산하여 처리하기 위한 마이크로서비스 구조 사용
- 한국어 토큰 단위의 역색인을 통한 공연 이벤트 검색성능 향상
- 기능들을 최소한으로 보여줄 수 있는 프론트엔드 웹 구축
- <s>안면 인증과 예매 데이터 대조를 통한 현장발권 시스템 구축</s> 
    * (미완성)

<br><br>

# 사용 스택

<div>
<img src="https://img.shields.io/badge/Spring Cloud-6DB33F?style=flat-square&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=apache&logoColor=white"> 
<img src="https://img.shields.io/badge/MaridDB-003545?style=flat-square&logo=apachekafka&logoColor=white">
<img src="https://img.shields.io/badge/Apache Kafka-231F20?style=flat-square&logo=apachekafka&logoColor=white">
<img src="https://img.shields.io/badge/Elasticsearch-005571?style=flat-square&logo=elasticsearch&logoColor=white">
<img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white">
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/Angular-DD0031?style=flat-square&logo=angular&logoColor=white">
</div>

<br><br>

# 시스템 아키텍쳐
<img src="image/image.png" alt="alt text" width="70%" height="70%"/>
<br>

## 1. Naming server
- Spring cloud Netflix Eureka 서버를 네이밍 서버로 사용하여 마이크로서비스의 인스턴스를 등록하고 관리해주는 역할을 담당
- <a href="https://github.com/TRUSTICKET/TRUSTICKET_NAMING_SERVER">Repository</a>
## 2. API Gateway
- Spring API Gateway를 사용하여 엔드포인트를 통합시키고 여러개의 마이크로서비스에 라우팅을 수행
- 권한이 필요한 API에 대해서 Authorization 수행 (JWT)
- <a href="https://github.com/TRUSTICKET/TRUSTICKET_GATEWAY">Repository</a>
## 3. Microservices
## 3-1. Core Service
- 회원 관련 API 제공 (회원가입, 로그인)
- 사용자의 예매 요청을 대기열 큐에 넣음 (해당 기능을 회원 서비스와 분리하면 좋았을 것 같습니다)
- <a href="https://github.com/TRUSTICKET/TRUSTICKET-CORE">Repository</a>
## 3-2. Payment Service
- 결제 관련 처리 (결제이력)
- 연동된 IamPort API의 결제 이력 결과를 저장하여 사용자들의 결제 내역을 추적할 수 있도록 함
- 성공적으로 결제 처리 시 메시지 버스를 통해서 Booking Service의 예매 상태를 결제 확정 상태로 갱신함
-  <a href="https://github.com/TRUSTICKET/TRUSTICKET-PAYMENT">Repository</a>
## 3-3. Content Service
- 공연 이벤트 내역 조회를 담당하는 API
-  <a href="https://github.com/TRUSTICKET/TRUSTICKET-CONTENT">Repository</a>
## 3-4. Booking Service
- 예매 처리를 담당하는 서비스
- Core Service로부터 등록된 대기열 큐의 사용자를 순차적으로 처리
-  <a href="https://github.com/TRUSTICKET/TRUSTICKET-BOOKING">Repository</a>
## 3-5. Resources Service
- 이미지 파일에 대한 업로드 및 제공을 담당
- <a href="https://github.com/TRUSTICKET/TRUSTICKET-RESOURCES">Repository</a>
## 4. Frontend
- Angular를 사용하여 Single page Application 사용자 인터페이스 구현
-  <a href="https://github.com/TRUSTICKET/TRUSTICKET-FRONT">Repository</a>

<br><br>

# 주요 개발내용과 이슈

## 1. 이벤트 조회 및 검색
<img src="image/trusticket-img1.png" alt="alt text" width="70%" height="70%"/>

<br>
이벤트는 일반적으로 키워드를 통해 조회됩니다. 기존 SQL의 LIKE 연산을 통해 이벤트를 검색하는 것은 최악의 경우 Full-table scan을 하여 성능에 좋지 않을 것으로 예상하였습니다   
<br><br>
<h3>ElasticSearch</h3> 
1. 해당 문제를 극복하기 위해서 토큰 단위의 역색인을 사용하여 검색 엔진 성능의 향상을 꾀하였습니다. <br>  
2. 마이크로서비스로 분리된 Content Service는 이벤트 검색의 단일 기능에 충실하여 검색 성능의 향상에만 서버가 집중할 수 있도록 설계하였습니다. 
<br><br>
<h3>추가적으로 개발하고 싶은 사항</h3>
- Kibana를 사용하여 메인 검색 화면에 실시간으로 인기있는 이벤트들을 노출시키고 싶습니다.<br>
- 검색에도 캐싱을 도입하여 성능을 향상시킬 수 있을 것으로 보입니다.<br>
<br><br><br><br>

## 2. 예매 시도 및 대기열 큐
<img src="image/trusticket-video-ezgif.com-video-to-gif-converter.gif" alt="alt text" width="70%" height="70%"/>

<br>
보통 실시간 선착순 시스템의 경우 단기간에 사용자의 트래픽이 급증합니다. 이는 서버가 부하를 견디지 못하면 성능 하락 및 최악의 경우 서비스의 중단으로 이어질 수 있습니다.
<br><br>
<h3>마이크로서비스 아키텍쳐</h3> 
1. 하나의 서비스에 트래픽이 몰려도, 다른 기능들의 성능에는 영향이 가지 않도록 마이크로서비스 구조를 채택하였습니다. <br>
2. 특정 서비스가 이용이 불가능한 상태라도 다른 서비스를 이용 가능하며, Kafka를 통해 데이터 동기화가 일어나기 때문에 단일 지점 오류에 대한 회복성을 목표로 설계하였습니다. <br>
<h3>대기열 큐</h3> 
1. <b>Core Service</b> : 사용자가 예매 시도 요청을 받아 Kafka 대기열 큐에 진입시키는 역할을 합니다.  <br><br>
2. <b>Booking Service</b>  : Kafka 대기열 큐에 쌓여있는 메시지를 처리하고 예매 내역을 업데이트합니다. <br><br>
3. <b>동시성 문제</b> <br>
특정 제한 인원 이상으로 신청되지 않기 위해서 Lock을 사용하였습니다. <br>
맨 처음 시간안에 가장 간단하게 해결할 수 있는 방법이 DB단의 비관적 락이었는데, 단기간에 특정 이벤트에 대해서 집중될 때, 테이블에 대해 잠금을 걸어버리면 트래픽이 몰리지 않는 행사에 대해서도 Lock 획득을 대기해야 하므로 비효율적일 것이라고 생각했습니다.<br>
따라서 분산 Lock을 사용하여 특정 행사에 대해서 Lock을 사용하여 동시성을 제어하고, 동시에 다른 행사에는 영향이 미치지 않도록 하였습니다. 또한 서버 규모를 Scale-out 할 시에도 확장이 용이할 것입니다.<br><br><br>
4. <b>캐싱</b> <br>
일반적으로 예매 내역을 처리할 때 같은 이벤트의 예매 요청을 단기간 내에 처리할 확률이 높습니다. <br>
따라서 Redis를 사용해 캐싱 처리하여 처리 성능 향상을 생각하였습니다.  <br><br>
5. <b>대기열 상태 예매결과 확인</b> <br>
클라이언트 측에서 자신의 대기열 순서를 파악할 수 있어야 하고, 카프카의 메시지 처리가 종료되었을 때 결과를 받을 수 있어야 했습니다. <br>
따라서 클라이언트가 요청한 Kafka 메시지의 Offset과, 현재 큐에서 처리되어 Commit된 Offset을 비교하여 현재 순번을 파악할 수 있도록 아이디어를 얻었습니다.<br>
수신은 SSE(Server send event)를 활용하여 대기열에 진입한 프론트엔드 측에서 현재 Commit된 Offset에 대한 정보를 주기적으로 수신받도록 하였습니다. <br>
또한 예매 내역에 대한 성공, 실패에 대한 응답은 SSE 수신에 실패, 혹은 연결 이전에 완료될 때를 대비하여 Redis에 저장해두어 응답을 받을 수 있도록 하였습니다.<br>
<br>
6. <b>대기열 이탈</b> <br>
클라이언트 측에서 대기열에서 이탈하는 경우가 있을 수 있습니다. <br>
대기열 이탈 시 메시지를 서버에 전송하며 메시지에는 이탈 처리해야 하는 대기순번의 Offset이 포함됩니다.<br>
대기열 큐에서 순차적으로 처리할 때 해당 Offset의 차례라면 무시하고 진행하도록 설정하여 이탈 처리를 하였습니다.

<br><br><br><br>

## 3. 결제 처리 및 예매 확정
<img src="image/trusticket-img2.png" alt="alt text" width="45%" height="40%" style="display:inline"/>
<img src="image/trusticket-img3.png" alt="alt text" width="45%" height="40%" style="display:inline"/>


<br>
<h3>결제 프로세스</h3> 
1. 결제 대행 플랫폼인 IamPort API를 사용하여 여러 PG사들 중 KG 이니시스와 연동하여 다양한 카드사들과의 거래를 할 수 있도록 하였습니다. 단 실거래는 사업자 등록증이 있어야 하기 때문에 시도하지 못했습니다. <br>
2. 금융 거래와 관련된 민감한 사안들에 관련해서는 서비스 제공자 측에서 이력을 추적할 수 있도록 거래 내역의 데이터를 축적하였습니다. <br>
3. 거래가 성공적으로 완료될 시 카프카를 통해 데이터가 동기화되어 티켓 발권 상태가 결제 대기 상태에서 예매 확정 상태로 변경됩니다. <br>
4. 거래가 24시 자정까지 이루어지지 않는다면 당일에 예매한 티켓은 취소처리가 되고, 잔여석이 복귀되도록 하였습니다. <br>
