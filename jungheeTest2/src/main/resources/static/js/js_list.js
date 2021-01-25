/**
 * 
 */

$(document).ready(function() {
	$(".search-btn").click(function() {
		var nameKeyword = $(".searchName-bar").val();
		var departmentKeyword = $(".searchDepartment-bar").val();

		$('#listTable tr').remove();
		var tableColumn =	
			"<tr><th style='text-align:center' class='list-name'>番号</th>" +
			"<th id='column' class='list-name'>名前</th>" +
			"<th id='column' class='list-name'>部署</th>" +
			"<th id='column' class='list-name'>職責</th>" +
			"<th id='column' class='list-name'>性別</th>" +
			"<th id='column' class='list-name'>入社日</th>" +
			"<th style='text-align:center' class='list-name'>電話番号</th>" +
			"<th id='column' class='list-name'>　</th>" +
			"<th id='column' class='list-name'>　</th></tr>";

		$('#listTable').append(tableColumn);
		$.ajax({
			url: "search",
			data: {
				nameKeyword: nameKeyword,
				departmentKeyword: departmentKeyword
			},
			type: "POST"
		}).done(function(data) {
			var userList = data.userList;
			if (userList == null || userList.length == 0) {
				var columnEmpty = "<tr><td colspan='9'>該当するユーザー情報がありません。</td></tr>";
				$('#listTable').append(columnEmpty);
				return false;
			} else if (userList.length >= 10) {
				var columnEmpty = "<tr><td colspan='9'>ユーザー情報が多すぎるため、条件を絞って再建策してください。</td></tr>";
				$('#listTable').append(columnEmpty);
				return false;
			}
			for (var i = 0; i < userList.length; i++) {
				var column =
					"<tr>" +
					"<td class='indexLine'>" + userList[i].index_id + "</td>" +
					"<td class='noLine'>" + userList[i].name + "</td>" +
					"<td class='noLine' value='"+userList[i].departmentCode + "'>" + userList[i].department + 
				//	"<input type='hidden' id='departmentCode' value='"+userList[i].departmentCode + "'>"+ "</td>" +
					"<td class='noLine' value='"+userList[i].positionCode + "'>" + userList[i].position + "</td>" +
				//	"<input type='hidden' id='positionCode' value='"+userList[i].positionCode + "'>"+
					"<td class='noLine' value='"+userList[i].genderCode + "'>" + userList[i].gender + "</td>" +
				//	"<input type='hidden' id='genderCode' value='"+userList[i].genderCode + "'>"+
					"<td class='noLine'>" + userList[i].hireDate + "</td>" +
					"<td class='PHLine'>" + userList[i].phoneNumber + "</td>" +
					"<td class='noLine'><span class='updateFn' value='" + userList[i].index_id + "'>修正</span></td>" +
					"<td class='noLine'><span class='deleteFn' value='" + userList[i].index_id + "'>削除</span></td>";

				$('#listTable').append(column);
			}
		}).fail(function(data) {
			console.log("データ通信に失敗しました。");
		});
	});

	$("#registerBtn").click(function() {
		$("#userModel").css("display", "block");
		$(".curtain").css("display", "block");
	});

	$(".close").click(function() {
		registerModalClose();
	});
	$(".curtain").click(function() {
		registerModalClose();
	});
	
	function registerModalClose() {
		
		$("#userModel").css("display", "none");
		$("#userUpdateModal").css("display", "none");
		$(".curtain").css("display", "none");
		$("#nameText").val("");
		$("#departmentText").val("000");
		$("#positionText").val("000");
		$("#woman-check").prop("checked", false);
		$("#man-check").prop("checked", true);
		$("#hireDateText").val("");
		$("#phoneNuberText").val("");
	}
	
	function checkKeyword(name,department,position,gender,hireDate,phoneNumber) {
		//CHECK KEYWORD
		var msg = "";
		
		if (name.length<1)					//is blank name?
			return msg = "名前を入力してください。";	
		else if (department == "000")			//is blank department? 
			return msg ="部署を選んでください。";
		else if (position == "000")				//is blank position?
			return msg ="職責を選んでください。";
		else if (gender == "000")				//is blank gender?
			return msg ="性別を選んでください。";
		else if(hireDate.length<1) 				//is blank hireDate?
			return msg ="入社日を入力してください。";
		else if (name.length > 30 )				//name length >30
			return msg ="名前を30文字以内で入力してください。";
		else if(phoneNumber.length != 13)		//phoneNumber length==13
			return msg ="電話番号を形式に合わせて入力してください。例：(半角)'000-0000-0000'";
		
		var patt = new RegExp("[0-9]{3}-[0-9]{4}-[0-9]{4}");
		if(!patt.test(phoneNumber))				//pattern&&phoneNumber 
			return msg ="電話番号を形式に合わせて入力してください。例：(半角)'000-0000-0000'";
		
		return msg;
	}

	$("#userRegister").click(function() {
		//GET KEYWORD name, department, position,
		// 			 gender, hireDate, phoneNumber
		var name = $("#nameText").val();
		var department = $("#departmentText option:selected").val();
		var position = $("select[name='positionText']").val();
		var gender = $("input[name='gender']:checked").val(); 
		var hireDate = $("#hireDateText").val();
		var phoneNumber = $("#phoneNumberText").val();
		var msgLog = "";
		
		msgLog = checkKeyword(name,department,position,gender,hireDate,phoneNumber);
		if(msgLog!="")
			return alert(msgLog);
		
		$.ajax({
			url: "register",
			data: {
				name: name,
				department: department,
				position : position,
				gender: gender,
				hireDate: hireDate,
				phoneNumber: phoneNumber
			},
			type: "POST"
		}).done(function(data) {
			var msg = data.msg;
			if (msg === "success") {
				alert("登録に成功しました");
				$(".curtain").click();
				$(".search-btn").click();
			} else {
				alert("登録に失敗しました");
				return false;
			}
		}).fail(function(data) {
			console.log("データ通信に失敗しました。");
		});
	});
	
	//show update modal
	$(document).on("click", ".updateFn", function() {
		$("#userUpdateModal").css("display", "block");
		$(".curtain").css("display", "block");
		
		//Get user data
		var selectTable=$(this).closest("tr");
		var selectIndexNo= $(this).attr('value');
		var name = selectTable.find("td:eq(1)").text();
		var department = selectTable.find("td:eq(2)").attr('value');
		
		var position = selectTable.find("td:eq(3)").attr('value');
		var gender = selectTable.find("td:eq(4)").attr('value'); 
		var hireDate = selectTable.find("td:eq(5)").text();
		var phoneNumber = selectTable.find("td:eq(6)").text();
		
		//default data set
		//SET KEYWORD name, department, position,
		// 			 gender, hireDate, phoneNumber
		$("#indexUp").attr("value", selectIndexNo);
		
		$("#nameTextUp").val(name);
		
		$("#departmentTextUp").val(department).attr("selected","selected");
		
		$("#positionTextUp").val(position).attr("selected","selected");
		
		if(gender=="001") $("#man-checkUp").prop("checked", true);
		else $("#woman-checkUp").prop("checked", true);
	
		//convert string '/' -> '-'
		hireDate = hireDate.split("/").join("-");
		$("#hireDateTextUp").val(hireDate);
		
		$("#phoneNumberTextUp").val(phoneNumber);
		
		});
	
	//update start
	$("#updateBtn").click(function() {
		//GET KEYWORD index_id, name, department, position,
		// 			 gender, hireDate, phoneNumber
		
		var index_id = parseInt($("#indexUp").val());
		var name = $("#nameTextUp").val();
		var department = $("#departmentTextUp option:selected").val();
		var position = $("#positionTextUp option:selected").val();
		var gender = $("input[name='genderUp']:checked").val(); 
		var hireDate = $("#hireDateTextUp").val();
		var phoneNumber = $("#phoneNumberTextUp").val();
		
		var msgLog = "";
		
		msgLog = checkKeyword(name,department,position,gender,hireDate,phoneNumber);
		if(msgLog!="")
			return alert(msgLog);
		
		$.ajax({
			url: "update",
			data: {
				index_id : index_id,
				name: name,
				department: department,
				position : position,
				gender: gender,
				hireDate: hireDate,
				phoneNumber: phoneNumber
			},
			type: "POST"
		}).done(function(data) {
			var msg = data.msg;
			if (msg === "success") {
				alert("修正に成功しました");
				$(".curtain").click();
				$(".search-btn").click();
			} else {
				alert("修正に失敗しました");
				return false;
			}
		}).fail(function(data) {
			console.log("データ通信に失敗しました。");
		});
		
		
	});
	
	$("#searchBarName").keypress(function(event){
		var keycode = (event.keyCode ? event.keyCode: event.which);
		//enter
		if(keycode == '13')
			$(".search-btn").click();
		
	});
		$("#searchBarDepartment").keypress(function(event){
		var keycode = (event.keyCode ? event.keyCode: event.which);
		//enter
		if(keycode == '13')
			$(".search-btn").click();
		
	});

	$(document).on("click", ".deleteFn", function() {
		var selectIndexNo = $(this).attr('value');
		var name = $(this).closest("tr").find("td:eq(1)").text();
		var department = $(this).closest("tr").find("td:eq(2)").text();
		var r = confirm(department +"の"+ name + "様を削除しますか？");
		if (!r) return;
		$.ajax({
			url: "delete",
			data: {
				selectIndexNo: selectIndexNo
			},
			type: "POST"
		}).done(function(data) {
			var msg = data.msg;
			if (msg == "success") {
				alert("削除に成功しました");
				$(".search-btn").click();
			} else {
				alert("削除に失敗しました");
				return false;
			}
		}).fail(function(data) {
			console.log("データ通信に失敗しました。");
		});
	});

});

