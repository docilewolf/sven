
svenModule.controller('indexController', function($scope, $state, $timeout, profileService, commonService){
  profileService.list().then(function(res){
    $scope.list = res.list;
    //在DOM渲染完之后，启用timeline动画效果
    $timeout(function () {
      initTimeline();
    })
  });
  
  $scope.profileDetail = function (profile) {
    commonService.chooseNextStateForProfile(profile);
  };
});

svenModule.controller('accountController', function ($scope, $state) {
  $scope.account = 123123
});

svenModule.controller('categoryController', function ($rootScope, $scope, $state, $stateParams, profileService, commonService) {
  $scope.type = $stateParams.type;
  $scope.itemList = $scope.type == 1?$rootScope.essayCategoryList:$rootScope.pictureCategoryList;
  $scope.searchParams = function (entityId) {
    return {
      categoryId: entityId
    }
  };
  commonService.getProfileList($scope, $stateParams.id, $scope.itemList, profileService);
});

svenModule.controller('commentController', function ($scope, $state) {
});

svenModule.controller('essayController', function ($scope, $state, $stateParams, essayService, commonService) {
  essayService.getEassyById($stateParams.id).then(function (res) {
    $scope.entity = res.entity;
  })
});

svenModule.controller('newEssayController', function ($rootScope, $scope, $state, $modal, essayService, profileService, ProfileType) {

  $('.header').css("display", "none");

  $scope.text = {};
  $scope.newLink = function () {
    $modal.open({
      templateUrl: 'src/templates/partial/new-link.html',
      scope:false,
      controller: [
        '$scope',
          function(scope){
            scope.entity = {};
            scope.confirm = function () {
              scope.entity.linkAddress =
                  (scope.entity.linkAddress.indexOf('http://') >= 0
                  || scope.entity.linkAddress.indexOf('https://') >= 0)? scope.entity.linkAddress: 'http://'+scope.entity.linkAddress;
              var aTag = '<a href='+ scope.entity.linkAddress +'>' + scope.entity.linkName + '</a>';
              insertHtml(aTag, $rootScope.range);
              scope.$close();
            }
          }
      ]
    })
  };

  $scope.newPicture = function () {
    $modal.open({
      templateUrl: 'src/templates/partial/new-picture.html',
      controller: [
        '$scope',
        function(scope){
          scope.entity = {};
          scope.confirm = function () {
            if(!scope.entity.imgUrl){
              alert("请先选择图片");
              return;
            }
            var imgTag = "<br/><div class='image-package'><img src="+ scope.entity.imgUrl +" /><div class='image-caption'></div></div><p><br/></p>"
            insertHtml(imgTag, $rootScope.range);
            scope.$close();
          };
        }
      ]
    })
  };

  /*转换成html格式*/
  $scope.$watch('context', function (newValue, oldValue) {
  });

  $scope.save = function () {
    if(!$scope.title){
      alert("请输入标题");
      return;
    }
    profileService.newProfile(ProfileType.ESSAY, $scope.confirm)
  };

  $scope.confirm = function (profile) {
    var data = {
      name: $scope.title,
      description: profile.description,
      content: $scope.context,
      categoryId: profile.categoryId
    };
    console.log(data);
    return essayService.save(data);
  };
});

svenModule.controller('memberController', function ($scope, $state) {
});

svenModule.controller('pictureController', function ($scope, $state, $stateParams, pictureService, commonService) {
});

svenModule.controller('profileController', function ($scope, $state, $stateParams, profileService, commonService) {
  $scope.type = $stateParams.type;
  $scope.itemList = [{id:1, name:'文章'}, {id:2, name:"图片"}];
  $scope.searchParams = function (type) {
    return {
      type: type
    }
  };
  //注意要放到最低部
  commonService.getProfileList($scope, $stateParams.type, $scope.itemList, profileService);
});

