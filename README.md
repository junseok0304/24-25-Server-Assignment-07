## Description

<!-- 구현 및 작업 내용을 적어주세요 -->

- 구글 소셜 로그인 OAuth 및 자체 로그인 (jwt+refresh) 멤버 데이터베이스 통합
- 로그인한 사용자의 댓글 CRUD 기능
- 댓글 CRUD API는 인증된 사용자만 접근하고, 인가된 사용자만 접근할 수 있도록 제한

## 본인이 이해한 OAuth를 통한 로그인 흐름


<img width="1147" alt="스크린샷 2024-11-22 오전 12 18 42" src="https://github.com/user-attachments/assets/95bae11f-8521-4810-b418-daea313182a2">
<br></br>
1. 사용자가 애플리케이션에서 로그인 버튼을 클릭하면, 구글 소셜로그인 페이지로 리디렉션, Google 계정을 통해 로그인 <br></br>
2. 구글 OAuth 에서 애플리케이션으로 인증 코드를 전달, 애플리케이션은 인증 코드를 사용해 구글 OAuth 에게 액세스 토큰 요청 <br></br>
3. 액세스 토큰으로 구글 OAuth에서 사용자 정보를 가져옴<br></br>
4. 사용자 정보를 바탕으로 JWT 발급해서 활용, RefreshToken도 같이 발급하여 보안성 재고<br></br>

핵심사항 : 
애플리케이션 → 인증 서버 → 리소스 서버로 이어지는 요청 흐름
댓글 CRUD API는 인증된 사용자만 접근하고, 인가된 사용자만 접근할 수 있도록 제한
OAuth 인증 및 JWT 기반 권한 관리

⇒ 추후 스터디 주제로 
1. docker와 aws를 활용하여 구현한 코드를 배포하는 방법이 궁금합니다.
3. 깃허브 액션을 활용해 CI/CD를 도입하는 방법이 궁금합니다.
5. Find Usages 활용해서 사용되지 않는 코드 찾는 방법

## Important content

<!-- 주의 깊게 봐줬으면 하는 부분을 적어주세요 -->

- 애플리케이션 → 인증 서버 → 리소스 서버로 이어지는 요청 흐름이 정상적으로 구성되었는지

## Question

<!-- 궁금한 점을 적어주세요 -->

- 코드를 작성하다보니 dto에 많은 파일들이 몰려서 짜여있다고 느꼈는데 이 경우에 폴더(패키지)로 세분화해서 써주는게 유지보수나 가독성에 이점이 있는건지 궁금합니다.

## Reference

코어 벨로그 자료,
스프링부트3 백엔드 개발자 되기 서적
