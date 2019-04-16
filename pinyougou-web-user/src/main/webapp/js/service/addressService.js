// 定义服务层:
app.service("addressService",function($http){
	this.findListByLoginUser = function(){
		return $http.get("../address/findListByLoginUser.do");
	}

	//新增地址
	this.add=function(entity){
        return $http.post('../address/addAddress.do?',entity);
    }

    //修改地址
    this.update=function(entity){
        return $http.post('../address/update.do?',entity);
    }
    //删除地址
    this.dele = function(id){
        return $http.get("../address/delete.do?id="+id);
    }
	//设为默认地址
    this.setDefault = function(id){
        return $http.get("../address/setDefault.do?id="+id);
    }

    this.findOneById=function(id){
        return $http.get("../address/findOneById.do?id="+id);
    }

});