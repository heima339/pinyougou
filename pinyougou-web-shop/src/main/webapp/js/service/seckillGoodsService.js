//服务层
app.service('seckillGoodsService',function($http){

    //搜索
    this.search=function(page,rows,searchEntity){
        return $http.post('../seckillGoods/search.do?page='+page+"&rows="+rows, searchEntity);
    };

    //增加
    this.add=function(entity){
        return  $http.post('../seckillGoods/add.do',entity );
    }
});