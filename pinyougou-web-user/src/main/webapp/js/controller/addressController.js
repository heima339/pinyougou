//地址控制器
// 定义控制器:
app.controller("addressController",function($scope,$controller,$http,addressService) {

    $scope.entity = {};
    // 查询所有的品牌列表的方法:
    $scope.findListByLoginUser = function () {
        // 向后台发送请求:
        addressService.findListByLoginUser().success(function (response) {
            $scope.addressList = response;
        });
    }
    //新增地址
    $scope.addAddress = function(){
        // 区分是保存还是修改
        var object;
        if($scope.entity.id != null){
            // 更新

            object = addressService.update($scope.entity);
        }else{
            // 保存
            $scope.entity = {};
            object = addressService.add($scope.entity);
        }
        object.success(function(response){
            // {flag:true,message:xxx}
            // 判断保存是否成功:
            if(response.flag){
                //重新查询
                alert(response.message);
                $scope.entity = {};
                location.href="home-setting-address.html";
            }else{
                // 保存失败
                alert(response.message);
            }
        });
    }


    /*$scope.addAddress = function () {
        // 向后台发送请求:
        addressService.addAddress($scope.entity).success(function(response){
            if(response.flag){
                //重新查询
                alert(response.message);
                location.href="home-setting-address.html";
            }else{
                alert(response.message);
            }

        });
    }*/

    // 删除地址:
    $scope.dele = function(id){
        addressService.dele(id).success(function(response){
            // 判断保存是否成功:
            if(response.flag){
                // 保存成功
                // alert(response.message);
                alert(response.message);
                location.href="home-setting-address.html";
            }else{
                // 保存失败
                alert(response.message);
            }
        });
    }
    // 删除地址:
    $scope.setDefault = function(id){
        addressService.setDefault(id).success(function(response){
            // 判断保存是否成功:
            if(response.flag){
                // 保存成功
                // alert(response.message);
                alert(response.message);
                location.href="home-setting-address.html";
            }else{
                // 保存失败
                alert(response.message);
            }
        });
    }

    // 查询一个地址:
    $scope.findOneById = function(id){
        addressService.findOneById(id).success(function(response){
            $scope.entity = response;
        });
    }


});
