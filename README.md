# view sweetalert2 + bookstrap5

## index
使用者登入、註冊頁面
intro.js

## indexadmin
管理者登入、註冊頁面
intro.js

## Lotto_Menu 
lottofunc.js
### 管理者 
- 產生號碼
- service.SELECTODAY() > 查詢今日號碼
### 使用者
- Lotto_List > OpenListFunc (iframe)
- Lotto_Edit > OpenEditFunc2 (連結當前頁面)
```javascript
$(document).ready(function() {
    OpenListFunc('/Hello1221/Lotto_List.jsp');
});
```
### Lotto_List
顯示今日號碼列表
- service.selectBetWhichDay(today ,OrgId) > 查詢使用者購買今日號碼
- todo: 無法顯示本日兌獎後的號碼

### Lotto_Edit
選號相關功能
- lottofunc2.js
- 首頁  > 連結
- func1 > ReplenishNumFunc  > 補齊號碼
- func2 > FastBuyFunc       > 速選N組號碼
- func3 > DreamComeNumFunc  > 將描述找出相對應名詞比對資料庫數據
- func4 > OpenEditFunc3     > (iframe) 描述列表顯示在左側
- func5 > GoogleTransFunc   > google中翻英 > fun6
- func6 > TransNumFunc      > 將英文轉換成數字 ,翻譯完的英文會對應 A:1 B:2 ... Z:26 
                                todo: 改善只有到26的問題
- func7 > BuyFunc           > 購買一組號碼
  
### Lotto_DreamList
描述列表
- service.selectAllDescribe(OrgId) > 查詢使用者所有描述

## js
### intro.js
url: "/Hello1221/intro"
- SignUpFunc(is)
  - is > isAdmin  
  - step1: verify data (空值、長度)
  - step2: keyword exist? > isAdmin
  - step3: to servletname: intro
  - step4: 成功的話將 email ,password 帶入login email ,password 欄位
  - step5: 其他可能:
    - email 驗證錯誤
    - email 已存在
    - name 已存在
      - 維持不變
      - 調整名稱    
```ajax
var data = {
  action: "SignUp" ,
  keyword: keyword ,
  name: $('#signup_name').val() ,
  email: $('#signup_email').val() ,
  pwd: $('#signup_pwd').val()
}
```
- LoginFunc()
  - step1: verify data (空值、長度)
  - step2: 轉址 Lotto_Menu 
```ajax
var data = {
  action: "LogIn" ,
  email: $('#signup_email').val() ,
  pwd: $('#signup_pwd').val()
}
```
### lottofunc.js
url: "/Hello1221/lotto"
- CreateToDayFunc() > 產生一組號碼
  - step1: 接收兩個值(一組號碼 ,特別號)
  - step2: ","切割後for迴圈 ,亂數產生的號碼將依序會被放入div ,Swal
  - step3: 重整頁面
```ajax
var data = {
  action: "Today"
}
```
### lottofunc2.js
url: "/Hello1221/lotto"
- ReplenishNumFunc() > 補齊6個號碼(針對自選號)
  - todo: 現在有其他功能也會產生號碼 ,所以不能只針對自選號 ,而是所有功能產生的號碼 
  - step1: 先把已選號碼改成 checked=false
  - step2: 取得 (6-自選號數) 
    - 顯示1: ","切割後for迴圈 ,亂數產生的號碼將依序會被放入div
    - 顯示2: 下面按鈕 checked=true
  - step3~5: 同 step2 
    - 自選號 
    - 解夢描述產生的號碼 
    - 英轉數的號碼 
```ajax
var data = {
  action: "UserRandomBall"
}
```
- DreamComeNumFunc() > 依照使用者輸入的夢的描述找出相對應名詞比對資料庫數據
  - step1: verify data (空值)
  - step2: 
    - 顯示1: ","切割後for迴圈 ,相對應名詞比對資料庫數據產生的號碼將依序會被放入div
    - 顯示2: 下面按鈕 checked=true
```ajax
var data = {
  action: "CallTheGodOfWealth",
  describeStr: $("#v-pills-letDream-tab").val()
}
```
- GoogleTransFunc() > google 中翻英
  - step1: verify data (空值)
  - step2: 
    - 顯示1: ","切割後for迴圈 ,依照 A:1 to Z:26 的規則將數字被放入div
    - 顯示2: 下面按鈕 checked=true
```ajax
var data = {
   action: "EnTransToNum",
   func: "GOOGLE",
   wordtotransNum: $("#v-pills-googleTranslate-tab").val() 
 }
```
- TransNumFunc() > 英轉數
  - step1: verify data (空值)
  - step2: 
    - 顯示1: ","切割後for迴圈 ,依照 A:1 to Z:26 的規則將數字被放入div
    - 顯示2: 下面按鈕 checked=true
```ajax
var data = {
   action: "ENNUM",
   func: "GOOGLE",
   wordtotransNum: $("#v-pills-englishToNumber-tab").val() 
 }
```
- chooseBallFunc(ball) > 自選一個號碼
  - step1: 如果沒有 checked 下次點擊則 true ; 如果已經 checked 下次點擊則 false
  - step2: 
    - 顯示1: ","切割後for迴圈 ,將數字被放入div
    - 顯示2: 下面按鈕 checked=true
  - step3: 取消號碼 
```ajax
var data = {
   action: "ChooseBall",
   ball: ball,
   calculate: KEY
 }
```
- BuyFunc() > 購買一組號碼
  - step1: 抓checked 
  - step2: verify data (總數)
  - step3: 回首頁 (Lotto_List 列表)
```ajax
var data = {
   action: "Buy",
   value: cbxVehicle
 }
```
- FastBuyFunc() > 速選N組號碼
  - step1: verify data (數量>0)
  - step2: list to arr
  - step3: 回首頁 (Lotto_List 列表)
```ajax
var data = {
   action: "fastRandomBuy",
   value: $('#v-pills-fastbuy-tab').val()
 }
```
