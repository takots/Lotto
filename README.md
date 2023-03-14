# user

<table>
    <tr>
        <th></th>
        <th>view</th>
        <th>js</th>
        <th>controller</th>
	    <th>model</th>
    </tr>
    <tr>
        <td>sweetalert2
        <br>bookstrap5</td>
        <td>index.jsp
        <br>indexadmin.jsp</td>
        <td>intro.js</td>
        <td>UserServlet.java</td>
        <td>UserDAO.java
        <br>UserDAOImpl.java
        <br>UserService.java
        <br>UserServiceImpl.java
        <br>UserVO.java</td>
    </tr>
</table>

## login:  
- js: LoginFunc() 
- controller: action: "LogIn" 
- service
    - UserVO loginnn(JSONObject jsonobject)
- dao
    - UserVO login(JSONObject jsonobject)

## signup: 
- js: SignUpFunc(is) 
- controller: action: "SignUp" 
- service
    - boolean signuppp(UserVO vo)
    - boolean nameExist(String username)
    - boolean emailExist(String email)
    - boolean emailFormat(String email)
- dao
    - boolean signUp(UserVO vo)
    - boolean findByUsername(String username)
    - boolean findByUseremail(String email)

<hr>

# lotto

<table>
    <tr>
        <th></th>
        <th>view</th>
        <th>js</th>
        <th>controller</th>
	    <th>model</th>
    </tr>
    <tr>
        <td>sweetalert2
        <br>bookstrap5</td>
        <td>Lotto_Menu.jsp
        <br>Lotto_List.jsp
        <br>Lotto_Edit.jsp
        <br>Lotto_DescribeList.jsp</td>
        <td>lottofunc.js
        <br>lottofunc2.js</td>
        <td>LottoServlet.java</td>
        <td>LottoDAO.java
        <br>LottoDAOImpl.java
        <br>LottoService.java
        <br>LottoServiceImpl.java
        <br>DescribeVO.java
        <br>LottoVO.java</td>
    </tr>
</table>

## today:
- js: CreateToDayFunc()
- controller: action: "Today"
- service
    - String randomSixNumber()
    - String generateTodayLotto()
    - String generateTodaySpecial()
    - void goLotto()
    - void countNumbers()
- dao 
    - String createTodayLotto(String str)
    - void createTodaySpecial(String str)

## buy:
- js: BuyFunc() 
- controller: action: "Buy"
- service
    - JSONObject isSixNumber(String str)
    - boolean buyLotto(Integer OrgId, String str)
- dao 
    - boolean insertUserBuyLotto(Integer OrgId, String str)

## ReplenishNum:
- js: ReplenishNumFunc()
- controller: action: "UserRandomBall"
- service
    - String replenishWithRandom(String str)

## FastBuy:
- js: FastBuyFunc()
- controller: action: "fastRandomBuy"
- service
    - List<String> fastRandomBuy(Integer OrgId, Integer x)
- dao 
    - void batchRandomBuy(Integer OrgId, List list)
    
## Dream:
- js: DreamComeNumFunc()
- controller: action: "DreamToNumber"
- service
    - String dreamNumber(Integer OrgId, String str)
- dao 
    - String insertDream(Integer OrgId, String str)

## GoogleTrans:
- js: GoogleTransFunc()
- controller: action: "EnTransToNum"
- service
    - String googleTranslate(String str) throws Exception
    - JSONObject englishToNumber(Integer type, String str, Integer OrgId)
- dao 
    - void saveEnglishAndNumber(DescribeVO vo)

## Remove:
- js: RemoveFunc()
- controller: action: "Remove"
- service
    - JSONObject removeNumber(String str1, String str2)
