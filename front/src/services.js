
svenModule.factory('accountService', function ($http) {
});

svenModule.factory('categoryService', function ($http, project, $modal) {
    return {
        newCategory: function (obj, categoryList) {
            $modal.open({
                templateUrl: 'src/templates/partial/new-category.html',
                controller:[
                    '$scope',
                    function (scope) {
                        scope.entity = {};
                        scope.entity.type = obj.type;
                        scope.confirm = function () {
                            $http({
                                method: 'POST',
                                url: project.uri + '/admin/category/save',
                                data: scope.entity
                            }).then(function (res) {
                                categoryList.push(res.entity);
                                obj.categoryId = res.entity.id;
                                scope.$close();
                            })
                        }
                    }
                ]
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

svenModule.factory('pictureService', function ($http, project, $modal, $timeout) {
    return {
        getPicturesById: function(id){
            return $http({
                url: project.uri + '/admin/picture/getById',
                params: {
                    id: id
                }
            })
        },
        save: function (data) {
            return $http({
                url: project.uri + '/admin/picture/save',
                method: 'POST',
                data:data
            })
        },
        allPicture: function (profile) {
            console.log(profile);
            $modal.open({
                templateUrl: 'src/templates/picture.html',
                controller: [
                    '$scope',
                    function (scope) {
                        $http({
                            url: project.uri + '/admin/picture/getById',
                            params: {id:profile.pictureId}
                        }).then(function (res) {
                            scope.list= res.list;
                            scope.entity = angular.extend(res.entity,profile);
                            $timeout(function () {
                                pictureInit();
                            })
                        })
                    }
                ]
            })
        }
    }
});

svenModule.factory('profileService', function ($http, $modal, $rootScope, project, $state, categoryService) {
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
                        };
                        scope.newCategory = function () {
                            categoryService.newCategory(scope.entity, scope.categoryList);
                        };
                        scope.confirm = function () {
                            scope.processing = true;
                            callback(scope.entity).then(function (res) {
                                var result = res.entity;
                                scope.entity.type==1? scope.entity.essayId = result.id: scope.entity.pictureId = result.id;
                                $http({
                                    method: 'POST',
                                    url: project.uri+'/admin/profile/save',
                                    data: scope.entity
                                }).then(function (response) {
                                    scope.$close();
                                    scope.entity.type==1?$state.go('essay',{id:result.id}): $state.go('profile',{type:2});
                                }, function () {
                                })['finally'](function () {
                                    scope.processing = false;
                                });
                            }, function (rej) {
                                scope.processing = false;
                            });
                        }
                    }
                ]
            })
        },
        list: function(params){
            return $http({
                url: project.uri + '/admin/profile/list',
                params: params
            })
        },
        displayPicture: function (scope, params) {
            if(scope.type == 2) { //图片的时候做处理
                scope.list.forEach(function (item,index) {
                    item.index = 'box-index-'+index; //每个box绑定独立的事件
                    var num =   parseInt(Math.random()*9+1);
                    item.isFloat= num%2==0;
                });
                params.timeout(function () {
                    scope.list.forEach(function (item) {
                        $('.'+item.index).mouseover(function () {
                            $('.div-'+item.index).css('display', 'block');
                        });
                        $('.'+item.index).mouseout(function () {
                            $('.div-'+item.index).css('display', 'none');
                        });
                    })
                })
            }
        }
    }
});

svenModule.factory('commonService', function($q, $rootScope, $state, $timeout, pictureService){
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
        },
        getProfileList: function(scope, id, list, service, callback){
            scope.entityId = id;
            scope.itemList = list;
            scope.query = function (entityId) {
                if(scope.entityId != entityId) {
                    scope.entityId = entityId;
                    scope.init();
                }
                service.list(scope.params()).then(function (res) {
                    if(res.data) res.list = res.data.list;
                    if(res.list <= 0) scope.listEmpty = true;
                    else scope.listEmpty = false;
                    if(scope.list) scope.list = scope.list.concat(res.list);
                    else scope.list = res.list;
                    callback(scope, {timeout: $timeout});
                    scope.loading = false;
                })
            }

            scope.params = function () {
                return angular.extend({
                    page: scope.page,
                    size: scope.size
                }, scope.searchParams(scope.entityId));
            }
            
            scope.init = function () {
                scope.page = 1;
                scope.size = 10;
                scope.list = null;
            };

            scope.loadMore = function () {
                scope.loading = true;
                scope.page = scope.page?scope.page+1:1;
                scope.query(scope.entityId);
            }

            scope.init();
            scope.query(scope.entityId);

            scope.goNextSate = this.chooseNextStateForProfile;
        },
        chooseNextStateForProfile: function (profile) {
            profile.type == 1?$state.go("essay", {id:profile.essayId}):pictureService.allPicture(profile);
        }
    }
});
