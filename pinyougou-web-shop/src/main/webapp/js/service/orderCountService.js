//服务层
app.service('orderCountService',function($http){

    //搜索
    this.search=function(page,rows,searchEntity){
        return $http.post('../orderCount/search.do?page='+page+"&rows="+rows, searchEntity);
    };


});