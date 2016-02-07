var controllers = angular.module('sBookControllers', []);

controllers.controller('HomeCtrl', function($scope) {
    console.log('HomeCtrl');
    
});

controllers.controller('AdminCtrl', function($scope, $http) {
    console.log('AdminCtrl');
    $http.get('productApp?command=products&args=page=1').then(function(response){
    	console.log(response.data);
    	$scope.products = response.data;
    }, function(){
    	console.log('Error');
    });
    $http.get('productApp?command=tags&args=page=1').then(function(response){
    	console.log(response.data);
    	$scope.tags = response.data;
    }, function(){
    	console.log('Error');
    });
});

controllers.controller('ProductCtrl', function($scope, $http, $routeParams) {
    console.log('ProductCtrl');
    $http.get('productApp?command=display&args=name='+$routeParams.name+'@products').then(function(response){
    	console.log(response.data);
    	$scope.product = response.data;
    }, function(){
    	console.log('Error');
    });
    $http.get('productApp?command=visitstoday&args=productname='+$routeParams.name+'@products').then(function(response){
    	console.log(response.data);
    	$scope.product_visits = response.data;
    }, function(){
    	console.log('Error');
    });
    $http.get('productApp?command=purchasestoday&args=productname='+$routeParams.name).then(function(response){
    	console.log(response.data);
    	$scope.product_purchases = response.data;
    }, function(){
    	console.log('Error');
    });
});

controllers.controller('NewProductCtrl', function($scope, $http, $location) {
    console.log('NewProductCtrl');
    $scope.message="";
    $scope.create = function($event){
    	$event.preventDefault();
    	$http.get('productApp?command=commission&args=name='+
    			$scope.name+'@products:cost='+$scope.cost+':catagory='+
    			$scope.category+':author='+$scope.author+':tags='+
    			$scope.tags).then(function(response){
        	console.log(response);
        	b = response.data;
        	if(b==1){
        		console.log('successful');
        		$location.path('/admin');
        	}
        	else{
        		$scope.message="Error while creating new book";
        	}
        }, function(){
        	console.log('Error');
        });
    };
});

controllers.controller('TagCtrl', function($scope, $http, $routeParams) {
    console.log('TagCtrl');
    $http.get('productApp?command=displaytag&args=tagname='+$routeParams.name).then(function(response){
    	console.log(response.data);
    	$scope.tag = $routeParams.name;
    	$scope.values = response.data;
    }, function(){
    	console.log('Error');
    });
});

controllers.controller('UserCtrl', function($scope, $http, $location, $cookies) {
    console.log('UserCtrl');
    $scope.message="";
    $scope.loggedin = false;
    $scope.bought = false;
    $scope.cartempty= true;
    $scope.receipt = "";
    sessionid = $cookies.get("sessionid");
    if(sessionid){
    	$scope.sessionid=sessionid;
    	$scope.loggedin = true;
    	$http.get('userApp?command=showmycart&args=sessionid='+sessionid).then(function(response){
        	console.log(response);
        	b = response.data;
        	if(b!=0){
        		$scope.cart = b;
        		$scope.cartempty = false;
        	}
        }, function(){
        	console.log('Error');
        });
    	$scope.remove = function($event, prod){
    		$event.preventDefault();
    		$http.get('userApp?command=editcart&args=sessionid='+sessionid+':product='+prod+'@0').then(function(response){
            	console.log(response);
            	b = response.data;
            	window.location.reload();
            }, function(){
            	console.log('Error');
            });
    	};
    	$scope.buy = function($event){
    		$event.preventDefault();
    		$http.get('userApp?command=buy&args=sessionid='+sessionid).then(function(response){
            	console.log(response);
            	b = response.data;
            	$scope.bought = true;
            	$scope.receipt = b;
            }, function(){
            	console.log('Error');
            });
    	};
    }
    $http.get('productApp?command=products&args=page=1').then(function(response){
    	console.log(response.data);
    	$scope.products = response.data;
    }, function(){
    	console.log('Error');
    });
    $scope.login = function($event){
    	$event.preventDefault();
    	console.log($scope.username);
    	console.log($scope.password);
    	$http.get('userApp?command=login&args=name='+$scope.username+':password='+$scope.password).then(function(response){
        	b = response.data;
        	if(b.session){
        		$cookies.put("sessionid", b.session);
        		$location.path('/user/'+b.session);
        	}
        	else{
        		console.log(b);
        		$scope.message="Error while login";
        	}
        }, function(){
        	console.log('Error');
        });
    };
    $scope.logout = function($event){
    	$event.preventDefault();
    	$http.get('userApp?command=logout&args=sessionid='+$scope.sessionid).then(function(response){
    		console.log(response);
        	b = response.data;
        	if(b==1){
        		$cookies.remove("sessionid");
        		window.location.reload();
        	}
        	else{
        		console.log(b);
        	}
        }, function(){
        	console.log('Error');
        });
    };
});

controllers.controller('NewUserCtrl', function($scope, $http, $location) {
    console.log('NewUserCtrl');
    $scope.message="";
    $scope.create = function($event){
    	$event.preventDefault();
    	$http.get('userApp?command=register&args=name='+$scope.username+':password='+$scope.password+':address='+$scope.address).
    	then(function(response){
        	console.log(response);
        	var b = response.data;
        	if(b!=""){
        		console.log('successful');
        		$location.path('/user/'+b);
        	}
        	else{
        		$scope.message="Error while registering";
        	}
        }, function(){
        	console.log('Error');
        });
    };
});

controllers.controller('UserDataCtrl', function($scope, $http, $location, $cookies) {
    console.log('UserDataCtrl');
    sessionid = $cookies.get("sessionid");
    if(sessionid){
	    $scope.message="";
	    $http.get('userApp?command=mydata&args=sessionid='+sessionid).
    	then(function(response){
        	console.log(response);
        	$scope.user = response.data;
        }, function(){
        	console.log('Error');
        });
	    $http.get('userApp?command=stats&args=sessionid='+sessionid).
    	then(function(response){
        	console.log(response);
        	$scope.bproducts = response.data;
        }, function(){
        	console.log('Error');
        });
	    $http.get('userApp?command=mypurchasehistory&args=sessionid='+sessionid).
    	then(function(response){
        	console.log(response);
        	$scope.pproducts = response.data;
        }, function(){
        	console.log('Error');
        });
	    $scope.update = function($event){
	    	$event.preventDefault();
	    	$http.get('userApp?command=editmydata&args=name='+$scope.user.name+':password='+$scope.user.password+
	    			':address='+$scope.user.address+':sessionid='+sessionid).
	    	then(function(response){
	        	console.log(response);
	        	b = response.data;
	        	if(b==1){
	        		console.log('successful');
	        		$scope.message="Succesful";
	        	}
	        	else{
	        		$scope.message="Error while updating";
	        	}
	        }, function(){
	        	console.log('Error');
	        });
	    };
    }
    else{
    	$location.path('/user');
    }
});

controllers.controller('BrowseCtrl', function($scope, $http, $routeParams, $cookies, $location) {
    console.log('BrowseCtrl');
    sessionid = $cookies.get("sessionid");
    if(sessionid){
	    product = $routeParams.name+'@products';
	    $scope.quantity = 1;
	    $http.get('userApp?command=browse&args=sessionid='+sessionid+':browse='+product).
	    then(function(response){
	    	console.log(response.data);
	    	$scope.product = response.data;
	    }, function(){
	    	console.log('Error');
	    });
	    $http.get('userApp?command=recommendbyproduct&args=sessionid='+sessionid+':productname='+product).
	    then(function(response){
	    	console.log(response.data);
	    	$scope.recomendations = response.data;
	    }, function(){
	    	console.log('Error');
	    });
	    $scope.add = function($event){
	    	$event.preventDefault();
	    	$http.get('userApp?command=add2cart&args=sessionid='+sessionid+':product='+$routeParams.name+'@'+$scope.quantity).
	        then(function(response){
	        	console.log(response.data);
	        	b = response.data;
	        	if(b==1){
	        		console.log('successful');
	        		$location.path('/user');
	        	}
	        	else{
	        		console.log("Error while adding to cart");
	        	}
	        }, function(){
	        	console.log('Error');
	        });
	    };
    }
    else{
    	$location.path('/user');
    }
});

