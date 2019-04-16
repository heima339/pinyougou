//服务层
app.service('collectService',function($http){

    //loadItemList
    this.loadItemList=function(){
        return $http.get('../myOrder/loadItemList.do');
    }
	
});