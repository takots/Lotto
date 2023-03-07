function CreateToDayFunc() {
	var sa = "";
	$.ajax({
		type: "POST",
		url: "/Hello1221/lotto",
		dataType: "json",
		data: { "action": "Today" },
		success: function(response) {
			var str = "";
			var array = response.lotto.split(",");
			for (var i = 0; i < array.length; i++) {
				if (array[i] !== '') {
					sa += array[i] + " ";
					str += "<div class=\"newbtn-list\" style=\"display:inline-block;\">" + array[i] + "</div>&nbsp;&nbsp;"
				}
			}
			str += "<div class=\"specbtn\" style=\"display:inline-block;\">" + response.special + "</div>&nbsp;&nbsp;"
			$("#v-pills-home").html(str);
			Swal.fire(sa + " [" + response.special + "]")
			.then(() => {
				location.reload();
			});
		},
		error: function() {
			alert('SetupError!');
		}
	});
}