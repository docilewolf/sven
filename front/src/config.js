// define module
var svenModule = angular.module('svenModule', ['ui.router', 'ui.bootstrap', 'angularFileUpload']);

//JSON数据序列化
var serialize = function (obj) {
    var query = '';
    var name, value, fullSubName, subName, subValue, innerObj, i;

    for (name in obj) {
        if (obj.hasOwnProperty(name)) {
            value = obj[name];

            if (value instanceof Array) {
                for (i = 0; i < value.length; ++i) {
                    subValue = value[i];
                    fullSubName = name;
                    innerObj = {};
                    innerObj[fullSubName] = subValue;
                    query += serialize(innerObj) + '&';
                }
            }
            else if (value instanceof Object) {
                for (subName in value) {
                    if (value.hasOwnProperty(subName)) {
                        subValue = value[subName];
                        fullSubName = name + '[' + subName + ']';
                        innerObj = {};
                        innerObj[fullSubName] = subValue;
                        query += serialize(innerObj) + '&';
                    }
                }
            }
            else if (value !== undefined && value !== null) {
                query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
            }
        }
    }

    return query.length ? query.substr(0, query.length - 1) : query;
};

//request， response拦截器
svenModule.config(function ($httpProvider) {
    // POST method use x-www-form-urlencoded Content-Type
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';

    // Override transformRequest to serialize form data like jquery
    $httpProvider.defaults.transformRequest = [

        function (data) {
            return angular.isObject(data) && String(data) !== '[object File]' ? serialize(data) : data;
        }
    ];

    // Add interceptor
    $httpProvider.interceptors.push(function ($q, $injector, $rootScope) {
        return {
            request: function (config) {
                // REST 风格路由重写
                var rules = config.url.match(/:(\w+)/g);
                if (rules !== null) {
                    angular.forEach(rules, function (rule) {
                        var name = rule.substring(1);
                        if (config.params && config.params.hasOwnProperty(name)) {
                            config.url = config.url.replace(rule, config.params[name]);
                            delete config.params[name];
                        }
                        else if (config.data && config.data.hasOwnProperty(name)) {
                            config.url = config.url.replace(rule, config.data[name]);
                            delete config.data[name];
                        }
                    });
                }
                return $q.when(config);
            },
            response: function (response) {
                if (angular.isObject(response.data)) {
                    var res = response.data;
                    // 兼容旧数据格式 {code:0, message: '', data: {...}} --> {code:200, data: {message: '', ...}}
                    res.data = res.data || {};
                    res.data.message = res.data.message || res.message;

                    // 约定的未登录状态码
                    if (res.code === 401) {
                        $rootScope.$user = null;
                        $rootScope.$emit('loginRequired');
                        return $q.reject({
                            message: '会话过期，请重新登录！'
                        });
                    }
                    // 约定的没权限
                    if (res.code === 403) {
                        $injector.get('$state').go('noauth');
                        return $q.reject({
                            message: '你无权进行相关操作'
                        });
                    }

                    /*if(res.code != 200){
                        $injector.get('growl').addErrorMessage("保存失败");
                        return $q.reject({
                            message: '保存失败'
                        });
                    }*/

                    // 默认自动拆包
                    if (response.config.autoparse !== false) {
                        return [0, 200].indexOf(res.code) !== -1 ? $q.when(res.data) : $q.reject(res.data);
                    }

                    return $q.when(response.data);
                }
                return $q.when(response);
            },
            requestError: function (rejection) {
                return $q.reject(rejection);
            },
            responseError: function (rejection) {
                return $q.reject(rejection);
            }
        };
    });
});



//项目部署名称
svenModule.constant('project', {name: 'svenbg', uri: '/svenbg'});
svenModule.constant('ProfileType', {ESSAY:1, PICTURE:2});
svenModule.constant('defaultProfileImage', 'http://7xpc60.com1.z0.glb.clouddn.com/sven-76424657-4059-4fff-a157-5ebb7430f257.png');

//底部导航栏内容
svenModule.run(function($rootScope, accountService, $state){
    $rootScope.aboutme = [
        {
            name: "关于我们",
            url: "http://www.baidu.com"
        },
        {
            name: "联系我",
            url: "http://sports.sina.com.cn"
        }
    ];

    $rootScope.logout = function () {
        accountService.logout().then(function () {
            $rootScope.$emit("logout");
        })
    }
});

//认证：
svenModule.run(function($rootScope, project, $http, $state, $window){
    $rootScope.$on('noauth', function(){
        $state.go('welcome');
    });
    $rootScope.$on("logout", function () {
            $rootScope.auth = false;
            $rootScope.account = null;
            $rootScope.member = null;
    });
    $rootScope.$on("login", function () {
        $rootScope.isLogin = false;
        $rootScope.auth = true;
        $rootScope.oldHref?$window.location.href = $rootScope.oldHref:
            $state.go('welcome');
    });
});

//获取用户信息
/*svenModule.run(function ($rootScope, $http, project) {
    $http({
        url: project.uri + '/admin/account/getUserInfo'
    }).then(function (res) {
        $rootScope.auth = true;
        $rootScope.account = res.entity.account;
        $rootScope.member = res.entity.member;
    }, function (rej) {
        $rootScope.auth = false;
        $rootScope.account = null;
        $rootScope.member = null;
    })
})*/

//页面跳转
svenModule.run(function ($rootScope, $http, project) {
    $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){
        if($('.header').css('display') == 'none') $('.header').css('display', 'block');
        if(!$('.header-essay').hasClass('hide')) $('.header-essay').addClass('hide');
        if(!$('.header-picture').hasClass('hide')) $('.header-picture').addClass('hide');
        //获取用户信息
        $http({
            url: project.uri + '/admin/account/getUserInfo'
        }).then(function (res) {
            $rootScope.auth = true;
            $rootScope.account = res.entity.account;
            $rootScope.member = res.entity.member;
        }, function (rej) {
            $rootScope.auth = false;
            $rootScope.account = null;
            $rootScope.member = null;
        })
    })
})

//分类
svenModule.run(function($rootScope, $http, project){
    $http({
        url: project.uri + '/admin/category/list'
    }).then(function(res){
        $rootScope.essayCategoryList = [];
        $rootScope.pictureCategoryList = [];
        angular.forEach(res.list, function (item) {
            item.type === 1?$rootScope.essayCategoryList.push(item):$rootScope.pictureCategoryList.push(item);
        });
    }, function(rej){
        console.log("error")
    });
});