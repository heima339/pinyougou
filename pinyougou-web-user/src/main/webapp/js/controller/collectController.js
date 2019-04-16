//首页控制器
app.controller('collectController',function($scope,$controller,$http,collectService){

    // AngularJS中的继承:伪继承
    $controller('baseController',{$scope:$scope});

	//展示用户名
	$scope.loadItemList=function(){
         // alert(123);
        collectService.loadItemList().success(
					function(response){
						$scope.itemList=response;
					}
			);
	}






});