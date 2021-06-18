# 블랙잭 미션 구현
레벨 2 방학기간 동안 레벨 1,2 에서 배운 내용으로 블랙잭을 재구현한다.

# 목표
## 도전~ 클린코드
- TDD 로 구현한다. (단위테스트와 인수테스트 필수)
- 객체지향으로 구현한다.
- 가장 읽기 좋은 코드로 구현한다.
- 있을 법한 곳에 간결한 코드로 로직을 구현한다.
- 뷰를 고려하지 않고 테스트 코드로만 도메인을 작성한 뒤, 스프링 부트를 사용하여 DB, Controller 에 매핑한다.

## 배포도 한다.
- json 반환으로 RESTful API 를 작성한다.
- 배포과정을 학습하며 공부한다.

# 구현목록

## 1차 도메인 
### 시스템 책임 파악
- 이름과 베팅금액을 넣어 플레이어를 생성한다.
- 딜러와 플레이어는 처음 게임 시작시 2개의 카드를 받는다.
- Ace 는 1 또는 11으로 계산가능하며, K,Q,J 은 10으로 계산
    - 최종계산시에서만 21이 넘지 않는 경우에 한하여 11로 계산되며 기본적으로 1로 계산됨
- 플레이어는 21이 넘지 않으면 카드를 계속 뽑을 수 있다.
- 딜러는 16이하이면 1장을 더 받고, 16 초과이면 받지 않는다.
- 처음 받은 2장의 카드의 합이 21이면 블랙잭이다.
- 플레이어는 딜러와 게임을 하며 21을 넘지 않으면서 높은 숫자인 경우 이긴다.
- 비긴 경우이거나 딜러가 Bust인 경우, 베팅금액의 1.0배를 돌려받는다.
- 블랙잭이거나 이길 경우, 베팅금액의 1.5배를 돌려받는다.
- 지는 경우, 베팅금액을 잃는다.

### 시스템 책임을 더 작은 책임으로 분할
- [x] Participant 라는 interface로 Player와 Dealer를 초기화
- [x] Card 객체로 카드를 생성
- [x] 52장의 Card는 셔플되어 관리되며 중복되지 않는다.
- [x] Participant는 카드를 받을 수 있다.
- [x] Blackjack(처음 2장 카드합계 21), Hit(21이하이며 더 받을 수 있는 상태), Stay(21이하이나 받지 않음), Bust(21 초과) 상태를 가진다.
### 분할된 책임을 수행할 수 있는 적절한 객체 또는 역할을 찾아 책임을 할당 
- [x] Card의 Suit, Denomination 을 분리하여 생성
- [x] Denomination이 점수 정보를 가짐
- [X] 카드는 Deck 객체가 52장을 관리한다.
- [x] 카드 52장은 캐싱하여 사용한다.
- [x] String로 캐싱된 Card를 조회가능하다.
- [x] Suit, Denomination에 id를 부여하여 조합하여 사용한다.
- [x] Deck 설정은 셔플된 카드 혹은 임의의 카드로 가능하다.
  - [x] 1장씩 뽑을 때마다 Deck의 카드의 갯수는 줄어든다.
  - [x] 2장씩 뽑기 가능
  - [x] 소진된 상태에서 추가로 뽑으면 예외를 발생한다.
- Participant 카드 받기
  - [x] 초기 2장 받기
  - [x] 1장 받기
  - [x] 받을 수 있는 조건 확인(플레이어는 21이하, 딜러는 16이하)

### 객체가 책임을 수행하는 도중 다른 객체의 도움이 필요한 경우, 이를 책임질 적절한 객체 또는 역할을 찾음
- [x] 예외는 객체로 만들어 관리한다.
- [x] 예외는 계층 구조를 가진다. 
    - 발생하지 않을 예외(UnreachableCustomException)
    - 발생할 수 있는 예외(CustomE xception)
- [x] participant가 가진 카드를 관리하는 일급컬렉션 Cards 객체를 생성
    - [x] Ace 는 1 또는 11 로 계산

### 해당 객체 또는 역할에게 책임을 할당함으로써 두 객체가 협력
- 상태를 관리하는 상태객체를 만든다.
  - [x] 각 blackjack.domain.participant 는 상태를 가지고 있는다.
  - [x] 상태는 새로운 상태를 반환해준다.
  - [x] 카드관리에 따라 상태가 달라지므로 카드를 상태객체가 관리한다.
  - [x] 진행상태(Hit, InitTurn), 게임끝난상태(블랙잭, Stay, Bust)로 나뉜다.
  - [x] isBlackjack
  - [x] 카드 받기
  - [x] 점수 계산
  
- 결과를 판정하는 객체를 만든다. (PlayerJudge)
  - [x] 딜러와 플레이어를 받아 결과를 알려준다.
  - [x] Enum으로 WIN(1.5), DRAW(1.0), LOSE(0.0) 상태를 관리한다.
  
|구분|P-Stay|P-Blackjack|P-Bust|
|---|---|---|---|
|D-Stay     |점수높은자|P|D|
|D-Blackjack|D|비김|D|
|D-Bust     |비김|비김|D|

- 비용관련 처리를 하는 객체를 만든다. (BettingCalculator)
  - [x] dealer는 플레이어들간의 오고간 최종 금액을 계산한다.
  - [x] 결과를 알려준다.
- 최종결과를 관리하는 객체를 만든다. (ResultBoard)
  - [x] 딜러, 플레이어별로 결과를 관리한다.
  
- [x] Stay 상태는 Blackjack, Bust가 아닌 상태의 게임이 종료하는 로직으며, Stay 결정 로직 구현한다.

## 2차 스프링 부트를 사용하여 웹, DB 연결
1. 방을 만든다. (딜러를 생성) post
  1-1. 플레이어의 이름과 베팅 금액을 리스트로 보낸다. 
2. 처음 셋팅 상황을 보여준다. get
3. 플레이어별로 추가 사항을 물어본다. get, post
4. 최종 결과를 출력한다.

### DB 설계
- [x] BlackjackGame 객체 구현
- [x] 객체 필드 단위로 DB 테이블 설계 
### RestAPI 설계

- [x] 게임 생성
- post `/api/blackjack`
```text
<request>
body: 
{
    "playerRequests": [
        {
            "name": "안녕",
            "bettingMoney": 1000
        },
        {
            "name": "바이",
            "bettingMoney": 3000
        }
    ]
}

<response>

Location: /api/blackjack/{gameId}
StatusCode: 201(CREATED)

body:
{   
    "gameId" : 1L,
    "dealer" :
        {
          "participantId" : 1L,
          "name" : "딜러",
          "bettingMoney" : 0,
          "cards" : [
              {
                  "suit" : "d",
                  "denomination" : "6"
              }
          ],
          "state" : "hit"
        },    
    "participants":
        [
            {
                "participantId" : 2L,
                "name" : "안녕",
                "bettingMoney" : 1000,
                "cards" : [
                    {
                        "suit" : "c",
                        "denomination" : "2"
                    },
                    {
                        "suit" : "d",
                        "denomination" : "3"
                    }
                ],
                "state" : "hit"
            },
            {
                "participantId" : 3L,
                "name" : "바이",
                "bettingMoney" : 3000
                "cards" : [
                    {
                        "suit" : "s",
                        "denomination" : "2"
                    },
                    {
                        "suit" : "d",
                        "denomination" : "6"
                    }
                ],
                "state" : "hit"
            }
            
        ]
}
```
- [x] 전체 상태조회
- get `/api/blackjack/{gameId}`
```text
body:
body:
{   
    "gameId" : 1L,
    "dealer" :
        {
          "participantId" : 1L,
          "name" : "딜러",
          "bettingMoney" : 0,
          "cards" : [
              {
                  "suit" : "d",
                  "denomination" : "6"
              }
          ],
          "state" : "hit"
        },    
    "participants":
        [
            {
                "participantId" : 2L,
                "name" : "안녕",
                "bettingMoney" : 1000,
                "cards" : [
                    {
                        "suit" : "c",
                        "denomination" : "2"
                    },
                    {
                        "suit" : "d",
                        "denomination" : "3"
                    }
                ],
                "state" : "hit"
            },
            {
                "participantId" : 3L,
                "name" : "바이",
                "bettingMoney" : 3000
                "cards" : [
                    {
                        "suit" : "s",
                        "denomination" : "2"
                    },
                    {
                        "suit" : "d",
                        "denomination" : "6"
                    }
                ],
                "state" : "hit"
            }
            
        ]
}
```
- [x] 전체 플레이어 상태 조회
- get `/api/blackjack/{gameId}/players`
```text
<response>
body: 
{
    "participants":
    [
        {
            "participantId" : 2L,
            "name" : "안녕",
            "bettingMoney" : 1000,
            "cards" : [
                {
                    "suit" : "c",
                    "denomination" : "2"
                },
                {
                    "suit" : "d",
                    "denomination" : "3"
                }
            ],
            "state" : "hit"
        },
        {
            "participantId" : 3L,
            "name" : "바이",
            "bettingMoney" : 3000,
            "cards" : [
                {
                    "suit" : "s",
                    "denomination" : "2"
                },
                {
                    "suit" : "d",
                    "denomination" : "6"
                }
            ],
            "state" : "hit"
        }
    ]
}
```
- [x] 딜러 상태조회
- get `/api/blackjack/{gameId}/dealer`
```text
body:
{
        "participantId" : 1L,
        "name" : "딜러",
        "cards" : [
            {
                "suit" : "d",
                "denomination" : "6"
            }
        ],
        "state" : "hit"
    }
```
- [x] 개별 플레이어 상태 조회
- get `/api/blackjack/{gameId}/players/{participantId}`
```text
<response>
body: 
{
    "participantId" : 2L,
    "name" : "안녕",
    "bettingMoney" : 1000
    "cards" : [
        {
            "suit" : "c",
            "denomination" : "2"
        },
        {
            "suit" : "d",
            "denomination" : "3"
        }
    ],
    "state" : "hit"
}
```
- [x] 카드 받기 가능 여부 확인
- get `/api/blackjack/{gameId}/players/{participantId}/availability` 
```text
<response>
status: ok
body:
{
    "isAbleToTake" : true
}


```
- [x] 플레이어 카드 받기
- post `/api/blackjack/{gameId}/players/{participantId}`
```text
<request>
body:
{
    "isTaking" : true
}

<response>
status: ok
```
- [x] 딜러 카드 받기 가능 여부 확인
- get `/api/blackjack/{gameId}/dealer/availability`
```text
<response>
status: ok
body:
{
    "isAbleToTake" : true
}
```

- [x] 딜러 카드 받기
- post `/api/blackjack/{gameId}/dealer`
```text
<response>
status: ok
```

- [x] 전체 결과 조회
- get `/api/blackjack/{gameId}/result`
```text
<response>
body:
[
    {
        "id" : 1L,
        "name" : "딜러",
        "result" : {
            "win" : 0,
            "draw" : 1,
            "lose" : 1
        },
        "cards": 
            [
                {
                    "suit": "c",
                    "denomination": "2"
                },
                {
                    "suit": "c",
                    "denomination": "2"
                },
                {
                    "suit": "c",
                    "denomination": "9"
                }
            ],
        "money" : -1500
    },
    {
        "id" : 2L,
        "name" : "안녕",
        "result" : {
            "win" : 0,
            "draw" : 1,
            "lose" : 0
        },
        "cards": 
            [
                {
                    "suit": "c",
                    "denomination": "2"
                },
                {
                    "suit": "c",
                    "denomination": "2"
                },
                {
                    "suit": "c",
                    "denomination": "9"
                }
            ],
        "money" : 1000
    },
    {
        "id" : 3L,
        "name" : "바이",
        "result" : {
            "win" : 1,
            "draw" : 0,
            "lose" : 0
        },
        "cards": 
            [
                {
                    "suit": "c",
                    "denomination": "2"
                },
                {
                    "suit": "c",
                    "denomination": "2"
                },
                {
                    "suit": "c",
                    "denomination": "9"
                }
            ],
        "money" : 4500
    },
]
```

# 예외처리
- [x] 참가자 입력(request)
  - "딜러" 이름 사용 불가
  - 이름은 공백일 수 없음
  - 초기 베팅금액은 1000원 이상
  
- [ ] url variable(request)
  - gameId는 존재해야 함
  - playerId는 존재해야 함
  
- [x] 게임진행(domain logic)
  - 모두 게임 종료상태가 아니라면 결과 조회 불가
  - 게임 종료상태에서는 카드를 받기가 불가능
  - deck이 빈 상태에서는 카드 뽑기가 불가능
  - 유효하지 않은 카드는 조회가 불가
  