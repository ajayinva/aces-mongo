angular.module("acesApp")
.controller("contactController", function(
	$scope
,	acesService
) {
	$scope.tempData = {};
	$scope.tempData.showPage = true;
	$scope.initData = {};
	$scope.contact = function () {
		$scope.tempData.showPage = false;
		$scope.saveData = {};
		acesService.contact($scope.initData.dto).then(function(data){	
			$scope.saveData = data;	
			$scope.tempData.showPage = true;
			if($scope.saveData.message!=null){
				acesService.initContact().then(function(data){	
					$scope.initData = data;
					$scope.tempData.showPage = true;
				});	
			}
		});
	};
	$scope.tempData.maxLength = 4000;
	$scope.remainingMessage = function() {
		if ($scope.initData.dto == null || $scope.initData.dto.message == null) {
			return $scope.tempData.maxLength;
		} else {
			return $scope.tempData.maxLength - $scope.initData.dto.message.length;
		}
	};
	$scope.getMessageClass = function() {
		if ($scope.initData.dto==null || $scope.initData.dto.message == null || ($scope.initData.dto.message.length < $scope.tempData.maxLength)) {
			return "text-info";
		}
		return "text-danger";
	};	
})
.controller("resetPasswordController", function(
	$scope
,	$location
,	acesConstants
,	acesService
) {
	$scope.initData = {};	
	$scope.initData.token  = $location.search().token;
	$scope.tempData = {};
	$scope.tempData.showPage = true;
	$scope.isDisabled = function () {
		if($scope.initData.password==null || $scope.initData.password=='' || $scope.initData.confirmPassword==null || $scope.initData.confirmPassword==''){
			return true;
		}
		return false;
	};
	$scope.reset = function () {		
		$scope.tempData.showPage = false;
		acesService.resetPassword($scope.initData).then(function(data){	
			$scope.saveData = data;	
			if($scope.saveData.error || $scope.saveData.invalidLink){
				$scope.tempData.showPage = true;				
			} else {
				acesService.setSuccessfulRegistrationMessage($scope.saveData.message);
				$location.search("token",null);
				$location.path(acesConstants.PATH_LOGIN)
			}			
		});
	};	
})
.controller("loginController", function(
	$scope
,	$rootScope
,	$location
,	acesConstants
,	acesService
) {
	$scope.tempData = {};
	$scope.tempData.showPage = true;
	$scope.tempData.successFullRegistrationMessage = acesService.getSuccessfulRegistrationMessage();
	acesService.setSuccessfulRegistrationMessage(null);
	$scope.initData = {};
	$scope.initData.username = "ajayagarwal007@yahoo.com";
	$scope.initData.password = "Kenya123";
	$scope.loginData = {};
	$scope.resetPasswordData = {};
	$scope.login = function () {
		acesService.login($scope.initData).then(function(data){	
			$scope.loginData = data;			
			if(data.status===200){
				$rootScope.$broadcast(acesConstants.EVENT_USER_LOGGED_IN, null);
				$rootScope.authenticated = true;
				$location.path(acesConstants.PATH_DASHBOARD);
			} else {
				$rootScope.authenticated = false;
				$scope.tempData.authenticationFailed = true;
			}
		});
	};	
	$scope.isDisabled = function () {
		if($scope.initData.username==null || $scope.initData.username=='' || $scope.initData.password==null || $scope.initData.password==''){
			return true;
		}
		return false;
	};
	$scope.resetPassword = function () {
		$scope.tempData.showPage = false;
		acesService.sendPasswordResetEmail($scope.resetPasswordData.email).then(function(data){	
			$scope.resetPasswordData = data;	
			$scope.tempData.showPage = true;
		});
	};	
	$scope.isResetPasswordDisabled = function () {
		if($scope.resetPasswordData.email==null || $scope.resetPasswordData.email==''){
			return true;
		}
		return false;
	};	
})
.controller("headerController", function(
	$scope
,	$rootScope
,	$location
,	acesConstants
,	acesService
) {
	$scope.initData = {};		
	$scope.logout = function () {
		acesService.logout($scope.initData).then(function(data){	
			$scope.headerData = null;
			$scope.logoutData = data;
			$rootScope.authenticated = false;
			$location.$$search = {};
			$location.path("/");		
		});
	};			
	acesService.getCurrentUser().then(function(data){		
		$scope.headerData = data;
		if(data.name==null){
			$scope.initData.showLogin = true;
			$rootScope.authenticated = false;
		} else {
			$scope.initData.showLogoff = true;
			$rootScope.authenticated = true;
		}
	});
	$scope.$on(acesConstants.EVENT_USER_LOGGED_IN, function (event, args) {		
		acesService.getCurrentUser().then(function(data){		
			$scope.headerData = data;			
		});
	});	
})
.controller("changePasswordController", function(
	$scope
,	acesService
) {
	$scope.initData = {};
	$scope.tempData = {};
	$scope.tempData.showPage = false;
	acesService.initChangePassword().then(function(data){	
		$scope.initData = data;		
		$scope.tempData.showPage = true;
	});
	$scope.isDisabled = function () {
		if($scope.initData.changePasswordDto){
			if($scope.initData.changePasswordDto.currentPassword==null 
					|| $scope.initData.changePasswordDto.currentPassword=='' 
						|| $scope.initData.changePasswordDto.password==null 
							|| $scope.initData.changePasswordDto.password==''
								|| $scope.initData.changePasswordDto.confirmPassword==null 
									|| $scope.initData.changePasswordDto.confirmPassword==''
			){
				return true;
			} else {
				return false;
			}
		}	
	};
	$scope.change = function () {
		$scope.tempData.showPage = false;
		acesService.changePassword($scope.initData.changePasswordDto).then(function(data){	
			$scope.saveData = data;
			$scope.tempData.showPage = true;
			if(!$scope.saveData.error){
				$scope.initData.changePasswordDto = {};				
			} 
		});
	};	
})
.controller("registerController", function(
	$scope
,	$location
,	acesConstants
,	acesService
,	vcRecaptchaService
) {
	$scope.initData = {};
	$scope.initData.registerDto = {};
	$scope.tempData = {};
	$scope.tempData.showPage = true;
	$scope.register = function () {
		$scope.saveData = null;
		$scope.tempData.showPage = false;
		acesService.register($scope.initData.registerDto).then(function(data){
			$scope.tempData.showPage = true;
			vcRecaptchaService.reload($scope.initData.widgetId);
			$scope.saveData = data;	
			if($scope.saveData.error){
				$scope.initData.registerDto.recaptchaResponse = null;				
			} else{
				acesService.setSuccessfulRegistrationMessage($scope.saveData.successFullRegistrationMessage);
				$location.path(acesConstants.PATH_LOGIN)
			}
		});
	};	
	$scope.captchaExpiration = function () {
		$scope.initData.registerDto.recaptchaResponse = null;
	};	
	$scope.setWidgetId = function (widgetId) {
		$scope.initData.widgetId = widgetId;
	};	
})
.controller("productListController", function(
	$scope
,	acesService
) {
	$scope.initData = {};
	$scope.tempData = {};
	$scope.tempData.showPage = false;
	acesService.getProducts().then(function(data){	
		$scope.initData = data;		
		$scope.tempData.showPage = true;
	});
})
.controller("dashboardController", function(
	$scope
,	$location
,	acesService
,	acesConstants
) {
	$scope.initData = {};
	$scope.tempData = {};
	$scope.deleteExamData = null;
	
	$scope.tempData.showPage = false;
	$scope.tempData.examStartAt = 0;
	$scope.tempData.examPageNumber = 1;	
	$scope.tempData.quizStartAt = 0;
	$scope.tempData.quizPageNumber = 1;	
	$scope.tempData.perPage = 5;
	
	$scope.paginateExam = function () {
		$scope.tempData.examStartAt = ($scope.tempData.examPageNumber - 1) * $scope.tempData.perPage;
	};
	$scope.paginateQuiz = function () {
		$scope.tempData.quizStartAt = ($scope.tempData.quizPageNumber - 1) * $scope.tempData.perPage;
	};
	
	acesService.dashboard().then(function(data){	
		$scope.initData = data;					
		$scope.tempData.showPage = true;
	});	
	$scope.deleteExam = function(examId) {
		$scope.deleteExamData = null;
		$scope.tempData.showPage = false;
		acesService.deleteExam($scope.initData.plan.id,examId).then(function(data){	
			$scope.deleteExamData = data;		
			if($scope.deleteExamData.deleted){
				acesService.dashboard().then(function(data){	
					$scope.initData = data;		
					$scope.tempData.showPage = true;
				});	
			} else {
				$scope.tempData.showPage = true;
			}
		});
	};		
	$scope.resumeExam = function(examId) {
		$scope.tempData.showPage = false;
		acesService.getExamSummary($scope.initData.plan.id,examId).then(function(data){	
			acesService.setFirstQuestion(data);	
			$location.search("planId", $scope.initData.plan.id);
			$location.search("examId", examId);
			$location.path(acesConstants.PATH_QUESTION);			
		});
	};		
})
.controller("examController", function(
	$scope
,	$location
,	acesService
,	acesConstants
) {
	$scope.initData = {};
	$scope.tempData = {};
	$scope.tempData.showPage = false;
	$scope.tempData.planId = $location.search().planId;
	acesService.initExam($scope.tempData.planId).then(function(data){	
		$scope.initData = data;		
		$scope.tempData.showPage = true;
	});	
	$scope.createExam = function (nowOrLater) {
		acesService.createExam($scope.initData.exam, $scope.tempData.planId).then(function(data){	
			$scope.saveData = data;		
			if(!$scope.saveData.error){
				acesService.setFirstQuestion($scope.saveData);	
				$location.path(acesConstants.PATH_QUESTION);	
			} else { 
				$scope.tempData.showPage = true;
			}
		});
	};	
	
})
.controller("productController", function(
	$scope
,	$rootScope
,	$location
,	acesService
,	acesConstants
) {
	$scope.initData = {};
	$scope.tempData = {};
	$scope.tempData.showPage = false;
	acesService.getProduct($location.search().productId).then(function(data){	
		$scope.initData = data;		
		$scope.tempData.showPage = true;
	});
	$scope.tryOrBuyNow = function () {
		$location.search("productId",null);
		if($rootScope.authenticated){
			$location.path(acesConstants.PATH_ACCOUNT_PRODUCTS);
		} else {
			$location.path(acesConstants.PATH_REGISTER);
		}		
	};
})
.controller("questionController", function(
	$scope
,	$location
,	$uibModal
,	acesService
,	acesConstants
) {
	$scope.tempData = {};
	$scope.tempData.showPage = false;
	$scope.tempData.planId = $location.search().planId;
	$scope.initData = acesService.getFirstQuestion();
	$scope.tempData.showPage = true;
	acesService.setFirstQuestion(null);
	$scope.tempData.totalQuestions = $scope.initData.totalQuestions;
	$scope.initAnswers = function () {
		if($scope.initData.examQuestionDto.selectedOptions.length > 0){
			if($scope.initData.question.numberOfCorrectOptions==1){						
				$scope.tempData.option = $scope.initData.examQuestionDto.selectedOptions[0];
			} else{
				for(var i=0;i<$scope.initData.examQuestionDto.selectedOptions.length;i++){
					angular.forEach($scope.initData.question.options, function(option, idx){
						if(option.id==$scope.initData.examQuestionDto.selectedOptions[i]){
							option.selected = true;
						}
					});
				}
			}					
		} else {
			$scope.tempData.option = null;
		}
	}
	$scope.initAnswers();
	$scope.preProcess = function () {
		$scope.initData.examQuestionDto.selectedOptions = [];
		$scope.tempData.showPage = false;
		if($scope.initData.question.numberOfCorrectOptions==1 && $scope.tempData.option){
			$scope.initData.examQuestionDto.selectedOptions.push($scope.tempData.option);
		}
		if($scope.initData.question.numberOfCorrectOptions > 1){			
			angular.forEach($scope.initData.question.options, function(option, idx){
				if(option.selected){
					$scope.initData.examQuestionDto.selectedOptions.push(option.id);
				}
			});
		}		
	}	
	$scope.postProcess = function () {
		if(!$scope.saveData.error){
			$scope.initData = $scope.saveData;	
			$scope.initAnswers();
		}		
		$scope.tempData.showPage = true;
	}
	$scope.next = function () {
		$scope.preProcess();
		if($scope.initData.examQuestionDto.selectedOptions.length > $scope.initData.question.numberOfCorrectOptions){
			$scope.tempData.showPage = true;
			$uibModal.open({
			    animation: true,
			    templateUrl: acesConstants.HTML_ACES_ALERT,
			    controller: acesConstants.CONTROLLER_ACES_ALERT,				    
			    resolve: {
			    	message: function () {
			    		return "Please select only "+$scope.initData.question.numberOfCorrectOptions+" options.";
			        }
			    }
			});			
		} else {
			acesService.saveAndQuestion($location.search().planId, $scope.initData.examQuestionDto).then(function(data){	
				$scope.saveData = data;	
				if($scope.saveData.question==null){
					$location.search("examId",$scope.saveData.examId);
					$location.path(acesConstants.PATH_EXAM_CONFIRM);
				} else {
					$scope.postProcess();
				}
			});
		}
	};
	$scope.previous = function () {
		$scope.initData.examQuestionDto.previous = true;
		$scope.preProcess();
		acesService.saveAndQuestion($location.search().planId, $scope.initData.examQuestionDto).then(function(data){					
			$scope.saveData = data;		
			$scope.postProcess();
		});
	};
	$scope.starTheQuestion = function () {
		$scope.initData.examQuestionDto.starred = !$scope.initData.examQuestionDto.starred;
	};
	
	$scope.getStarredClass = function () {
		if($scope.initData.examQuestionDto.starred){
			return "btn btn-warning btn-sm rounded";
		}
		return "btn btn-default btn-sm rounded";
	};
	$scope.notes = function () {
		$uibModal.open({
		    animation: true,
		    templateUrl: acesConstants.HTML_QUESTION_NOTES,
		    controller: acesConstants.CONTROLLER_QUESTION_NOTES,		
		    backdrop: 'static',
		    size:'lg',
		    resolve: {
		    	request: function () {
		    		return {
		    			'questionId' : $scope.initData.examQuestionDto.questionId
		    		,	'planId' : $scope.tempData.planId
		    		};
		        }
		    }
		});		
	};
})
.controller("examSummaryController", function(
	$scope
,	$location
,	$uibModal
,	acesService
,	acesConstants
) {
	$scope.initData = {};
	$scope.tempData = {};
	$scope.tempData.showPage = false;
	$scope.tempData.planId = $location.search().planId;
	acesService.getExamSummary($location.search().planId, $location.search().examId).then(function(data){	
		$scope.initData = data;		
		$scope.postProcess();
	});
	$scope.next = function () {
		$scope.viewQuestion($location.search().planId, $location.search().examId, $scope.initData.examQuestion.nextQuestionId);
	};
	$scope.previous = function () {
		$scope.viewQuestion($location.search().planId, $location.search().examId, $scope.initData.examQuestion.previousQuestionId);
	};
	$scope.viewQuestion = function (planId,examId,questionId) {
		$scope.tempData.showPage = false;
		acesService.viewQuestion(planId,examId,questionId).then(function(data){	
			$scope.initData.question = data.question;
			$scope.initData.examQuestion = data.examQuestion;
			$scope.postProcess();
		});
	}
	$scope.postProcess = function () {
		if($scope.initData.question.numberOfCorrectOptions==1){						
			$scope.tempData.option = $scope.initData.examQuestion.selectedOption1;
		} else{
			angular.forEach($scope.initData.question.options, function(option, idx){
				if((option.id==$scope.initData.examQuestion.selectedOption1)||(option.id==$scope.initData.examQuestion.selectedOption2)||(option.id==$scope.initData.examQuestion.selectedOption3)){
					option.selected = true;
				}
			});
		}
		$scope.tempData.showPage = true;
	};
	$scope.getStarredClass = function () {
		if($scope.initData!=null && $scope.initData.examQuestion!=null && $scope.initData.examQuestion.starred){
			return "btn btn-warning btn-sm rounded";
		}
		return "btn btn-default btn-sm rounded";
	};
	$scope.notes = function () {
		$uibModal.open({
		    animation: true,
		    templateUrl: acesConstants.HTML_QUESTION_NOTES,
		    controller: acesConstants.CONTROLLER_QUESTION_NOTES,		
		    backdrop: 'static',
		    size:'lg',
		    resolve: {
		    	request: function () {
		    		return {
		    			'questionId' : $scope.initData.question.id
		    		,	'planId' : $scope.tempData.planId
		    		};
		        }
		    }
		});		
	};
})
.controller("examConfirmController", function(
	$scope
,	$location
,	acesService
,	acesConstants
) {
	$scope.initData = {};
	$scope.tempData = {};
	$scope.tempData.showPage = false;
	$scope.tempData.planId = $location.search().planId;
	$scope.tempData.examId = $location.search().examId;
	acesService.getExamSummary($location.search().planId, $location.search().examId).then(function(data){	
		$scope.initData = data;	
		$scope.tempData.showPage = true;
	});
	$scope.submit = function () {		
		$scope.tempData.showPage = false;
		acesService.submitExam($location.search().planId, $location.search().examId).then(function(data){	
			$scope.tempData.confirm = true;
			$scope.initData = data;		
			$scope.tempData.showPage = true;
		});
	};
	$scope.resumeExam = function() {
		$scope.tempData.showPage = false;
		acesService.getExamSummary($scope.tempData.planId,$scope.tempData.examId).then(function(data){	
			acesService.setFirstQuestion(data);	
			$location.search("planId", $scope.tempData.planId);
			$location.search("examId", $scope.tempData.examId);
			$location.path(acesConstants.PATH_QUESTION);			
		});
	};		
})
.controller("activateAccountController", function(
	$scope
,	$location
,	acesService
,	acesConstants
) {
	$scope.initData = {};
	$scope.tempData = {};
	$scope.tempData.showPage = false;
	acesService.activateAccount($location.search().token).then(function(data){	
		$scope.initData = data;	
		if($scope.initData.message){
			acesService.setSuccessfulRegistrationMessage($scope.initData.message);
			$location.search("token",null);
			$location.path(acesConstants.PATH_LOGIN)
		} else {
			$scope.tempData.showPage = true;
		}
	});		
})

.controller("questionNotesController", function(
	$scope
,	$uibModalInstance
,	acesService
,	request	
) {
	$scope.tempData = {};
	$scope.tempData.maxLength = 4000;
	$scope.tempData.showPage = false;
	$scope.initData = {};
	$scope.tempData.questionId = request.questionId;
	$scope.tempData.planId = request.planId;
	acesService.viewQuestionNotes($scope.tempData.planId, $scope.tempData.questionId).then(function(data){	
		$scope.initData = data;
		$scope.tempData.showPage = true;
	});
	$scope.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	};
	$scope.ok = function() {
		$uibModalInstance.close();
	};		
	$scope.remainingNotes = function() {
		if ($scope.initData.dto==null || $scope.initData.dto.notes == null) {
			return $scope.tempData.maxLength;
		} else {
			return $scope.tempData.maxLength
					- $scope.initData.dto.notes.length;
		}
	};
	$scope.getNotesClass = function() {
		if ($scope.initData.dto ==null || $scope.initData.dto.notes == null || $scope.initData.dto.notes.length < $scope.tempData.maxLength) {
			return "text-info";
		}
		return "text-danger";
	};
	$scope.save = function() {
		$scope.tempData.showPage = false;
		acesService.saveQuestionNotes($scope.tempData.planId,$scope.initData.dto).then(function(data){	
			$scope.saveData = data;
			if(!$scope.saveData.error){
				$scope.ok();
			} else{
				$scope.tempData.showPage = true;
			}		
		});
	};
})
.controller("acesAlertController", function(
	$scope
,	$uibModalInstance
,	message
) {
	$scope.tempData = {};
	$scope.tempData.message = message;
	$scope.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	};
	$scope.ok = function() {
		$uibModalInstance.close();
	};		
});
