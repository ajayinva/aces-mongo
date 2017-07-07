angular.module("acesApp")
.factory("acesService", function(
	$http
,	acesConstants
){
	var successfulRegistrationMessage = null;
	var firstQuestion = null;
	return {
		setSuccessfulRegistrationMessage : function(message){
			successfulRegistrationMessage = message;
		},
		getSuccessfulRegistrationMessage : function(){
			return successfulRegistrationMessage;
		},
		setFirstQuestion : function(question){
			firstQuestion = question;
		},
		getFirstQuestion : function(){
			return firstQuestion;
		},
		login : function(userDto){
			var loginData = {};
			return $http({
				url: acesConstants.URL_LOGIN,
				method: "POST",
				data: $.param({
					username : userDto.username
				,	password : userDto.password
				}),
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}).then(function success(response){
				loginData = response;
				return loginData;
			}, function failure(response){
				loginData = response
				return loginData;
			});
		},
		contact : function(contactDto){
			var initContactData= {};	
			return $http({
				url: acesConstants.URL_CONTACT,
				method: "POST",
				data : contactDto
			}).success(function(data){
				initContactData = data;
			}).then(function(){
				return initContactData;
			});
		},
		getCurrentUser : function(){
			var userData= {};	
			return $http({
				url: acesConstants.URL_CURRENT_USER,
				method: "GET"
			}).success(function(data){
				userData = data;
			}).then(function(){
				return userData;
			});
		},
		logout : function(){
			var userData= {};	
			return $http({
				url: acesConstants.URL_LOGOUT,
				method: "POST"
			}).success(function(data){
				userData = data;
			}).then(function(){
				return userData;
			});
		},
		initChangePassword : function(){
			var changePasswordInitData= {};	
			return $http({
				url: acesConstants.URL_CHANGE_PASSWORD,
				method: "GET"
			}).success(function(data){
				changePasswordInitData = data;
			}).then(function(){
				return changePasswordInitData;
			});
		},
		changePassword : function(changePasswordDto){
			var changePasswordData = {};	
			return $http({
				url: acesConstants.URL_CHANGE_PASSWORD,
				method: "POST",
				data : changePasswordDto
			}).success(function(data){
				changePasswordData = data;
			}).then(function(){
				return changePasswordData;
			});
		},
		register : function(registerDto){
			var registerData = {};	
			return $http({
				url: acesConstants.URL_REGISTER,
				method: "POST",
				data : registerDto
			}).success(function(data){
				registerData = data;
			}).then(function(){
				return registerData;
			});
		},
		resetPassword : function(resetPasswordDto){			
			var resetPasswordData = {};	
			return $http({
				url: acesConstants.URL_RESET_PASSWORD,
				method: "POST",
				data : resetPasswordDto
			}).success(function(data){
				resetPasswordData = data;
			}).then(function(){
				return resetPasswordData;
			});
		},
		activateAccount : function(token){			
			var activateAccountData = {};	
			return $http({
				url: acesConstants.URL_ACTIVATE_ACCOUNT,
				method: "GET",
				params: {
					token : token
				}		
			}).success(function(data){
				activateAccountData = data;
			}).then(function(){
				return activateAccountData;
			});
		},
		sendPasswordResetEmail : function(email){
			var sendPasswordResetEmailData= {};	
			return $http({
				url: acesConstants.URL_SEND_PASSWORD_RESET_EMAIL,
				method: "POST",
				data: $.param({
					email : email
				}),
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}).success(function(data){
				sendPasswordResetEmailData = data;
			}).then(function(){
				return sendPasswordResetEmailData;
			});
		},
		getProducts : function(){
			var productData= {};	
			return $http({
				url: acesConstants.URL_PRODUCT_ALL,
				method: "GET"
			}).success(function(data){
				productData = data;
			}).then(function(){
				return productData;
			});
		},
		getProduct : function(productId){
			var productData= {};	
			return $http({
				url: acesConstants.URL_PRODUCT_VIEW,
				method: "GET",
				params: {
					productId : productId
				}					
			}).success(function(data){
				productData = data;
			}).then(function(){
				return productData;
			});
		},
		dashboard : function(){
			var dashboardData= {};	
			return $http({
				url: acesConstants.URL_DASHBOARD,
				method: "GET"						
			}).success(function(data){
				dashboardData = data;
			}).then(function(){
				return dashboardData;
			});
		},
		initExam : function(planId){
			var initExamData= {};	
			return $http({
				url: acesConstants.URL_INIT_EXAM,
				method: "GET",
				params: {
					planId : planId
				}					
			}).success(function(data){
				initExamData = data;
			}).then(function(){
				return initExamData;
			});
		},
		createExam : function(exam,planId){			
			var createExamData = {};	
			return $http({
				url: acesConstants.URL_SAVE_EXAM,
				method: "POST",
				params: {
					planId : planId
				},	
				data : exam
			}).success(function(data){
				createExamData = data;
			}).then(function(){
				return createExamData;
			});
		},
		saveAndQuestion : function(planId,examQuestionDto){			
			var saveAndQuestionData = {};	
			return $http({
				url: acesConstants.URL_QUESTION,
				method: "POST",
				params: {
					planId : planId
				},	
				data : examQuestionDto
			}).success(function(data){
				saveAndQuestionData = data;
			}).then(function(){
				return saveAndQuestionData;
			});
		},
		submitExam : function(planId,examId){			
			var submitExamData = {};	
			return $http({
				url: acesConstants.URL_SUBMIT_EXAM,
				method: "POST",
				params: {
					planId : planId
				,	examId : examId
				},					
			}).success(function(data){
				submitExamData = data;
			}).then(function(){
				return submitExamData;
			});
		},
		getExamSummary : function(planId, examId){
			var examSummaryData= {};	
			return $http({
				url: acesConstants.URL_EXAM_SUMMARY,
				method: "GET",
				params: {
					planId : planId
				,	examId : examId
				}					
			}).success(function(data){
				examSummaryData = data;
			}).then(function(){
				return examSummaryData;
			});
		},
		deleteExam : function(planId, examId){
			var deleteExamData= {};	
			return $http({
				url: acesConstants.URL_EXAM_DELETE,
				method: "GET",
				params: {
					planId : planId
				,	examId : examId
				}					
			}).success(function(data){
				deleteExamData = data;
			}).then(function(){
				return deleteExamData;
			});
		},
		viewQuestion : function(planId, examId, questionId){
			var viewQuestionData= {};	
			return $http({
				url: acesConstants.URL_VIEW_QUESTION,
				method: "GET",
				params: {
					planId : planId
				,	examId : examId
				,	questionId : questionId
				}					
			}).success(function(data){
				viewQuestionData = data;
			}).then(function(){
				return viewQuestionData;
			});
		},
		viewQuestionNotes : function(planId, questionId){
			var viewQuestionNotesData= {};	
			return $http({
				url: acesConstants.URL_USER_NOTES_VIEW,
				method: "GET",
				params: {
					planId : planId
				,	questionId : questionId
				}					
			}).success(function(data){
				viewQuestionNotesData = data;
			}).then(function(){
				return viewQuestionNotesData;
			});
		},
		saveQuestionNotes : function(planId,notes){			
			var saveQuestionNotesData = {};	
			return $http({
				url: acesConstants.URL_USER_NOTES_SAVE,
				method: "POST",
				data : notes,
				params: {
					planId : planId
				},					
			}).success(function(data){
				saveQuestionNotesData = data;
			}).then(function(){
				return saveQuestionNotesData;
			});
		},
	};
});