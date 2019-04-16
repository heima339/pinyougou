//首页控制器
app.controller('indexController',function($scope,$controller,$http,loginService){

    // AngularJS中的继承:伪继承
    $controller('baseController',{$scope:$scope});

	//展示用户名
	$scope.showName=function(){
        // alert(123);
			loginService.showName().success(
					function(response){
						$scope.loginName=response.loginName;
					}
			);
	}



    $scope.searchEntity={};
    $scope.status='';

    $scope.loadstatus=function(status){
        // alert(123);
        $scope.status=status;

    }


    // 假设定义一个查询的实体：searchEntity
    $scope.search = function(page,rows){
        // 向后台发送请求获取数据:
        loginService.search(page,rows,$scope.status).success(function(response){

            // alert($scope.searchEntity.status);
            //   alert($scope.status);

            $scope.paginationConf.totalItems = response.total;
            $scope.list = response.rows;

        });
    }

    // loadmiaosha
    $scope.loadmiaosha=function(){
        // alert(123);
        window.open("http://localhost:8091/seckill-index.html");

    }


    $scope.ss = ["","待付款","待发货","","待收货","","","待评价"];

});