
svenModule.factory('accountService', function ($http, project) {
    return{
        login: function (data) {
            return $http({
                url: project.uri + '/admin/account/login',
                method: 'POST',
                data: data
            })
        },
        logout: function () {
            return $http({
                url: project.uri + '/admin/account/logout',
                method: 'POST'
            })
        },
        register: function (data) {
            return $http({
                url: project.uri + '/admin/account/register',
                method: 'POST',
                data: data
            })
        },
        getInfoByIds: function (ids) {
            return $http({
                url: project.uri + '/admin/account/getByIds',
                params:{
                    ids: ids
                }
            })
        }
    }
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

svenModule.factory('commentService', function($http, project, accountService) {
    return{
        list: function (params) {
            return $http({
                url: project.uri + '/admin/comment/list',
                params: params
            })
        },
        save: function (data) {
            return $http({
                method: 'POST',
                url: project.uri + '/admin/comment/save',
                data: data
            })
        },
        commentUserInfo: function (list) {
            var accountList = [];
            var accountIdList = [];
            list.forEach(function (item) {
                if(accountIdList.indexOf(item.fromAccountId) < 0) accountIdList.push(item.fromAccountId);
            });
            accountService.getInfoByIds(accountIdList.join(",")).then(function (res) {
                res.list.forEach(function (item) {
                    var index = accountIdList.indexOf(item.id);
                    if(index >= 0) accountList.splice(index, 0, item);
                });
                list.forEach(function (item) {
                    var toIndex = accountIdList.indexOf(item.toAccountId);
                    if(toIndex >= 0 && item.type==30){
                        item.content = "<span style='color:blue'>@" + accountList[toIndex].userName + "</span> " + item.content;
                    }
                    var fromIndex = accountIdList.indexOf(item.fromAccountId);

                    fromIndex >= 0?
                        item.userName = accountList[fromIndex].userName: "匿名用户";
                })
            });
        }
    }
});

svenModule.factory('essayService', function ($http, project, ProfileType, profileService) {
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
        },
        update: function (data) {
            return $http({
                method: 'POST',
                url: project.uri + '/admin/essay/update',
                data: data
            })
        },
        saveOperation: function (scope, otherConfig) {
            if(!scope.data.name){
                alert("请输入标题");
                return;
            }
            if(otherConfig) {
                otherConfig();
            }

            var saveFn = this.save;
            var updateFn = this.update;

            function confirm(profile){
                scope.data.description = profile.description;
                scope.data.categoryId = profile.categoryId;
                return scope.isEdit?updateFn(scope.data):saveFn(scope.data);
            }

            profileService.newProfile(ProfileType.ESSAY, confirm, scope.isEdit, scope.data)
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
        update: function (data) {
            return $http({
                url: project.uri + '/admin/picture/update',
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
                            scope.list= res.entity.pictureUrlList;
                            scope.entity = angular.extend(res.entity.picture,profile);
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
        newProfile: function (profileType, callback, isEdit, originData) {
            $modal.open({
                templateUrl: 'src/templates/partial/new-profile.html',
                controller: [
                    '$scope',
                    function (scope) {
                        scope.entity = {};
                        scope.entity.type = profileType;
                        if(isEdit){
                            var params = {
                                type: profileType
                            };
                            profileType == 1?params.essayId=originData.id:params.pictureId=originData.id;
                            $http({
                                url: project.uri+'/admin/profile/getByParam',
                                params:params
                            }).then(function (res) {
                                scope.entity = res.entity;
                            })
                        }
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
                                var result;
                                isEdit?result=originData:result = res.entity;
                                scope.entity.type==1? scope.entity.essayId = result.id: scope.entity.pictureId = result.id;
                                var url = isEdit?'/admin/profile/update':'/admin/profile/save';
                                $http({
                                    method: 'POST',
                                    url: project.uri+url,
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
