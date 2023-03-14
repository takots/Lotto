03/14 todois:
1. 增加訪客功能
2. 合併 今日號碼 ,特別號 
3. 補齊號碼 先判斷目前數字 是否有 6 在ajax /ok
4. A:1 to Z:26 缺點 1~49
5. filter 過濾 未登入使用者

03/10 todois:
1. 去除單雙數 ,去除n的倍數 ,質數 /ok
2. 當我得到不同種類的號碼字串時 ,我可能有 字串1: ,1,3,15,20, 字串2: 2,26,48 字串3: ,13,15,29
這時候我在顯示過程是只需要顯示全部所以就不需要再各別處理又各別將值放回 ,可以串起來排序後再一併處理

03/06 todois:
1. Access to XMLHttpRequest at 'http://localhost:8080/Hello1221/lotto' from origin 'null' has been blocked by CORS policy: Response to preflight request doesn't pass access control check: No 'Access-Control-Allow-Origin' header is present on the requested resource.
解決 CORS 問題
2. Read why.md

03/04 todois:
1. 使用新版sweetalert2 把所有 swal 改成 Swal /ok

03/01 todois:
1. 把所有lottery ,user controller 分別統整到各一支 /ok

02/24 todois:
1. https://lotteryno.one/
2. 產生隨機的描述範例在 #letsDream 的 placeholder 
3. 解夢數字 可能發生一個名詞都沒有對到的情況 ,需要返回訊息告知使用者
4. <fieldset><legend>xxx</legend><div id="xxx"></div></fieldset> 功能點選之前不顯示

02/21 todois:
1. 單元測試 /ok
2. 列表如果開完獎 ,同一天所選的號碼就不能顯示在列表上

4. 選 3、13、23、33 然後移除掉3 ,四個數字都會同時被移除 /ok ,n,
   - ball, 會導致尾數被取代 要改成 ,ball, 才是正確取代 
5. 全域變數在 v2.jsp 重置為空值 ,但目前在v2.jsp取不到ran的值 /應該是ok ,待測試 /ok
6. 增加不同方式選號 
   - 英文轉數字 /算ok 缺驗證
   - 傳統夢境解析號碼對照表
   		- data 存成 json 格式 /ok

02/20 todois:
1. 可以選多組號碼 ,批次送出 /ok

02/17 todois: 
1. 中幾個號碼分別存到 lo_main 的L_getOne ,L_getTwo ,...以此類推 /ok
2. today 增加特別號 01-08
   - 今日號碼顯示 /ok
   - 兌獎也要對特別號 /ok 
3. 產生今天樂透的同時去將今天使用者購買的號碼全部兌獎 /ok
4. 購買跟兌獎應該要分開寫 /ok

