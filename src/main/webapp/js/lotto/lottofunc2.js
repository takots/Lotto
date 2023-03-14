function ReplenishNumFunc() { // 補齊號碼

	var cbxVehicle = '';
	var i = 0;
	$('input:checkbox:checked[name="cbball"]').each(function(i) { cbxVehicle += this.value + ','; });
	if(cbxVehicle !== ''){
		cbxVehicle = cbxVehicle.substr(0, cbxVehicle.length - 1);
		cbxVehicle.split(",").forEach(function(cbx) {
  			i++;
		});
	} 
	if(i < 6){
		
		for (var i = 1; i < 50; i++) {
			var cbball = $("#cb_ball" + i).is(":checked");
			if (cbball == true) { // 如果沒有 checked 下次點擊則 true
				// 				console.log(i);
				$("#cb_ball" + i).prop('checked', false);
				$("#ball" + i).css({
					"background": "#6009f0",
					"color": "#fff"
				});
			}
		}
		$("#v-pills-replenish").html("");
		$.ajax({
			type: "post",
			url: "/Hello1221/lotto",
			dataType: "json",
			data: { "action": "UserRandomBall" ,"value": cbxVehicle},
			success: function(response) {
				var str = "";
				if(response !== null){
					var arr = response.split(",");
					for (var i = 0; i < arr.length; i++) {
						if (i > 0) {
							str += "<div class=\"newbtn-list\" style=\"display:inline-block;\">" + arr[i] + "</div>&nbsp;&nbsp;";
							$('#cb_ball' + arr[i]).prop('checked', true);
							$('#ball' + arr[i]).css({
								"background": "#feba28",
								"color": "black"
							})
						}
					}
				}
				if (str !== '') {
					str = "你產生的號碼： " + str;
					$("#v-pills-replenish").html(str);
				}
			},
			error: function() {
				alert("SetupError!");
			}
		});
	}else{
		Swal.fire("目前檯面上的號碼已經在 6 個以上 ,無法使用此功能");
	}
}

function DreamComeNumFunc() {
	if ($('#v-pills-letDream-tab').val() == '') {
		Swal.fire("請輸入關於夢的任何描述");
		return false;
	}
	$.ajax({
		type: "post",
		url: "/Hello1221/lotto",
		data: { "action": "DreamToNumber", "describeStr": $("#v-pills-letDream-tab").val() },
		dataType: "html",
		success: function(response) {
			var str = "";
			if (response !== '') {
				var arr = response.split(","); // ,1,2,3,4
				for (var i = 0; i < arr.length; i++) {
					if (i > 0) {
						str += "<div class=\"newbtn-list\" style=\"display:inline-block;\">" + arr[i] + "</div>&nbsp;&nbsp;";
						$('#cb_ball' + arr[i]).prop('checked', true);
						$('#ball' + arr[i]).css({
							"background": "#feba28",
							"color": "black"
						})
					}
				}
				if (str !== '') {
					str = "你產生的號碼： " + str;
					$("#v-pills-letDream").html(str);
				}
			} else {
				Swal.fire("no match number!! sorry");
			}
		},
		error: function() {
			alert("SetupError!");
		}
	});
}

function GoogleTransFunc() {
	if ($('#v-pills-googleTranslate-tab').val() == '') {
		Swal.fire("你需要先有字才能翻譯");
		return false;
	}
	$.ajax({
		type: "post",
		url: "/Hello1221/lotto",
		data: { "action": "EnTransToNum", "type": "GOOGLE", "wordtotransNum": $("#v-pills-googleTranslate-tab").val() },
		dataType: "json",
		success: function(response) {
			var arr = response.numberStr;
			var str = "";
			for (var i = 0; i < arr.length; i++) {
				if (i > 0) {
					str += "<div class=\"newbtn-list\" style=\"display:inline-block;\">" + arr[i] + "</div>&nbsp;&nbsp;";
					$('#cb_ball' + arr[i]).prop('checked', true);
					$('#ball' + arr[i]).css({
						"background": "#feba28",
						"color": "black"
					})
				}
			}
			var str2 = $("#v-pills-googleTranslate-tab").val() + ": " + response.content;
			if (str !== '') {
				str = "<br>你產生的號碼： " + str;
				$("#v-pills-googleTranslate").html(str2 + str);
			}else{ // 比對不到
				Swal.fire("你需要正確地輸入才能翻譯");
				$("#v-pills-googleTranslate").html(str2);
			}
		},
		error: function() {
			alert("SetupError!");
		}
	});
}

function TransNumFunc() {
	if ($('#v-pills-englishToNumber-tab').val() == '') {
		Swal.fire("你需要先有字才能轉換");
		return false;
	}
	$.ajax({
		type: "post",
		url: "/Hello1221/lotto",
		data: { "action": "EnTransToNum", "type": "ENNUM", "wordtotransNum": $("#v-pills-englishToNumber-tab").val() },
		dataType: "html",
		success: function(response) {
			var arr = response.numberStr.split(",");
			var str = "";
			for (var i = 0; i < arr.length; i++) {
				if (i > 0) {
					str += "<div class=\"newbtn-list\" style=\"display:inline-block;\">" + arr[i] + "</div>&nbsp;&nbsp;";
					$('#cb_ball' + arr[i]).prop('checked', true);
					$('#ball' + arr[i]).css({
						"background": "#feba28",
						"color": "black"
					})
				}
			}
			if (str !== '') {
				str = "你產生的號碼： " + str;
				$("#v-pills-englishToNumber").html(str);
			}
		},
		error: function() {
			alert("SetupError!");
		}
	});
}

function chooseBallFunc(ball) {
	// 上面按鈕的顯示
	// 		alert("ball> " + ball);
	var KEY = "", choose = "";
	var cbball = $("#cb_ball" + ball).is(":checked");
	// 	    alert("A1> " + cbball);
	if (cbball == false) { // 如果沒有 checked 下次點擊則 true
		$("#cb_ball" + ball).prop('checked', true);
		// 	        alert("A2> " + cbball);
		$("#ball" + ball).css({
			"background": "#feba28",
			"color": "black"
		})
		KEY = "plus";
	} else {             // 如果已經 checked 下次點擊則 false
		$('#cb_ball' + ball).prop('checked', false);
		// 	        alert("A3> " + cbball);
		$("#ball" + ball).css({
			"background": "#6009f0",
			"color": "#fff"
		});
		KEY = "minus";
	}
	$.ajax({
		type: "post",
		url: "/Hello1221/lotto",
		data: { "action": "ChooseBall", "ball": ball, "calculate": KEY },
		error: function() {
			alert("SetupError!");
		}
	});
}


function BuyFunc() {
	var cbxVehicle = '';
	$('input:checkbox:checked[name="cbball"]').each(function(i) { cbxVehicle += this.value + ','; });
	cbxVehicle = cbxVehicle.substr(0, cbxVehicle.length - 1);
	$.ajax({
		type: "post",
		url: "/Hello1221/lotto",
		dataType: "json",
		data: { "action": "Buy", "value": cbxVehicle },
		success: function(response) {
			if (response.sum == false) {
				Swal.fire(response.errormsg);
			} else if (response == true) {
				//				Swal.fire(cbxVehicle);
				Swal.fire("GO!")
					.then(() => {
						cbxVehicle.split(",").forEach(function(ball) {
							var cbball = $("#cb_ball" + ball).is(":checked");
							if (cbball == true) {
								$('#cb_ball' + ball).prop('checked', false);
								$("#ball" + ball).css({
									"background": "#6009f0",
									"color": "#fff"
								});
							}
						});
						$('#v-pills-home-tab').click();
					});
			}
		},
		error: function() {
		}
	});
}

function FastBuyFunc() {
	if ($('#v-pills-fastbuy-tab').val() == '') {
		Swal.fire("要幾組?");
		return false;
	} else if ($('#v-pills-fastbuy-tab').val() <= 0) {
		Swal.fire("要" + $('#v-pills-fastbuy-tab').val() + "組? 要確定內");
		return false;
	}
	$.ajax({
		type: "post",
		url: "/Hello1221/lotto",
		dataType: "html",
		data: { "action": "fastRandomBuy", "value": $('#v-pills-fastbuy-tab').val() },
		success: function(response) {
			var x = $('#v-pills-fastbuy-tab').val();
			var str = "";
			let arr = JSON.parse(response);
			let chunks = [];
			let size = 6;
			for (let i = 0; i < arr.length; i += size) {
				chunks.push(arr.slice(i, i + size));
			}
			let result = chunks.map(chunk => chunk.join(",")).join(" ");
			//			console.log(result);
			var i = 0;
			result.split(" ").forEach(function(r) {
				i++;
				str += "第" + i + "組號碼：" + r + "<br>";
			});
			Swal.fire("您隨機產生的" + x + "組號碼分別為：<br>" + str)
				.then(() => {
					$('#v-pills-home-tab').click();
				});
		},
		error: function() {
		}
	});
}

function RemoveFunc() {
	
	// 所有被選中的值
	var cbxVehicle1 = '';
	$('input:checkbox:checked[name="cbball"]').each(function(i) { cbxVehicle1 += this.value + ','; });
	if (cbxVehicle1 == '') {
        Swal.fire("目前檯面上沒有值需要被過濾");
        return false;
    }
	
	// 過濾條件
	var cbxVehicle2 = '';
	$('input:checkbox:checked[name="removevalue"]').each(function(i) { cbxVehicle2 += this.value + ','; });
	if (cbxVehicle == '') {
        Swal.fire("你沒有勾選過濾條件要過濾甚麼?");
        return false;
    }

	if(cbxVehicle1 !== '' && cbxVehicle2 !== ''){
		$.ajax({
			type: "post",
			url: "/Hello1221/lotto",
			dataType: "json",
			data: { "action": "Remove", "value": cbxVehicle1, "removevalue": cbxVehicle2 },
			success: function(response) {
				if (response.remove !== null) {
					response.remove.split(",").forEach(function(ball) {
						var cbball = $("#cb_ball" + ball).is(":checked");
						if (cbball == true) {
							$('#cb_ball' + ball).prop('checked', false);
							$("#ball" + ball).css({
								"background": "#6009f0",
								"color": "#fff"
							});
						}
					});
				}
				Swal.fire("排除的號碼是: " + response.remove
						+ "<br>"
						+ "保留的號碼是: " + response.keep);
			},
			error: function() {
			}
		});
	}
}