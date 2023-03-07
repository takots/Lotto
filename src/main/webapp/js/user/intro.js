console.clear();

const loginBtn = document.getElementById('login');
const signupBtn = document.getElementById('signup');

loginBtn.addEventListener('click', (e) => {
	let parent = e.target.parentNode.parentNode;
	Array.from(e.target.parentNode.parentNode.classList).find((element) => {
		if(element !== "slide-up") {
			parent.classList.add('slide-up')
		}else{
			signupBtn.parentNode.classList.add('slide-up')
			parent.classList.remove('slide-up')
		}
	});
});

signupBtn.addEventListener('click', (e) => {
	let parent = e.target.parentNode;
	Array.from(e.target.parentNode.classList).find((element) => {
		if(element !== "slide-up") {
			parent.classList.add('slide-up')
		}else{
			loginBtn.parentNode.parentNode.classList.add('slide-up')
			parent.classList.remove('slide-up')
		}
	});
});

function SignUpFunc(is){
	if($('#signup_name').val() == ''){
		Swal.fire("還是要有名字好嗎");
		return false;
	}
	
	if($('#signup_email').val() == ''){
		Swal.fire("email???");
		return false;
	}
	
	if($('#signup_pwd').val() == ''){
		Swal.fire("你沒輸入密碼");
		return false;
	}
	
	if($('#signup_pwd').val().length <= 7){
		Swal.fire("你的密碼至少要 8 個字");
		return false;
	}
	var keyword = "";
	if(is === 'yes'){
		keyword = "isAdmin";
	}
	var data = {
        action: "SignUp" ,
 		keyword: keyword ,
		name: $('#signup_name').val() ,
	    email: $('#signup_email').val() ,
	    pwd: $('#signup_pwd').val()
    }
    $.ajax({
        type: "post",
        url: "/Hello1221/intro",
        dataType: "json",
		data,
//        data: {"action": "SignUp" , "keyword": keyword , "name": $('#signup_name').val() ,
//			   "email": $('#signup_email').val() , "pwd": $('#signup_pwd').val()},
        success: function (response) { 
			if(response === true){
				Swal.fire({
				  icon: 'success',
				  title: 'GO!',
				  showConfirmButton: false,
				  timer: 1500
				}).then(() => {
					loginBtn.click();
					$('#login_email').val($('#signup_email').val());
					$('#login_pwd').val($('#signup_pwd').val());
				})
			}else if(response.format === false){
        		Swal.fire(response.errormsg);
       			return false;
        	}else if(response.emailexist === false){
        		Swal.fire(response.errormsg);
       			return false;
        	}else if(response.nameexist === true){
				Swal.fire({
				  title: response.errormsg,
				  showDenyButton: true,
				  showCancelButton: true,
				  confirmButtonText: '就要這個名字',
				  denyButtonText: '那我換別的',
				}).then((result) => {
				  /* Read more about isConfirmed, isDenied below */
				  if (result.isConfirmed) {
				    Swal.fire('恭喜你維持不變', '', 'success');
				  } else if (result.isDenied) {
				    Swal.fire('已經幫你清除了你的欄位，準備好就可以開始', '', 'info');
					$('#signup_name').val('');
				  }
				})
        	}
        },
        error:function (){
        }
    }); 
}

function LoginFunc(){
	if($('#login_email').val() == ''){
		Swal.fire("你需要輸入email");
		return false;
	}
	
	if($('#login_pwd').val() == ''){
		Swal.fire("有沒有可能你沒有密碼");
		return false;
	}
	
	if($('#login_pwd').val().length <= 7){
		Swal.fire("你的密碼至少要 8 個字");
		return false;
	}
	
    $.ajax({
        type: "post",
        url: "/Hello1221/intro",
        dataType: "json",
        data: {"action": "LogIn" , "email": $('#login_email').val() ,"pwd":$('#login_pwd').val()},
        success: function (response) { 
        	if(response === true){
        		Swal.fire("GO!")
        		.then(() => {
	        		document.location.href="/Hello1221/Lotto_Menu.jsp";
	        	});
        	}else{
        		Swal.fire("帳號密碼有錯誤，請重新填寫")
        		.then(() => {
//        			signupBtn.click();
	        	});
        	}
        },
        error:function (){
        }
    }); 
}