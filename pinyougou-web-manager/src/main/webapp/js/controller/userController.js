app.controller("userController",function($scope,userService){
    //显示当前登陆人名称
    $scope.countUser = function(){
        userService.countUser().success(function(response){
            $scope.entity = response;
        });
    }

});