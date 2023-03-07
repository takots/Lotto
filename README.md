# view sweetalert2 + bookstrap5

## index
使用者登入、註冊頁面

## indexadmin
管理者登入、註冊頁面

## Lotto_Menu 
lottofunc.js
### 管理者 
- 產生號碼
- service.SELECTODAY() > 查詢今日號碼
### 使用者
- Lotto_List > OpenListFunc (iframe)
- Lotto_Edit > OpenEditFunc2 (連結當前頁面)

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
- func3 > DreamComeNumFunc  > 將描述找出相對應名詞比對資料庫數字
- func4 > OpenEditFunc3     > (iframe) 描述列表顯示在左側
- func5 > GoogleTransFunc   > google中翻英 > fun6
- func6 > TransNumFunc      > 將英文轉換成數字 ,翻譯完的英文會對應 A:1 B:2 ... Z:26 
                                todo: 改善只有到26的問題
- func7 > BuyFunc           > 購買一組號碼
  
### Lotto_DreamList
描述列表
