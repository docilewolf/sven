
svenModule.factory('accountService', function ($http) {
});

svenModule.factory('categoryService', function ($http, project) {
    return {
        getContentByCategoryId: function(id){
            return $http({
                url: project.uri + '/admin/profile/getListByCategoryId',
                params: {
                    id:id
                }
            })
        }
    }
});

svenModule.factory('commentService', function($http) {
});

svenModule.factory('essayService', function ($http, project) {
    return {
        getEassyById: function(id){
            return $http({
                url: project.uri + '/admin/essay/getById',
                params: {
                    id: id
                }
            })
        },
        save: function (data) {
            return $http({
                method: 'POST',
                url: project.uri + '/admin/essay/save',
                data: data
            })
        }
    }
});

svenModule.factory('memberService', function ($http) {
});

svenModule.factory('pictureService', function ($http, project) {
    return {
        getPicturesById: function(id){
            return $http({
                url: project.uri + '/admin/picture/getById',
                params: {
                    id: id
                }
            })
        }
    }
});

svenModule.factory('profileService', function ($http, $modal, $rootScope, project, $state) {
    return{
        newProfile: function (profileType, callback) {
            $modal.open({
                templateUrl: 'src/templates/partial/new-profile.html',
                controller: [
                    '$scope',
                    function (scope) {
                        scope.entity = {};
                        scope.entity.type = profileType;
                        scope.categoryList = 1 == profileType?$rootScope.essayCategoryList:$rootScope.pictureCategoryList;
                        scope.choose = function (item) {
                            scope.entity.categoryId = item.id;
                        }
                        scope.confirm = function () {
                            callback(scope.entity).then(function (res) {
                                var result = res.entity;
                                scope.entity.type==1? scope.entity.essayId = result.id: scope.entity.pictureId = result.id;
                                $http({
                                    method: 'POST',
                                    url: project.uri+'/admin/profile/save',
                                    data: scope.entity
                                }).then(function (response) {
                                    scope.$close();
                                    scope.entity.type==1?$state.go('essay',{id:result.id}): $state.go('picture',{id:result.id});
                                });
                            }, function (rej) {
                                
                            });
                        }
                    }
                ]
            })
        }
    }
});

svenModule.factory('commonService', function($q, $rootScope){
    return {
        //加载外部js文件
        getScript: function(url){
            var deferred = $q.defer();
            $.getScript(url, function () {
                deferred.resolve();
            });
            return deferred.promise;
        },
        getCategoryById: function(id){
            var essayCategoryList = $rootScope.essayCategoryList;
            var pictureCategoryList = $rootScope.pictureCategoryList;
            var list = essayCategoryList.concat(pictureCategoryList);
            for(var i=0; i<list.length; i++){
                var item = list[i];
                if(item.id == id){
                    return item;
                }
            }
        }
    }
})