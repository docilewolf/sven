
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

svenModule.controller('categoryController', function ($rootScope, $scope, $state, $stateParams, profileService, commonService, pictureService) {
  $scope.type = $stateParams.type;
  $scope.itemList = $scope.type == 1?$rootScope.essayCategoryList:$rootScope.pictureCategoryList;
  $scope.searchParams = function (entityId) {
    return {
      categoryId: entityId
    }
  };
  commonService.getProfileList($scope, $stateParams.id, $scope.itemList, profileService, profileService.displayPicture);
  /*$scope.list = [
    {name:'z3', imgUrl:"http://www.jq22.com/demo/jQuery-pbl20160309/images/23.jpg", description:"it's good!"},
    {name:'放假啊快乐健康老年的年检阿卡房价阿里开了分离卡', imgUrl:"http://www.jq22.com/demo/jQuery-pbl20160309/images/24.jpg", description:"西安大大"},
    {name:'放假啊快乐健康老年的年检阿卡房价阿里开了分离卡放假啊快乐健康老年的年检阿卡房价阿里开了分离卡', imgUrl:"http://7xpc60.com1.z0.glb.clouddn.com/sven-1ed8cb7b-3b18-414c-af11-6bcb80a47604.jpg", description:"放假啊快乐健康老年的年检阿卡房价阿里开了分离卡"},
    {name:'l4合格你放假', imgUrl:"http://www.jq22.com/demo/jQuery-pbl20160309/images/25.jpg", description:"放假啊快乐健康老年的年检阿卡房价阿里开了分离卡放假啊快乐健康老年的年检阿卡房价阿里开了分离卡"},
    {name:'${}Fafafafa接口卡手机看', imgUrl:"http://www.jq22.com/demo/jQuery-pbl20160309/images/22.jpg", description:"放假啊快乐健康老年的年检阿卡房价阿里开了分离卡dsakjfjkdajkfda"},
    {name:'fklaklkl fldakflak到拉萨', imgUrl:"http://7xpc60.com1.z0.glb.clouddn.com/sven-2086494c-4cd3-40e8-9f7f-b2046111a76b.png", description:"放假啊快乐健康老年的年检阿卡房价阿里开了分离卡放假快大家看来发掘了"}
  ];
  $scope.list.forEach(function (item,index) {
    item.index = 'box-index-'+index;
    var num =   parseInt(Math.random()*9+1);
    item.isFloat= num%2==0;
  });
  if($scope.type == 2) {
    $timeout(function () {
      $scope.list.forEach(function (item) {
        $('.'+item.index).mouseover(function () {
          $('.div-'+item.index).css('display', 'block');
        });
        $('.'+item.index).mouseout(function () {
          $('.div-'+item.index).css('display', 'none');
        });
      })
    })
  }*/

});

svenModule.controller('commentController', function ($scope, $state) {
});

svenModule.controller('essayController', function ($scope, $state, $stateParams, essayService, commentService) {
  $scope.entity = {};
  essayService.getEassyById($stateParams.id).then(function (res) {
    $scope.entity = angular.extend($scope.entity, res.entity);
    if($scope.entity.isMarkdown){
      $state.go('markdownView', {id: $scope.entity.id});
    }
  });
  var params = {
    essayId: $stateParams.id
  };
  commentService.list(params).then(function (response) {
    $scope.entity.list = response.list;
    commentService.commentUserInfo($scope.entity.list);
  });
  
  $scope.displayComment = function (id) {
    $("#item"+id).css("display", "block");
  }
});

svenModule.controller('newEssayController', function ($rootScope, $scope, $state, $stateParams, $modal, essayService, profileService, ProfileType) {
  if(!$rootScope.auth){
    $state.go('profile',{type:1});
    return;
  }

  //页面初始化
  $('.header').css("display", "none");

  $scope.data = {};
  if($stateParams.id){
    $scope.isEdit = true;
    essayService.getEassyById($stateParams.id).then(function (res) {
      $scope.data = res.entity;
    })
  }else{
    initEditPage();
  }


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
              var aTag = '<a href='+ scope.entity.linkAddress +' target="_blank">' + scope.entity.linkName + '</a>';
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
  /*$scope.$watch('context', function (newValue, oldValue) {
  });*/

  $scope.save = function () {
    essayService.saveOperation($scope);
  };
});

svenModule.controller('markdownEditController', function ($rootScope, $scope, $state, $stateParams, commonService, $timeout, profileService, ProfileType, essayService) {
  if(!$rootScope.auth){
    $state.go('profile',{type:1});
    return;
  }
  //页面初始化
  $('.header').css("display", "none");

  $scope.data = {};
  $scope.data.isMarkdown = 1;//需要新增加此字段
  if($stateParams.id){
    $scope.isEdit = true;
    essayService.getEassyById($stateParams.id).then(function (res) {
      $scope.data = res.entity;
      initTextAreaHeight()
    })
  }else{
    $scope.data.name=commonService.name;
    $scope.data.content=commonService.content;
    initTextAreaHeight()
  }

  function initTextAreaHeight() {
    $timeout(function () {
      flexContent();
    });
  }

  $scope.preview = function () {
    commonService.name = $scope.data.name;
    commonService.content = $scope.data.content;
    $state.go('markdownView');
  };

  $scope.save = function () {
    essayService.saveOperation($scope, function () {
      commonService.name = null;
      commonService.content = null;
    })
  }
});

svenModule.controller('markdownViewController', function ($scope, commonService, essayService, $stateParams, $timeout) {
  $scope.entity = {};
  if($stateParams.id){
    essayService.getEassyById($stateParams.id).then(function (res) {
      $scope.entity = res.entity;
      $scope.entity.content = marked($scope.entity.content);
    })
  }else{
    $scope.entity.name = commonService.name;
    $scope.entity.content = marked(commonService.content);
  }
  /*$timeout(function () {
    $('img').parents('p').css('text-align', 'center');
  })*/
});

svenModule.controller('memberController', function ($scope, $state) {
});

svenModule.controller('pictureController', function ($scope, $state, $stateParams, pictureService, commonService) {
});

svenModule.controller('profileController', function ($scope, $state, $stateParams, profileService, commonService, pictureService) {
  $scope.type = $stateParams.type;
  $scope.itemList = [{id:1, name:'文章'}, {id:2, name:"图片"}];
  $scope.searchParams = function (type) {
    $scope.type = type;
    return {
      type: type
    }
  };
  //注意要放到最低部
  commonService.getProfileList($scope, $stateParams.type, $scope.itemList, profileService, profileService.displayPicture);
});

svenModule.controller('newPictureController', function ($rootScope, $scope, $state, $stateParams, pictureService, profileService, ProfileType) {
  if(!$rootScope.auth){
    $state.go('profile',{type:2});
    return;
  }

  $scope.entity ={};

  if($stateParams.id){
    $scope.isEdit = true;
    pictureService.getPicturesById($stateParams.id).then(function (res) {
      $scope.entity = res.entity;
      $scope.list = [];
      res.list.forEach(function (item) {
        if($scope.list.indexOf(item.url) < 0)
          $scope.list.push(item.url);
      });
    })
  }

  $scope.delPic = function (item) {
    var index = $scope.list.indexOf(item);
    if(index >= 0) $scope.list.splice(index, 1);
  };
  $scope.save = function () {
    if(!$scope.list || $scope.list.length<=0){
      alert("请上传图片");
      return;
    }
    profileService.newProfile(ProfileType.PICTURE, $scope.confirm, $scope.isEdit, $scope.entity);
  };
  $scope.confirm = function (profile) {
    var data = {
      pictureUrlList: $scope.list,
      categoryId: profile.categoryId
    };
    if($scope.isEdit) data.id=$scope.entity.id;
    return $scope.isEdit?pictureService.update(data):pictureService.save(data);
  }
});

svenModule.controller('loginController', function ($rootScope, $scope, $state, accountService, $location) {
  if($rootScope.auth){
    $state.go('welcome');
    return;
  }
  //记录 referrer
  $rootScope.oldHref = location.href == $location.href?"": location.href;
  $('.header').css("display", "none");
  $scope.entity = {};
  $rootScope.isLogin = true;
  $scope.isSign = $rootScope.isSign;
  if($scope.isSign == undefined || $scope.isSign == null) $scope.isSign = true;
  $scope.confirm = function () {
    $scope.isSign?
      accountService.login($scope.entity).then(function (res) {
        $rootScope.$emit("login");
      }, function (rej) {
        $scope.errorMessage = rej.message || "服务器异常";
        hiddenNotyContainer($scope.errorMessage);
      }):
      accountService.register($scope.entity).then(function (res) {
        $rootScope.$emit("login");
      }, function (rej) {
        $scope.errorMessage = rej.message || "服务器异常";
        hiddenNotyContainer($scope.errorMessage);
      })
  }
});